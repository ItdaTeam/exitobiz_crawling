package co.kr.exitobiz.Crawling;

import co.kr.exitobiz.Mappers.Api.CrawlingMapper;
import co.kr.exitobiz.Vo.Cms.SupportVo;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SeoulSbdcCrawling implements Crawling {

    @Autowired
    CrawlingMapper crawlingMapper;

    @Autowired
    Environment environment;

    /*
     * 서울시 자영업지원센터
     * https://www.seoulsbdc.or.kr/
     * 게시글 페이지가 POST로 되어 있어서 자바스크립트 함수 실행 처리를 했음
     *  */

    private String url = "https://www.seoulsbdc.or.kr/cs/businessSearch.do";
    private int page = 1;

    @Override
    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public void craw() throws InterruptedException {

        String driverPath = environment.getProperty("chrome.driver.path");
        File driverFile = new File(String.valueOf(driverPath));

        String driverFilePath = driverFile.getAbsolutePath();
        if (!driverFile.exists() && driverFile.isFile()) {
            throw new RuntimeException("Not found");
        }

        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
        options.addArguments("no-sandbox");
        options.addArguments("disable-dev-shm-usage");

        ChromeDriverService service = new ChromeDriverService.Builder()
                .usingDriverExecutable(driverFile)
                //.usingPort(5000)
                .usingAnyFreePort()
                .build();

        try {
            service.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        WebDriver driver = new ChromeDriver(service,options);
        WebDriverWait wait = new WebDriverWait(driver, 10);

        JavascriptExecutor jse = (JavascriptExecutor) driver;

        SupportVo supportVo = new SupportVo();
        supportVo.setTitle("서울시자영업지원센터");
        supportVo.setUrl("https://www.seoulsbdc.or.kr/");
        supportVo.setLocCode("C02");
        supportVo.setActiveYn("Y");
        supportVo.setErrorYn("N");

        List<SupportVo> supportVos = new ArrayList<>();

        driver.get(url);
        Thread.sleep(1500);

        for (int i=page; i>0; i--) {

            WebElement pageXpath = driver.findElement(By.xpath("//*[@id='container']/div/div/nav/ul/li["+ i +"]/a"));
            pageXpath.click();
            Thread.sleep(1500);

            for(int j=4; j<14; j++) {

                try {

//                    WebElement titleXpath = driver.findElement(By.xpath("//*[@id=\"container\"]/div/div/div["+j+"]/div[2]/a/text"));
                    String baseUrl = "https://www.seoulsbdc.or.kr/cs/supportBusinessDetail.do?mseq=";
                    WebElement bodyUrlXpath = driver.findElement(By.xpath("//*[@id=\"container\"]/div/div/div["+j+"]/div[2]/a"));
                    String onclickFn = bodyUrlXpath.getAttribute("onclick");

                    Pattern p = Pattern.compile("'(.*?)'");
                    Matcher m = p.matcher(onclickFn);
                    ArrayList<String> pattern = new ArrayList<String>();

                    while (m.find()) {
                        pattern.add(m.group());
                    }

                    String mseq = pattern.get(0).replaceAll("'", "");
                    String url = baseUrl + mseq;

                    String targettype = "";

                    WebElement endTimeXpath = driver.findElement(By.xpath("//*[@id=\"container\"]/div/div/div["+j+"]/div[3]"));

                    String title = bodyUrlXpath.getText().replaceAll("진행  ","");
                    String endTime = endTimeXpath.getText();

                    SupportVo vo = new SupportVo();
                    vo.setTargetName("서울시자영업지원센터");
                    vo.setTargetCatName("-");
                    vo.setLocCode("C02");
                    vo.setSiTitle(title);
                    vo.setMobileUrl(url);
                    vo.setPcUrl("-");


                    HashMap<String, String> params = new HashMap<>();
                    params.put("bodyurl", url);
                    boolean isUrl = crawlingMapper.isUrl(params);
                    if (!isUrl) {
                        supportVos.add(vo);
                    }


                } catch (Exception e) {
                    supportVo.setErrorYn("Y");
                    e.printStackTrace();
                    crawlingMapper.createMaster(supportVo);
                    System.out.println(e.getMessage());
                }

            }

        }

        /* 빈 리스트가 아니면 크레이트 */
        if (!supportVos.isEmpty()) {
            try{
                crawlingMapper.create(supportVos);
                crawlingMapper.createMaster(supportVo);
            }catch (Exception e){
                supportVo.setErrorYn("Y");
                e.printStackTrace();
                crawlingMapper.createMaster(supportVo);
            }
        }else {
            supportVo.setErrorYn("N");
            crawlingMapper.createMaster(supportVo);
        }

        driver.close();
        driver.quit();
        service.stop();
    }


}

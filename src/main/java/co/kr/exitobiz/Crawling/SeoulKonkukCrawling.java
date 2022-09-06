package co.kr.exitobiz.Crawling;

import co.kr.exitobiz.Mappers.Api.CrawlingMapper;
import co.kr.exitobiz.Vo.Cms.SupportVo;
import org.openqa.selenium.By;
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
public class SeoulKonkukCrawling implements Crawling {

    @Autowired
    CrawlingMapper crawlingMapper;

    @Autowired
    Environment environment;

    /*
     * 건국대학교 창업지원단
     * https://kkubi.konkuk.ac.kr/
     *  */

    private String url = "https://startup.konkuk.ac.kr/BoardList.do?menuSeq=43850&configSeq=51126&categorySeq=&searchBy=all&searchValue=&sortY=&curPage=10&pageNum=";
    private int page = 1;

    @Override
    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public void craw() throws InterruptedException {

        String driverPath = environment.getProperty("chrome.driver.path");
        File driverFile = new File(String.valueOf(driverPath));

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

        List<SupportVo> supportVos = new ArrayList<>();
        SupportVo supportVo = new SupportVo();
        supportVo.setTitle("건국대학교 창업지원단");
        supportVo.setUrl("https://kkubi.konkuk.ac.kr");
        supportVo.setLocCode("C02");
        supportVo.setActiveYn("Y");
        supportVo.setErrorYn("N");

        for (int i=page; i>=0; i--) {

            driver.get(url + i);

            Thread.sleep(1000);

            List <WebElement> list = driver.findElements(By.xpath("//*[@id=\"container\"]/section/div[2]/div[2]/div/div[2]/table/tbody[2]/tr"));

            // 공고가 가변적이기 때문에 리스트 사이즈 구해서 for문 돌리기
            int k = list.size();

            for(int j=1; j<k+1; j++) {

                try {
                    WebElement titleXpath = driver.findElement(By.xpath("//*[@id=\"container\"]/section/div[2]/div[2]/div/div[2]/table/tbody[2]/tr["+ j +"]/td[2]/div/div/dl/dt/a"));

                    Pattern typePattern = Pattern.compile("\\[(.*?)\\]"); // 대괄호안에 문자 뽑기
                    Matcher typeMatcher = typePattern.matcher(titleXpath.getText());
                    ArrayList<String> typePatternArray = new ArrayList<String>();

                    while (typeMatcher.find()) {
                        typePatternArray.add(typeMatcher.group());
                    }

                    String title = titleXpath.getText();
                    String url = titleXpath.getAttribute("data-itsp-view-link");
                    String bodyurl = "https://startup.konkuk.ac.kr/BoardView.do?menuSeq=43850&configSeq=51126&seq=" + url;

                    SupportVo vo = new SupportVo();
                    vo.setTargetName("건국대학교 창업지원단");
                    vo.setTargetCatName("-");
                    vo.setLocCode("C02");
                    vo.setSiTitle(title);
                    vo.setMobileUrl(bodyurl);
                    vo.setPcUrl("-");


                    HashMap<String, String> params = new HashMap<>();
                    params.put("bodyurl", url);
                    boolean isUrl = crawlingMapper.isUrl(params);
                    if (!isUrl) {
                        supportVos.add(vo);
                    }

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    supportVo.setErrorYn("Y");
                }
            }

            Thread.sleep(500);
        }

        /* 빈 리스트가 아니면 크레이트 */
        if (!supportVos.isEmpty()) {
            try{
                crawlingMapper.create(supportVos);
                crawlingMapper.createMaster(supportVo);
            }catch (Exception e){
                supportVo.setErrorYn("Y");
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

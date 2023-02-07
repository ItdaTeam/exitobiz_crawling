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

@Component
public class SeoulArteCrawling implements Crawling {

    @Autowired
    CrawlingMapper crawlingMapper;

    @Autowired
    Environment environment;

    /*
     * 한국문화예술교육진흥원
     * https://www.arte.or.kr/
     *  */

    private String url = "https://www.arte.or.kr/notice/business/notice/Business_BoardList.do";
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
        options.addArguments("--headless", "--disable-gpu","--no-sandbox");
        options.addArguments("window-size=1920x1080");
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.36");
        options.addArguments("lang=ko_KR");

        ChromeDriverService service = null;
        WebDriver driver = null;

        SupportVo supportVo = new SupportVo();
        supportVo.setTitle("한국문화예술교육진흥원");
        supportVo.setUrl("https://www.arte.or.kr");
        supportVo.setLocCode("C02");
        supportVo.setActiveYn("Y");
        supportVo.setErrorYn("N");

        try {
            service = new ChromeDriverService.Builder()
                .usingDriverExecutable(driverFile)
                .usingAnyFreePort()
                //.usingPort(5000)
                .build();
            service.start();
            driver = new ChromeDriver(service,options);

            WebDriverWait wait = new WebDriverWait(driver, 10);
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            List<SupportVo> supportVos = new ArrayList<>();

            driver.get(url);
            Thread.sleep(1500);

            for (int i=page; i>0; i--) {

                Thread.sleep(1500);

                for(int j=1; j<16; j++) {

                    try {

                        WebElement titleXpath = driver.findElement(By.xpath("//*[@id=\"frm\"]/div/div[3]/table/tbody/tr["+j+"]/td[2]/a"));

                        String baseUrl = "https://www.arte.or.kr/notice/business/notice/Business_BoardView.do?board_id=";

                        /* 숫자만 남기기 */
                        String url = titleXpath.getAttribute("href");
                        String intStr = url.replaceAll("javascript:fnView", "").replaceAll("\\(","").replaceAll("\\)","").replaceAll("'","").replaceAll(";","");

                        String bodyUrl = baseUrl + intStr;

                        String title = titleXpath.getText();
                        String targettype = "사업공고";

                        SupportVo vo = new SupportVo();
                        vo.setTargetName("한국문화예술교육진흥원");
                        vo.setTargetCatName("-");
                        vo.setLocCode("C02");
                        vo.setSiTitle(title);
                        vo.setMobileUrl(bodyUrl);
                        vo.setPcUrl("-");

                        HashMap<String, String> params = new HashMap<>();
//                        params.put("bodyurl", bodyUrl);
                        params.put("title",title);
                        boolean isUrl = crawlingMapper.isUrl(params);
                        if (!isUrl) {
                            supportVos.add(vo);
                        }


                    } catch (Exception e) {
                        supportVo.setErrorYn("Y");
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
                    crawlingMapper.createMaster(supportVo);
                }
            }else {
                supportVo.setErrorYn("N");
                crawlingMapper.createMaster(supportVo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(driver != null){
                driver.close();
                driver.quit();
            }else{
                supportVo.setErrorYn("Y");
                crawlingMapper.createMaster(supportVo);
            }
            if(service != null){
                service.stop();
            }else{
                supportVo.setErrorYn("Y");
                crawlingMapper.createMaster(supportVo);
            }
        }
    }
}

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
public class KStartUpCrawling implements Crawling {

    @Autowired
    CrawlingMapper crawlingMapper;

    @Autowired
    Environment environment;

    /*
    *
    *
    * K-StartUp
    * */

    private String url = "https://www.k-startup.go.kr/web/contents/bizpbanc-ongoing.do";
    private int page = 5;

    @Override
    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public void craw() {
        String driverPath = environment.getProperty("chrome.driver.path");
        File driverFile = new File(String.valueOf(driverPath));

        if (!driverFile.exists() && driverFile.isFile()) {
            throw new RuntimeException("Not found");
        }

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--headless", "--disable-gpu","--no-sandbox");
        options.addArguments("window-size=1920x1080");
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.36");
        options.addArguments("lang=ko_KR");

        ChromeDriverService service = null;
        WebDriver driver = null;
        JavascriptExecutor jse = null;

        SupportVo supportVo = new SupportVo();
        supportVo.setTitle("K-Startup");
        supportVo.setUrl("https://www.k-startup.go.kr/");
        supportVo.setLocCode("C82");
        supportVo.setActiveYn("Y");
        supportVo.setErrorYn("N");



        try {
            service = new ChromeDriverService.Builder()
                    .usingDriverExecutable(driverFile)
                    //.usingPort(5000)
                    .usingAnyFreePort()
                    .build();
            service.start();
            driver = new ChromeDriver(service,options);
            jse = (JavascriptExecutor) driver;


            List<SupportVo> supportVos = new ArrayList<>();
            try {
                //중앙부처 , 지자체, 공공기관
                for (int i=1; i<5 ; i++) {

                    driver.get(url);

                    WebElement pageXpath = driver.findElement(By.xpath("//*[@id='bizPbancList']/div/a["+ i +"]"));

                    //페이지이동
//                    jse.executeScript("arguments[0].click()", pageXpath);
                    jse.executeScript("fn_egov_link_page("+i+");");

                    Thread.sleep(1000);

                    for(int j=1; j<16; j++) {

                        WebElement titleXpath = driver.findElement(By.xpath("//*[@id='bizPbancList']/ul/li["+ j +"]/div/div[1]/div[2]/a/div/p"));
                        WebElement urlXpath = driver.findElement(By.xpath("//*[@id='bizPbancList']/ul/li["+ j +"]/div/div[1]/div[2]/a"));

                        String title = titleXpath.getText();
                        String url = urlXpath.getAttribute("href");
                        String index = url.replace("javascript:go_view","").replace("(","").replace(")","").replace(";","");

                        String bodyUrl = "https://www.k-startup.go.kr/web/contents/bizpbanc-ongoing.do?schM=view&pbancSn=" + index;

                        SupportVo vo = new SupportVo();
                        vo.setTargetName("K-Startup");
                        vo.setTargetCatName("-");
                        vo.setLocCode("C82");
                        vo.setSiTitle(title);
                        vo.setMobileUrl(bodyUrl);
                        vo.setPcUrl("-");

                        HashMap<String, String> params = new HashMap<>();
                        params.put("bodyurl", bodyUrl);
                        params.put("title",title);
                        boolean isUrl = crawlingMapper.isUrl(params);
                        if (!isUrl) {
                            supportVos.add(vo);
                        }

                    }

                    Thread.sleep(500);
                }


                //민간, 지자체
                WebElement btnXpath = driver.findElement(By.xpath("//*[@id=\"pbancClss_PBC020\"]/a"));

                jse.executeScript("arguments[0].click()",btnXpath);

                for (int i=1; i<5 ; i++) {

//                    driver.get(url);

                    WebElement pageXpath = driver.findElement(By.xpath("//*[@id='bizPbancList']/div/a["+ i +"]"));
                    jse.executeScript("arguments[0].click()", pageXpath);

                    Thread.sleep(1000);

                    for(int j=1; j<16; j++) {

                        WebElement titleXpath = driver.findElement(By.xpath("//*[@id='bizPbancList']/ul/li["+ j +"]/div/div[1]/div[2]/a/div/p"));
                        WebElement urlXpath = driver.findElement(By.xpath("//*[@id='bizPbancList']/ul/li["+ j +"]/div/div[1]/div[2]/a"));

                        String title = titleXpath.getText();
                        String url = urlXpath.getAttribute("href");
                        String index = url.replace("javascript:go_view","").replace("(","").replace(")","").replace(";","");

                        String bodyUrl = "https://www.k-startup.go.kr/web/contents/bizpbanc-ongoing.do?schM=view&pbancSn=" + index;

                        SupportVo vo = new SupportVo();
                        vo.setTargetName("K-Startup");
                        vo.setTargetCatName("-");
                        vo.setLocCode("C82");
                        vo.setSiTitle(title);
                        vo.setMobileUrl(bodyUrl);
                        vo.setPcUrl("-");

                        HashMap<String, String> params = new HashMap<>();
//                    params.put("bodyurl", bodyUrl);
                        params.put("title",title);
                        boolean isUrl = crawlingMapper.isUrl(params);
                        if (!isUrl) {
                            supportVos.add(vo);
                        }

                    }

                    Thread.sleep(500);
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
                supportVo.setErrorYn("Y");
                e.printStackTrace();
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
        } catch (Exception e) {
            e.printStackTrace();
            supportVo.setErrorYn("Y");
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

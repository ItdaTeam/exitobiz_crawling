package co.kr.exitobiz.Crawling;

import co.kr.exitobiz.Mappers.Api.CrawlingMapper;
import co.kr.exitobiz.Util.Util;
import co.kr.exitobiz.Vo.Cms.SupportVo;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class KortaCrawling implements Crawling {

    @Autowired
    CrawlingMapper crawlingMapper;

    @Autowired
    Environment environment;

    /*
     * KOTRA24
     * https://www.kotra.or.kr
     *  */

    private String url = "https://www.kotra.or.kr/subList/20000020753";
    private int page = 10; // 10 페이지만 크롤링

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

        SupportVo supportVo = new SupportVo();
        supportVo.setTitle("KOTRA24");
        supportVo.setUrl("https://www.kotra.or.kr");
        supportVo.setLocCode("C82");
        supportVo.setActiveYn("Y");
        supportVo.setErrorYn("N");


        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--headless", "--disable-gpu","--no-sandbox");
        options.addArguments("window-size=1920x1080");
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.36");
        options.addArguments("lang=ko_KR");

        ChromeDriverService service = null;
        WebDriver driver = null;
        try {
            service = new ChromeDriverService.Builder()
                    .usingDriverExecutable(driverFile)
                    //.usingPort(5000)
                    .usingAnyFreePort()
                    .build();

            service.start();
            try{
                driver = new ChromeDriver(service, options);
            }catch(Exception e){

            }

            List<SupportVo> supportVos = new ArrayList<>();



//            for (int i = page; i > 0; i--) {
                driver.get(url);
            Thread.sleep(1000);

            JavascriptExecutor jse = (JavascriptExecutor) driver;

                //상시 신청 가능
            List<WebElement> awaysList = driver.findElements(By.xpath("//*[@id=\"itemRcritYArea\"]/ul/li"));
            for(int i=1; i<9; i++){
                jse.executeScript("fnPage1("+i+");");
                 for (int j = 1; j <= awaysList.size(); j++) {
                     Thread.sleep(300);
                    try {
                        WebElement titleXpath = driver.findElement(By.xpath("//*[@id=\"itemRcritYArea\"]/ul/li["+j+"]/div/ul/li/a"));

                        SupportVo vo = new SupportVo();
                        String title = titleXpath.getText();
                        String href = titleXpath.getAttribute("href");
                        String bodyUrl = "https://www.kotra.or.kr/subList/20000020753/subhome/bizAply/selectBizMntInfoDetail.do?" +  href.substring(href.indexOf("&"), href.length()-3);

                        vo.setTargetName("KOTRA24");
                        vo.setTargetCatName("-");
                        vo.setLocCode("C82");
                        vo.setSiTitle(title);
                        vo.setMobileUrl(bodyUrl);
                        vo.setPcUrl("-");

                        HashMap<String, String> params = new HashMap<>();
                        params.put("title", title);

                        boolean isUrl = crawlingMapper.isUrl(params);
                        if (!isUrl) {
                            supportVos.add(vo);
                        }
                    } catch (Exception e) {
                        supportVo.setErrorYn("Y");
                    }
                }

            }

                Thread.sleep(100);


            // 신청기한 있는 지원 사업
            List<WebElement> endList = driver.findElements(By.xpath("//*[@id=\"itemTermArea\"]/ul/li"));
            for(int i=1; i<=10; i++){
                jse.executeScript("fnPage2("+i+");");
                for (int j = 1; j <= endList.size(); j++) {
                        Thread.sleep(500);
                    try {
                        WebElement titleXpath = driver.findElement(By.xpath("//*[@id=\"itemTermArea\"]/ul/li["+j+"]/div[2]/ul/li[1]/a"));

                        SupportVo vo = new SupportVo();
                        String title = titleXpath.getText();
                        String href = titleXpath.getAttribute("href");
                        String bodyUrl = "https://www.kotra.or.kr/subList/20000020753/subhome/bizAply/selectBizMntInfoDetail.do?" +  href.substring(href.indexOf("&"), href.length()-3);

                        vo.setTargetName("KOTRA24");
                        vo.setTargetCatName("-");
                        vo.setLocCode("C82");
                        vo.setSiTitle(title);
                        vo.setMobileUrl(bodyUrl);
                        vo.setPcUrl("-");

                        HashMap<String, String> params = new HashMap<>();
                        params.put("title", title);

                        boolean isUrl = crawlingMapper.isUrl(params);
                        if (!isUrl) {
                            supportVos.add(vo);
                        }
                    } catch (Exception e) {
                        supportVo.setErrorYn("Y");
                    }
                }

            }

            /* 빈 리스트가 아니면 크레이트 */
            if (!supportVos.isEmpty()) {
//                List<SupportVo> list = supportVos.stream().filter(Util.distinctByKey(o ->o.getSiTitle())).collect(Collectors.toList()); //페이지별 공지 때문에 url 기준 중복 제거.

                try {
                    crawlingMapper.create(supportVos);
                    crawlingMapper.createMaster(supportVo);
                } catch (Exception e) {
                    supportVo.setErrorYn("Y");
                    crawlingMapper.createMaster(supportVo);
                }
            } else {
                supportVo.setErrorYn("N");
                crawlingMapper.createMaster(supportVo);
            }

        } catch (Exception e) {
            e.printStackTrace();
            supportVo.setErrorYn("Y");
            crawlingMapper.createMaster(supportVo);
        } finally {
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

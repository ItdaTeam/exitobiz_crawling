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
import org.openqa.selenium.support.ui.Select;
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
public class SbaCrawling implements Crawling {

    @Autowired
    CrawlingMapper crawlingMapper;

    @Autowired
    Environment environment;

    /*
     * 서울산업진흥원
     * http://new.sba.kr/
     *
     *  */

    private String url = "https://www.sba.seoul.kr/Pages/ContentsMenu/Company_Support.aspx?C=6FA70790-6677-EC11-80E8-9418827691E2";

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
        options.addArguments("--remote-allow-origins=*");
//        options.addArguments("--headless", "--disable-gpu","--no-sandbox");
        options.addArguments("window-size=1920x1080");
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.36");
        options.addArguments("lang=ko_KR");

        SupportVo supportVo = new SupportVo();
        supportVo.setTitle("서울산업진흥원");
        supportVo.setUrl("http://new.sba.kr/");
        supportVo.setLocCode("C02");
        supportVo.setActiveYn("Y");
        supportVo.setErrorYn("N");

        ChromeDriverService service = null;
        WebDriver driver = null;

        try {
             service = new ChromeDriverService.Builder()
                .usingDriverExecutable(driverFile)
                //.usingPort(5000)
                .usingAnyFreePort()
                .build();

            service.start();
            driver = new ChromeDriver(service,options);
            JavascriptExecutor jse = (JavascriptExecutor) driver;



            List<SupportVo> supportVos = new ArrayList<>();


            for (int i=page; i>0; i--) {

                driver.get(url);

                Thread.sleep(1000);

//                WebElement orderXpath = driver.findElement(By.id("ContentPlaceHolder1_MainContents_P_ORDER"));
                Select order = new Select(driver.findElement(By.id("ContentPlaceHolder1_MainContents_P_ORDER")));
                order.selectByValue("REG");
                WebElement btnXPath = driver.findElement(By.xpath("//*[@id=\"btn_search\"]"));
                btnXPath.click();

                WebElement pageXpath = driver.findElement(By.xpath("//*[@id='pagination']/ol/li["+ i +"]"));

                //페이지이동
                jse.executeScript("arguments[0].click()", pageXpath);

                Thread.sleep(1500);

                for(int j=1; j<13; j++) {

                        WebElement titleXpath = driver.findElement(By.xpath("//*[@id=\"card_list\"]/div["+ j +"]/div[2]/a/h3"));
                        WebElement urlXpath = driver.findElement(By.xpath("//*[@id=\"card_list\"]/div["+ j +"]/div[2]/a"));
                        String urlHref = urlXpath.getAttribute("href");
                        String index = urlHref.replace("javascript:contentsDetail","").replace("(","").replace(")","").replace("'","");
                        String url = "https://www.sba.seoul.kr/Pages/ContentsMenu/Company_Support_Detail.aspx?RID=" + index;
                        String title = titleXpath.getText();

                        SupportVo vo = new SupportVo();
                        vo.setTargetName("서울산업진흥원");
                        vo.setTargetCatName("-");
                        vo.setLocCode("C02");
                        vo.setSiTitle(title);
                        vo.setMobileUrl(url);
                        vo.setPcUrl("-");

                        HashMap<String, String> params = new HashMap<>();
//                        params.put("bodyurl", url);
                        params.put("title",title);
                        boolean isUrl = crawlingMapper.isUrl(params);
                        if (!isUrl) {
                            supportVos.add(vo);
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

package co.kr.exitobiz.Crawling;

import co.kr.exitobiz.Mappers.Api.CrawlingMapper;
import co.kr.exitobiz.Vo.Cms.SupportVo;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
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
public class GwangjuGiconCrawling implements Crawling {

    @Autowired
    CrawlingMapper crawlingMapper;

    @Autowired
    Environment environment;

    /*
     *
     * 광주정보문화산업진흥원
     * https://www.gicon.or.kr/
     *  */

    private String url = "https://www.gicon.or.kr/board.es?mid=a10204000000&bid=0003";
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

        SupportVo supportVo = new SupportVo();
        supportVo.setTitle("광주정보문화산업진흥원");
        supportVo.setUrl("https://www.gicon.or.kr/");
        supportVo.setLocCode("C062");
        supportVo.setActiveYn("Y");
        supportVo.setErrorYn("N");
        List<SupportVo> supportVos = new ArrayList<>();


        for (int i=page; i>0; i--) {

            driver.get(url);
            Thread.sleep(1000);

            for(int j=1; j<16; j++) {
                    try {

                        String bodyurl;
                        String title;
                        
                        //a 태그 있는지 여부 체크
                        try {
                            WebElement urlXpath = driver.findElement(By.xpath("//*[@id=\"contents_body\"]/div[2]/table/tbody/tr["+ j +"]/td[2]/strong/a"));
                            bodyurl = urlXpath.getAttribute("href").substring(0,urlXpath.getAttribute("href").lastIndexOf("nPage") -1);
                            title = urlXpath.getText().replace("새글\n","");
                        } catch (NoSuchElementException ex){
                            WebElement urlXpath = driver.findElement(By.xpath("//*[@id=\"contents_body\"]/div[2]/table/tbody/tr["+ j +"]/td[2]/a"));
                            bodyurl = urlXpath.getAttribute("href").substring(0,urlXpath.getAttribute("href").lastIndexOf("nPage") -1);
                            title = urlXpath.getText().replace("새글\n","");
                        }

                        SupportVo vo = new SupportVo();

                        vo.setTargetName("광주정보문화산업진흥원");
                        vo.setTargetCatName("-");
                        vo.setLocCode("C062");
                        vo.setSiTitle(title);
                        vo.setMobileUrl(bodyurl);
                        vo.setPcUrl("-");

                        HashMap<String, String> params = new HashMap<>();
                        params.put("bodyurl", bodyurl);
                        boolean isUrl = crawlingMapper.isUrl(params);
                        if (!isUrl) {
                            supportVos.add(vo);
                        }

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        supportVo.setErrorYn("Y");
                        crawlingMapper.createMaster(supportVo);
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

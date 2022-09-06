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
public class JeonnamTpCrawling implements Crawling {

    @Autowired
    CrawlingMapper crawlingMapper;

    @Autowired
    Environment environment;

    /*
     *
     * 전남테크노파크
     * http://www.jntp.or.kr/
     *  */

    private String url = "http://www.jntp.or.kr/home/menu/202.do";
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

        SupportVo supportVo = new SupportVo();
        supportVo.setTitle("전남테크노파크");
        supportVo.setUrl("http://www.jntp.or.kr/");
        supportVo.setLocCode("C061");
        supportVo.setActiveYn("Y");
        supportVo.setErrorYn("N");
        List<SupportVo> supportVos = new ArrayList<>();


        Thread.sleep(1000);
        for (int i=page; i>0; i--) {

            driver.get(url);
            Thread.sleep(1000);

            for(int j=1; j<11; j++) {
                    try {

                        WebElement titleXpath = driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/div/div[2]/table/tbody/tr["+ j +"]/td[2]/a"));
                        SupportVo vo = new SupportVo();
                        String title = titleXpath.getText();
                        String bodyurl = titleXpath.getAttribute("href");

                        vo.setTargetName("전남테크노파크");
                        vo.setTargetCatName("-");
                        vo.setLocCode("C061");
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

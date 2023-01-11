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

@Component
public class SeoulRnbdCrawling implements Crawling {

    @Autowired
    CrawlingMapper crawlingMapper;

    @Autowired
    Environment environment;

    /*
     *
     * 서울R&D지원센터
     * https://seoul.rnbd.kr/
     *  */

    private String url = "https://seoul.rnbd.kr/client/c030100/c030100_00.jsp?sField=&sWord=&sFlag=&cPage=";
    private int page = 2;

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
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

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
        supportVo.setTitle("서울R&D지원센터");
        supportVo.setUrl("https://seoul.rnbd.kr/");
        supportVo.setLocCode("C02");
        supportVo.setActiveYn("Y");
        supportVo.setErrorYn("N");
        List<SupportVo> supportVos = new ArrayList<>();


        for (int i=page; i>0; i--) {

            driver.get(url+i);
            Thread.sleep(1000);

            for(int j=1; j<11; j++) {
                    try {

                        WebElement urlXpath = driver.findElement(By.xpath("//*[@id=\"contents\"]/div[2]/div[2]/table/tbody/tr["+ j +"]/td[2]/a"));
                        WebElement statusXpath = driver.findElement(By.xpath("//*[@id=\"contents\"]/div[2]/div[2]/table/tbody/tr["+ j +"]/td[4]/span"));
                        SupportVo vo = new SupportVo();
                        String status = statusXpath.getText();

                        if(status.equals("모집중")){
                            String title = urlXpath.getText();
                            String bodyurl = urlXpath.getAttribute("href");

                            vo.setTargetName("서울R&D지원센터");
                            vo.setTargetCatName("-");
                            vo.setLocCode("C02");
                            vo.setSiTitle(title);
                            vo.setMobileUrl(bodyurl);
                            vo.setPcUrl("-");

                            HashMap<String, String> params = new HashMap<>();
                            params.put("bodyurl", bodyurl);
                            boolean isUrl = crawlingMapper.isUrl(params);
                            if (!isUrl) {
                                supportVos.add(vo);
                            }
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

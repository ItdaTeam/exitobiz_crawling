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
public class SeoulSehubCrawling implements Crawling {

    @Autowired
    CrawlingMapper crawlingMapper;

    @Autowired
    Environment environment;

    /*
     * 서울시청년활동지원센터
     * https://www.sygc.kr/
     *  */

    private String url = "https://sygc.kr/NOTICE/?page=";
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

        SupportVo supportVo = new SupportVo();
        supportVo.setTitle("서울시청년활동지원센터");
        supportVo.setUrl("https://www.sygc.kr/");
        supportVo.setLocCode("C02");
        supportVo.setActiveYn("Y");
        supportVo.setErrorYn("N");

        List<SupportVo> supportVos = new ArrayList<>();

        for (int i=page; i>0; i--) {
            driver.get(url + i);
            for(int j=2; j<12; j++) {

                try {
                    WebElement titleXpath = driver.findElement(By.xpath("//*[@id=\"w20220822288d3e65f885e\"]/div/div[2]/div[1]/ul["+ j +"]/li[2]/a[2]/span"));
                  //  WebElement targetTypeXpath = driver.findElement(By.xpath("/html/body/section/div/div/div[3]/table/tbody/tr["+ j +"]/td[4]"));
                    WebElement urlXpath = driver.findElement(By.xpath("//*[@id=\"w20220822288d3e65f885e\"]/div/div[2]/div[1]/ul[\"+ j +\"]/li[2]/a[2]"));

                    String title = titleXpath.getText();
                    String url = "https://www.sygc.kr" + urlXpath.getAttribute("href");
                   // String targettype = targetTypeXpath.getText();

                    SupportVo vo = new SupportVo();
                    vo.setTargetName("서울시청년활동지원센터");
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

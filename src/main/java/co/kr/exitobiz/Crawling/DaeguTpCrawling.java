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
public class DaeguTpCrawling implements Crawling {

    @Autowired
    CrawlingMapper crawlingMapper;

    @Autowired
    Environment environment;

    /*
     * 대구테크노파크
     * https://ttp.org/
     * 지원사업 텍스트 안받아서 오류처리중
     *  */

    private String url = "https://ttp.org/bbs/BoardControll.do?bbsId=BBSMSTR_000000000003&PageIndex=";
    private int page = 3;

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
        JavascriptExecutor jse = (JavascriptExecutor) driver;

        SupportVo supportVo = new SupportVo();
        supportVo.setTitle("대구테크노파크");
        supportVo.setUrl("https://ttp.org/");
        supportVo.setLocCode("C053");
        supportVo.setActiveYn("Y");
        supportVo.setErrorYn("N");
        List<SupportVo> supportVos = new ArrayList<>();


        Thread.sleep(1000);
        for (int i=page; i>0; i--) {

            driver.get(url+i);
            Thread.sleep(1000);

            for(int j=1; j<11; j++) {
                    try {

                        WebElement urlXpath = driver.findElement(By.xpath("/html/body/div[2]/section/div/form[2]/table/tbody/tr["+ j +"]/td[3]/div/span/a"));
                        SupportVo vo = new SupportVo();
                        String title = urlXpath.getAttribute ("innerHTML");

                        System.out.println("체큰" + title);
                        String url = urlXpath.getAttribute("onclick").replaceAll("javascript:fn_egov_inqire_notice","").replaceAll("\\(","").replaceAll("\\)","").replaceAll("\'","").replaceAll(" ","");
                        String[] urlTemp = url.split(",");
                        String baseurl = "https://ttp.org/bbs/BoardControllView.do?bbsId=";
                        String bodyurl = baseurl + urlTemp[1] + "&nttId=" + urlTemp[0];

                        vo.setTargetName("대구테크노파크");
                        vo.setTargetCatName("-");
                        vo.setLocCode("C053");
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
                        e.printStackTrace();
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

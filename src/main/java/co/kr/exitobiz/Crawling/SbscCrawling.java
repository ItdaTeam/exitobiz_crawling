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
public class SbscCrawling implements Crawling {

    @Autowired
    CrawlingMapper crawlingMapper;

    @Autowired
    Environment environment;

    /*
    *
    *  */

    private String url = "https://sbsc.seoul.go.kr/fe/support/seoul/NR_list.do?bbsCd=1&bbsSeq=&searchVals=&bbsGrpCds_all=on&orgCd=&currentPage=";
    private int page = 1;

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
        options.addArguments("--headless", "--disable-gpu","--no-sandbox");
        options.addArguments("window-size=1920x1080");
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.36");
        options.addArguments("lang=ko_KR");

        SupportVo supportVo = new SupportVo();
        supportVo.setTitle("서울기업지원센터");
        supportVo.setUrl("https://sbsc.seoul.go.kr/");
        supportVo.setLocCode("C02");
        supportVo.setActiveYn("Y");
        supportVo.setErrorYn("N");

        ChromeDriverService service = null;
        WebDriver driver = null;

        try {
            service =   new ChromeDriverService.Builder()
                    .usingDriverExecutable(driverFile)
                    //.usingPort(5000)
                    .usingAnyFreePort()
                    .build();
            service.start();
            driver = new ChromeDriver(service,options);
            WebDriverWait wait = new WebDriverWait(driver, 10);

            List<SupportVo> supportVos = new ArrayList<>();

            try {
                for (int i=page; i>0; i--) {
                    driver.get(url + i);

                    for(int j=1; j<11; j++) {
                        WebElement urlXpath = driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[2]/table/tbody/tr["+j+"]/td[2]/a"));
                        WebElement titleXpath = driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[2]/table/tbody/tr["+j+"]/td[2]"));
                        WebElement targetTypeXpath = driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[2]/table/tbody/tr["+j+"]/td[3]"));
                        WebElement endTimeXpath = driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[2]/table/tbody/tr["+j+"]/td[4]"));

                        String title = titleXpath.getAttribute("title");
                        String targettype = targetTypeXpath.getText();
                        String endtime = endTimeXpath.getText();

                        String url = urlXpath.getAttribute("onclick");
                        String intStr = url.replaceAll("[^0-9]", "");

                        String bodyurl = "https://sbsc.seoul.go.kr/fe/support/seoul/NR_view.do?bbsCd=1&bbsSeq=" + intStr;

                        SupportVo vo =new SupportVo();
                        vo.setTargetName("서울기업지원센터");
                        vo.setTargetCatName("-");
                        vo.setLocCode("C02");
                        vo.setSiTitle(title);
                        vo.setMobileUrl(bodyurl);
                        vo.setPcUrl("-");

                        HashMap<String, String> params = new HashMap<>();
                        params.put("title", title);
                        boolean isUrl = crawlingMapper.isUrl(params);
                        if (!isUrl) {
                            supportVos.add(vo);
                        }

                    }

                    Thread.sleep(500);
                }

            } catch (Exception e) {
                supportVo.setErrorYn("Y");
                crawlingMapper.createMaster(supportVo);
                e.printStackTrace();
            } finally {
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

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
public class SeoulKoccaCrawling implements Crawling {

    @Autowired
    CrawlingMapper crawlingMapper;

    @Autowired
    Environment environment;

    /*
     * 한국콘텐츠진흥원
     * https://www.kocca.kr/
     *  */

    private String url = "https://www.kocca.kr/kocca/pims/list.do?menuNo=204104";
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
                .usingPort(5000)
                .build();

        try {
            service.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        WebDriver driver = new ChromeDriver(service,options);

        SupportVo supportVo = new SupportVo();
        supportVo.setTitle("한국콘텐츠진흥원");
        supportVo.setUrl("https://www.kocca.kr/");
        supportVo.setLocCode("C82");
        supportVo.setActiveYn("Y");
        supportVo.setErrorYn("N");

        List<SupportVo> supportVos = new ArrayList<>();


        for (int i=page; i>0; i--) {

            driver.get(url + i);


            List <WebElement> list = driver.findElements(By.xpath("//*[@id=\"frm\"]/div[4]/table/tbody/tr"));

            // 공고가 가변적이기 때문에 리스트 사이즈 구해서 for문 돌리기
            int k = list.size();

            for(int j=1; j<k; j++) {

                try {

                    WebElement titleXpath = driver.findElement(By.xpath("//*[@id=\"frm\"]/div[4]/table/tbody/tr["+ j +"]/td[2]/a"));

                    String title = titleXpath.getText();
                    String bodyurl = titleXpath.getAttribute("href");

                    SupportVo vo = new SupportVo();
                    vo.setMobileUrl(bodyurl);
                    vo.setTargetName("한국콘텐츠진흥원");
                    vo.setTargetCatName("-");
                    vo.setLocCode("C82");
                    vo.setSiTitle(title);
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

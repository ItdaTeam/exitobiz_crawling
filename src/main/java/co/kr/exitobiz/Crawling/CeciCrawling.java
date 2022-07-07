package co.kr.exitobiz.Crawling;

import co.kr.exitobiz.Mappers.Api.CrawlingMapper;
import co.kr.exitobiz.Vo.Crawling.CeciCrawVo;
import co.kr.exitobiz.Vo.Cms.SupportVo;
import lombok.AllArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
@AllArgsConstructor
public class CeciCrawling {

    private final CrawlingMapper crawlingMapper;
    private final Environment environment;


    public void craw(CeciCrawVo dto) throws InterruptedException {
        String driverPath = environment.getProperty("chrome.driver.path");
        String url = dto.getBaseUrl() + "/custom/notice_list.do?&page=";
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
                .usingAnyFreePort()
                .build();

        try {
            service.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        WebDriver driver = new ChromeDriver(service);

        SupportVo supportVo = new SupportVo(dto.getTitle(), dto.getBaseUrl(), dto.getLocCode(), "Y", "N");

        List<SupportVo> supportVos = new ArrayList<>();

        for (int i=dto.getPage(); i>0; i--) {

            driver.get(url + i);
            Thread.sleep(1000);
            for(int j=1; j<9; j++) {
                try {

                    WebElement titleXpath = driver.findElement(By.xpath("/html/body/div[2]/div[1]/div[3]/section/div[2]/div[4]/table/tbody/tr["+ j +"]/td[3]/a"));

                    String title = titleXpath.getText();
                    String targetUrl = titleXpath.getAttribute("onclick").replaceAll("fnDetailPage","").replaceAll("\\(","").replaceAll("\\)","").replaceAll("\"","");
                    String[] urlTemp = targetUrl.split(",");
                    String bodyurl = dto.getBaseUrl() + "/custom/notice_view.do?no=" + urlTemp[0];

                    SupportVo vo = new SupportVo(dto.getTitle(), "-", dto.getLocCode(), title, bodyurl, "-");

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

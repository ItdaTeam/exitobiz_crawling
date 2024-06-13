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
        //options.addArguments("--remote-allow-origins=*");
        options.addArguments("--headless", "--disable-gpu","--no-sandbox");
        options.addArguments("window-size=1920x1080");
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.36");
        options.addArguments("lang=ko_KR");

        ChromeDriverService service = null;
        WebDriver driver = null;

        SupportVo supportVo = new SupportVo(dto.getTargetName(), dto.getBaseUrl(), dto.getLocCode(), "Y", "N");

        try {
            service = new ChromeDriverService.Builder()
                .usingDriverExecutable(driverFile)
                .usingAnyFreePort()
                .build();

            service.start();
            driver = new ChromeDriver(service,options);

            List<SupportVo> supportVos = new ArrayList<>();

            for (int i=dto.getPage(); i>0; i--) {

                driver.get(url + i);
                Thread.sleep(1000);
                for(int j=1; j<9; j++) {
                    try {

                        WebElement titleXpath = driver.findElement(By.xpath("//*[@id=\"list_body\"]/tr["+j+"]/td[3]/a"));

                        StringBuilder stringBuilder = new StringBuilder();

                        String title = titleXpath.getText();
                        String targetUrl = titleXpath.getAttribute("onclick").replaceAll("fnDetailPage","").replaceAll("\\(","").replaceAll("\\)","").replaceAll("\"","");
                        String[] urlTemp = targetUrl.split(",");
                        String bodyUrl = dto.getBaseUrl() + "/custom/notice_view.do?no="+urlTemp[0]+"&div_code=&rnum="+urlTemp[1] +"&pn=1&kind=my&sPtime=now&sMenuType=00040001&pagePerContents=6";
                        SupportVo vo = new SupportVo(dto.getTargetName(), "-", dto.getLocCode(), title, stringBuilder.toString(), "-");
                        vo.setTargetName(dto.getTargetName());
                        HashMap<String, String> params = new HashMap<>();
                        params.put("bodyurl", bodyUrl);
                        params.put("title",title);
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

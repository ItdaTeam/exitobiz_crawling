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
import java.util.*;
import java.text.SimpleDateFormat;

@Component
public class SeoulBizinfoCrawling implements Crawling {

    @Autowired
    CrawlingMapper crawlingMapper;

    @Autowired
    Environment environment;

    /*
     * 기업마당
     * https://www.bizinfo.go.kr/web/index.do
     *  */

    private String url = "https://www.bizinfo.go.kr/web/lay1/bbs/S1T122C128/AS/74/list.do?rows=15&cpage=";
    private int page = 10;

    @Override
    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public void craw() throws InterruptedException {

        String driverPath = "C:\\Users\\Administrator\\Downloads\\chromedriver_win32\\chromedriver.exe";
        File driverFile = new File(String.valueOf(driverPath));

        if (!driverFile.exists() && driverFile.isFile()) {
            throw new RuntimeException("Not found");
        }

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--disable-gpu","--no-sandbox");
        options.addArguments("window-size=1920x1080");
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.36");
        options.addArguments("lang=ko_KR");

        ChromeDriverService service = null;
        WebDriver driver = null;

        SupportVo supportVo = new SupportVo();
        supportVo.setTitle("기업마당");
        supportVo.setUrl("https://www.bizinfo.go.kr/web/index.do");
        supportVo.setLocCode("C82");
        supportVo.setActiveYn("Y");
        supportVo.setErrorYn("N");

        try {
            service = new ChromeDriverService.Builder()
                .usingDriverExecutable(driverFile)
                //.usingPort(5000)
                .usingAnyFreePort()
                .build();

            service.start();
            driver = new ChromeDriver(service,options);

            List<SupportVo> supportVos = new ArrayList<>();

            Date today = new Date();
            today = new Date(today.getTime() - (1000*60*60*24-1));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
            String yesterday = sdf.format(today);

            for (int i=page; i>0; i--) {

                driver.get(url + i);
                Thread.sleep(1000);
                for(int j=1; j<16; j++) {
//                    WebElement dateXpath = driver.findElement(By.xpath("//*[@id=\"articleSearchForm\"]/div[3]/div[3]/table/tbody/tr["+ j +"]/td[6]"));
//                    String date = dateXpath.getText();
    //                if(date.equals(yesterday)){
                        try {
                            WebElement titleXpath = driver.findElement(By.xpath("//*[@id=\"articleSearchForm\"]/div[2]/div[3]/table/tbody/tr["+ j +"]/td[3]/a"));
                            SupportVo vo = new SupportVo();

                            String title = titleXpath.getText();

                            //기업마당 개편에따른 URL 중복문제를 해결하기위해 url을 잘라서 저장함
                            String bodyurl = titleXpath.getAttribute("href").substring(0, 93);

                            vo.setTargetName("기업마당");
                            vo.setTargetCatName("-");
                            vo.setLocCode("C82");
                            vo.setSiTitle(title);
                            vo.setMobileUrl(bodyurl);
                            vo.setPcUrl("-");


                            HashMap<String, String> params = new HashMap<>();
                            params.put("bodyurl", bodyurl);
                            params.put("title",title);
                            boolean isUrl = crawlingMapper.isUrl(params);
                            if (!isUrl) {
                                supportVos.add(vo);
                            }

                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            supportVo.setErrorYn("Y");
                        }
    //                }
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

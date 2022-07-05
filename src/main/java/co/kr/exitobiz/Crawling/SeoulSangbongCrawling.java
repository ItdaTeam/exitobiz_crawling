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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SeoulSangbongCrawling implements Crawling {

    @Autowired
    CrawlingMapper crawlingMapper;

    @Autowired
    Environment environment;

    /*
     * 서울창업카페상봉점
     * http://seoulstartupcafe.com/
     *  */

    private String url = "http://www.xn--9n3b11ebufi2hb1cvzk.com/kwa-47155?PB_1582776078=";
    private int page = 4;

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
                .usingPort(5000)
                .build();

        try {
            service.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        WebDriver driver = new ChromeDriver(service,options);
        WebDriverWait wait = new WebDriverWait(driver, 10);

        SupportVo supportVo = new SupportVo();
        supportVo.setTitle("서울창업카페상봉점");
        supportVo.setUrl("http://www.xn--9n3b11ebufi2hb1cvzk.com");
        supportVo.setLocCode("C02");
        supportVo.setActiveYn("Y");
        supportVo.setErrorYn("N");
        List<SupportVo> supportVos = new ArrayList<>();


        for (int i=page; i>0; i--) {

            driver.get(url + i);

            for(int j=1; j<11; j++) {

                try {

                    WebElement titleXpath = driver.findElement(By.xpath("//*[@id='TCBOARD_BD47155_LIST_index192']/div/div[2]/div/div[1]/table/tbody/tr[" + j + "]/td[2]/a/span"));
                    WebElement urlXpath = driver.findElement(By.xpath("//*[@id='TCBOARD_BD47155_LIST_index192']/div/div[2]/div/div[1]/table/tbody/tr[" + j + "]/td[2]/a"));

                    Pattern typePattern = Pattern.compile("\\[(.*?)\\]"); // 대괄호안에 문자 뽑기
                    Matcher typeMatcher = typePattern.matcher(titleXpath.getText());
                    ArrayList<String> typePatternArray = new ArrayList<String>();

                    while (typeMatcher.find()) {
                        typePatternArray.add(typeMatcher.group());
                    }

                    String title = titleXpath.getText();
                    String bodyurl = urlXpath.getAttribute("href");


                    SupportVo vo = new SupportVo();

                    //타입코드 없을경우 - 처리
//                    if(!typePatternArray.isEmpty()){
//                        String targettype = typePatternArray.get(0).replaceAll("\\[", "").replaceAll("\\]", "");
//                        vo.setTargettype(targettype);
//                        vo.setTargettypecode(targettype);
//                    }else {
//                        vo.setTargettype("-");
//                        vo.setTargettypecode("-");
//                    }

                    vo.setTargetName("서울창업카페상봉점");
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

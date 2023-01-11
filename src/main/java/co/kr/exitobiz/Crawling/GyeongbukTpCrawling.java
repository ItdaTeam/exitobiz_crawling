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
public class GyeongbukTpCrawling implements Crawling {

    @Autowired
    CrawlingMapper crawlingMapper;

    @Autowired
    Environment environment;

    /*
     *
     * 경북테크노파크
     * https://www.gbtp.or.kr/
     *  */

    private String url = "https://www.gbtp.or.kr/user/board.do?bbsId=BBSMSTR_000000000021&pageIndex=";
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
                .usingAnyFreePort()
                .build();

        try {
            service.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        WebDriver driver = new ChromeDriver(service);

        SupportVo supportVo = new SupportVo();
        supportVo.setTitle("경북테크노파크");
        supportVo.setUrl("https://www.gbtp.or.kr/");
        supportVo.setLocCode("C054");
        supportVo.setActiveYn("Y");
        supportVo.setErrorYn("N");
        List<SupportVo> supportVos = new ArrayList<>();

        Thread.sleep(1000);
        for (int i=page; i>0; i--) {
            driver.get(url+i);

            Thread.sleep(1000);

            for(int j=1; j<11; j++) {
                    try {

                        WebElement titleXpath = driver.findElement(By.xpath("//*[@id=\"contentDiv\"]/table/tbody/tr["+ j +"]/td[3]/a"));
                        String title = titleXpath.getText();
                        String url = titleXpath.getAttribute("onclick").replaceAll("javascript:fn_detail","").replaceAll("\\(","").replaceAll("\\)","").replaceAll("\'","");
                        String[] urlTemp = url.split(",");
                        String baseurl = "https://www.gbtp.or.kr/user/boardDetail.do?Stylesheetfullpage.css?ver=1.1Stylesheetnotosans.css?ver=1.1Stylesheetplay.css?ver=1.1Stylesheetcommon.css?ver=1.1Stylesheetlayout.css?ver=1.2Stylesheetboard.css?ver=1.1Stylesheetmediaquery.css?ver=1.1Stylesheetfullcalendar.css?ver=1.1Stylesheetjquery-ui.css?ver=1.1Scriptjquery-3.6.0.min.jsScriptbootstrap.min.jsScriptjquery-ui.jsScriptfullcalendar.jsScriptfullcalendar_locales_all.jsScriptcommon.jsScriptslick.min.jsScriptfullpage.jsScriptjs?id=G-H68ZMSGFLYImageimageSrc.do?path=20220224&physical=234BFBBE43844CDC87EE52A69FAED9DC&contentType=image/jpegImagef_logo.pngImagesns_icon01.pngImagesns_icon02.pngImagesns_icon03.pngFontfa-light-300.woff2Fontfa-regular-400.woff2Fontfa-solid-900.woff2Fontfa-thin-100.woff2FontNotoSansKR-Regular.woff2FontNotoSansKR-Bold.woff2FontNotoSansKR-Black.woff2FontPlay-Regular.woff2FontPlay-Bold.woff2Imagelogo_b.pngImagesub_visual01.jpgPingcollect?v=2&tid=G-H68ZMSGFLY&gtm=2oe320&_p=7041301%E2%80%A6ED%85%8C%ED%81%AC%EB%85%B8%ED%8C%8C%ED%81%AC&_s=1%2037%20requests%2017%20B%20transferred%206.9%20MB%20resources%20Finish:%205.21%20s%20DOMContentLoaded:%20156%20ms%20Load:%20156%20ms%20pageIndex=1&bbsId=BBSMSTR_000000000021&nttNo=";
                        String bodyurl = baseurl + urlTemp[0];

                        SupportVo vo = new SupportVo();

                        vo.setTargetName("경북테크노파크");
                        vo.setTargetCatName("-");
                        vo.setLocCode("C054");
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

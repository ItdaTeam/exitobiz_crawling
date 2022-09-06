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
public class UlsanCceiCrawling implements Crawling {

    @Autowired
    CrawlingMapper crawlingMapper;

    @Autowired
    Environment environment;

    /*
     *
     * 울산창조경제혁신센터
     * https://ccei.creativekorea.or.kr/ulsan/
     *  */

    private String url = "https://ccei.creativekorea.or.kr/ulsan/custom/notice_list.do?&page=";
    private int page = 1;

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
        supportVo.setTitle("울산창조경제혁신센터");
        supportVo.setUrl("https://ccei.creativekorea.or.kr/ulsan/");
        supportVo.setLocCode("C052");
        supportVo.setActiveYn("Y");
        supportVo.setErrorYn("N");
        List<SupportVo> supportVos = new ArrayList<>();


        for (int i=page; i>0; i--) {

            driver.get(url+i);
            Thread.sleep(1000);
            for(int j=1; j<9; j++) {
                    try {

                        WebElement titleXpath = driver.findElement(By.xpath("//*[@id=\"list_body\"]/tr["+ j +"]/td[3]/a"));
                        SupportVo vo = new SupportVo();
                        String title = titleXpath.getText();
                        String url = titleXpath.getAttribute("onclick").replaceAll("fnDetailPage","").replaceAll("\\(","").replaceAll("\\)","").replaceAll("\"","");
                        String[] urlTemp = url.split(",");
                        String baseurl = "https://ccei.creativekorea.or.kr/ulsan/custom/notice_view.do?no=";
                        String bodyurl = baseurl + urlTemp[0];

                        vo.setTargetName("울산창조경제혁신센터");
                        vo.setTargetCatName("-");
                        vo.setLocCode("C052");
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

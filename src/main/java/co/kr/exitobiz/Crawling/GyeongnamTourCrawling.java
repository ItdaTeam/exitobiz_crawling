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
public class GyeongnamTourCrawling implements Crawling {

    @Autowired
    CrawlingMapper crawlingMapper;

    @Autowired
    Environment environment;

    /*
     *
     * 경남관광기업지원센터
     * https://gyeongnam.tourbiz.or.kr/
     *  */

    private String url = "https://gyeongnam.tourbiz.or.kr/sub05/sub01_01.do";
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
                .usingAnyFreePort()
                .build();

        try {
            service.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        WebDriver driver = new ChromeDriver(service);

        SupportVo supportVo = new SupportVo();
        supportVo.setTitle("경남관광기업지원센터");
        supportVo.setUrl("https://gyeongnam.tourbiz.or.kr/");
        supportVo.setLocCode("C055");
        supportVo.setActiveYn("Y");
        supportVo.setErrorYn("N");
        List<SupportVo> supportVos = new ArrayList<>();


        for (int i=page; i>0; i--) {

            driver.get(url);
            Thread.sleep(1000);

            for(int j=2; j<12; j++) {
                    try {

                        WebElement titleXpath = driver.findElement(By.xpath("/html/body/div[1]/div[3]/div[2]/div[3]/div[1]/ul/li["+ j +"]/h5/a/span[2]/b[2]"));
                        WebElement statusXpath = driver.findElement(By.xpath("/html/body/div[1]/div[3]/div[2]/div[3]/div[1]/ul/li["+ j +"]/h5/a/span[2]/b[1]"));
                        WebElement urlXpath = driver.findElement(By.xpath("/html/body/div[1]/div[3]/div[2]/div[3]/div[1]/ul/li["+ j +"]/h5/a"));

                        if(statusXpath.getText().equals("진행")){
                            String title = titleXpath.getText().trim();
                            String url = urlXpath.getAttribute("onclick").replace("fn_egov_inqire_BBS","").replace("(","").replace(")","").replace(";","").replace("'","");
                            String[] urlTemp = url.split(",");

                            String bodyurl = "https://gyeongnam.tourbiz.or.kr/sub05/sub01_view.do?bbsId=" + urlTemp[1].trim() + "&nttId=" + urlTemp[0].trim();

                            SupportVo vo = new SupportVo();

                            vo.setTargetName("경남관광기업지원센터");
                            vo.setTargetCatName("-");
                            vo.setLocCode("C055");
                            vo.setSiTitle(title);
                            vo.setMobileUrl(bodyurl);
                            vo.setPcUrl("-");

                            HashMap<String, String> params = new HashMap<>();
                            params.put("bodyurl", bodyurl);
                            boolean isUrl = crawlingMapper.isUrl(params);
                            if (!isUrl) {
                                supportVos.add(vo);
                            }
                        }


                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        supportVo.setErrorYn("Y");
                        e.printStackTrace();
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

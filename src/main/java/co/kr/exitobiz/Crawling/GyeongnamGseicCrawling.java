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
public class GyeongnamGseicCrawling implements Crawling {

    @Autowired
    CrawlingMapper crawlingMapper;

    @Autowired
    Environment environment;

    /*
     *
     * 경남사회적경제통합지원센터
     * https://gseic.or.kr/bbs_list.php
     *  */

    private String url = "https://gseic.or.kr/bbs_list.php?start=0&tb=support_business";
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
        supportVo.setTitle("경남사회적경제통합지원센터");
        supportVo.setUrl("https://gseic.or.kr/bbs_list.php");
        supportVo.setLocCode("C055");
        supportVo.setActiveYn("Y");
        supportVo.setErrorYn("N");
        List<SupportVo> supportVos = new ArrayList<>();


        for (int i=page; i>0; i--) {

            driver.get(url);
            Thread.sleep(1000);

            List <WebElement> list = driver.findElements(By.xpath("/html/body/div[2]/div[2]/div[2]/div[2]/div/div[3]/table[3]/tbody/tr"));

            // 공고가 가변적이기 때문에 리스트 사이즈 구해서 for문 돌리기
            int k = list.size();

            for(int j=1; j<k+1; j++) {
                    try {

                        WebElement titleXpath = driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[2]/div[2]/div/div[3]/table[3]/tbody/tr["+ j +"]/td/table/tbody/tr/td[2]/table/tbody/tr/td[1]/div/a"));

                        String title = titleXpath.getText().replace("[기타]","").trim();
                        String url = titleXpath.getAttribute("href");
                        String bodyurl = url.substring(0,url.lastIndexOf("b_category")-1) + "&tb=support_business";

                        SupportVo vo = new SupportVo();

                        vo.setTargetName("경남사회적경제통합지원센터");
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


                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        supportVo.setErrorYn("Y");
                        crawlingMapper.createMaster(supportVo);
                    }
            }

            List <WebElement> notiList = driver.findElements(By.xpath("/html/body/div[2]/div[2]/div[2]/div[2]/div/div[3]/table[2]/tbody/tr"));

            // 공고가 가변적이기 때문에 리스트 사이즈 구해서 for문 돌리기
            int l = notiList.size();

            for(int j=1; j<l+1; j++) {
                try {

                    WebElement titleXpath = driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[2]/div[2]/div/div[3]/table[2]/tbody/tr["+ j +"]/td/table/tbody/tr/td[2]/table/tbody/tr/td[1]/div/a"));

                    String title = titleXpath.getText().replace("[기타]","").trim();
                    String url = titleXpath.getAttribute("href");
                    String bodyurl = url.substring(0,url.lastIndexOf("b_category")-1) + "&tb=support_business";

                    SupportVo vo = new SupportVo();

                    vo.setTargetName("경남사회적경제통합지원센터");
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

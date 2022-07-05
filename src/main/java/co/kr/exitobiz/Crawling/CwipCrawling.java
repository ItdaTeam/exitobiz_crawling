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
public class CwipCrawling implements Crawling {

    @Autowired
    CrawlingMapper crawlingMapper;

    @Autowired
    Environment environment;

    /*
     *
     * 창업산업진흥원
     * http://cwip.or.kr/
     *  */

    private String url = "http://cwip.or.kr/bbs/board.php?bo_table=b0201&sfl=wr_9&stx=2022&sop=and&page=";
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
        supportVo.setTitle("창업산업진흥원");
        supportVo.setUrl("http://cwip.or.kr/");
        supportVo.setLocCode("C82");
        supportVo.setActiveYn("Y");
        supportVo.setErrorYn("N");
        List<SupportVo> supportVos = new ArrayList<>();


        for (int i=page; i>0; i--) {

            driver.get(url+i);
            Thread.sleep(1000);

            List <WebElement> list = driver.findElements(By.xpath("//*[@id=\"fboardlist\"]/div[1]/table/tbody/tr"));

            // 공고가 가변적이기 때문에 리스트 사이즈 구해서 for문 돌리기
            int k = list.size();
            for(int j=1; j<k; j++) {
                    try {

                        WebElement urlXpath = driver.findElement(By.xpath("//*[@id=\"fboardlist\"]/div[1]/table/tbody/tr["+ j +"]/td[2]/a"));
                        WebElement statusXpath = driver.findElement(By.xpath("//*[@id=\"fboardlist\"]/div[1]/table/tbody/tr["+ j +"]/td[6]/span"));
                        SupportVo vo = new SupportVo();
                        String bodyurl = urlXpath.getAttribute("href").substring(0 ,urlXpath.getAttribute("href").lastIndexOf("page") - 1);
                        String status = statusXpath.getText();

                        if(status.equals("진행중")){
                            driver.navigate().to(bodyurl);
                            WebElement titleXpath = driver.findElement(By.xpath("//*[@id=\"bo_v_title\"]"));
                            String title = titleXpath.getText().substring(titleXpath.getText().lastIndexOf("|") + 2, titleXpath.getText().length());

                            vo.setTargetName("창업산업진흥원");
                            vo.setTargetCatName("-");
                            vo.setLocCode("C82");
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

                        driver.navigate().to("http://cwip.or.kr/bbs/board.php?bo_table=b0201&sfl=wr_9&stx=2022&sop=and&page=1");

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

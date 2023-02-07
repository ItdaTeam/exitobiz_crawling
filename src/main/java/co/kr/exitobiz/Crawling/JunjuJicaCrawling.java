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
public class JunjuJicaCrawling implements Crawling {

    @Autowired
    CrawlingMapper crawlingMapper;

    @Autowired
    Environment environment;

    /*
     *
     * 전주정보문화산업진흥원
     * https://www.jica.or.kr/
     *  */

    private String url = "https://www.jica.or.kr/2016/inner.php?sMenu=A1000&pno=";
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
        options.addArguments("--headless", "--disable-gpu","--no-sandbox");
        options.addArguments("window-size=1920x1080");
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.36");
        options.addArguments("lang=ko_KR");

        ChromeDriverService service = null;
        WebDriver driver = null;

        SupportVo supportVo = new SupportVo();
        supportVo.setTitle("전주정보문화산업진흥원");
        supportVo.setUrl("https://www.jica.or.kr/");
        supportVo.setLocCode("C063");
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

            for (int i=page; i>0; i--) {

                driver.get(url + i);
                Thread.sleep(1000);

                for(int j=1; j<10; j++) {
                        try {

                            WebElement titleXpath = driver.findElement(By.xpath("//*[@id=\"Start\"]/div/div/div[1]/table/tbody/tr["+ j +"]/td[2]/span/a"));
                            WebElement statusXpath = driver.findElement(By.xpath("//*[@id=\"Start\"]/div/div/div[1]/table/tbody/tr["+j+"]/td[1]"));

                            if(statusXpath.getText().equals("접수중")){
                                String title = titleXpath.getText().trim();
                                String bodyurl = titleXpath.getAttribute("href").replace("pno=1&","");

                                SupportVo vo = new SupportVo();

                                vo.setTargetName("전주정보문화산업진흥원");
                                vo.setTargetCatName("-");
                                vo.setLocCode("C063");
                                vo.setSiTitle(title);
                                vo.setMobileUrl(bodyurl);
                                vo.setPcUrl("-");

                                HashMap<String, String> params = new HashMap<>();
        //                        params.put("bodyurl", bodyurl);
                                params.put("title",title);
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

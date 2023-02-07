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
public class SeoulseCrawling implements Crawling {

    @Autowired
    CrawlingMapper crawlingMapper;

    @Autowired
    Environment environment;

    /*
     * 서울사회적기업협의회
     * http://www.seoulse.kr/
     *  */

    private String url = "http://www.seoulse.kr/bbs/board.php?bo_table=notice&page=";
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


        SupportVo supportVo = new SupportVo();
        supportVo.setTitle("서울사회적기업협의회");
        supportVo.setUrl("http://www.seoulse.kr/");
        supportVo.setLocCode("C02");
        supportVo.setActiveYn("Y");
        supportVo.setErrorYn("N");



        ChromeDriverService service = null;
        WebDriver driver = null;

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
                for(int j=1; j<16; j++) {
                    try {
                        WebElement bodyUrlXpath = driver.findElement(By.xpath("//*[@id=\"list-body\"]/li[not(contains(@class, 'bg-light'))]["+j+"]/div[2]/a"));

                        String title = bodyUrlXpath.getText();
                        String url = bodyUrlXpath.getAttribute("href");

                        Pattern p = Pattern.compile("\\[(.*?)\\]");
                        Matcher m = p.matcher(title);
                        ArrayList<String> pattern = new ArrayList<String>();

                        while (m.find()) {
                            pattern.add(m.group());
                        }

                        driver.navigate().to(url + i);

                        /* 글 상세페이지 들어가므로 1500 기다림 */
                        Thread.sleep(1500);

                        WebElement targettypeXpath = driver.findElement(By.xpath("//*[@id=\"page\"]/div[1]/section/div/div/section/article/div[1]/div[1]/div/span[2]"));

                        String targettype = targettypeXpath.getText();

                        SupportVo vo = new SupportVo();
                        vo.setTargetName("서울사회적기업협의회");
                        vo.setTargetCatName("-");
                        vo.setLocCode("C02");
                        vo.setSiTitle(title);
                        vo.setMobileUrl(url);
                        vo.setPcUrl("-");

                        HashMap<String, String> params = new HashMap<>();
    //                    params.put("bodyurl", url);
                        params.put("title",title);
                        boolean isUrl = crawlingMapper.isUrl(params);
                        if (!isUrl) {
                            supportVos.add(vo);
                        }

                        driver.navigate().back();

                    } catch (Exception e) {
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
        }finally{
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

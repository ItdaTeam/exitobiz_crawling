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
public class GyeonggiGconCrawling implements Crawling {

    @Autowired
    CrawlingMapper crawlingMapper;

    @Autowired
    Environment environment;

    /*
     *
     * 경기콘텐츠진흥원
     * https://www.gcon.or.kr
     *  */

    private String url = "https://www.gcon.or.kr/gcon/business/gconNotice/list.do?menuNo=200061&pageIndex=";
    private int page = 2;

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
//        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--headless", "--disable-gpu","--no-sandbox");
        options.addArguments("window-size=1920x1080");
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.36");
        options.addArguments("lang=ko_KR");

        ChromeDriverService service = null;
        WebDriver driver = null;

        SupportVo supportVo = new SupportVo();
        supportVo.setTitle("경기콘텐츠진흥원");
        supportVo.setUrl("https://www.gcon.or.kr");
        supportVo.setLocCode("C031");
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

                for(int j=1; j<11; j++) {
                        try {

                            WebElement titleXpath = driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/table/tbody/tr["+j+"]/td[3]"));
                            WebElement urlXpath = driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/table/tbody/tr["+j+"]/td[3]/a[1]"));
                            WebElement typeXpath = driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/table/tbody/tr["+j+"]/td[2]/strong"));

                            if(typeXpath.getText().equals("접수중")){

                                SupportVo vo = new SupportVo();
                                String title = titleXpath.getText();
                                String bodyurl = urlXpath.getAttribute("href").substring(0,urlXpath.getAttribute("href").lastIndexOf("&"));

                                vo.setTargetName("경기콘텐츠진흥원");
                                vo.setTargetCatName("-");
                                vo.setLocCode("C031");
                                vo.setSiTitle(title);
                                vo.setMobileUrl(bodyurl);
                                vo.setPcUrl("-");

                                HashMap<String, String> params = new HashMap<>();
//                                params.put("bodyurl", bodyurl);
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

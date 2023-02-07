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
public class YouthSeoulCrawling implements Crawling {

    @Autowired
    CrawlingMapper crawlingMapper;

    @Autowired
    Environment environment;

    /*
     * 서울청년정책
     * https://youth.seoul.go.kr/
     *  */

    private String url = "https://youth.seoul.go.kr/site/main/youth/politics/user/list?cp=";
    private int page = 3;

    @Override
    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public void craw() {

        String driverPath = environment.getProperty("chrome.driver.path");
        File driverFile = new File(String.valueOf(driverPath));

        String driverFilePath = driverFile.getAbsolutePath();
        if (!driverFile.exists() && driverFile.isFile()) {
            throw new RuntimeException("Not found");
        }

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--disable-gpu","--no-sandbox");
        options.addArguments("window-size=1920x1080");
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.36");
        options.addArguments("lang=ko_KR");

        SupportVo supportVo = new SupportVo();
        supportVo.setTitle("서울청년정책");
        supportVo.setUrl("https://youth.seoul.go.kr/");
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
            WebDriverWait wait = new WebDriverWait(driver, 10);
            List<SupportVo> supportVos = new ArrayList<>();

            try {
                for (int i=page; i>0; i--) {

                    driver.get(url + i);

                    for(int j=1; j<11; j++) {
                        WebElement titleXpath = driver.findElement(By.xpath("//*[@id=\"searchMove\"]/li["+j+"]/div/a"));
                        WebElement endTimeXpath = driver.findElement(By.xpath("//*[@id=\"searchMove\"]/li["+j+"]/div/ul/li[1]"));

                        String title = titleXpath.getText();
                        String endtime = endTimeXpath.getText().replaceAll("신청기간 : ","");

                        String url = titleXpath.getAttribute("onclick");

                        String[] split = url.split(",");
                        String intStr = split[0].replaceAll("[^0-9]", "");


                        Pattern p = Pattern.compile("\\[(.*?)\\]");
                        Matcher m = p.matcher(title);
                        ArrayList<String> pattern = new ArrayList<String>();

                        while (m.find()) {
                            pattern.add(m.group());
                        }

                        String bodyurl = "https://youth.seoul.go.kr/site/main/youth/politics/user/detail/" + intStr;
                        String targettype = pattern.get(0).replaceAll("\\[", "").replaceAll("\\]", "");

                        SupportVo vo = new SupportVo();
                        vo.setTargetName("서울청년정책");
                        vo.setTargetCatName("-");
                        vo.setLocCode("C02");
                        vo.setSiTitle(title);
                        vo.setMobileUrl(bodyurl);
                        vo.setPcUrl("-");

                        HashMap<String, String> params = new HashMap<>();
                        params.put("title",title);
                        boolean isUrl = crawlingMapper.isUrl(params);
                        if (!isUrl) {
                            supportVos.add(vo);
                        }
                    }

                    Thread.sleep(500);
                }

            } catch (Exception e) {
                supportVo.setErrorYn("Y");
                e.printStackTrace();
                crawlingMapper.createMaster(supportVo);
            } finally {
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

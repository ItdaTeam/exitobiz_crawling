package co.kr.exitobiz.Crawling;

import co.kr.exitobiz.Mappers.Api.CrawlingMapper;
import co.kr.exitobiz.Vo.Cms.SupportVo;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class KoreaTrCrawling implements Crawling {

    @Autowired
    CrawlingMapper crawlingMapper;

    @Autowired
    Environment environment;

    /*
     * 한국화학융합시험연구원
     * http://www.ktr.or.kr
     *  */

    private String url = "http://www.ktr.or.kr/customer-service/communityid/notice/list.do?pageIndex=";
    private int page = 2;  //게시글이 많지 않아서 2페이지만 우선 크롤링함.

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

        SupportVo supportVo = new SupportVo();
        supportVo.setTitle("한국화학융합시험연구원");
        supportVo.setUrl("http://www.ktr.or.kr");
        supportVo.setLocCode("C82");
        supportVo.setActiveYn("Y");
        supportVo.setErrorYn("N");


        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--disable-gpu","-no-sandbox");
        options.addArguments("window-size=1920x1080");
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.36");
        options.addArguments("lang=ko_KR");

        ChromeDriverService service = null;
        WebDriver driver = null;
        try {
            service = new ChromeDriverService.Builder()
                    .usingDriverExecutable(driverFile)
                    //.usingPort(5000)
                    .usingAnyFreePort()
                    .build();

            service.start();
            try{
                driver = new ChromeDriver(service, options);
            }catch(Exception e){
            }

            List<SupportVo> supportVos = new ArrayList<>();
            for (int i = page; i > 0; i--) {
            driver.get(url+i);
                List<WebElement> list = driver.findElements(By.xpath("//*[@id=\"wrapper\"]/div[2]/div[5]/div/div[1]/table/tbody/tr"));
                Thread.sleep(1000);
                for (int j = 1; j <= list.size(); j++) {
                    try {
                        WebElement titleXpath = driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[2]/div[5]/div/div[1]/table/tbody/tr["+j+"]/td[2]/a"));

                        Pattern typePattern = Pattern.compile("\\[(.*?)\\]"); // 대괄호안에 문자 뽑기
                        Matcher typeMatcher = typePattern.matcher(titleXpath.getText());
                        ArrayList<String> typePatternArray = new ArrayList<String>();

                        while (typeMatcher.find()) {
                            typePatternArray.add(typeMatcher.group());
                        }

                        SupportVo vo = new SupportVo();
                        String title = titleXpath.getText();
                        String url = titleXpath.getAttribute("onclick");
                        String bodyUrl = "http://www.ktr.or.kr/customer-service/communityid/notice/view.do?detailsKey="+ url.replace("commonCtrl.setViewPage('detailsKey', '", "").replace("', './view.do');","");
                        vo.setTargetName("한국화학융합시험연구원");
                        vo.setTargetCatName("-");
                        vo.setLocCode("C82");
                        vo.setSiTitle(title);
                        vo.setMobileUrl(bodyUrl);
                        vo.setPcUrl("-");

                        HashMap<String, String> params = new HashMap<>();
                        params.put("title", title);
                        boolean isUrl = crawlingMapper.isUrl(params);
                        if (!isUrl) {
                            supportVos.add(vo);
                        }
                    } catch (Exception e) {
                        supportVo.setErrorYn("Y");
                    }
                }

                Thread.sleep(500);
            }

            /* 빈 리스트가 아니면 크레이트 */
            if (!supportVos.isEmpty()) {
                try {
                    crawlingMapper.create(supportVos);
                    crawlingMapper.createMaster(supportVo);
                } catch (Exception e) {
                    supportVo.setErrorYn("Y");
                    crawlingMapper.createMaster(supportVo);
                }
            } else {
                supportVo.setErrorYn("N");
                crawlingMapper.createMaster(supportVo);
            }

        } catch (Exception e) {
            e.printStackTrace();
            supportVo.setErrorYn("Y");
            crawlingMapper.createMaster(supportVo);
        } finally {
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

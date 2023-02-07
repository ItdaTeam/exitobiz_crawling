package co.kr.exitobiz.Crawling;

import co.kr.exitobiz.Mappers.Api.CrawlingMapper;
import co.kr.exitobiz.Util.Util;
import co.kr.exitobiz.Vo.Cms.SupportVo;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class GgEnergyCrawling implements Crawling {

    @Autowired
    CrawlingMapper crawlingMapper;

    @Autowired
    Environment environment;

    /*
     * 경기환경에너지진흥원
     * https://www.ggenergy.or.kr/
     *  */

    private String url = "https://www.ggenergy.or.kr/energy/notice";
    private int page = 1;  //게시글이 많지 않아서 첫 페이지만 우선 크롤링함.

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
        supportVo.setTitle("경기환경에너지진흥원");
        supportVo.setUrl("https://www.ggenergy.or.kr");
        supportVo.setLocCode("C02");
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

            driver.get(url );
            for (int i = page; i > 0; i--) {
                ((JavascriptExecutor)driver).executeScript("javascript:pagingUtil.pageSubmit('"+i+"');");

                List<WebElement> list = driver.findElements(By.xpath("//*[@id=\"wrap_scon\"]/div[2]/div/div[2]/table/tbody/tr"));
                Thread.sleep(1000);
                for (int j = 1; j <= list.size(); j++) {
                    try {
                        WebElement titleXpath = driver.findElement(By.xpath("//*[@id=\"wrap_scon\"]/div[2]/div/div[2]/table/tbody/tr["+j+"]/td[3]/a"));

                        Pattern typePattern = Pattern.compile("\\[(.*?)\\]"); // 대괄호안에 문자 뽑기
                        Matcher typeMatcher = typePattern.matcher(titleXpath.getText());
                        ArrayList<String> typePatternArray = new ArrayList<String>();

                        while (typeMatcher.find()) {
                            typePatternArray.add(typeMatcher.group());
                        }

                        SupportVo vo = new SupportVo();
                        String title = titleXpath.getText();
                        String url = titleXpath.getAttribute("href");

                        String bodyUrl = "https://www.ggenergy.or.kr/energy/notice/view?board_seq="+url.replace("javascript:goBoardView('", "").replace("');","");
                        vo.setTargetName("경기환경에너지진흥원");
                        vo.setTargetCatName("-");
                        vo.setLocCode("C031");
                        vo.setSiTitle(title);
                        vo.setMobileUrl(bodyUrl);
                        vo.setPcUrl("-");

                        HashMap<String, String> params = new HashMap<>();
                        params.put("title", title);
                        params.put("bodyUrl",bodyUrl);
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

                List<SupportVo> list = supportVos.stream().filter(Util.distinctByKey(o ->o.getMobileUrl())).collect(Collectors.toList());
                try {
                    crawlingMapper.create(list);
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

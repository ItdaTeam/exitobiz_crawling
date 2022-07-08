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
public class SeoulPiportalCrawling implements Crawling {

    @Autowired
    CrawlingMapper crawlingMapper;

    @Autowired
    Environment environment;

    /*
     * 고려대학교 크림슨창업지원단
     * https://piportal.korea.ac.kr/
     *  */

    private String url = "https://piportal.korea.ac.kr/front/board/list.do?sep_cd=NOTICE&current_page_no=";
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

        WebDriver driver = new ChromeDriver(service,options);

        SupportVo supportVo = new SupportVo();
        supportVo.setTitle("고려대학교크림슨창업지원단");
        supportVo.setUrl("https://piportal.korea.ac.kr/");
        supportVo.setLocCode("C02");
        supportVo.setActiveYn("Y");
        supportVo.setErrorYn("N");
        List<SupportVo> supportVos = new ArrayList<>();


        for (int i=page; i>0; i--) {

            driver.get(url + i);
            Thread.sleep(1000);
            List <WebElement> col = driver.findElements(By.xpath("/html/body/div[3]/div/div/div[2]/div[2]/table/tbody/tr"));

            int k = col.size();

            for(int j=1; j<k; j++) {
                    try {

                        WebElement titleXpath = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[2]/div[2]/table/tbody/tr["+ j +"]/td[2]/a/span/span"));
                        WebElement urlXpath = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[2]/div[2]/table/tbody/tr["+ j +"]/td[2]/a"));

                        Pattern typePattern = Pattern.compile("\\[(.*?)\\]"); // 대괄호안에 문자 뽑기
                        Matcher typeMatcher = typePattern.matcher(titleXpath.getText());
                        ArrayList<String> typePatternArray = new ArrayList<String>();

                        while (typeMatcher.find()) {
                            typePatternArray.add(typeMatcher.group());
                        }

                        SupportVo vo = new SupportVo();

                        String title = titleXpath.getText();
                        String url = urlXpath.getAttribute("href");
                        String bodyurl = "https://piportal.korea.ac.kr/front/board/view.do;" + url.substring(url.lastIndexOf("?"),url.length());

                        vo.setTargetName("고려대학교크림슨창업지원단");
                        vo.setTargetCatName("-");
                        vo.setLocCode("C02");
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

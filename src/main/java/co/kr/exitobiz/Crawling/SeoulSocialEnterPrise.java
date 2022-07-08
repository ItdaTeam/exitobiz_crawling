package co.kr.exitobiz.Crawling;

import co.kr.exitobiz.Mappers.Api.CrawlingMapper;
import co.kr.exitobiz.Vo.Cms.SupportVo;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
public class SeoulSocialEnterPrise implements Crawling {

    @Autowired
    CrawlingMapper crawlingMapper;

    @Autowired
    Environment environment;

    /*
     * 한국사회적기업진흥원
     * https://www.socialenterprise.or.kr/
     *  */

    private String url = "https://www.socialenterprise.or.kr/social/board/list.do?m_cd=D019&board_code=BO02&category_id=CA92&category_sub_id=&com_certifi_num=&selectyear=&magazine=&search_word=&search_type=&mode=list&pg=";
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
                .usingPort(5000)
                .build();

        try {
            service.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        WebDriver driver = new ChromeDriver(service,options);

        List<SupportVo> supportVos = new ArrayList<>();

        SupportVo supportVo = new SupportVo();
        supportVo.setTitle("한국사회적기업진흥원");
        supportVo.setUrl("https://www.socialenterprise.or.kr");
        supportVo.setLocCode("C02");
        supportVo.setActiveYn("Y");
        supportVo.setErrorYn("N");


        for (int i=page; i>0; i--) {
            driver.get(url+i);

            Thread.sleep(1500);

            for(int j=3; j<11; j++) {

                try {

                    WebElement titleXpath = driver.findElement(By.xpath("//*[@id=\"contents\"]/section/table/tbody/tr["+j+"]/td[2]/*/a"));

                    String baseUrl = "https://www.socialenterprise.or.kr/social/board/view.do?m_cd=D019&pg=2&board_code=BO02&category_id=CA92&category_sub_id=&com_certifi_num=&selectyear=&magazine=&title=&search_word=&search_type=&seq_no=";

                    /* 숫자만 남기기 */
                    String url = titleXpath.getAttribute("onclick");
                    String intStr = url.replaceAll("goViewPage2", "").replaceAll("[^0-9]", "");

                    String bodyUrl = baseUrl + intStr;

                    String title = titleXpath.getText();

                    SupportVo vo = new SupportVo();
                    vo.setTargetName("한국사회적기업진흥원");
                    vo.setTargetCatName("-");
                    vo.setLocCode("C02");
                    vo.setSiTitle(title);
                    vo.setMobileUrl(bodyUrl);
                    vo.setPcUrl("-");


                    HashMap<String, String> params = new HashMap<>();
                    params.put("bodyurl", bodyUrl);
                    boolean isUrl = crawlingMapper.isUrl(params);
                    if (!isUrl) {
                        supportVos.add(vo);
                    }


                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    supportVo.setErrorYn("Y");
                }

            }

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

package co.kr.exitobiz.Crawling;

import co.kr.exitobiz.Mappers.Api.CrawlingMapper;
import co.kr.exitobiz.Util.Util;
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
import java.util.stream.Collectors;

@Component
public class JeonnamJciaCrawling implements Crawling {

    @Autowired
    CrawlingMapper crawlingMapper;

    @Autowired
    Environment environment;

    /*
     *
     * 전남정보문화산업진흥원
     * http://www.jcia.or.kr/
     *  */

    private String url = "http://www.jcia.or.kr/cf/information/business.do?PageIndex=";
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
        supportVo.setTitle("전남정보문화산업진흥원");
        supportVo.setUrl("http://www.jcia.or.kr/");
        supportVo.setLocCode("C061");
        supportVo.setActiveYn("Y");
        supportVo.setErrorYn("N");

        try {
            service = new ChromeDriverService.Builder()
                .usingDriverExecutable(driverFile)
                .usingAnyFreePort()
                .build();

            service.start();
            driver = new ChromeDriver(service, options);

            List<SupportVo> supportVos = new ArrayList<>();

            for (int i=page; i>0; i--) {

                driver.get(url + i);
                Thread.sleep(1000);

                List <WebElement> list = driver.findElements(By.xpath("//*[@id=\"Form\"]/div[1]/div[2]/table/tbody/tr"));

                // 공고가 가변적이기 때문에 리스트 사이즈 구해서 for문 돌리기
                int k = list.size();

                for(int j=1; j<=k; j++) {
                        try {
                            WebElement titleXpath = driver.findElement(By.xpath("//*[@id=\"Form\"]/div[1]/div[2]/table/tbody/tr["+ j +"]/td[3]/a"));

                            WebElement statusXpath = driver.findElement(By.xpath("//*[@id=\"Form\"]/div[1]/div[2]/table/tbody/tr["+j+"]/td[4]"));

                            if(statusXpath.getText().equals("진행중")){
                                String title = titleXpath.getText().trim();
                                String url = titleXpath.getAttribute("onclick").replace("pf_DetailMove","").replace("'","").replace("(","").replace(")","");
                                String bodyurl = "http://www.jcia.or.kr/cf/Board/" + url + "/detailView.do";

                                SupportVo vo = new SupportVo();

                                vo.setTargetName("전남정보문화산업진흥원");
                                vo.setTargetCatName("-");
                                vo.setLocCode("C061");
                                vo.setSiTitle(title);
                                vo.setMobileUrl(bodyurl);
                                vo.setPcUrl("-");

                                HashMap<String, String> params = new HashMap<>();
    //                            params.put("bodyurl", bodyurl);
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
                    List<SupportVo> list = supportVos.stream().filter(Util.distinctByKey(o ->o.getSiTitle())).collect(Collectors.toList()); //페이지별 공지 때문에 url 기준 중복 제거.
                    crawlingMapper.create(list);
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

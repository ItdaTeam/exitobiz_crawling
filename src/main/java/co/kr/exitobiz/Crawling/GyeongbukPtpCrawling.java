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
public class GyeongbukPtpCrawling implements Crawling {

    @Autowired
    CrawlingMapper crawlingMapper;

    @Autowired
    Environment environment;

    /*
     *
     * 포항테크노파크
     * https://www.ptp.or.kr/
     *  */

    private String url = "https://www.ptp.or.kr/main/board/index.do?menu_idx=116&manage_idx=15&board_idx=0&old_menu_idx=0&old_manage_idx=0&old_board_idx=0&group_depth=0&parent_idx=0&group_idx=0&group_ord=0&search.category1=&search_type=title%2Bcontent&search_text=&memo1=&memo2=&bid_search=ongoing&_code_id_arr=on&order_name=add_date&order_type=DESC&rowCount=10&dtl_opt_view=true&viewPage=";
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
                //.usingPort(5000)
                .usingAnyFreePort()
                .build();

        try {
            service.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        WebDriver driver = new ChromeDriver(service,options);

        SupportVo supportVo = new SupportVo();
        supportVo.setTitle("포항테크노파크");
        supportVo.setUrl("https://www.ptp.or.kr/");
        supportVo.setLocCode("C054");
        supportVo.setActiveYn("Y");
        supportVo.setErrorYn("N");
        List<SupportVo> supportVos = new ArrayList<>();

        Thread.sleep(1000);
        for (int i=page; i>0; i--) {
            driver.get(url+i);

            List <WebElement> col = driver.findElements(By.xpath("//*[@id=\"board\"]/div[4]/table/tbody/tr"));

            //공지사항때문에 행사이즈 구해서 for문 돌리기
            int k = col.size();

            Thread.sleep(1000);

            for(int j=1; j<k; j++) {
                    try {
                        WebElement urlXpath = driver.findElement(By.xpath("//*[@id=\"board\"]/div[4]/table/tbody/tr["+ j +"]/td[3]/a"));
                        WebElement titleXpath = driver.findElement(By.xpath("//*[@id=\"board\"]/div[4]/table/tbody/tr["+ j +"]"));

                        String title = titleXpath.getAttribute("title");
                        String url = "https://www.ptp.or.kr/main/board/view.do?menu_idx=116&manage_idx=15&board_idx=";
                        String bodyurl = url + urlXpath.getAttribute("onclick").replace("viewBoard","").replace("(","").replace(")","").replace(";","");

                        SupportVo vo = new SupportVo();

                        vo.setTargetName("포항테크노파크");
                        vo.setTargetCatName("-");
                        vo.setLocCode("C054");
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

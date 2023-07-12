package co.kr.exitobiz.Crawling;

import co.kr.exitobiz.Mappers.Api.CrawlingMapper;
import co.kr.exitobiz.Vo.Cms.SupportVo;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class SeoulBizinfoCrawling implements Crawling {

    @Autowired
    CrawlingMapper crawlingMapper;

    @Autowired
    Environment environment;

    /*
     * 기업마당
     * https://www.bizinfo.go.kr/web/index.do
     *  */

//    private String url = "https://www.bizinfo.go.kr/web/lay1/bbs/S1T122C128/AS/74/list.do?rows=15&cpage=";
    private int page = 10;

    @Override
    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public void craw() throws InterruptedException {

//        String driverPath = "C:\\Users\\Administrator\\Downloads\\chromedriver_win32\\chromedriver.exe";
//        File driverFile = new File(String.valueOf(driverPath));

//        if (!driverFile.exists() && driverFile.isFile()) {
//            throw new RuntimeException("Not found");
//        }

//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless", "--disable-gpu","--no-sandbox");
//        options.addArguments("window-size=1920x1080");
//        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.36");
//        options.addArguments("lang=ko_KR");
//
//        ChromeDriverService service = null;
//        WebDriver driver = null;

        SupportVo supportVo = new SupportVo();
        supportVo.setTitle("기업마당");
        supportVo.setUrl("https://www.bizinfo.go.kr/web/index.do");
        supportVo.setLocCode("C82");
        supportVo.setActiveYn("Y");
        supportVo.setErrorYn("N");

        try {




//            service = new ChromeDriverService.Builder()
//                .usingDriverExecutable(driverFile)
//                //.usingPort(5000)
//                .usingAnyFreePort()
//                .build();
//
//            service.start();
//            driver = new ChromeDriver(service,options);

            List<SupportVo> supportVos = new ArrayList<>();

//            Date today = new Date();
//            today = new Date(today.getTime() - (1000*60*60*24-1));
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
//            String yesterday = sdf.format(today);


            //API 호출
            URI uri = new URI("https://www.bizinfo.go.kr/uss/rss/bizinfoApi.do?crtfcKey=t4ot3o&dataType=json&pageUnit=150&pageIndex=1");
            uri = new URIBuilder(uri).build();



            CloseableHttpClient httpClient = HttpClients.custom().setMaxConnTotal(100).setMaxConnPerRoute(100).build();

            HttpResponse httpResponse = httpClient.execute(new HttpGet(uri));
            HttpEntity entity = httpResponse.getEntity();
            String content = EntityUtils.toString(entity);


            JSONParser jsonParser1 = new JSONParser();
            JSONObject jObject = (JSONObject) jsonParser1.parse(content);
            JSONArray jArray = (JSONArray) jObject.get("jsonArray");

            for (int i=0; i<jArray.size(); i++) {
                JSONObject obj = (JSONObject) jArray.get(i);

//                driver.get(url + i);
                Thread.sleep(1000);
//                for(int j=1; j<16; j++) {
//                    WebElement dateXpath = driver.findElement(By.xpath("//*[@id=\"articleSearchForm\"]/div[3]/div[3]/table/tbody/tr["+ j +"]/td[6]"));
//                    String date = dateXpath.getText();
    //                if(date.equals(yesterday)){
                        try {
//                            WebElement titleXpath = driver.findElement(By.xpath("//*[@id=\"articleSearchForm\"]/div[2]/div[4]/table/tbody/tr["+ j +"]/td[3]/a"));
                            SupportVo vo = new SupportVo();

//                            String title = titleXpath.getText();

                            //기업마당 개편에따른 URL 중복문제를 해결하기위해 url을 잘라서 저장함
//                            String bodyurl = titleXpath.getAttribute("href").substring(0, 93);


                            //분류코드
                            String targetCatName = null;
                            String targetCatCd = null;
                            String apiTargetCatName = (String) obj.get("pldirSportRealmLclasCodeNm");

                            if(apiTargetCatName.equals("창업") || apiTargetCatName.equals("경영")){
                                targetCatName = "경영•사업화•창업";
                                targetCatCd = "02";
                            }else if(apiTargetCatName.equals("기술")){
                                targetCatName = "기술개발•R&D";
                                targetCatCd = "07";
                            }else if(apiTargetCatName.equals("수출") || apiTargetCatName.equals("내수")){
                                targetCatName = "판로•해외진출•수출";
                                targetCatCd = "11";
                            }else if(apiTargetCatName.equals("인력")){
                                targetCatName = "인력";
                                targetCatCd = "04";
                            }else if(apiTargetCatName.equals("금융")){
                                targetCatName = "융자•금융";
                                targetCatCd = "09";
                            }else if(apiTargetCatName.equals("기타")){
                                targetCatName ="기타";
                                targetCatCd = "10";
                            }

                            //지역코드
                            String locCode = null;
                            String apiLocName = (String) obj.get("jrsdInsttNm");

                            if(apiLocName.contains("전라남도")){
                                if(locCode == null) locCode = "C061";
                                else locCode += ",C061";
                            }
                            if(apiLocName.contains("충청북도")){
                                if(locCode == null) locCode = "C043";
                                else locCode += ",C043";
                            }
                             if(apiLocName.contains("광주광역시")){
                                if(locCode == null) locCode = "C062";
                                else locCode += ",C062";
                            }
                             if(apiLocName.contains("경기도")){
                                if(locCode == null) locCode = "C031";
                                else locCode += ",C031";
                            }
                             if(apiLocName.contains("경상북도")){
                                 if(locCode == null) locCode = "C054";
                                 else locCode += ",C054";
                            }
                             if(apiLocName.contains("강원특별자치도")){
                                 if(locCode == null) locCode = "C033";
                                 else locCode += ",C033";
                             }
                             if(apiLocName.contains("대구광역시")){
                                 if(locCode == null) locCode = "C053";
                                 else locCode += ",C053";
                             }
                             if(apiLocName.contains("인천광역시")){
                                 if(locCode == null) locCode = "C032";
                                 else locCode += ",C032";
                             }
                             if(apiLocName.contains("경상남도")){
                                 if(locCode == null) locCode = "C055";
                                 else locCode += ",C055";
                             }
                             if(apiLocName.contains("울산광역시")){
                                 if(locCode == null) locCode = "C052";
                                 else locCode += ",C052";
                             }
                             if(apiLocName.contains("서울특별시")){
                                 if(locCode == null) locCode = "C02";
                                 else locCode += ",C02";
                             }
                             if(apiLocName.contains("대전광역시")){
                                 if(locCode == null) locCode = "C042";
                                 else locCode += ",C042";
                             }
                             if(apiLocName.contains("충청남도")){
                                 if(locCode == null) locCode = "C041";
                                 else locCode += ",C041";
                             }
                             if(apiLocName.contains("세종")){
                                 if(locCode == null) locCode = "C044";
                                 else locCode += ",C044";
                             }
                             if(apiLocName.contains("전라북도")){
                                 if(locCode == null) locCode = "C063";
                                 else locCode += ",C063";
                             }
                             if(apiLocName.contains("부산광역시")){
                                 if(locCode == null) locCode = "C051";
                                 else locCode += ",C051";
                             }
                             if(apiLocName.contains("제주특별자치도")){
                                 if(locCode == null) locCode = "C064";
                                 else locCode += ",C064";
                             }
                             if(locCode == null) locCode = "C82";

                            //사업자형태
                            String businessType = null ;
                            String apiTrgetNm = (String) obj.get("trgetNm");
                            String apiHashtags = (String) obj.get("hashtags");
                            if(apiHashtags.contains("예비창업") || apiTrgetNm.contains("예비창업")) businessType = "02,03";
                            else if(!apiHashtags.contains("예비창업") && !apiTrgetNm.contains("예비창업")) businessType = "03";

                            //기업형태
                            String companyType = null;

                            Stream<String> stream = Stream.of(apiHashtags.split(","));
                            List<String> str = stream.collect(Collectors.toList());

                            str.replaceAll(v -> v.equals("마을기업") ? "마을" : v );
                            str.replaceAll(v -> v.equals("장애인기업") ? "장애인" : v);

                            if(str.contains("마을") || str.contains("장애인") || str.contains("청년로컬크리에이터") || str.contains("1인기업") || str.contains("크리에이터") || str.contains("1인 미디어") || str.contains("사회적협동조합")){
                                if(companyType == null) companyType = "07";
                                else companyType += ",07";
                            }

                            if(apiTrgetNm.contains("중소") || apiHashtags.contains("중소") || apiTrgetNm.contains("스타트업") || apiHashtags.contains("스타트업") || apiTrgetNm.contains("창업") || apiHashtags.contains("창업") || str.stream().filter(v -> v.substring(v.length()-2).equals("기업")).count() > 0){
                                if(companyType == null) companyType = "02";
                                else companyType += ",02";
                            }
                            if(apiTrgetNm.contains("소상공인") || apiTrgetNm.contains("소공인") || apiHashtags.contains("소상공인") || apiHashtags.contains("소공인")){
                                if(companyType == null) companyType = "04";
                                else companyType += ",04";
                            }
                            if(apiTrgetNm.contains("여성기업") || apiHashtags.contains("여성기업")){
                                if(companyType == null) companyType = "05";
                                else companyType += ",05";
                            }
                            if(apiTrgetNm.contains("사회적기업") || apiHashtags.contains("사회적기업")){
                                if(companyType == null) companyType = "06";
                                else companyType += ",06";
                            }
                            if(apiTrgetNm.contains("중견") || apiHashtags.contains("중견")){
                                if(companyType == null) companyType = "08";
                                else companyType += ",08";
                            }
                            if(companyType == null) companyType = "07";


                            vo.setTargetName("기업마당");
                            vo.setSiActiveYn("Y");
                            vo.setTargetCatName(targetCatName);
                            vo.setTargetCatCd(targetCatCd);
                            vo.setLocCode(locCode);
                            vo.setStartPeriod("100");
                            vo.setSiTitle((String) obj.get("pblancNm"));
                            vo.setMobileUrl("https://www.bizinfo.go.kr" + obj.get("pblancUrl"));


                            String strDate = obj.get("reqstBeginEndDe").toString().split(" ~ ")[1];
                            SimpleDateFormat dtFormat = new SimpleDateFormat("yyyyMMdd");
                            SimpleDateFormat newDtFormat = new SimpleDateFormat("yyyy-MM-dd");
                            Date formatDate = dtFormat.parse(strDate);
                            vo.setSiEndDt(newDtFormat.format(formatDate));

                            vo.setHashtags((String) obj.get("hashtags"));
                            vo.setBusinessType(businessType);
                            vo.setCompanyType(companyType);
                            vo.setPcUrl("-");

                            HashMap<String, String> params = new HashMap<>();
                            params.put("bodyurl", "https://www.bizinfo.go.kr" + obj.get("pblancUrl"));
                            params.put("title", (String) obj.get("pblancNm"));
                            boolean isUrl = crawlingMapper.isUrl(params);
                            if (!isUrl) supportVos.add(vo);

                        } catch (Exception e) {
                            System.out.println(e);
                            supportVo.setErrorYn("Y");
                        }
    //                }
//                }

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
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
//            if(driver != null){
//                driver.close();
//                driver.quit();
//            }else{
//                supportVo.setErrorYn("Y");
//                crawlingMapper.createMaster(supportVo);
//            }
//            if(service != null){
//                service.stop();
//            }else{
//                supportVo.setErrorYn("Y");
//                crawlingMapper.createMaster(supportVo);
//            }
        }
    }


}

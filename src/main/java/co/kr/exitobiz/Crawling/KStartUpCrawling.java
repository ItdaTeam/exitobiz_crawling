package co.kr.exitobiz.Crawling;


import co.kr.exitobiz.Mappers.Api.CrawlingMapper;
import co.kr.exitobiz.Vo.Cms.SupportVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.XML;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class KStartUpCrawling implements Crawling {

    @Autowired
    CrawlingMapper crawlingMapper;

    @Autowired
    Environment environment;

    /*
    *
    *
    * K-StartUp
    * */

    private String url = "https://www.k-startup.go.kr/web/contents/bizpbanc-ongoing.do";
    private int page = 5;

    @Override
    public void setPage(int page) {
        this.page = page;
    }

    public String xmlToJson(String str){
        String output = null;
        try{
            String xml = str;
            org.json.JSONObject jObject = XML.toJSONObject(xml);
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            Object json = mapper.readValue(jObject.toString(), Object.class);
            output = mapper.writeValueAsString(json);

        }catch (Exception e){
            e.printStackTrace();
        }
        return output;
    }

    @Override
    public void craw() {
        String driverPath = environment.getProperty("chrome.driver.path");
        File driverFile = new File(String.valueOf(driverPath));

        if (!driverFile.exists() && driverFile.isFile()) {
            throw new RuntimeException("Not found");
        }

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--disable-gpu","--no-sandbox");
        options.addArguments("window-size=1920x1080");
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.O36");
        options.addArguments("lang=ko_KR");

        ChromeDriverService service = null;
        WebDriver driver = null;
        JavascriptExecutor jse = null;

        SupportVo supportVo = new SupportVo();
        supportVo.setTitle("K-Startup");
        supportVo.setUrl("https://www.k-startup.go.kr/");
        supportVo.setLocCode("C82");
        supportVo.setActiveYn("Y");
        supportVo.setErrorYn("N");



        try {
//            service = new ChromeDriverService.Builder()
//                    .usingDriverExecutable(driverFile)
//                    //.usingPort(5000)
//                    .usingAnyFreePort()
//                    .build();
//            service.start();
//            driver = new ChromeDriver(service,options);
//            jse = (JavascriptExecutor) driver;


            List<SupportVo> supportVos = new ArrayList<>();
            try {
                //API 호출
                URI uri = new URI("http://apis.data.go.kr/B552735/k-startup/kisedGWAPI/getAnnouncementList?serviceKey=J7tK4QjewyuyUgCX%2B8AToMhiQ971eia%2BPow%2BdppvNUJpJ%2FKVqs7b9bXoKujFAslaPIK8WdwZUwdTNqh2IjYg1g%3D%3D&pageNo=1&numOfRows=1000&openYn=Y");
                LocalDate now = LocalDate.now();
                LocalDate plusMonth = now.plusMonths(6);

                uri = new URIBuilder(uri).addParameter("startDate", String.valueOf(now).replaceAll("-", "")).addParameter("endDate", String.valueOf(plusMonth).replaceAll("-","")).build();
                CloseableHttpClient httpClient = HttpClients.custom().setMaxConnTotal(100).setMaxConnPerRoute(100).build();

                HttpResponse httpResponse = httpClient.execute(new HttpGet(uri));
                HttpEntity entity = httpResponse.getEntity();
                String content = xmlToJson(EntityUtils.toString(entity));

                JSONParser jsonParser1 = new JSONParser();
                JSONObject jObject = (JSONObject) jsonParser1.parse(content);

                JSONObject response = (JSONObject) jObject.get("response");

                JSONObject body = (JSONObject) response.get("body");
                JSONObject items = (JSONObject) body.get("items");
                JSONArray jArray = (JSONArray) items.get("item");

                for(int i=0; i<jArray.size(); i++){
                    JSONObject obj = (JSONObject) jArray.get(i);

                    SupportVo vo = new SupportVo();

                    //분류코드
                    String targetCatName = null;
                    String targetCatCd = null;
                    String apiTargetCatName = (String) obj.get("supporttype");

                    if(apiTargetCatName.equals("사업화") || apiTargetCatName.equals("시설ㆍ공간ㆍ보육") || apiTargetCatName.equals("멘토링ㆍ컨설팅") || apiTargetCatName.equals("정책자금") || apiTargetCatName.equals("행사ㆍ네트워크") || apiTargetCatName.equals("창업교육")){
                        targetCatName = "경영•사업화•창업";
                        targetCatCd = "02";
                    }else if(apiTargetCatName.contains("기술개발")){
                        targetCatName = "기술개발•R&D";
                        targetCatCd = "07";
                    }else if(apiTargetCatName.equals("글로벌") || apiTargetCatName.equals("판로ㆍ해외진출")){
                        targetCatName = "판로•해외진출•수출";
                        targetCatCd = "11";
                    }else if(apiTargetCatName.equals("인력")){
                        targetCatName = "인력";
                        targetCatCd = "04";
                    }else if(apiTargetCatName.equals("융자")){
                        targetCatName = "융자•금융";
                        targetCatCd = "09";
                    }

                    vo.setTargetCatName(targetCatName);
                    vo.setTargetCatCd(targetCatCd);

                    //지역코드
                    String locCode = null;
                    String apiLocName = (String) obj.get("areaname");

                    if(apiLocName.contains("전남")){
                        if(locCode == null) locCode = "C061";
                        else locCode += ",C061";
                    }
                    if(apiLocName.contains("충북")){
                        if(locCode == null) locCode = "C043";
                        else locCode += ",C043";
                    }
                    if(apiLocName.contains("광주")){
                        if(locCode == null) locCode = "C062";
                        else locCode += ",C062";
                    }
                    if(apiLocName.contains("경기")){
                        if(locCode == null) locCode = "C031";
                        else locCode += ",C031";
                    }
                    if(apiLocName.contains("경북")){
                        if(locCode == null) locCode = "C054";
                        else locCode += ",C054";
                    }
                    if(apiLocName.contains("강원")){
                        if(locCode == null) locCode = "C033";
                        else locCode += ",C033";
                    }
                    if(apiLocName.contains("대구")){
                        if(locCode == null) locCode = "C053";
                        else locCode += ",C053";
                    }
                    if(apiLocName.contains("인천")){
                        if(locCode == null) locCode = "C032";
                        else locCode += ",C032";
                    }
                    if(apiLocName.contains("경남")){
                        if(locCode == null) locCode = "C055";
                        else locCode += ",C055";
                    }
                    if(apiLocName.contains("울산")){
                        if(locCode == null) locCode = "C052";
                        else locCode += ",C052";
                    }
                    if(apiLocName.contains("서울")){
                        if(locCode == null) locCode = "C02";
                        else locCode += ",C02";
                    }
                    if(apiLocName.contains("대전")){
                        if(locCode == null) locCode = "C042";
                        else locCode += ",C042";
                    }
                    if(apiLocName.contains("충남")){
                        if(locCode == null) locCode = "C041";
                        else locCode += ",C041";
                    }
                    if(apiLocName.contains("세종")){
                        if(locCode == null) locCode = "C044";
                        else locCode += ",C044";
                    }
                    if(apiLocName.contains("전북")){
                        if(locCode == null) locCode = "C063";
                        else locCode += ",C063";
                    }
                    if(apiLocName.contains("부산")){
                        if(locCode == null) locCode = "C051";
                        else locCode += ",C051";
                    }
                    if(apiLocName.contains("제주")){
                        if(locCode == null) locCode = "C064";
                        else locCode += ",C064";
                    }

                    if(apiLocName.contains("전국")) locCode = "C82";

                    vo.setLocCode(locCode);

                    //사업자형태
                    String businessType = null ;
                    String apiTrgetNm = (String) obj.get("posttargetcomage");
                    if( apiTrgetNm.contains("예비창업자") && apiTrgetNm.contains("미만")) businessType = "02,03";
                    else if(apiTrgetNm.contains("미만")) businessType = "03";
                    else if(apiTrgetNm.contains("예비창업자")) businessType = "02";

                    vo.setBusinessType(businessType);

                    //창업기간
                    String startPeriod = null;
                    if(apiTrgetNm.contains("10년미만")) startPeriod = "100";
                    else if(apiTrgetNm.contains("7년미만")) startPeriod = "70";
                    else if(apiTrgetNm.contains("5년미만")) startPeriod = "50";
                    else if(apiTrgetNm.contains("3년미만")) startPeriod = "30";
                    else if(apiTrgetNm.contains("1년미만")) startPeriod = "10";

                    vo.setStartPeriod(startPeriod);

                    vo.setTargetName("K-Startup");
                    vo.setSiActiveYn("Y");

                    String title = obj.get("title").toString().replaceAll("&#40;","(").replaceAll("&#41;", ")")
                            .replaceAll("&amp;","&").replaceAll("&apos;","'").replaceAll("&lt;","<").replaceAll("&gt;",">");
                    vo.setSiTitle(title);

                    vo.setMobileUrl(obj.get("detailurl").toString().replaceAll("amp;" ,""));
                    vo.setSiEndDt((String) obj.get("enddate"));

                    vo.setPcUrl("-");

                    HashMap<String, String> params = new HashMap<>();
                    params.put("bodyurl", obj.get("detailurl").toString().replaceAll("amp;" ,""));
                    params.put("title", title);
                    boolean isUrl = crawlingMapper.isUrl(params);
                    if (!isUrl) supportVos.add(vo);
                 }

                //중앙부처 , 지자체, 공공기관



//                for (int i=1; i<5 ; i++) {
//
//                    driver.get(url);
//
//                    WebElement pageXpath = driver.findElement(By.xpath("//*[@id='bizPbancList']/div/a["+ i +"]"));
//
//                    //페이지이동
////                    jse.executeScript("arguments[0].click()", pageXpath);
//                    jse.executeScript("fn_egov_link_page("+i+");");
//
//                    Thread.sleep(1000);
//
//                    for(int j=1; j<16; j++) {
//
//                        WebElement titleXpath = driver.findElement(By.xpath("//*[@id='bizPbancList']/ul/li["+ j +"]/div/div[1]/div[2]/a/div/p"));
//                        WebElement urlXpath = driver.findElement(By.xpath("//*[@id='bizPbancList']/ul/li["+ j +"]/div/div[1]/div[2]/a"));
//
//                        String title = titleXpath.getText();
//                        String url = urlXpath.getAttribute("href");
//                        String index = url.replace("javascript:go_view","").replace("(","").replace(")","").replace(";","");
//
//                        String bodyUrl = "https://www.k-startup.go.kr/web/contents/bizpbanc-ongoing.do?schM=view&pbancSn=" + index;
//
//                        SupportVo vo = new SupportVo();
//                        vo.setTargetName("K-Startup");
//                        vo.setTargetCatName("-");
//                        vo.setLocCode("C82");
//                        vo.setSiTitle(title);
//                        vo.setMobileUrl(bodyUrl);
//                        vo.setPcUrl("-");
//
//                        HashMap<String, String> params = new HashMap<>();
//                        params.put("bodyurl", bodyUrl);
//                        params.put("title",title);
//                        boolean isUrl = crawlingMapper.isUrl(params);
//                        if (!isUrl) {
//                            supportVos.add(vo);
//                        }
//
//                    }
//
//                    Thread.sleep(500);
//                }
//
//
//                //민간, 지자체
//                WebElement btnXpath = driver.findElement(By.xpath("//*[@id=\"pbancClss_PBC020\"]/a"));
//
//                jse.executeScript("arguments[0].click()",btnXpath);
//
//                for (int i=1; i<5 ; i++) {
//
////                    driver.get(url);
//
//                    WebElement pageXpath = driver.findElement(By.xpath("//*[@id='bizPbancList']/div/a["+ i +"]"));
//                    jse.executeScript("arguments[0].click()", pageXpath);
//
//                    Thread.sleep(1000);
//
//                    for(int j=1; j<16; j++) {
//
//                        WebElement titleXpath = driver.findElement(By.xpath("//*[@id='bizPbancList']/ul/li["+ j +"]/div/div[1]/div[2]/a/div/p"));
//                        WebElement urlXpath = driver.findElement(By.xpath("//*[@id='bizPbancList']/ul/li["+ j +"]/div/div[1]/div[2]/a"));
//
//                        String title = titleXpath.getText();
//                        String url = urlXpath.getAttribute("href");
//                        String index = url.replace("javascript:go_view","").replace("(","").replace(")","").replace(";","");
//
//                        String bodyUrl = "https://www.k-startup.go.kr/web/contents/bizpbanc-ongoing.do?schM=view&pbancSn=" + index;
//
//                        SupportVo vo = new SupportVo();
//                        vo.setTargetName("K-Startup");
//                        vo.setTargetCatName("-");
//                        vo.setLocCode("C82");
//                        vo.setSiTitle(title);
//                        vo.setMobileUrl(bodyUrl);
//                        vo.setPcUrl("-");
//
//                        HashMap<String, String> params = new HashMap<>();
////                    params.put("bodyurl", bodyUrl);
//                        params.put("title",title);
//                        boolean isUrl = crawlingMapper.isUrl(params);
//                        if (!isUrl) {
//                            supportVos.add(vo);
//                        }
//
//                    }
//
//                    Thread.sleep(500);
//                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
                supportVo.setErrorYn("Y");
                e.printStackTrace();
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
        } catch (Exception e) {
            e.printStackTrace();
            supportVo.setErrorYn("Y");
        }finally {
            if(driver != null){
                driver.close();
                driver.quit();
            }else{
                supportVo.setErrorYn("Y");
//                crawlingMapper.createMaster(supportVo);
            }
            if(service != null){
                service.stop();
            }else{
                supportVo.setErrorYn("Y");
//                crawlingMapper.createMaster(supportVo);
            }
        }

        }






}

package co.kr.exitobiz.Crawling;

import co.kr.exitobiz.Mappers.Api.CrawlingMapper;
import co.kr.exitobiz.Vo.Cms.SupportVo;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class SbizFoundCrawling {

    @Autowired
    CrawlingMapper crawlingMapper;

    @Autowired
    Environment environment;


    //오픈 API로 처리
    public void getCrawlingData() throws IOException {

        SupportVo supportVo = new SupportVo();
        supportVo.setTitle("소상공인마당 정책자금");
        supportVo.setUrl("https://www.sbiz.or.kr/");
        supportVo.setLocCode("C82");
        supportVo.setActiveYn("Y");
        supportVo.setErrorYn("N");

        String apiURL = "https://www.sbiz.or.kr/sup/policy/json/policyfound.do";

        try {

            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(false); //url 서버와 통신시 출력 가능한 상태

            int responseCode = con.getResponseCode();
            BufferedReader br;
            String inputLine;
            StringBuffer response = new StringBuffer();

            if (responseCode == 200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream())); //콘솔에서 버퍼를 입력받음

                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();

                List<SupportVo> supportVos = new ArrayList<>();

                //json 파싱
                JSONParser jsonParse = new JSONParser();
                JSONObject jsonObj = (JSONObject) jsonParse.parse(response.toString()); //Json형태로 파싱해서 JsonObject에 넣음
                JSONArray jsonArray = (JSONArray) jsonObj.get("item");
                for (int j = 0; j < jsonArray.size(); j++) {

                    JSONArray itemObj =(JSONArray) ((JSONObject) ((JSONArray) jsonObj.get("item")).get(j)).get("items");

                    for (int i = 0; i < itemObj.size(); i++) {
                        String year = (String) ((JSONObject)itemObj.get(i)).get("year").toString();
                        if(year.equals("23년")){
                            String title = (String) ((JSONObject)itemObj.get(i)).get("title").toString();
                            String bodyurl = (String) ((JSONObject)itemObj.get(i)).get("url").toString();
                            SupportVo vo = new SupportVo();
                            vo.setTargetName("소상공인마당 정책자금");
                            vo.setTargetCatName("-");
                            vo.setLocCode("C82");
                            vo.setPcUrl("-");
                            vo.setSiTitle(title);
                            vo.setMobileUrl(bodyurl);

                            HashMap<String, String> params = new HashMap<>();
//                            params.put("bodyurl", bodyurl);
                            params.put("title",title);
                            boolean isUrl = crawlingMapper.isUrl(params);
                            if (!isUrl) {
                                supportVos.add(vo);
                            }
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

            } else {  // 오류 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));

                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();

                System.out.println(response.toString());
            }
        } catch (Exception e) {
            System.out.println(e);
            supportVo.setErrorYn("Y");
            crawlingMapper.createMaster(supportVo);
        }

    }
}

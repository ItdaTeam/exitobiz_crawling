package co.kr.exitobiz.Service.Mobile.impl;

import co.kr.exitobiz.Mappers.Api.ItdaMapper;
import co.kr.exitobiz.Service.Mobile.ItdaService;
import co.kr.exitobiz.Vo.Cms.ProviderVo;
import kr.hyosang.coordinate.CoordPoint;
import kr.hyosang.coordinate.TransCoord;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

@Service
public class ItdaServiceImpl implements ItdaService {

    @Autowired
    ItdaMapper itdaMapper;
    
	@Override
	public void addQuestion(HashMap<String, String> params) {
        itdaMapper.addQuestion(params);
	}

    //네이버 검색 API
    @Override
    public void updatePlace() {
        String clientId = "wXlUOaxuLFCOxwZgkBny";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "GNPBq4NVDZ";//애플리케이션 클라이언트 시크릿값
        List<ProviderVo> list = itdaMapper.getProvider();

        for(int i=0; i<list.size(); i++){
            String providerNm = list.get(i).getProviderNm();
            String providerLoc = list.get(i).getProviderLoc();
            String keyword = providerNm.replace("주식회사","").replace("(주)", "").replace("주)", "").replace("㈜","").replace(" ","") + " " + providerLoc;
            System.out.println("검색업체 : ---" + keyword);
            try{
                keyword = URLEncoder.encode(keyword, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException();
            }

            try{
                String apiURL = "https://openapi.naver.com/v1/search/local.json?query="+keyword+"&display=100&start=1&sort=random";    // json 결과
                URL url = new URL(apiURL);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("X-Naver-Client-Id", clientId);
                con.setRequestProperty("X-Naver-Client-Secret", clientSecret);

                int responseCode = con.getResponseCode();
                BufferedReader br;
                String inputLine;
                StringBuffer response = new StringBuffer();
                Thread.sleep(1000);

                if(responseCode == 200) {
                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    while ((inputLine = br.readLine()) != null) {
                        response.append(inputLine);
                    }
                    br.close();

                    JSONParser jsonParser = new JSONParser();
                    JSONObject jsonObj = (JSONObject) jsonParser.parse(response.toString());

                    System.out.println(jsonObj);
                    String total = jsonObj.get("total").toString();
                    if(total.equals("0")){
                        HashMap<String, String> params = new HashMap<>();
                        params.put("title", "-");
                        params.put("latitude", "-");
                        params.put("longitude", "-");
                        params.put("index", String.valueOf(list.get(i).getIndex()));
                        params.put("addr", "-");
                        params.put("link", "-");
                        params.put("tel", "-");
                        params.put("ctg", "-");
                        itdaMapper.updateLoc(params);
                        System.out.println(list.get(i).getProviderNm() + "가 조회되지 않았습니다.");
                    }else {
                        JSONObject itemObj1 = (JSONObject) (((JSONArray) jsonObj.get("items")).get(0));
                        double mapx = Double.parseDouble(itemObj1.get("mapx").toString());
                        double mapy = Double.parseDouble(itemObj1.get("mapy").toString());
                        CoordPoint pt = new CoordPoint(mapx,mapy);
                        CoordPoint wgsPt = TransCoord.getTransCoord(pt,TransCoord.COORD_TYPE_KTM, TransCoord.COORD_TYPE_WGS84);
                        System.out.println(itemObj1.get("title").toString());
                        System.out.println("latitude" + wgsPt.x);
                        System.out.println("longitude" + wgsPt.y);
                        HashMap<String, String> params = new HashMap<>();
                        params.put("title", itemObj1.get("title").toString().replaceAll("<b>","").replaceAll("</b>",""));
                        params.put("latitude", String.valueOf(wgsPt.y));
                        params.put("longitude", String.valueOf(wgsPt.x));
                        params.put("index", String.valueOf(list.get(i).getIndex()));
                        params.put("addr", itemObj1.get("address").toString());
                        params.put("link", itemObj1.get("link").toString().replace("\\",""));
                        params.put("tel", itemObj1.get("telephone").toString());
                        params.put("ctg", itemObj1.get("category").toString());
                        itdaMapper.updateLoc(params);
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
            }
        }
    }

    @Override
    public void updatePhone() {
        String authorization = "KakaoAK 98fc2900d511a864061c57103a225032";//애플리케이션 키값
        List<ProviderVo> list = itdaMapper.getProvider();

        for (int i = 0; i < list.size(); i++) {
            String providerNm = list.get(i).getProviderNm().replace("주식회사","").replace("(주)", "").replace("주)", "").replace("㈜","").replace(" ","");
            String providerLoc = list.get(i).getProviderLoc();
            String index = list.get(i).getIndex();
            String keyword = providerNm +" "+providerLoc;
            System.out.println(keyword);
            try {
                keyword = URLEncoder.encode(keyword, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException();
            }

            try {
                String apiURL = "https://dapi.kakao.com/v2/local/search/keyword.json?query=" + keyword;
                URL url = new URL(apiURL);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("Authorization", authorization);

                int responseCode = con.getResponseCode();
                BufferedReader br;
                String inputLine;
                StringBuffer response = new StringBuffer();
                Thread.sleep(1000);

                if (responseCode == 200) {
                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    while ((inputLine = br.readLine()) != null) {
                        response.append(inputLine);
                    }
                    br.close();

                    JSONParser jsonParser = new JSONParser();
                    JSONObject jsonObj = (JSONObject) jsonParser.parse(response.toString());
                    System.out.println(jsonObj);
                    int count = ((JSONArray) jsonObj.get("documents")).size();
                    JSONArray returnArray = new JSONArray();
                    HashMap<String, String> params = new HashMap<>();

                    if(count == 0){
                        params.put("index", index);
                        params.put("phone", "-");
                        itdaMapper.updatePhone(params);
                    }else {
                        params.put("index", index);
                        params.put("phone", ((JSONObject) ((JSONArray) jsonObj.get("documents")).get(0)).get("phone").toString());
                        itdaMapper.updatePhone(params);
                    }

                } else {  // 오류 발생
                    br = new BufferedReader(new InputStreamReader(con.getErrorStream()));

                    while ((inputLine = br.readLine()) != null) {
                        response.append(inputLine);
                    }
                    br.close();
                }

            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }


}
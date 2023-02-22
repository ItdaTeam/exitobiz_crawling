package co.kr.exitobiz.Controller.WebApp;

import co.kr.exitobiz.Service.WebApp.MainpageService;
import co.kr.exitobiz.Util.HttpClientUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.JwtClaims;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.ECDSASigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.InvalidKeyException;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.security.PrivateKey;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
;


@Controller
@RequiredArgsConstructor
@RequestMapping("/mainpage")
public class MainpageController {

    private final MainpageService mainpageService;

    private static final String TEAM_ID = "Z5R3BPPHVX";
    private static final String REDIRECT_URI = "https://dev.exitobiz.co.kr:8443/mainpage/AppleLogin";
    private static final String CLIENT_ID = "kr.co.exitobiz";
    private static final String KEY_ID = "5Q9A2LP44F";

    private static final String AUTH_URL = "https://appleid.apple.com";
    private static final String KEY_PATH = "static/apple/AuthKey_5Q9A2LP44F.p8";


    // 인기 키워드 리스트
    @PostMapping("/getSearchHotKeyWord")
    @ResponseBody
    public String getSearchHotKeyWord() throws ParseException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writeValueAsString(mainpageService.getSearchHotKeyWord());
        return jsonStr;
    }

    @RequestMapping(value = "/login/getAppleAuthUrl")
    public @ResponseBody String getAppleAuthUrl(HttpServletRequest req) throws Exception{
        String reqUrl = AUTH_URL + "/auth/authorize?client_id=" + CLIENT_ID +
                "&redirect_uri=" + REDIRECT_URI +
                "&response_type=code id_token&scope=name email&response_mode=form_post";

        return reqUrl;
    }


    @RequestMapping(value = "/login/oauth_apple")
    public String oauth_apple(HttpServletRequest req, @RequestParam(value="code", required = false) String code, HttpServletResponse res, Model model) throws Exception{
        if(code == null) return "/";

        String client_id = CLIENT_ID;
        return "ss";
    }

    public String createClientSecret(String teamId, String clientId, String keyId, String keyPath, String authUrl) throws Exception{
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(keyId).build();
//        "kty": "RSA",
//                "kid": "YuyXoY",
//                "use": "sig",
//                "alg": "RS256",
//                "n": "1JiU4l3YCeT4o0gVmxGTEK1IXR-Ghdg5Bzka12tzmtdCxU00ChH66aV-4HRBjF1t95IsaeHeDFRgmF0lJbTDTqa6_VZo2hc0zTiUAsGLacN6slePvDcR1IMucQGtPP5tGhIbU-HKabsKOFdD4VQ5PCXifjpN9R-1qOR571BxCAl4u1kUUIePAAJcBcqGRFSI_I1j_jbN3gflK_8ZNmgnPrXA0kZXzj1I7ZHgekGbZoxmDrzYm2zmja1MsE5A_JX7itBYnlR41LOtvLRCNtw7K3EFlbfB6hkPL-Swk5XNGbWZdTROmaTNzJhV-lWT0gGm6V1qWAK2qOZoIDa_3Ud0Gw",
//                "e": "AQAB"

        Date now = new Date();
        JWTClaimsSet jwtClaim = new JWTClaimsSet.Builder()
                .issuer(teamId)
                .audience(authUrl)
                .subject(clientId)
                .expirationTime(new Date(now.getTime() + 3600000))
                .issueTime(now)
                .build();

//
        SignedJWT jwt = new SignedJWT(header, jwtClaim);
//
//
//        try {
//            ECPrivateKey ecPrivateKey = new ECPrivateKeyImpl(readPrivateKey(keyPath));
//        } catch (InvalidKeyException e) {
//            e.printStackTrace();
//        } catch (JOSEException e) {
//            e.printStackTrace();
//        }

        return jwt.serialize();
    }

    @PostMapping("/getAppleToken")
    @ResponseBody
    public String getAppleToken(@RequestHeader HashMap<String, Object> header) throws ParseException, IOException {
        Date expirationDate = Date.from(LocalDateTime.now().plusDays(30).atZone(ZoneId.systemDefault()).toInstant());

        String makeClientSecret = Jwts.builder()
                .setHeaderParam("kid", KEY_ID)
                .setHeaderParam("alg", "ES256")
                .setIssuer(TEAM_ID)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expirationDate)
                .setAudience("https://appleid.apple.com")
                .setSubject(CLIENT_ID)
                .signWith(this.getPrivateKey(), SignatureAlgorithm.ES256)
                .compact();

        Map<String, String> tokenRequest = new HashMap<>();

        tokenRequest.put("client_id", CLIENT_ID);
        tokenRequest.put("client_secret", makeClientSecret);
        tokenRequest.put("grant_type", "authorization_code");
        tokenRequest.put("code", (String) header.get("code"));

        String response = HttpClientUtils.doPost("https://appleid.apple.com/auth/token", tokenRequest);
        return response;
    }

    @PostMapping("/revokeApple")
    @ResponseBody
    public String revokeApple(@RequestHeader HashMap<String, Object> header) throws ParseException, IOException {
        String result = "fail";
        Date expirationDate = Date.from(LocalDateTime.now().plusDays(30).atZone(ZoneId.systemDefault()).toInstant());

        String makeClientSecret = Jwts.builder()
                .setHeaderParam("kid", KEY_ID)
                .setHeaderParam("alg", "ES256")
                .setIssuer(TEAM_ID)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expirationDate)
                .setAudience("https://appleid.apple.com")
                .setSubject(CLIENT_ID)
                .signWith(this.getPrivateKey(), SignatureAlgorithm.ES256)
                .compact();

        Map<String, String> tokenRequest = new HashMap<>();

        tokenRequest.put("client_id", CLIENT_ID);
        tokenRequest.put("client_secret", makeClientSecret);
        tokenRequest.put("token", (String) header.get("access_token"));

        try{
            HttpClientUtils.doPost("https://appleid.apple.com/auth/revoke", tokenRequest);
            result = "success";
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public PrivateKey getPrivateKey() throws IOException{
        ClassPathResource resource = new ClassPathResource(KEY_PATH);
        String privateKey = new String(Files.readAllBytes(Paths.get(resource.getURI())));
        Reader pemReader = new StringReader(privateKey);
        PEMParser pemParser = new PEMParser(pemReader);
        JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
        PrivateKeyInfo object = (PrivateKeyInfo) pemParser.readObject();
        return converter.getPrivateKey(object);
    }



    @RequestMapping(value="/AppleLogin", method=RequestMethod.POST)
    public void getAppleLogin(@RequestBody String appleData, HttpServletResponse response ) throws IOException {
        response.addHeader("Access-Control-Allow-Origin","*");
        response.sendRedirect("https://dev.exitobiz.co.kr/AppleLogin#" + appleData);
        //return "redirect:" + appleData;
    }

    // 나의 최근 키워드 리스트
    @PostMapping("/getMyRecentKeyword")
    @ResponseBody
    public String getMyRecentKeyword(@RequestHeader HashMap<String, Object> header) throws ParseException, JsonProcessingException {
        HashMap<String, Object> params = new HashMap<>();
        params.put("tl_cret_id", header.get("user_id"));

        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writeValueAsString(mainpageService.getMyRecentKeyword(params));
        return jsonStr;
    }

    // 나의 최근 키워드 단일 삭제
    @PostMapping("/delMyRecentKeyword")
    @ResponseBody
    public String delMyRecentKeyword(@RequestHeader HashMap<String, Object> header, @RequestBody HashMap<String, Object> body) throws ParseException, JsonProcessingException {
        String result = "fail";
        body.put("tl_cret_id" , header.get("user_id"));
        try{
            mainpageService.delMyRecentKeyword(body);
            result = "success";
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    // 나의 최근 키워드 전체 삭제
    @PostMapping("/delMyAllRecentKeyword")
    @ResponseBody
    public String delMyAllRecentKeyword(@RequestHeader HashMap<String, Object> header) throws ParseException, JsonProcessingException {
        HashMap<String, Object> params = new HashMap<>();
        params.put("tl_cret_id", header.get("user_id"));
        String result = "fail";

        try{
            mainpageService.delMyAllRecentKeyword(params);
            result = "success";
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;

    }

    // 타임라인 추가 (나의 최근 검색어 저장, 최근 본 지원사업 저장) _ 공통
    @PostMapping("/insertTimeLine")
    @ResponseBody
    public String insertTimeLine(@RequestHeader HashMap<String, Object> header, @RequestBody HashMap<String, Object> body) throws ParseException, JsonProcessingException {
        String result = "fail";
        HashMap<String, Object> params = new HashMap<>();

        if(header.get("user_id") != null && header.get("user_id") != ""){
            params.put("tl_cret_id", header.get("user_id"));

            if(body.get("btn_type") != null){
                //앱에서 호출할 경우
                if(body.get("btn_type").equals("하단저장버튼")){
                    //찜 저장

                    params.put("tl_page_type", "지원사업-상세페이지");
                    params.put("tl_page_depth", "2");
                    params.put("tl_page_name", "상세페이지");
                    params.put("tl_button_name", "하단저장버튼");
                    params.put("tl_type_cd",  0);
                    params.put("tl_event", "이동");
                    params.put("tl_memo", "-");
                }else if(body.get("btn_type").equals("하단공유버튼")){

                    //공유버튼 클릭
                    params.put("tl_page_type", "지원사업-상세페이지");
                    params.put("tl_page_depth", "2");
                    params.put("tl_page_name", "상세페이지");
                    params.put("tl_button_name", "하단공유버튼");
                    params.put("tl_type_cd",  0);
                    params.put("tl_event", "공유");
                    params.put("tl_memo", "-");
                }else if(body.get("btn_type").equals("뒤로가기버튼")){

                    //뒤로가기 버튼 클릭
                    params.put("tl_page_type", "지원사업-상세페이지");
                    params.put("tl_page_depth", "2");
                    params.put("tl_page_name", "상세페이지-상세보기");
                    params.put("tl_button_name", "뒤로가기버튼");
                    params.put("tl_type_cd",  0);
                    params.put("tl_event", "이동");
                    params.put("tl_memo", "-");
                }
            }else{
                params.putAll(body);
            }

            try{
                mainpageService.insertTimeLine(params);
                result = "success";
            }catch(Exception e){
                e.printStackTrace();
            }
        }


        return result;

    }

    //배너리스트
    @PostMapping("/getBannerList")
    @ResponseBody
    public String getCommunityList() throws ParseException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writeValueAsString(mainpageService.getBannerList());
        return jsonStr;
    }

    // 누적갯수(누적 지원사업 개수, 이번주 지원사업, 정보 제공기관, 누적 가입 기업) 리스트
    @PostMapping("/getTotalCount")
    @ResponseBody
    public String getTotalCount() throws ParseException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writeValueAsString(mainpageService.getTotalCount());
        return jsonStr;
    }


    // 키워드 정기배송 리스트 (","로 구분)
    @PostMapping("/getKeyword")
    @ResponseBody
    public String getKeyword(@RequestHeader HashMap<String, Object> header) throws ParseException, JsonProcessingException {
        HashMap<String, Object> params = new HashMap<>();

        params.put("user_id", header.get("user_id"));

        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writeValueAsString(mainpageService.getKeyword(params));
        return jsonStr;
    }

    // 키워드 정기배송 추가
    @PostMapping("/insertKeyword")
    @ResponseBody
    public String getKeyword(@RequestHeader HashMap<String, Object> header, @RequestBody HashMap<String, Object> body) throws ParseException, JsonProcessingException {
        body.put("user_id", header.get("user_id"));
        String result = "fail";
        try{
            mainpageService.insertKeyword(body);
            result = "success";
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;

    }

    // 키워드 정기배송 단일 삭제
    @PostMapping("/delKeyword")
    @ResponseBody
    public String delKeyword(@RequestHeader HashMap<String, Object> header, @RequestBody HashMap<String, Object> body) throws ParseException, JsonProcessingException {
        String result = "fail";
        body.put("user_id", header.get("user_id"));

        try{
            mainpageService.delKeyword(body);
            result = "success";
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;

    }

    // 키워드 정기배송 전체 삭제
    @PostMapping("/delAllKeyword")
    @ResponseBody
    public String delAllKeyword(@RequestHeader HashMap<String, Object> header) throws ParseException, JsonProcessingException {
        HashMap<String, Object> params = new HashMap<>();
        params.put("user_id", header.get("user_id"));

        String result = "fail";
        try{
            mainpageService.delAllKeyword(params);
            result = "success";
        }catch(Exception e){
            e.printStackTrace();
        }
       return result;
    }

    // 엑시토 (실시간 인기 | 찜 인기 ) 리스트
    @PostMapping("/getPopularList")
    @ResponseBody
    public String getPopularList(@RequestBody HashMap<String, Object> body) throws ParseException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writeValueAsString(mainpageService.getPopularList(body));
        return jsonStr;
    }


    // 엑시토 추천 리스트
    @PostMapping("/getPushBookList")
    @ResponseBody
    public String getPushBookList(@RequestHeader HashMap<String, Object> header) throws ParseException, JsonProcessingException {
        HashMap<String, Object> params = new HashMap<>();
        params.put("user_id", header.get("user_id"));
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writeValueAsString(mainpageService.getPushBookList(params));
        return jsonStr;
    }

    /**
     * 조회수 증가
     */
    @PostMapping("/upViewCnt")
    @ResponseBody
    public String upViewCnt(@RequestParam HashMap<String, Object> params) throws ParseException, JsonProcessingException {
        String result = "fail";
        try{
            mainpageService.upViewCnt(params);
            result = "success";
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    //지원가능한 갯수 조회
    @PostMapping("/getSupportableCnt")
    @ResponseBody
    public String getSupportableCnt() throws ParseException, JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writeValueAsString(mainpageService.getSupportableCnt());
        return jsonStr;
    }

}

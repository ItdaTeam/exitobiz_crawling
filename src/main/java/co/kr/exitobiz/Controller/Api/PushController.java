package co.kr.exitobiz.Controller.Api;

import co.kr.exitobiz.Service.Api.PushMultipleService;
import co.kr.exitobiz.Service.Api.PushService;
import co.kr.exitobiz.Service.Cms.BannerService;
import co.kr.exitobiz.Service.WebApp.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/push")
public class PushController {

    @Autowired
    PushService pushService;

    @Autowired
    PushMultipleService pushMultipleService;

    @Autowired
    UserService userService;

    @Autowired
    Environment environment;

    @Autowired
    BannerService bannerService;

    /**
     * DB에서 사용자 토큰을 가져와서 각 사용자별로 FCM Push 발송처리
     * @param params
     * @return 성공여부
     * @throws Exception
     */
    @RequestMapping(value="/send")
    @ResponseBody
    public String sendPush(@RequestParam HashMap<String,String> params ) throws Exception {

        List<Map<String,Object>> userToken = userService.getUserToken(params); // DB에서 사용자 토큰 가져오기
        String result ="fail";
        if(userToken.size() > 0){ // 토큰이 존재하는 경우에만 처리
            params.put("firebaseKeyPath", environment.getProperty("firebase.path.key")); // key파일 path 가져오기
            pushMultipleService.sendListPush(params, userToken);
            result = "success";
        }
        return result;
    }


    /**
     * 유저 위치코드로 푸시보내기
    * params { userloc: 'C051,C055' }
    * */
    @PostMapping(value="/sendSplit")
    @ResponseBody
    public String sendSplitPush(@RequestParam HashMap<String,String> params) throws Exception {

        HashMap<String, Object> params2 = new HashMap<>();

        params.put("title", URLDecoder.decode(params.get("title")));
        params.put("body", URLDecoder.decode(params.get("body")));

//        /* C82,C055,C051을 콤마로 끊어서 배열로 저장 */
        if (params.get("userloc") != null) {
            String location = params.get("userloc");
            List<String> userlocs = new ArrayList<String>();
            String[] splits = location.split(",");
            for(int i=0; i< splits.length; i++){
                userlocs.add(splits[i]);
            }

            params2.put("userlocs", userlocs);
        }

        List<Map<String, Object>> userTokens = new ArrayList<>();

        Map<String,Object> usertoken = new HashMap<String,Object>();
        usertoken.put("usertoken", "e9lDCbfGTl-WF8isL10xi0:APA91bHE-A68f4sHnI10vqLnLRUjXc8l2Caw129D6HW0J-L8gNVEirrrIAlUfCkTxWHbDPdl3gzFQXP9GzkLB9ASyBYO_GbgzcGulu4NPfm17sJFh8nxTgbLf3ah5J-vDKYWQxkjI0Kx");

        userTokens.add(usertoken);
                //userService.getUserTokenByLocation(params2);

        String result ="fail";
        int failCount = 0;

        pushMultipleService.savePush(params);
        pushMultipleService.sendListPush(params, userTokens);

        if (failCount != 0) {
            return failCount + "건 실패";
        } else {
            return "성공";
        }
    }


    @PostMapping(value="/sendSplitTest")
    @ResponseBody
    public String sendSplitPushTest(@RequestParam HashMap<String,String> params) throws Exception {

        HashMap<String, Object> params2 = new HashMap<>();

        params.put("title", URLDecoder.decode(params.get("title")));
        params.put("body", URLDecoder.decode(params.get("body")));

//        /* C82,C055,C051을 콤마로 끊어서 배열로 저장 */
        if (params.get("userloc") != null) {
            String location = params.get("userloc");
            List<String> userlocs = new ArrayList<String>();
            String[] splits = location.split(",");
            for(int i=0; i< splits.length; i++){
                userlocs.add(splits[i].toString());
            }

            params2.put("userlocs", userlocs);
        }

        List<Map<String, Object>> userTokens = userService.getUserTokenByLocationTest(params2);
        System.out.println(userTokens.toString());
        System.out.println(userTokens.size());

        String result ="fail";
        int failCount = 0;

        System.out.println(0 % 500);

        pushMultipleService.sendListPushTest(params, userTokens);

        if (failCount != 0) {
            return failCount + "건 실패";
        } else {
            return "성공";
        }
    }

     /**
     * 앱에서 사용자에게 발송되는 개별 푸쉬 알람
     * @param params [userId(로그인 ID), idx(게시물번호), title(푸쉬제목), body(푸쉬 부제목), keyId(구분값)]
     * @return 성공여부
     * @throws Exception
     */
    @RequestMapping(value="/sendOne")
    @ResponseBody
    public String sendOnePush(@RequestBody HashMap<String,String> params ) throws Exception {

        params.put("id",params.get("userId")); // 쿼리 공통 사용을 위한 변수 id(사용자 아이디) 추가
        if(params.get("keyId").equals("comm") || params.get("keyId").equals("like") || params.get("keyId").equals("reco")){
            params.put("url" , "https://exitobiz.co.kr/community/communityView/" + params.get("idx"));
        }

        List<Map<String,Object>>  userTokens = userService.getUserTokenByKeyId(params); // DB에서 사용자 토큰 가져오기

//        Map<String,Object> usertoken = new HashMap<String,Object>();
//        usertoken.put("usertoken", "e9lDCbfGTl-WF8isL10xi0:APA91bHE-A68f4sHnI10vqLnLRUjXc8l2Caw129D6HW0J-L8gNVEirrrIAlUfCkTxWHbDPdl3gzFQXP9GzkLB9ASyBYO_GbgzcGulu4NPfm17sJFh8nxTgbLf3ah5J-vDKYWQxkjI0Kx");

//        userTokens.add(usertoken);

        if(!userTokens.isEmpty()){ // 토큰이 존재하는 경우에만 처리
                params.put("firebaseKeyPath",environment.getProperty("firebase.path.key")); // key파일 path 가져오기
                pushMultipleService.sendListPush(params, userTokens);
        }

        return "success";
    }

    @PostMapping(value="/sendNotice")
    @ResponseBody
    public String sendNotice(@RequestParam HashMap<String,String> params) {

        params.put("title",URLDecoder.decode(params.get("title")));
        params.put("body",URLDecoder.decode(params.get("body")));

        //banner 조회
        HashMap<String, Object> bannParams = new HashMap<>();
        bannParams.put("index", params.get("idx"));

        HashMap<String, Object> bannObj = bannerService.getBanner(bannParams);

        if(params.get("keyId").equals("noti")){
            params.put("url" , "https://exitobiz.co.kr/notice/noticeView/" + params.get("idx"));
        }else if(params.get("keyId").equals("cus1")){
            params.put("url" , "https://exitobiz.co.kr/community/communityView/" + params.get("idx"));
        }else if(params.get("keyId").equals("bann")){
            if(bannObj.get("banner_ctg").equals("I") || bannObj.get("banner_ctg").equals("O")){
                params.put("url", (String) bannObj.get("banner_link"));
            }else if(bannObj.get("banner_ctg").equals("N")){
                params.put("url", "https://exitobiz.co.kr/notice/noticeList");
            }else if(bannObj.get("banner_ctg").equals("ND")){
                params.put("url", "https://exitobiz.co.kr/notice/noticeList/" + params.get("idx"));
            }
        }

        List<Map<String, Object>> userTokens = new ArrayList<>();
        //userService.getUserTokens();

        Map<String,Object> usertoken = new HashMap<String,Object>();
        usertoken.put("usertoken", "e9lDCbfGTl-WF8isL10xi0:APA91bHE-A68f4sHnI10vqLnLRUjXc8l2Caw129D6HW0J-L8gNVEirrrIAlUfCkTxWHbDPdl3gzFQXP9GzkLB9ASyBYO_GbgzcGulu4NPfm17sJFh8nxTgbLf3ah5J-vDKYWQxkjI0Kx");

        userTokens.add(usertoken);

        String result = "fail";
        if(!userTokens.isEmpty()){
            pushMultipleService.savePush(params);
            try{
                pushMultipleService.sendListPush(params, userTokens);
            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                result = "success";
            }
        }
        return result;
    }
}
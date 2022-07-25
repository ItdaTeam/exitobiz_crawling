package co.kr.exitobiz.Controller.Api;

import co.kr.exitobiz.Service.Api.PushMultipleService;
import co.kr.exitobiz.Service.Api.PushService;
import co.kr.exitobiz.Service.Mobile.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    /**
     * DB에서 사용자 토큰을 가져와서 각 사용자별로 FCM Push 발송처리
     * @param params
     * @return 성공여부
     * @throws Exception
     */
    @RequestMapping(value="/send")
    @ResponseBody
    public String sendPush(@RequestParam HashMap<String,String> params ) throws Exception {

        String userToken = userService.getUserToken(params); // DB에서 사용자 토큰 가져오기
        String result ="fail";
        if(userToken.length() > 0){ // 토큰이 존재하는 경우에만 처리
                params.put("userToken",userService.getUserToken(params)); // 사용자 토큰 추가 
                params.put("firebaseKeyPath",environment.getProperty("firebase.path.key")); // key파일 path 가져오기
                result = pushService.sendPush(params);
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

        List<Map<String, Object>> userTokens = userService.getUserTokenByLocation(params2);

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

        List<Map<String, Object>> userTokens = userService.getUserTokenByLocation(params2);
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
    public String sendOnePush(@RequestParam HashMap<String,String> params ) throws Exception {
        

        params.put("id",params.get("userId")); // 쿼리 공통 사용을 위한 변수 id(사용자 아이디) 추가

        String userToken = userService.getUserTokenByKeyId(params); // DB에서 사용자 토큰 가져오기
        String result ="fail";
        if(userToken != null){ // 토큰이 존재하는 경우에만 처리
                params.put("userToken",userToken); // 사용자 토큰 추가 
                params.put("firebaseKeyPath",environment.getProperty("firebase.path.key")); // key파일 path 가져오기
                result = pushService.sendPush(params);
        }
        return result;
    }
}
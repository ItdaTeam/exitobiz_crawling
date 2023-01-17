package co.kr.exitobiz.Controller.WebApp;

import co.kr.exitobiz.Service.WebApp.UserService;
import co.kr.exitobiz.Service.WebApp.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.util.Map;

/**
 * 고객 - 마이페이지 관련 컨트롤러
 */
@RequestMapping("/user")
@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    JwtService jwtService;

    /**
     *  사용자 정보 가져오기
     * @param params userid 사용자아이디
     * @return 사용자 기본 정보
     * @throws Exception
     */
    @PostMapping ("/getUserInfo")
    @ResponseBody
    public String getUserInfo(@RequestHeader Map<String,Object> params) throws  Exception {
        if(params.get("userid") != null) {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(userService.getUserInfo(params));
        }else
            return null;
    }

    /**
     * 사용자 정보 가져오기  JWT 버전
     * @param params userid 사용자아이디
     * @return 사용자 기본 정보
     */
    @PostMapping("/getUserInfoJWT")
    @ResponseBody
    public String getUserInfoJWT(@RequestHeader Map<String,Object> params){
        if(params.get("userid") != null) {
            return jwtService.createDataToken(userService.getUserInfo(params));
        }else
            return null;
    }

    /**
     * 마이페이지 기업 정보 조회
     * @param params userid 사용자아이디
     * @return 기업 정보
     */
    @PostMapping("/getCompanyInfo")
    @ResponseBody
    public String getCompanyInfo(@RequestHeader Map<String,Object> params) throws  Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(userService.getCompanyInfo(params));

    }

    /**
     * 알림설정 수정
    *  @param params userid 사용자아이디, 각 알림설정 Y/N값
     * @return success / fail
     */
    @PostMapping("/updatePushSetting")
    @ResponseBody
    public String updatePushSetting(@RequestBody Map<String,Object> params, @RequestHeader Map<String,Object> data) {
        String result = "fail";
        if(data.get("userid") != null) {
            try {
                params.put("userid",data.get("userid"));
                userService.updatePushSetting(params);
                result = "success";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;

    }

    /**
     * 알림설정 정보 조회
     * @param params userid 사용자아이디
     * @return 각 알림설정 된 Y/N 값
     */
    @PostMapping("/getPushSetting")
    @ResponseBody
    public String getPushSetting(@RequestHeader Map<String,Object> params) throws  Exception {
        if(params.get("userid") != null){
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(userService.getPushSetting(params));
        }else{
            return null;
        }
    }

    /**
     * 회원 탈퇴
     * @param params userid 사용자아이디
     * @return success / fail
     * @throws Exception
     */
    @PostMapping("/withdraw")
    @ResponseBody
    public String withdraw(@RequestHeader Map<String,Object> params)  {
        String result = "fail";
        if(params.get("userid") != null){
            try {
                userService.withdraw(params);
                result = "success";
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 탈퇴후 재가입
     * @param params id 사용자아이디, nickname, birth, profile, ageRange, gender, email
     * @return success / fail
     * @throws Exception
     */
    @PostMapping("/updateReSignIn")
    @ResponseBody
    public String updateReSignIn(@RequestHeader Map<String,Object> params)  {
        String result = "fail";
        if(params.get("id") != null){
            try {
                userService.updateReSignIn(params);
                result = "success";
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 닉네임 중복 확인
     * @param params usernickname 닉네임
     * @return  boolean 가능여부
     */
    @RequestMapping("/checkNickname")
    @ResponseBody
    public boolean checkNickname(@RequestHeader Map<String,Object> params)  {
        if(params.get("usernickname") == null){
            return false;
        }
        params.put("usernickname", URLDecoder.decode(params.get("usernickname").toString()));
        boolean result = false;
            int count = userService.checkNickname(params);
            if(count == 0)
                result = true;
        return result;
    }

    /**
     * 사용자 정보 업데이트
     * @param params    usernickname, userid
     * @return  성공여부
     */
    @RequestMapping("/updateUserInfo")
    @ResponseBody
    public String updateUserInfo(@RequestHeader Map<String,Object> header, @RequestParam Map<String,Object> params){
        String result = "fail";

        if(params.get("usernickname") != null && header.get("userid") != null){
            params.put("usernickname", URLDecoder.decode(params.get("usernickname").toString()));
            params.put("userid",header.get("userid"));
            try{
                userService.updateUserInfo(params);
                result = "success";
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 마이페이지 기업 정보 업데이트
     * @param params userid 사용자아이디
     * @return 기업 정보
     */
    @PostMapping("/updateCompanyInfo")
    @ResponseBody
    public String updateCompanyInfo(@RequestParam Map<String,Object> params, @RequestHeader Map<String,Object> header) throws  Exception {
        String result = "fail";


        if( header.get("userid") != null) {
            params.put("user_id",header.get("userid"));
            try {
                userService.updateCompanyInfo(params);
                result = "success";
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
         return result;

    }


}

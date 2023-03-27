package co.kr.exitobiz.Controller.Api;

import co.kr.exitobiz.Service.Api.StudyApiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/studyApi")
public class StudyApiController {
    private final StudyApiService studyApiService;

    //유저 정보 추가
    @PostMapping("/insertUserData")
    @ResponseBody
    public String insertUserData(@RequestHeader HashMap<String, Object> header, @RequestBody HashMap<String, Object> body, @RequestParam HashMap<String,Object> params) throws ParseException, JsonProcessingException {
        if (header.get("user_id") != null) body.put("id", header.get("user_id"));
        String result = "";
        try{
            if(header.get("user_id") == null){
                //유저 아이디에 null 데이터가 들어갔을 때
                result = "user_id is null";

            } else {
                //유저가 존재 하지 않을 때
                studyApiService.insertUserData(body);
                result = "success";
            }
        }
        catch(DuplicateKeyException dke){
            dke.printStackTrace();
            result = "dup" + dke;
        }
        catch(Exception e){
            e.printStackTrace();
            result = "fail" + e;
        }
        finally{
            return result;
        }
    }

    //유저 정보 변경
    @PostMapping("/updateUserData")
    @ResponseBody
    public String updateUserData(@RequestHeader HashMap<String, Object> header, @RequestBody HashMap<String, Object> body) throws ParseException, JsonProcessingException {
        String result = "";
        if (header.get("user_id") != null) body.put("id", header.get("user_id"));
        List<Map<String, Object>> checkData = studyApiService.selectUserData(header);

        try{
            if(header.get("user_id") == null){
                //유저 아이디에 null 데이터가 들어갔을 때
                result = "user_id is null";

            } else if(checkData.size() > 0){
                //유저가 존재 할 때
                studyApiService.updateUserData(body);
                result = "success";

            } else {
                //유저가 존재 하지 않을 때
                result = "User does not exist";
            }
        }
        catch(DuplicateKeyException dke){
            dke.printStackTrace();
            result = "dup" + dke;
        }
        catch(Exception e){
            e.printStackTrace();
            result = "fail" + e;
        }
        finally{
            return result;
        }
    }

    //유저 정보 삭제
    @PostMapping("/deleteUserData")
    @ResponseBody
    public String deleteUserData(@RequestHeader HashMap<String, Object> header) throws ParseException, JsonProcessingException {
        String result = "";
        List<Map<String, Object>> checkData = studyApiService.selectUserData(header);

        try{
            if(header.get("user_id") == null){
                //유저 아이디에 null 데이터가 들어갔을 때
                result = "user_id is null";

            } else if(checkData.size() > 0){
                //유저가 존재 할 때
                studyApiService.deleteUserData(header);
                result = "success";

            } else {
                //유저가 존재 하지 않을 때
                result = "User does not exist";
            }
        }
        catch(DuplicateKeyException dke){
            dke.printStackTrace();
            result = "dup" + dke;
        }
        catch(Exception e){
            e.printStackTrace();
            result = "fail" + e;
        }
        finally{
            return result;
        }
    }
}

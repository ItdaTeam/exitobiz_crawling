package co.kr.exitobiz.Controller.WebApp;

import co.kr.exitobiz.Service.WebApp.SavedService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/saved")
public class SavedController {

    private final SavedService savedService;

    // 조회 총 갯수(최근 본 사업, 찜한 사업, 지원한 사업) 리스트
    @PostMapping("/getTotalCountList")
    @ResponseBody
    public String getTotalCountList(@RequestHeader HashMap<String, Object> header) throws ParseException, JsonProcessingException {
        HashMap<String, Object> map = new HashMap<>();
        map.put("user_id", header.get("user_id"));
        // 최근 본 사업 갯수
        int view_cnt = savedService.getRecentlyMySavedBook(map).size();

        // 찜한 사업 갯수
        map.put("cat" , "찜");
        int save_cnt = savedService.getMySavedBook(map).size();

        // 지원한 사업 갯수
        map.replace("cat" , "지원");
        int done_cnt = savedService.getMySavedBook(map).size();

        // 결과값 넣기
        HashMap<String, Object> result = new HashMap<>();
        result.put("view_cnt", view_cnt);
        result.put("save_cnt", save_cnt);
        result.put("done_cnt", done_cnt);

        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writeValueAsString(result);
        return jsonStr;
    }


    //최근 본 사업 리스트
    @PostMapping("/getRecentlyMySavedBook")
    @ResponseBody
    public String getRecentlyMySavedBook(@RequestHeader HashMap<String, Object> header, @RequestBody HashMap<String, Object> body) throws ParseException, JsonProcessingException {

        body.put("user_id", header.get("user_id"));

        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writeValueAsString(savedService.getRecentlyMySavedBook(body));
        return jsonStr;
    }

    //찜한 사업, 지원한 사업 리스트
    @PostMapping("/getMySavedBook")
    @ResponseBody
    public String getMySavedBook(@RequestHeader HashMap<String, Object> header, @RequestBody HashMap<String, Object> body) throws ParseException, JsonProcessingException {

        body.put("user_id", header.get("user_id"));

        ObjectMapper mapper = new ObjectMapper();
        String tojsonStr = mapper.writeValueAsString(savedService.getMySavedBook(body));
        return tojsonStr;
    }

    // 찜 flag 변경
    @PostMapping("/isSavedMyBook")
    @ResponseBody
    public String isSavedMyBook(@RequestHeader HashMap<String, Object> header, @RequestBody HashMap<String, Object> body) throws ParseException, JsonProcessingException {
        body.put("mb_cret_id", header.get("user_id"));

        HashMap<String, Object> result = new HashMap<>();
        result.put("result", "fail");

        ObjectMapper mapper = new ObjectMapper();

        try {
            if (body.get("mb_save_yn") == null || body.get("mb_save_yn").equals("")) {
                savedService.insertSavedMyBook(body);
            } else if (body.get("mb_save_yn").equals("Y")) {
                body.replace("mb_save_yn", "N");
                savedService.updateSavedMyBook(body);
            } else if (body.get("mb_save_yn").equals("N")) {
                body.replace("mb_save_yn", "Y");
                savedService.updateSavedMyBook(body);
            }

            HashMap chkParams = new HashMap();
            chkParams.put("what_flag", "mb_save_yn");
            chkParams.put("mb_cret_id", header.get("user_id"));
            chkParams.put("mb_addidx", body.get("mb_addidx"));

            result.replace("result", "success");

            result.putAll(savedService.checkSavedFlag(chkParams));

        } catch (Exception e) {
            e.printStackTrace();
        }

        String tojsonStr = mapper.writeValueAsString(result);

        return tojsonStr;

    }

    // 지원 flag 변경
    @PostMapping("/isReqSavedMyBook")
    @ResponseBody
    public String isReqSavedMyBook(@RequestHeader HashMap<String, Object> header, @RequestBody HashMap<String, Object> body) throws ParseException, JsonProcessingException {

        String result = "fail";
        body.put("mb_cret_id", header.get("user_id"));


        if(body.get("mb_req_save_yn") == null || body.get("mb_req_save_yn").equals("")){
            //값이 없을 경우, 생성일자도 추가한다.
            body.replace("mb_req_save_yn", "Y");
            body.put("mb_req_cret_dt", "");
        }else if(body.get("mb_req_save_yn").equals("N")){
            body.replace("mb_req_save_yn", "Y");
            body.replace("mb_done_save_yn", "");

        }else if(body.get("mb_req_save_yn").equals("Y")){
            body.replace("mb_req_save_yn", "N");

            if(body.get("mb_done_save_yn") == null || body.get("mb_done_save_yn").equals("N")){
                body.replace("mb_done_save_yn", "Y");
            }else if(body.get("mb_done_save_yn").equals("Y")){
                // 지원을 해제할 때만 선정도 해제해줌.
                body.replace("mb_done_save_yn", "N");
            }
        }
        try{
            savedService.updateReqSavedMyBook(body);
            result = "success";
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;

    }

    // 선정 flag 변경
    @PostMapping("/isDoneSavedMyBook")
    @ResponseBody
    public String isDoneSavedMyBook(@RequestHeader HashMap<String, Object> header, @RequestBody HashMap<String, Object> body) throws ParseException, JsonProcessingException {
        body.put("mb_cret_id", header.get("user_id"));

        String result = "fail";

        if(body.get("mb_done_save_yn") == null || body.get("mb_done_save_yn").equals("")){
            //값이 없을 경우, 생성일자도 추가한다.
            body.replace("mb_done_save_yn", "Y");
            body.put("mb_done_cret_dt", "");
        }else if(body.get("mb_done_save_yn").equals("N")){
            body.replace("mb_done_save_yn", "Y");
        }else if(body.get("mb_done_save_yn").equals("Y")){
            body.put("mb_done_save_yn", "N");
        }

        try{
            savedService.updateDoneSavedMyBook(body);
            result = "success";

        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
    //찜 분류 개수 리스트
    @PostMapping("/getCatList")
    @ResponseBody
    public String getCatList(@RequestHeader HashMap<String, Object> header) throws ParseException, JsonProcessingException {
        HashMap<String, Object> params = new HashMap<>();
        params.put("mb_cret_id", header.get("user_id"));

        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writeValueAsString(savedService.getCatList(params));
        return jsonStr;
    }

    //찜 현황 개수 리스트
    @PostMapping("/getSavedTotalCount")
    @ResponseBody
    public String getSavedTotalCount(@RequestHeader HashMap<String, Object> header) throws ParseException, JsonProcessingException {
        HashMap<String, Object> map = new HashMap<>();
        map.put("user_id", header.get("user_id"));
        // 찜한 사업 갯수
        map.put("cat", "찜");
        int view_cnt = savedService.getMySavedBook(map).size();

        // 지원한 사업 갯수
        map.put("cat", "지원");
        int req_cnt = savedService.getMySavedBook(map).size();

        // 선정된 사업 갯수
        map.replace("cat", "선정");
        int done_cnt = savedService.getMySavedBook(map).size();

        // 결과값 넣기
        HashMap<String, Object> result = new HashMap<>();
        result.put("save_cnt", view_cnt);
        result.put("req_cnt", req_cnt);
        result.put("done_cnt", done_cnt);

        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writeValueAsString(result);
        return jsonStr;
    }

    // 우리 회사에서 필요한 것 조회
    @PostMapping("/getUserNeed")
    @ResponseBody
    public String getUserNeed(@RequestHeader HashMap<String, Object> header) throws ParseException, JsonProcessingException {
        HashMap<String, Object> map = new HashMap<>();
        map.put("user_id", header.get("user_id"));

        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writeValueAsString(savedService.getUserNeed(map));
        return jsonStr;
    }

    // 우리 회사에서 필요한 것 추가 / 수정
    @PostMapping("/updateUserNeed")
    @ResponseBody
    public String updateUserNeed(@RequestHeader HashMap<String, Object> header, @RequestBody HashMap<String, Object> body) throws ParseException, JsonProcessingException {
        body.put("user_id", header.get("user_id"));

        String result = "fail";
        int hasUserNeed = 0;

        try{
            //현 아이디로 데이터가 있는 지 확인
            Map<String,Object> data =  savedService.getUserNeed(body);
            if(data != null)
                hasUserNeed = savedService.getUserNeed(body).size();

            if ((body.get("idx") == null && hasUserNeed == 0) || data == null){
                savedService.insertUserNeed(body);
            }else if(body.get("idx") != null && hasUserNeed != 0){
                savedService.updateUserNeed(body);
            }
            result = "success";
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;

    }

    //이메일 정기배송 추가
    @PostMapping("/insertDeliverEmail")
    @ResponseBody
    public String insertDeliverEmail(@RequestHeader HashMap<String, Object> header, @RequestBody HashMap<String, Object> body) throws ParseException, JsonProcessingException {
        body.put("user_id", header.get("user_id"));
        String result = "fail";

        try{
            savedService.insertDeliverEmail(body);
            result = "success";
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;

    }

}

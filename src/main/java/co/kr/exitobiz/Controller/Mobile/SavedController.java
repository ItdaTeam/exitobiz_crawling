package co.kr.exitobiz.Controller.Mobile;

import co.kr.exitobiz.Service.Mobile.MainpageService;
import co.kr.exitobiz.Service.Mobile.SavedService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.authenticator.SavedRequest;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mobile")
public class SavedController {

    private final SavedService savedService;

    // 조회 총 갯수(최근 본 사업, 찜한 사업, 지원한 사업) 리스트
    @PostMapping("/saved/getTotalCountList")
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
    @PostMapping("/saved/getRecentlyMySavedBook")
    @ResponseBody
    public String getRecentlyMySavedBook(@RequestHeader HashMap<String, Object> header, @RequestBody HashMap<String, Object> body) throws ParseException, JsonProcessingException {

        body.put("user_id", header.get("user_id"));

        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writeValueAsString(savedService.getRecentlyMySavedBook(body));
        return jsonStr;
    }

    //찜한 사업, 지원한 사업 리스트
    @PostMapping("/saved/getMySavedBook")
    @ResponseBody
    public String getMySavedBook(@RequestHeader HashMap<String, Object> header, @RequestBody HashMap<String, Object> body) throws ParseException, JsonProcessingException {

        body.put("user_id", header.get("user_id"));

        ObjectMapper mapper = new ObjectMapper();
        String tojsonStr = mapper.writeValueAsString(savedService.getMySavedBook(body));
        return tojsonStr;
    }

    // 찜 flag 변경
    @PostMapping("/saved/isSavedMyBook")
    @ResponseBody
    public void isSavedMyBook(@RequestHeader HashMap<String, Object> header, @RequestBody HashMap<String, Object> body) throws ParseException, JsonProcessingException {
        body.put("mb_cret_id", header.get("user_id"));

        if(body.get("mb_save_yn").equals("")){
            savedService.insertSavedMyBook(body);
        }else if(body.get("mb_save_yn").equals("Y")){
            body.replace("mb_save_yn", "N");
            savedService.updateSavedMyBook(body);
        }else if(body.get("mb_save_yn").equals("N")){
            body.replace("mb_save_yn", "Y");
            savedService.updateSavedMyBook(body);
        }
    }

    // 지원 flag 변경
    @PostMapping("/saved/isReqSavedMyBook")
    @ResponseBody
    public void isReqSavedMyBook(@RequestHeader HashMap<String, Object> header, @RequestBody HashMap<String, Object> body) throws ParseException, JsonProcessingException {
        body.put("mb_cret_id", header.get("user_id"));
        if(body.get("mb_req_save_yn").equals("")){
            //값이 없을 경우, 생성일자도 추가한다.
            body.replace("mb_req_save_yn", "Y");
            body.put("mb_req_cret_dt", "");
        }else if(body.get("mb_req_save_yn").equals("N")){
            body.replace("mb_req_save_yn", "Y");
            body.replace("mb_done_save_yn", "");

        }else if(body.get("mb_req_save_yn").equals("Y")){
            body.replace("mb_req_save_yn", "N");

            if(body.get("mb_done_save_yn").equals("N")){
                System.out.println(body.get("mb_done_save_yn"));
                body.replace("mb_done_save_yn", "");
            }else if(body.get("mb_done_save_yn").equals("Y")){
                System.out.println(body.get("mb_done_save_yn"));
                // 지원을 해제할 때만 선정도 해제해줌.
                body.replace("mb_done_save_yn", "N");
            }
        }
        savedService.updateReqSavedMyBook(body);
    }

    // 선정 flag 변경
    @PostMapping("/saved/isDoneSavedMyBook")
    @ResponseBody
    public void isDoneSavedMyBook(@RequestHeader HashMap<String, Object> header, @RequestBody HashMap<String, Object> body) throws ParseException, JsonProcessingException {
        body.put("mb_cret_id", header.get("user_id"));

        if(body.get("mb_done_save_yn").equals("")){
            //값이 없을 경우, 생성일자도 추가한다.
            body.replace("mb_done_save_yn", "Y");
            body.put("mb_done_cret_dt", "");
        }else if(body.get("mb_done_save_yn").equals("N")){
            body.replace("mb_done_save_yn", "Y");
        }else if(body.get("mb_done_save_yn").equals("Y")){
            body.put("mb_done_save_yn", "N");
        }

        savedService.updateDoneSavedMyBook(body);
    }
    //찜 분류 개수 리스트
    @PostMapping("/saved/getCatList")
    @ResponseBody
    public String getCatList(@RequestHeader HashMap<String, Object> header) throws ParseException, JsonProcessingException {
        HashMap<String, Object> params = new HashMap<>();
        params.put("mb_cret_id", header.get("user_id"));

        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writeValueAsString(savedService.getCatList(params));
        return jsonStr;
    }

    //찜 현황 개수 리스트
    @PostMapping("/saved/getSavedTotalCount")
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
}

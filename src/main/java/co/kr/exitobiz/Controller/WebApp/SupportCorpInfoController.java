package co.kr.exitobiz.Controller.WebApp;

import co.kr.exitobiz.Service.WebApp.SupportCorpInfoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/corp")
public class SupportCorpInfoController {

    private final SupportCorpInfoService supportCorpService;

    //지원사업 리스트(정렬조건, 검색조건, 조회필터 포함)
    @PostMapping("/getSupportInfoList")
    @ResponseBody
    public String getSupportInfoList(@RequestHeader HashMap<String, Object> header, @RequestBody HashMap<String, Object> body, @RequestParam HashMap<String,String> data1) throws ParseException, JsonProcessingException {
        if(header.get("corp_cd") != null) body.put("corp_cd", header.get("corp_cd"));
        if(header.get("user_id") != null) body.put("user_id", header.get("user_id"));

        ObjectMapper mapper = new ObjectMapper();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        mapper.setDateFormat(dateFormat);

        String jsonStr = mapper.writeValueAsString(supportCorpService.getSupportCorpInfoList(body));
        return jsonStr;
    }

    // 누적갯수(누적 사업공고, 이번주 지원사업, 정보 제공기관) 리스트
    @PostMapping("/getTotalCount")
    @ResponseBody
    public String getTotalCount(@RequestBody HashMap<String, Object> params) throws ParseException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writeValueAsString(supportCorpService.getTotalCount(params));
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
            supportCorpService.upViewCnt(params);
            result = "success";
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    // 타임라인 추가 (인기 검색어 조회용 데이터)
    @PostMapping("/insertTimeLine")
    @ResponseBody
    public String insertTimeLine(@RequestHeader HashMap<String, Object> header, @RequestBody HashMap<String, Object> body) throws ParseException, JsonProcessingException {
        String result = "fail";

        if(header.get("user_id") != null && header.get("user_id") != "") {
            body.put("tl_cret_id", header.get("user_id"));

            try {
                supportCorpService.insertTimeLine(body);
                result = "success";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;

    }

    // 인기 키워드 리스트
    @PostMapping("/getSearchHotKeyWord")
    @ResponseBody
    public String getSearchHotKeyWord(@RequestBody HashMap<String, Object> params) throws ParseException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writeValueAsString(supportCorpService.getSearchHotKeyWord(params));
        return jsonStr;
    }

    // 필터 적용
    @PostMapping("/updateCorpCompanyInfo")
    @ResponseBody
    public String updateCorpCompanyInfo(@RequestHeader HashMap<String, Object> header, @RequestBody HashMap<String, Object> body) throws ParseException, JsonProcessingException{
        String result = "fail";

        if(header.get("user_id") != null && header.get("user_id") != ""){
            body.put("user_id", header.get("user_id"));

            try{
                supportCorpService.updateCorpCompanyInfo(body);
                result = "success";
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return result;
    }

    //필터 조회
    @PostMapping("/getCorpCompanyInfo")
    @ResponseBody
    public String getCorpCompanyInfo(@RequestHeader HashMap<String, Object> header, @RequestBody HashMap<String, Object> body) throws ParseException, JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = null;
        if(header.get("user_id") != null && header.get("user_id") != ""){
            body.put("user_id", header.get("user_id"));
            jsonStr = mapper.writeValueAsString(supportCorpService.getCorpCompanyInfo(body));
        }
        return jsonStr;
    }

    //상단 컨텐츠 조회

    @PostMapping("/getContentInfo")
    @ResponseBody
    public List<Map<String,Object>> getContentInfo(@RequestBody HashMap<String,Object> params){

        return supportCorpService.getContentInfo(params);
    }
}

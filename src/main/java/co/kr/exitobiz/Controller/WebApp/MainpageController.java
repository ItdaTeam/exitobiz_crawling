package co.kr.exitobiz.Controller.WebApp;

import co.kr.exitobiz.Service.WebApp.MainpageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.HashMap;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mainpage")
public class MainpageController {

    private final MainpageService mainpageService;

    // 인기 키워드 리스트
    @PostMapping("/getSearchHotKeyWord")
    @ResponseBody
    public String getSearchHotKeyWord() throws ParseException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writeValueAsString(mainpageService.getSearchHotKeyWord());
        return jsonStr;
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
        if(body.get("search_text") != null){

            // 나의 최근 검색어 저장
            params.put("tl_cret_id", header.get("user_id"));
            params.put("tl_page_type", "지원사업");
            params.put("tl_page_depth", "2");
            params.put("tl_page_name", "지원사업-검색화면");
            params.put("tl_button_name", "검색버튼");
            params.put("tl_type_cd", 0);
            params.put("tl_event", body.get("search_text"));
            params.put("tl_memo", "-");
        }else if(body.get("support_info") != null){

            // 최근 본 지원사업 저장
            params.put("tl_cret_id", header.get("user_id"));
            params.put("tl_page_type", "지원사업");
            params.put("tl_page_depth", "3");
            params.put("tl_page_name", "지원사업-웹뷰");
            params.put("tl_button_name", "화면진입");
            params.put("tl_type_cd", Integer.parseInt((String) body.get("support_info")));
            params.put("tl_event", "진입완료");
            params.put("tl_memo", "-");
        }else if(body.get("comm_memo") != null){

            //커뮤니티 상세 진입 저장
            params.put("tl_cret_id", header.get("user_id"));
            params.put("tl_page_type", "커뮤니티-상세화면");
            params.put("tl_page_dept", "2");
            params.put("tl_page_name", "커뮤니티-상세화면");
            params.put("tl_button_name", "화면진입");
            params.put("tl_type_cd",  Integer.parseInt((String)body.get("comm_id")));
            params.put("tl_event", "진입완료");
            params.put("tl_memo", body.get("comm_memo"));
        }

        try{
            mainpageService.insertTimeLine(params);
            result = "success";
        }catch(Exception e){
           e.printStackTrace();
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

}

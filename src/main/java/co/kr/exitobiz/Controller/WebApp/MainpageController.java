package co.kr.exitobiz.Controller.WebApp;

import co.kr.exitobiz.Service.WebApp.MainpageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
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
    public void delMyRecentKeyword(@RequestBody HashMap<String, Object> body) throws ParseException, JsonProcessingException {
        mainpageService.delMyRecentKeyword(body);
    }

    // 나의 최근 키워드 전체 삭제
    @PostMapping("/delMyAllRecentKeyword")
    @ResponseBody
    public void delMyAllRecentKeyword(@RequestHeader HashMap<String, Object> header) throws ParseException, JsonProcessingException {
        HashMap<String, Object> params = new HashMap<>();
        params.put("tl_cret_id", header.get("user_id"));

        mainpageService.delMyAllRecentKeyword(params);
    }

    // 타임라인 추가 (나의 최근 검색어 저장) _ 공통
    @PostMapping("/insertTimeLine")
    @ResponseBody
    public void insertTimeLine(@RequestHeader HashMap<String, Object> header, @RequestBody HashMap<String, Object> body) throws ParseException, JsonProcessingException {
        HashMap<String, Object> params = new HashMap<>();
        params.put("tl_cret_id", header.get("user_id"));
        params.put("tl_page_type", "지원사업");
        params.put("tl_page_depth", "2");
        params.put("tl_page_name", "지원사업-검색화면");
        params.put("tl_button_name", "검색버튼");
        params.put("tl_type_cd", 0);
        params.put("tl_event", body.get("search_text"));
        params.put("tl_memo", "-");

        mainpageService.insertTimeLine(params);
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
    public void getKeyword(@RequestHeader HashMap<String, Object> header, @RequestBody HashMap<String, Object> body) throws ParseException, JsonProcessingException {
        body.put("user_id", header.get("user_id"));
        mainpageService.insertKeyword(body);
    }

    // 키워드 정기배송 단일 삭제
    @PostMapping("/delKeyword")
    @ResponseBody
    public void delKeyword(@RequestHeader HashMap<String, Object> header, @RequestBody HashMap<String, Object> body) throws ParseException, JsonProcessingException {
        body.put("user_id", header.get("user_id"));
        mainpageService.delKeyword(body);
    }

    // 키워드 정기배송 전체 삭제
    @PostMapping("/delAllKeyword")
    @ResponseBody
    public void delAllKeyword(@RequestHeader HashMap<String, Object> header) throws ParseException, JsonProcessingException {
        HashMap<String, Object> params = new HashMap<>();
        params.put("user_id", header.get("user_id"));
        mainpageService.delAllKeyword(params);
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

}

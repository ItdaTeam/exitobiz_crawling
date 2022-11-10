package co.kr.exitobiz.Controller.Mobile;

import co.kr.exitobiz.Mappers.Mobile.MainpageMapper;
import co.kr.exitobiz.Service.Mobile.CommunityService;
import co.kr.exitobiz.Service.Mobile.FileService;
import co.kr.exitobiz.Service.Mobile.MainpageService;
import co.kr.exitobiz.Vo.Cms.ImageLink;
import co.kr.exitobiz.Vo.Mobile.CommunityVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mobile")
public class MainpageController {

    private final MainpageService mainpageService;

    // 인기 키워드 리스트
    @PostMapping("/mainpage/getSearchHotKeyWord")
    @ResponseBody
    public String getSearchHotKeyWord() throws ParseException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writeValueAsString(mainpageService.getSearchHotKeyWord());
        return jsonStr;
    }

    // 나의 최근 키워드 리스트
    @PostMapping("/mainpage/getMyRecentKeyword")
    @ResponseBody
    public String getMyRecentKeyword(@RequestHeader HashMap<String, Object> header) throws ParseException, JsonProcessingException {
        HashMap<String, Object> params = new HashMap<>();
        params.put("tl_cret_id", header.get("user_id"));

        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writeValueAsString(mainpageService.getMyRecentKeyword(params));
        return jsonStr;
    }

    // 나의 최근 키워드 단일 삭제
    @PostMapping("/mainpage/delMyRecentKeyword")
    @ResponseBody
    public void delMyRecentKeyword(@RequestBody HashMap<String, Object> body) throws ParseException, JsonProcessingException {
        mainpageService.delMyRecentKeyword(body);
    }

    // 나의 최근 키워드 전체 삭제
    @PostMapping("/mainpage/delMyAllRecentKeyword")
    @ResponseBody
    public void delMyAllRecentKeyword(@RequestHeader HashMap<String, Object> header) throws ParseException, JsonProcessingException {
        HashMap<String, Object> params = new HashMap<>();
        params.put("tl_cret_id", header.get("user_id"));

        mainpageService.delMyAllRecentKeyword(params);
    }

    // 타임라인 추가 (나의 최근 검색어 저장) _ 공통
    @PostMapping("/mainpage/insertTimeLine")
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
    @PostMapping("/mainpage/getBannerList")
    @ResponseBody
    public String getCommunityList() throws ParseException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writeValueAsString(mainpageService.getBannerList());
        return jsonStr;
    }

    // 누적갯수(누적 지원사업 개수, 이번주 지원사업, 정보 제공기관, 누적 가입 기업) 리스트
    @PostMapping("/mainpage/getTotalCount")
    @ResponseBody
    public String getTotalCount() throws ParseException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writeValueAsString(mainpageService.getTotalCount());
        return jsonStr;
    }


    // 키워드 정기배송 리스트 (","로 구분)
    @PostMapping("/mainpage/getKeyword")
    @ResponseBody
    public String getKeyword(@RequestHeader HashMap<String, Object> header) throws ParseException, JsonProcessingException {
        HashMap<String, Object> params = new HashMap<>();

        params.put("user_id", header.get("user_id"));

        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writeValueAsString(mainpageService.getKeyword(params));
        return jsonStr;
    }

    // 키워드 정기배송 추가
    @PostMapping("/mainpage/insertKeyword")
    @ResponseBody
    public void getKeyword(@RequestHeader HashMap<String, Object> header, @RequestBody HashMap<String, Object> body) throws ParseException, JsonProcessingException {
        body.put("user_id", header.get("user_id"));
        mainpageService.insertKeyword(body);
    }

    // 키워드 정기배송 단일 삭제
    @PostMapping("/mainpage/delKeyword")
    @ResponseBody
    public void delKeyword(@RequestHeader HashMap<String, Object> header, @RequestBody HashMap<String, Object> body) throws ParseException, JsonProcessingException {
        body.put("user_id", header.get("user_id"));
        mainpageService.delKeyword(body);
    }

    // 키워드 정기배송 전체 삭제
    @PostMapping("/mainpage/delAllKeyword")
    @ResponseBody
    public void delAllKeyword(@RequestHeader HashMap<String, Object> header) throws ParseException, JsonProcessingException {
        HashMap<String, Object> params = new HashMap<>();
        params.put("user_id", header.get("user_id"));
        mainpageService.delAllKeyword(params);
    }


    // 엑시토 추천 리스트
    @PostMapping("/mainpage/getPushBookList")
    @ResponseBody
    public String getPushBookList(@RequestHeader HashMap<String, Object> header) throws ParseException, JsonProcessingException {
        HashMap<String, Object> params = new HashMap<>();
        params.put("user_id", header.get("user_id"));
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writeValueAsString(mainpageService.getPushBookList(params));
        return jsonStr;
    }

}

package co.kr.exitobiz.Controller.Mobile;

import co.kr.exitobiz.Entity.Content;
import co.kr.exitobiz.Entity.Notice;
import co.kr.exitobiz.Entity.Popup;
import co.kr.exitobiz.Service.Cms.ContentService;
import co.kr.exitobiz.Service.Mobile.CommunityService;
import co.kr.exitobiz.Service.Mobile.FileService;
import co.kr.exitobiz.Vo.Cms.*;
import co.kr.exitobiz.Vo.Mobile.CommunityVo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mobile")
public class CommunityController {

    private final CommunityService communityService;

    //상세페이지
    @RequestMapping(value = "/community/detail", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
    public String disCommunity(@Valid HttpServletRequest req, Model model, HttpServletResponse res) throws Exception {
        int id = Integer.parseInt(req.getParameter("id"));

        model.addAttribute("id", id);
        model.addAttribute("userId", req.getParameter("userId"));

        // 저장된 쿠키 불러오기
        Cookie cookies[] = req.getCookies();
        Map mapCookie = new HashMap();
        if(req.getCookies() != null){
            for(int i=0; i<cookies.length; i++){
                Cookie obj = cookies[i];
                mapCookie.put(obj.getName(), obj.getValue());
            }
        }

        //저장된 쿠키 중에 readCount만 불러오기
        String readCount = (String) mapCookie.get("replyCount");

        //저장된 새로운 쿠키값 생성
        String newReadCount = "|" + id;

        // 저장된 쿠키에 새로운 쿠키값이 존재하는 지 검사
        if(readCount == null || readCount.indexOf(newReadCount) == -1){
            // 없을 경우 쿠키 생성
            Cookie cookie = new Cookie("replyCount", readCount + newReadCount);

            res.addCookie(cookie);

            communityService.reviewViews(id); // 조회수 업데이트
        }
//        if(StringUtils.indexOfIgnoreCase(readCount, newReadCount) == -1){
//
//        }

        return "/community/community2";
    }

    //수정, 등록페이지
    @RequestMapping(value = "/community/edit", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
    public String disCommunityEdit(@Valid HttpServletRequest request, Model model){
        model.addAttribute("id", request.getParameter("id"));
        model.addAttribute("userId", request.getParameter("userId"));
        return "/community/community";
    }

    @GetMapping("/community/all")
    @ResponseBody
    public List<CommunityVo> getCommunityList(@RequestParam HashMap<String, String> params) throws ParseException {
        return communityService.getCommunityList(params);
    }

    @GetMapping("/community/one")
    @ResponseBody
    public HashMap<String, Object> getCommunityDetail(CommunityVo communityVo) throws ParseException {
        return communityService.getCommunityDetail(communityVo);
    }

    @PostMapping("/community")
    @ResponseBody
    public void createCommunity(CommunityVo communityVo) throws ParseException {
        System.out.println("#########" + communityVo);
        communityService.insertCommunity(communityVo);
    }

    @PutMapping("/community/edit")
    @ResponseBody
    public void editCommunity(CommunityVo communityVo) throws ParseException {
        System.out.println("#########" + communityVo);
            communityService.updateCommunity(communityVo);
    }

    @PutMapping("/community/delete")
    @ResponseBody
    public void delCommunity(CommunityVo communityVo)  throws ParseException{
        System.out.println("$$$$$$$$$4" + communityVo);
        communityService.deleteCommunity(communityVo);
    }

    @PutMapping("/community/declare")
    @ResponseBody
    public void decCommunity(CommunityVo communityVo) throws ParseException {
        communityService.declareCommunity(communityVo);
    }


}

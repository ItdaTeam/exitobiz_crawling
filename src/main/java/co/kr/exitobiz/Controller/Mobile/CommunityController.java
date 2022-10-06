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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mobile")
public class CommunityController {

    private final CommunityService communityService;

    @GetMapping("/community")
    public String disCommunity(@Valid HttpServletRequest request, Model model){
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
            communityService.updateCommunity(communityVo);
    }

    @PutMapping("/community/delete")
    @ResponseBody
    public void delCommunity(CommunityVo communityVo) {
        communityService.deleteCommunity(communityVo);
    }

}

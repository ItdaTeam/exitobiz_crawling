package co.kr.exitobiz.Controller.Mobile;

import co.kr.exitobiz.Service.Cms.ContentService;
import co.kr.exitobiz.Service.Cms.TermsService;
import co.kr.exitobiz.Service.Mobile.ItdaService;
import co.kr.exitobiz.Vo.Cms.NoticeVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItdaController {

    private final ContentService contentService;

    @Autowired
    private ItdaService itdaService;

    @Autowired
    TermsService termsService;

    //랜딩페이지
    @RequestMapping(value = "/")
    public String main(){
        return "landing";
    }

    //이용약관페이지
    @RequestMapping(value = "/standard")
    public String standard(HttpServletRequest req)
    {
        req.setAttribute("termService", termsService.getTermService());
        return "policy01";
    }

    //개인정보처리약관페이지
    @RequestMapping(value = "/personinfo")
    public String personinfo(HttpServletRequest req)
    {
        req.setAttribute("termPrivacy", termsService.getTermPrivacy());
        return "policy02";
    }

    //위치기반서비스이용약관페이지
    @RequestMapping(value = "/locationinfo")
    public String locationinfo(HttpServletRequest req)
    {
        req.setAttribute("termLocate", termsService.getTermLocate());
        return "policy03";
    }


     //마케팅정보수신동의약관페이지
     @RequestMapping(value = "/marketingInfo")
     public String marketingInfo(HttpServletRequest req)
     {
       req.setAttribute("termLMarket", termsService.getTermMarket());
         return "policy05";
     }



    //엉따 모바일 개인정보처리약관페이지
    @RequestMapping(value = "/personmobile")
    public String personmobile(HttpServletRequest req)
    {
        req.setAttribute("personMobile", termsService.getPersonMobile());
        return "policy04";
    }

    @GetMapping("/m/content")
    public String getContentPage(@RequestParam HashMap<String, String> params, HttpServletRequest req){

        req.setAttribute("contentPage", contentService.getContentPage(params));

        return "/cms/content/mobile_content";
    }

    @GetMapping(value = "/m/notice")
    public String getNoticePage(Model model, @RequestParam HashMap<String,String> params) {
        if(params.get("id") == null){
            List<NoticeVo> mustNotices = contentService.getMustNotice();

            System.out.println(mustNotices.toString());

            List<NoticeVo> firstNotices = contentService.getFirstNotice();

            model.addAttribute("MustInfo", mustNotices);
            model.addAttribute("firstNotice", firstNotices);
            return "/cms/content/mobile_notice";
        }else {

            model.addAttribute("NoticeInfo", contentService.getNoticePage(params));
            return "/cms/content/popup_notice";
        }
    }

    @GetMapping("/m/getNotice")
    @ResponseBody
    public List<NoticeVo> getNotice(@RequestParam HashMap<String, String> params) {
        return contentService.getNoticeList(params);
    }

//    @RequestMapping(value = "/down")
//    public String down(){
//        return "user_down";
//    }
//
//    @RequestMapping(value = "/addQuestion")
//    @ResponseBody
//    public void addQuestion(@RequestParam HashMap<String,String> params){
//        itdaService.addQuestion(params);
//    }
//
//    @RequestMapping(value = "/landing")
//    public String landing(){
//        return "landing";
//    }
//
//    @RequestMapping(value = "/landingMobile")
//    public String landingMobile(){
//        return "mobile";
//    }
//
//    @RequestMapping(value = "/landingPc")
//    public String landingPc(){
//        return "pc";
//    }
//
//    @RequestMapping(value = "/test")
//    public String test(){
//        return "test";
//    }

//    @RequestMapping(value = "/updateLoc")
//    @ResponseBody
//    public void updateLoc(){
//        itdaService.updatePlace();
//    }
//
//    @RequestMapping(value = "/updatePhone")
//    @ResponseBody
//    public void updatePhone(){
//        itdaService.updatePhone();
//    }
}

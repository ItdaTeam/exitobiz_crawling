package co.kr.exitobiz.Controller.Cms;

import co.kr.exitobiz.Service.Cms.TermsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
public class TermsController {

    @Autowired
    TermsService termsService;

    //약관 관리 화면
    @GetMapping
    @RequestMapping(value = "/cms/terms")
    public String disTerms(){
        return "/cms/terms/terms";
    }

    //개인정보처리방침
    @GetMapping
    @RequestMapping(value = "/cms/terms/getTermPrivacy")
    @ResponseBody
    public String getTermPrivacy(){
        return termsService.getTermPrivacy();
    }

    @PostMapping
    @RequestMapping(value = "/cms/terms/saveTermPrivacy")
    @ResponseBody
    public void saveTermPrivacy(@RequestParam HashMap<String, String> params, HttpServletRequest req){

//        params.put("updtId",req.getSession().getAttribute("staffId").toString());

        termsService.saveTermPrivacy(params);
    }

    //서비스이용약관
    @RequestMapping(value = "/cms/terms/getTermService")
    @ResponseBody
    public String getTermService(){
        return termsService.getTermService();
    }

    @RequestMapping(value = "/cms/terms/saveTermService")
    @ResponseBody
    public void saveTermService(@RequestParam HashMap<String, String> params, HttpServletRequest req){

//        params.put("updtId",req.getSession().getAttribute("staffId").toString());

        termsService.saveTermService(params);
    }

    //위치기반서비스이용약관
    @RequestMapping(value = "/cms/terms/getTermLocate")
    @ResponseBody
    public String getTermLocate(){
        return termsService.getTermLocate();
    }

    @RequestMapping(value = "/cms/terms/saveTermLocate")
    @ResponseBody
    public void saveTermLocate(@RequestParam HashMap<String, String> params, HttpServletRequest req){

//        params.put("updtId",req.getSession().getAttribute("staffId").toString());

        termsService.saveTermLocate(params);
    }
    
     //마케팅정보수신동의
    @RequestMapping(value = "/cms/terms/getTermMarket")
    @ResponseBody
    public String getTermMarket(){
        return termsService.getTermMarket();
    }

    @RequestMapping(value = "/cms/terms/saveTermMarket")
    @ResponseBody
    public void saveTermMarket(@RequestParam HashMap<String, String> params, HttpServletRequest req){

//        params.put("updtId",req.getSession().getAttribute("staffId").toString());

        termsService.saveTermMarket(params);
    }

}

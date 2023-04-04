package co.kr.exitobiz.Controller.WebApp;

import co.kr.exitobiz.Service.WebApp.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 공통성
 */
@Controller
@RequestMapping("/common")
public class CommonController {

    @Autowired
    CommonService commonService;

    @PostMapping("/codeCtgList")
    @ResponseBody
    public List<Map<String,Object>> getCommonCodeCtgList(@RequestParam Map<String,Object> params){
        return commonService.getCommonCodeCtgList(params);
    }


    @PostMapping("/codeDtlList")
    @ResponseBody
    public List<Map<String,Object>> getCommonCodeDtlList(@RequestParam Map<String,Object> params){
        return commonService.getCommonCodeDtlList(params);
    }

    @PostMapping("/getCommonCode")
    @ResponseBody
    public Map<String, Object> getCommonCode(@RequestParam Map<String, Object> params){
        return commonService.getCommonCode(params);
    }

}

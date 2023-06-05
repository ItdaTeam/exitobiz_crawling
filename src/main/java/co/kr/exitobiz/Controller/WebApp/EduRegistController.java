package co.kr.exitobiz.Controller.WebApp;

import co.kr.exitobiz.Service.WebApp.EduRegistService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.HashMap;

@Controller
@RequiredArgsConstructor
@RequestMapping("/corp/edu")
public class EduRegistController {

    private final EduRegistService eduRegistService;

    // 교육 등록
    @PostMapping("/saveRegist")
    @ResponseBody
    public String saveRegist(@RequestHeader HashMap<String, Object> header, @RequestBody HashMap<String, Object> params) throws ParseException, JsonProcessingException {
        String result = "fail";
        params.put("corp_cd", header.get("corp_cd"));
        params.put("user_id", header.get("user_id"));

        try{
            //등록확인
            String chk = String.valueOf(eduRegistService.chkRegist(params).get("chk"));
            if(chk.equals("1")) return "done";

            eduRegistService.saveEduRegist(params);
            result = "success";
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

}

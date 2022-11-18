package co.kr.exitobiz.Controller.WebApp;

import co.kr.exitobiz.Service.WebApp.SupportInfoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.HashMap;

@Controller
@RequiredArgsConstructor
@RequestMapping("/support")
public class SupportInfoController {


    private final SupportInfoService supportService;

    //지원사업 리스트(정렬조건, 검색조건, 조회필터 포함)
    @PostMapping("/getSupportInfoList")
    @ResponseBody
    public String getSupportInfoList(@RequestHeader HashMap<String, Object> header, @RequestBody HashMap<String, Object> body) throws ParseException, JsonProcessingException {
        if(header.get("user_id") != null) body.put("user_id", header.get("user_id"));

        // 사업자형태 필터
        if(body.get("business_type") != null){
            String business_type = (String) body.get("business_type");
            String[] array = business_type.trim().split(",");
            body.put("business_type", array);
        }

        // 기업형태 필터
        if(body.get("company_type") != null){
            String company_type = (String) body.get("company_type");
            String[] array = company_type.trim().split(",");
            body.put("company_type", array);
        }

        // 지원분야 필터
        if(body.get("target_cat_name") != null){
            String target_cat_name = (String) body.get("target_cat_name");
            String[] array = target_cat_name.trim().split(",");
            body.put("target_cat_name", array);
        }

        // 기술분야 (사업분야) 필터
        if(body.get("business_ctg") != null){
            String business_ctg = (String) body.get("business_ctg");
            String[] array = business_ctg.trim().split(",");
            body.put("business_ctg", array);
        }

        // 기술분야 (기술분야) 필터
        if(body.get("tech_ctg") != null){
            String tech_ctg = (String) body.get("tech_ctg");
            String[] array = tech_ctg.trim().split(",");
            body.put("tech_ctg", array);
        }

        // 지역 필터
        if(body.get("loc_code") != null){
            String loc_code = (String) body.get("loc_code");
            String[] array = loc_code.trim().split(",");
            body.put("loc_code", array);
        }


        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writeValueAsString(supportService.getSupportInfoList(body));
        return jsonStr;
    }

}

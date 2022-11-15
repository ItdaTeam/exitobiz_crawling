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

    // 지원사업 리스트(정렬조건, 검색조건 포함)
    @PostMapping("/getSupportInfoList")
    @ResponseBody
    public String getSupportInfoList(@RequestHeader HashMap<String, Object> header, @RequestBody HashMap<String, Object> body) throws ParseException, JsonProcessingException {
        if(header.get("user_id") != null) body.put("user_id", header.get("user_id"));

        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writeValueAsString(supportService.getSupportInfoList(body));
        return jsonStr;
    }

}

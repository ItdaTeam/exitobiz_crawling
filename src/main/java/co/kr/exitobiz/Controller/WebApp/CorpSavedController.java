package co.kr.exitobiz.Controller.WebApp;

import co.kr.exitobiz.Service.WebApp.CorpSavedService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/corp/saved")
public class CorpSavedController {

    private final CorpSavedService corpSavedService;

    //찜한 사업 리스트
    @PostMapping("/getMySavedBook")
    @ResponseBody
    public String getMySavedBook(@RequestHeader HashMap<String, Object> header, @RequestBody HashMap<String, Object> body) throws ParseException, JsonProcessingException {

        body.put("user_id", header.get("user_id"));
        body.put("corp_cd", header.get("corp_cd"));

        ObjectMapper mapper = new ObjectMapper();
        String tojsonStr = mapper.writeValueAsString(corpSavedService.getMySavedBook(body));
        return tojsonStr;
    }

    // 찜 flag 변경
    @PostMapping("/isSavedMyBook")
    @ResponseBody
    public String isSavedMyBook(@RequestHeader HashMap<String, Object> header, @RequestBody HashMap<String, Object> body) throws ParseException, JsonProcessingException {
        body.put("mb_cret_id", header.get("user_id"));
        body.put("corp_cd", header.get("corp_cd"));

        HashMap<String, Object> result = new HashMap<>();
        result.put("result", "fail");

        ObjectMapper mapper = new ObjectMapper();

        try {
            if (body.get("mb_save_yn") == null || body.get("mb_save_yn").equals("")) {
                corpSavedService.insertSavedMyBook(body);
            } else if (body.get("mb_save_yn").equals("Y")) {
                body.replace("mb_save_yn", "N");
                corpSavedService.updateSavedMyBook(body);
            } else if (body.get("mb_save_yn").equals("N")) {
                body.replace("mb_save_yn", "Y");
                corpSavedService.updateSavedMyBook(body);
            }

            HashMap chkParams = new HashMap();
            chkParams.put("what_flag", "mb_save_yn");
            chkParams.put("mb_cret_id", header.get("user_id"));
            chkParams.put("corp_cd", header.get("corp_cd"));
            chkParams.put("mb_addidx", body.get("mb_addidx"));


            result.replace("result", "success");

            result.putAll(corpSavedService.checkSavedFlag(chkParams));

        } catch (Exception e) {
            e.printStackTrace();
        }

        String tojsonStr = mapper.writeValueAsString(result);

        return tojsonStr;

    }

    //찜 분류 개수 리스트
    @PostMapping("/getCatList")
    @ResponseBody
    public String getCatList(@RequestHeader HashMap<String, Object> header) throws ParseException, JsonProcessingException {
        HashMap<String, Object> params = new HashMap<>();
        params.put("mb_cret_id", header.get("user_id"));
        params.put("corp_cd", header.get("corp_cd"));

        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writeValueAsString(corpSavedService.getCatList(params));
        return jsonStr;
    }

}

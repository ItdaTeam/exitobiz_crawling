package co.kr.exitobiz.Controller.Api;


import co.kr.exitobiz.Service.Api.AppleService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/apple")
@RestController
public class AppleController {

    @Autowired
    AppleService appleService;


    @RequestMapping("/login")
    @ResponseBody
    public Map<String,String> login(@RequestHeader Map<String,Object> params) {

        String id_token = params.get("id_token").toString();
        Map<String, String> userInfo = new HashMap<String, String>();
        if (id_token != null) {
            try {
                Map<String, Object> data = new HashMap<>();
                data.put("id", id_token);
                userInfo = appleService.login(data);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else{
            userInfo.put("email", null);
            userInfo.put("usernickname",null);
        }
        return userInfo;
    }
}

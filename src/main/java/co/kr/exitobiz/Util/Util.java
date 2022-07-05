package co.kr.exitobiz.Util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Util {

    public static  List <String> makeForeach (String str, String sep){
        List<String> list =   new ArrayList<String>();
        String[] aCode = str.split(sep);

        if (str == null || "".equals(str)) {
            return null;
        }
        for(int i=0; i< aCode.length; i++){
            list.add(aCode[i].toString().trim());
        }

        return list;
    }


    // list<map> 을 json 형태로 변형.
    @SuppressWarnings({ "unchecked" })
    public static JSONArray convertListToJson(List<HashMap<String, Object>> bankCdList) {

        JSONArray jsonArray = new JSONArray();

        for (Map<String, Object> map : bankCdList) {
            jsonArray.add(convertMapToJson(map));
        }

        return jsonArray;

    }

    // map 을 json 형태로 변형
    @SuppressWarnings({ "unchecked" })
    public static JSONObject convertMapToJson(Map<String, Object> map) {

        JSONObject json = new JSONObject();

        for (Map.Entry<String, Object> entry : map.entrySet()) {

            String key = entry.getKey();
            Object value = entry.getValue();
            // json.addProperty(key, value);
            json.put(key, value);

        }

        return json;

    }


    public static Map<String, String> jsonStringToMap(String str) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> map = new HashMap<>();

        try{
            map = mapper.readValue(str, Map.class);
        } catch (IOException e){
            e.printStackTrace();
        }

        return map;
    }



    public static void alert(String message) throws IOException {
        ServletRequestAttributes servletContainer = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = servletContainer.getResponse();
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<script>alert('"+message+"');window.close()</script>");
        out.flush();
    }




}
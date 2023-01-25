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
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

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

    public static String getLocCode(String locName){
        String locCode  = "";

        switch (locName){
            case "서울" : locCode = "C02"; break;
            case "경기" : locCode = "C031"; break;
            case "인천" : locCode = "C032"; break;
            case "강원" : locCode = "C033"; break;
            case "대전" : locCode = "C042"; break;
            case "충북" : locCode = "C043"; break;
            case "충남" : locCode = "C041"; break;
            case "세종" : locCode = "C044"; break;
            case "광주" : locCode = "C062"; break;
            case "전북" : locCode = "C063"; break;
            case "전남" : locCode = "C061"; break;
            case "부산" : locCode = "C051"; break;
            case "울산" : locCode = "C052"; break;
            case "대구" : locCode = "C053"; break;
            case "경북" : locCode = "C054"; break;
            case "경남" : locCode = "C055"; break;
            case "제주" : locCode = "C064"; break;
            default : locCode = "C82";  break;
        }

        return locCode;
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T,Object> keyExtractor) {
        Map<Object,Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }




}
package co.kr.exitobiz.Service.WebApp;

import java.util.Map;


public interface JwtService {
    String createDataToken(Map<String,Object> data);
}

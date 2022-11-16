package co.kr.exitobiz.Controller.WebApp;

import co.kr.exitobiz.Service.WebApp.JwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.Map;


@Controller
@RequestMapping(value = "/jwt")
public class JWTController {

    @Autowired
    private JwtService jwtService;

    public String createDataToken(Map<String,Object> data){
        return jwtService.createDataToken(data);
    }
//
//    public Map<String,Object> decodeDataToken(String dataToken){
//        Map<String,Object> data = new Map<>();
//
//
//
//        return data;
//    }
}
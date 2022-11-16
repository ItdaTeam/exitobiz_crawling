package co.kr.exitobiz.Service.WebApp.impl;

import co.kr.exitobiz.Service.WebApp.JwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final SignatureAlgorithm al = SignatureAlgorithm.HS256;
    @Value("${jwt.secret}")
    private String secretKey;

    @Override
    public String createDataToken(Map<String, Object> data) {
        Key key = new SecretKeySpec(secretKey.getBytes(),al.getJcaName());
        return Jwts.builder()
                .setHeaderParam("type","JWT")
                .setSubject(data.get("id").toString())
                .claim("data",data)
                .setExpiration(new Date(System.currentTimeMillis()+ (1000 * 60))) // 데이터 제공 유효 토큰은 1시간
                .signWith(key,al)
                .compact();
    }
}

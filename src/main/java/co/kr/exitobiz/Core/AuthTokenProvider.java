package co.kr.exitobiz.Core;

import java.util.Date;

public interface AuthTokenProvider<T>{
    T createAuthToken(String id, String name, Date expiredDate);
    T convertAuthToken(String token);
}

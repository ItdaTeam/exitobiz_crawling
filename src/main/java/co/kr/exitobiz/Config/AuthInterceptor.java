package co.kr.exitobiz.Config;

import co.kr.exitobiz.Util.RedisUtil;
import co.kr.exitobiz.jwt.JwtAuthToken;
import co.kr.exitobiz.jwt.JwtAuthTokenProvider;
import com.google.auth.oauth2.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final RedisUtil redisUtil;
    private final JwtAuthTokenProvider jwtAuthTokenProvider;
    private static final String AUTHORIZATION_HEADER = "X-AUTH-TOKEN";


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception{
        log.info("preHandle!!");

        Optional<String> token = resolveToken(request);

        if(token.isPresent()){
            JwtAuthToken jwtAuthToken = jwtAuthTokenProvider.convertAuthToken(token.get());
            return true;
        }else {
            return false;
        }
    }

    private Optional<String> resolveToken(HttpServletRequest request) {

        //세션아이디를 키값으로 체크
        String authToken = redisUtil.getData(request.getSession().getId());

        if (StringUtils.hasText(authToken)) {
            return Optional.of(authToken);
        } else {
            return Optional.empty();
        }
    }

}

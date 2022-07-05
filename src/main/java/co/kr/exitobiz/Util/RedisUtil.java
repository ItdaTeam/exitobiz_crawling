package co.kr.exitobiz.Util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate redisTemplate;

    public String getData(String key){
        ValueOperations<String,String> values = redisTemplate.opsForValue();
        return values.get(key);
    }

    public void setData(String key, String value){
        ValueOperations<String,String> values = redisTemplate.opsForValue();
        values.set(key,value);
    }

    public void setDataExpire(String key,String value,long duration){
        ValueOperations<String,String> values = redisTemplate.opsForValue();
        Duration expireDuration = Duration.ofSeconds(duration);
        values.set(key,value,expireDuration);
    }

    public void deleteData(String key){
        redisTemplate.delete(key);
    }

}

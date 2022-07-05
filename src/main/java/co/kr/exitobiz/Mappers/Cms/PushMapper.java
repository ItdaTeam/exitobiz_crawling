package co.kr.exitobiz.Mappers.Cms;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;

@Mapper
public interface PushMapper {
    void savePush(HashMap<String, String> params);
}

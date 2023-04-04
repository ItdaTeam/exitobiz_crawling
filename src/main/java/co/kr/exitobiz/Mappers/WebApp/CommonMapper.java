package co.kr.exitobiz.Mappers.WebApp;

import co.kr.exitobiz.Vo.usertableVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface CommonMapper {

    List<Map<String, Object>> getCommonCodeCtgList(Map<String, Object> params);    //공통코드분류 조회
    List<Map<String, Object>> getCommonCodeDtlList(Map<String, Object> params);    //공통코드상세 조회
    Map<String, Object> getCommonCode(Map<String, Object> params); // 코드 조회
}
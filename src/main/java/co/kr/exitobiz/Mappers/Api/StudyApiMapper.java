package co.kr.exitobiz.Mappers.Api;

import org.apache.ibatis.annotations.Mapper;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface StudyApiMapper {
    void insertUserData(HashMap<String, Object> params) throws ParseException; //유저 정보 추가

    void updateUserData(HashMap<String, Object> params) throws ParseException; //유저 정보 변경

    void deleteUserData(HashMap<String, Object> params) throws ParseException; //유저 정보 변경

    List<Map<String, Object>> selectUserData(HashMap<String, Object> params) throws ParseException; //유저 정보 검색
}

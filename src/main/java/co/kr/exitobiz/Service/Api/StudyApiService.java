package co.kr.exitobiz.Service.Api;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface StudyApiService {
    void insertUserData(HashMap<String, Object> params) throws ParseException; //유저 정보 추가

    void updateUserData(HashMap<String, Object> params) throws ParseException; //유저 정보 변경

    void deleteUserData(HashMap<String, Object> params) throws ParseException; //유저 정보 삭제

    List<Map<String, Object>> selectUserData(HashMap<String, Object> params) throws ParseException; //유저 정보 검색
}

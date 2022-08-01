package co.kr.exitobiz.Mappers.Api;

import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SmsMapper {

    void insertSmsHistory(HashMap<String, String> params);

    int checkCertInfo(HashMap<String, String> params);

    HashMap<String,String> getCertInfo(HashMap<String, String> params);

    void updateSmsHistory(HashMap<String, String> params);
}

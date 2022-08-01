package co.kr.exitobiz.Service.Api.impl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.kr.exitobiz.Mappers.Api.SmsMapper;
import co.kr.exitobiz.Service.Api.SmsService;

@Service
public class SmsServiceImpl implements SmsService{

    @Autowired
    SmsMapper smsMapper;


    //인증번호 6자리 만들기
    @Override
    public String getRandomNumber() {
        double min = 100000;
        double max = 999999;
        int random = (int) ((Math.random() * (max - min)) + min);
        return String.valueOf(random);
    }

    //문자 발송 이력 남기기
    @Override
    public void insertSmsHistory(HashMap<String, String> params) {
        smsMapper.insertSmsHistory(params);
        
    }

    //문자인증정보 확인하기
    @Override
    public int checkCertInfo(HashMap<String, String> params) {
        return smsMapper.checkCertInfo(params);
    }

    //문자인증 정보 가져오기
    @Override
    public HashMap<String,String> getCertInfo(HashMap<String, String> params) {
        return smsMapper.getCertInfo(params);
    }

    //이력 업데이트
    // cert_yn  Y(성공) / N(실패)
    @Override
    public void updateSmsHistory(HashMap<String, String> params) {
        smsMapper.updateSmsHistory(params);
    }
    
}

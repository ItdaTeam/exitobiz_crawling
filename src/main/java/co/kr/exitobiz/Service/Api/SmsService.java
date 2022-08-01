package co.kr.exitobiz.Service.Api;

import java.util.HashMap;

public interface SmsService {

    public String getRandomNumber();

    public void insertSmsHistory(HashMap<String, String> params);

    public int checkCertInfo(HashMap<String, String> params);

    public HashMap<String,String> getCertInfo(HashMap<String, String> params);

    public void updateSmsHistory(HashMap<String, String> params);

    
}

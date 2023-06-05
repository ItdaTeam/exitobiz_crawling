package co.kr.exitobiz.Service.WebApp;

import java.text.ParseException;
import java.util.HashMap;

public interface EduRegistService {

    void saveEduRegist(HashMap<String, Object> params) throws ParseException; // 교육 신청

    HashMap chkRegist(HashMap<String, Object> params) throws ParseException; // 변경 값 리턴
}

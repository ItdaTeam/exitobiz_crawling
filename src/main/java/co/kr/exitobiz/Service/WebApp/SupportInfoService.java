package co.kr.exitobiz.Service.WebApp;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

public interface SupportInfoService {
    List<HashMap> getSupportInfoList(HashMap<String, Object> params) throws ParseException; // 지원사업 리스트(정렬조건, 검색조건 포함)

    HashMap<String, Object> getSPDetailData(HashMap<String, Object> params) throws ParseException; // 지원사업 상세 조회
}

package co.kr.exitobiz.Service.WebApp;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SupportCorpInfoService {
    List<HashMap> getSupportCorpInfoList(HashMap<String, Object> params) throws ParseException; // 지원사업 리스트(정렬조건, 검색조건 포함)

    public List<HashMap> getTotalCount(); // 상단 갯수 반환

    public void upViewCnt(HashMap<String, Object> params); // 지원사업 조회 업

    public void insertTimeLine(HashMap<String, Object> params); //검색 타임라인 추가

    List<HashMap> getSearchHotKeyWord() throws ParseException; // 인기 키워드 리스트

    void updateCorpCompanyInfo(Map<String, Object> params); // 필터 적용

    HashMap<String, Object> getCorpCompanyInfo(HashMap<String, Object> params) throws ParseException; // 필터 조회
}

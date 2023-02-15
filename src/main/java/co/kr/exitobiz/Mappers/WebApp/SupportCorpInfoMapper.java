package co.kr.exitobiz.Mappers.WebApp;

import org.apache.ibatis.annotations.Mapper;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

@Mapper
public interface SupportCorpInfoMapper {
    public List<HashMap> getSupportCorpInfoList(HashMap<String, Object> params); // 지원사업 리스트(정렬조건, 검색조건 포함)

    public List<HashMap> getTotalCount(); // 상단 갯수 반환

    void upViewCnt(HashMap<String, Object> params); // 지원사업 카운트 업

    void insertTimeLine(HashMap<String, Object> params); // 검색 타임라인 추가

    List<HashMap> getSearchHotKeyWord() throws ParseException; // 인기 키워드 리스트
}
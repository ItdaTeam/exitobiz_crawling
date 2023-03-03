package co.kr.exitobiz.Mappers.WebApp;

import org.apache.ibatis.annotations.Mapper;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

@Mapper
public interface SupportInfoMapper {
    public List<HashMap> getSupportInfoList(HashMap<String, Object> params); // 지원사업 리스트(정렬조건, 검색조건 포함)

    public HashMap<String, Object> getSPDetailData(HashMap<String, Object> params); // 지원사업 상세 조회

    public void updateViewCnt(HashMap<String, Object> params) throws ParseException; // 지원사업 조회수 1증가

    public HashMap<String, Object> getSupportInfoCnt(HashMap<String, Object> params); // 맞춤 지원사업 갯수 조회
}

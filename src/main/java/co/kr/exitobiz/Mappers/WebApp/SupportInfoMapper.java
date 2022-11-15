package co.kr.exitobiz.Mappers.WebApp;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface SupportInfoMapper {
    public List<HashMap> getSupportInfoList(HashMap<String, Object> params); // 지원사업 리스트(정렬조건, 검색조건 포함)


}

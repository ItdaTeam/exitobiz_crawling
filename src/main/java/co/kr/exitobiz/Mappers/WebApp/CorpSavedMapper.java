package co.kr.exitobiz.Mappers.WebApp;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface CorpSavedMapper {

    public List<HashMap> getMySavedBook(HashMap<String, Object> params); // 찜한 사업, 지원한 사업 리스트 (정렬 포함)

    public List<HashMap> getCatList(HashMap<String, Object> params); // 찜 분류 리스트

    public void insertSavedMyBook(HashMap<String, Object> params); // 찜 추가(처음 찜한 사업)

    public void updateSavedMyBook(HashMap<String, Object> params); // 찜 flag 변경

    public HashMap checkSavedFlag(HashMap<String, Object> params); // 변경 값 리턴

}

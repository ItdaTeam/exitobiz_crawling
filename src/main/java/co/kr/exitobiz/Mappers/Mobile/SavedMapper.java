package co.kr.exitobiz.Mappers.Mobile;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface SavedMapper {
    public List<HashMap> getRecentlyMySavedBook(HashMap<String, Object> params); // 최근 본 사업리스트 (정렬 포함)

    public List<HashMap> getMySavedBook(HashMap<String, Object> params); // 찜한 사업, 지원한 사업 리스트 (정렬 포함)

    public List<HashMap> getCatList(HashMap<String, Object> params); // 찜 분류 리스트

    public void insertSavedMyBook(HashMap<String, Object> params); // 찜 추가(처음 찜한 사업)

    public void updateSavedMyBook(HashMap<String, Object> params); // 찜 flag 변경

    public void updateReqSavedMyBook(HashMap<String, Object> params); // 지원 flag 변경

    public void updateDoneSavedMyBook(HashMap<String, Object> params); // 선정 flag 변경


}

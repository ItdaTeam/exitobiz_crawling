package co.kr.exitobiz.Mappers.Cms;

import org.apache.ibatis.annotations.Mapper;

import co.kr.exitobiz.Vo.Push.PushVo;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface PushMapper {
    void savePush(HashMap<String, String> params);
    
    public List<PushVo> getPushKeywords(); //키워드 리스트 가져오기

    int getKeywordSupportInfo(HashMap<String,String> params); // 해당 키워드의 지원사업이 존재하는지 확인

    List<PushVo> getKeywordPushUser(HashMap<String, String> params); // 키워드 푸쉬 발송을 위한 키워드 별 유저 대상 조회

    List<PushVo> getBookmarkSupportInfo(); // 찜한사업 마감 2일 전 푸쉬발송 대상 리스트 조회
}

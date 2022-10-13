package co.kr.exitobiz.Mappers.Mobile;

import co.kr.exitobiz.Vo.Cms.BannerVo;
import co.kr.exitobiz.Vo.Mobile.CommunityVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface CommunityMapper {
    public List<CommunityVo> getCommunityList(HashMap<String, String> params); //커뮤니티 전체 리스트 조회

    public HashMap<String, Object> getCommunityDetail(CommunityVo vo); // 커뮤니티 상세 조회

    public void insertCommunity(CommunityVo vo); // 커뮤니티 추가

    public void updateCommunity(CommunityVo vo); // 커뮤니티 수정

    public void deleteCommunity(CommunityVo vo); // 커뮤니티 삭제

    public void declareCommunity(CommunityVo vo); // 커뮤니티 신고 flag 변경

    public void reviewViews(int id); // 조회수 증가

    public int getNewId(); // 최근 추가한 커뮤니티 ID 조회

}

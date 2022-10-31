package co.kr.exitobiz.Mappers.Mobile;

import co.kr.exitobiz.Vo.Cms.UserVo;
import co.kr.exitobiz.Vo.Mobile.CommunityVo;
import org.apache.ibatis.annotations.Mapper;

import java.sql.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface CommunityMapper {
    public List<CommunityVo> getCommunityList(HashMap<String, Object> params); //커뮤니티 전체 리스트

    public List<HashMap> getPopularCommunityList(CommunityVo vo); // 커뮤니티 인기글 리스트

    public List<HashMap> getBlockList(CommunityVo vo); // 차단회원 리스트

    public void delBlockUser(List<Object> params); // 차단회원 해제

    public void insertBlock(HashMap<String, Object> params); // 차단회원 생성

    public HashMap<String, Object> getCommunityDetail(CommunityVo vo); // 커뮤니티 상세 조회

    public void insertCommunity(CommunityVo vo); // 커뮤니티 추가

    public void updateCommunity(CommunityVo vo); // 커뮤니티 수정

    public void deleteCommunity(CommunityVo vo); // 커뮤니티 삭제

    public void declareCommunity(CommunityVo vo); // 커뮤니티 신고 flag 변경

    public void reviewViews(int id); // 조회수 증가

    public int getNewId(); // 최근 추가한 커뮤니티 ID 조회

}

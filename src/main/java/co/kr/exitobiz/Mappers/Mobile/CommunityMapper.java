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

    public void delBlockUser(HashMap<String, Object> params); // 차단회원 단일 해제

    public void delAllBlockUser(HashMap<String, Object> params); // 차단회원 전체 해제

    public void insertBlock(HashMap<String, Object> params); // 차단회원 생성

    public void insertReport(HashMap<String, Object> param); // 신고 생성

    public HashMap<String, Object> getCommunityDetail(CommunityVo vo); // 커뮤니티 상세 조회

    public void insertCommunity(CommunityVo vo); // 커뮤니티 추가

    public void updateCommunity(CommunityVo vo); // 커뮤니티 수정

    public void deleteCommunity(CommunityVo vo); // 커뮤니티 삭제

    public void insertFile(HashMap<String, Object> params); // 첨부파일 추가

    public List<HashMap> getFile(HashMap<String, Object> params); // 첨부파일 조회

    public void insertContentLike(HashMap<String, Object> params); //  좋아요 flag 변경

    public void updateTotalContentLike(HashMap<String, Object> params); // 좋아요 개수 변경

    //댓글
    public List<HashMap> getCommentList(HashMap<String, Object> params); // 커뮤니티 내 댓글 리스트

    public List<HashMap> getRecommentList(HashMap<String, Object> params); // 커뮤니티 내 대댓글 리스트

    public void insertComment(HashMap<String, Object> params); // 커뮤니티 내 댓글 추가

    public void updateComment(HashMap<String, Object> params); // 커뮤니티 댓글 수정

    public void delComment(HashMap<String, Object> params); // 커뮤니티 댓글 삭제

    public void reviewViews(int id); // 조회수 증가

    public int getNewId(); // 최근 추가한 커뮤니티 ID 조회

    public void insertCommentLike(HashMap<String, Object> params); // 커뮤니티 내 댓글 좋아요 flag 변경

    public void updateTotalCommentLike(HashMap<String, Object> params); // 커뮤니티 내 댓글 좋아요 개수 변경

    List<Map<String, Object>> getMyContent(Map<String, String> header);

    List<Map<String, Object>> getMyComment(Map<String, String> header);

    Map<String, Object> getTotalCnt();
}

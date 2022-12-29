package co.kr.exitobiz.Service.Mobile;

import co.kr.exitobiz.Vo.Mobile.CommunityVo;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CommunityService {
    List<CommunityVo> getCommunityList(HashMap<String, Object> params) throws ParseException;

    List<HashMap> getPopularCommunityList(CommunityVo communityVo) throws ParseException;

    List<HashMap> getBlockList(CommunityVo communityVo) throws ParseException;

    void delBlockUser(HashMap<String, Object> params) throws ParseException;

    void delAllBlockUser(HashMap<String, Object> params) throws ParseException;

    void insertBlock(HashMap<String, Object> params) throws ParseException;

    void insertReport(HashMap<String, Object> params) throws ParseException;

    HashMap<String, Object> getCommunityDetail(CommunityVo communityVo);

    void insertCommunity(CommunityVo communityVo) throws ParseException;

    void updateCommunity(CommunityVo communityVo) throws ParseException;

    void deleteCommunity(CommunityVo communityVo) throws ParseException;

    public void insertFile(HashMap<String, Object> params); // 첨부파일 추가

    List<HashMap> getFile(HashMap<String, Object> params);  // 첨부파일 조회

    public void insertContentLike(HashMap<String, Object> params); //  좋아요 flag 변경

    public void updateTotalContentLike(HashMap<String, Object> params); // 좋아요 개수 변경

    // 댓글
    List<HashMap> getCommentList(HashMap<String, Object> params) throws ParseException;

    List<HashMap> getRecommentList(HashMap<String, Object> params) throws ParseException;

    void updateComment(HashMap<String, Object> params) throws ParseException;

    void insertComment(HashMap<String, Object> params) throws ParseException;

    void delComment(HashMap<String, Object> params) throws ParseException;

    void reviewViews(int id) throws ParseException;

    int getNewId();

    void insertCommentLike(HashMap<String, Object> params); // 커뮤니티 내 댓글 좋아요 flag 변경

    void updateTotalCommentLike(HashMap<String, Object> params); // 커뮤니티 내 댓글 좋아요 개수 변경

    List<Map<String, Object>> getMyContent(Map<String, String> header);

    List<Map<String, Object>> getMyComment(Map<String, String> header);

    Map<String, Object> getTotalCnt(Map<String, Object> params);
}

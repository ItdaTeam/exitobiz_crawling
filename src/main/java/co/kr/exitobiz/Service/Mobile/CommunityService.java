package co.kr.exitobiz.Service.Mobile;

import co.kr.exitobiz.Vo.Cms.UserVo;
import co.kr.exitobiz.Vo.Mobile.CommunityVo;

import java.sql.Array;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CommunityService {
    List<CommunityVo> getCommunityList(HashMap<String, Object> params) throws ParseException;

    List<HashMap> getPopularCommunityList(CommunityVo communityVo) throws ParseException;

    List<HashMap> getBlockList(CommunityVo communityVo) throws ParseException;

    void delBlockUser(List<Object> params) throws ParseException;

    HashMap<String, Object> getCommunityDetail(CommunityVo communityVo);

    void insertCommunity(CommunityVo communityVo) throws ParseException;

    void updateCommunity(CommunityVo communityVo) throws ParseException;

    void deleteCommunity(CommunityVo communityVo) throws ParseException;

    void declareCommunity(CommunityVo communityVo) throws ParseException;

    void reviewViews(int id) throws ParseException;

    int getNewId();
}

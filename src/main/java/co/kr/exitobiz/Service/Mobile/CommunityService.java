package co.kr.exitobiz.Service.Mobile;

import co.kr.exitobiz.Vo.Mobile.CommunityVo;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

public interface CommunityService {
    List<CommunityVo> getCommunityList(HashMap<String, String> params) throws ParseException;

    HashMap<String, Object> getCommunityDetail(CommunityVo communityVo);

    void insertCommunity(CommunityVo communityVo) throws ParseException;

    void updateCommunity(CommunityVo communityVo) throws ParseException;

    void deleteCommunity(CommunityVo communityVo);

    void declareCommunity(CommunityVo communityVo);

}

package co.kr.exitobiz.Service.Api;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.firebase.messaging.FirebaseMessagingException;

import co.kr.exitobiz.Vo.usertableVo;
import co.kr.exitobiz.Vo.Push.PushVo;

public interface PushService {
    public String sendPush(HashMap<String,String> params) throws Exception;
    public void aSyncSendPush(HashMap<String,String> params) throws Exception;

    public void AsyncSendPushMulti(HashMap<String,String> params) throws Exception;

    public void getUserTokenByLocationTest(HashMap<String,String> params) throws Exception;

    public void sendListPush(HashMap<String, String> params, List<Map<String, usertableVo>> usertokens) throws FirebaseMessagingException, IOException;

    public List<PushVo> getPushKeywords() throws Exception; // 키워드 리스트 조회

    public int getKeywordSupportInfo(HashMap<String, String> params); // 지원사업 리스트 조회

    public List<PushVo> getKeywordPushUser(HashMap<String, String> params); // 푸쉬발송 대상 유저 조회

}

package co.kr.exitobiz.Service.Api;

import co.kr.exitobiz.Vo.usertableVo;
import com.google.firebase.messaging.FirebaseMessagingException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface PushService {
    public String sendPush(HashMap<String,String> params) throws Exception;
    public void aSyncSendPush(HashMap<String,String> params) throws Exception;

    public void AsyncSendPushMulti(HashMap<String,String> params) throws Exception;

    public void getUserTokenByLocationTest(HashMap<String,String> params) throws Exception;

    public void sendListPush(HashMap<String, String> params, List<Map<String, usertableVo>> usertokens) throws FirebaseMessagingException, IOException;

}

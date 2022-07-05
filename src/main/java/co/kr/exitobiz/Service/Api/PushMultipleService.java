package co.kr.exitobiz.Service.Api;

import com.google.firebase.messaging.FirebaseMessagingException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface PushMultipleService {

    public void sendListPush(HashMap<String, String> params, List<Map<String, Object>> usertokens) throws FirebaseMessagingException, IOException;


    public void sendListPushTest(HashMap<String, String> params, List<Map<String, Object>> usertokens) throws FirebaseMessagingException, IOException;

    void savePush(HashMap<String, String> params);

}

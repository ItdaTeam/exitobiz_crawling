package co.kr.exitobiz.Service.Api.impl;

import co.kr.exitobiz.Service.Api.PushService;
import co.kr.exitobiz.Vo.usertableVo;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import org.json.simple.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.util.*;

@Service
@Configuration
@EnableAsync
public class PushServiceImpl implements PushService {


    /** Push 서비스
     * FCM 메시지 기본 구조
     * {
            "message":{
                "token":"bk3RNwTe3H0:CI2k_HHwgIpoDKCIZvvDMExUdFQ3P1...",  // 사용자 토큰
                "notification":{
                    "title":"Portugal vs. Denmark",   // 제목
                    "body":"great match!"             // 내용
                },
                "data" : {    // 사용자 커스터마이징 데이터
                    "Nick" : "Mario",
                    "Room" : "PortugalVSDenmark"
                }
            }
        }
     * 
     * 참고사항
     * sound가 있어야 push가 정상동작 한다.
     * sound는 android / ios 구조가 다르다.
     * android 용 기본 sound 추가
     * "android":{
            "sound":"default"
        },
     * IOS용 기본 sound 추가
     *  "apns":{
            "payload":{
                "sound":"default"
            }
        },
     */
    @Override
    @Async
    public String sendPush(HashMap<String, String> params) throws Exception {

        String result="";
        try {    
            //FCM처리를 위한 기본 셋팅
            String MESSAGING_SCOPE = "https://www.googleapis.com/auth/firebase.messaging";
            String[] SCOPES = { MESSAGING_SCOPE };

            Resource resource = new ClassPathResource(params.get("firebaseKeyPath"));
            GoogleCredentials credentials = GoogleCredentials.fromStream(resource.getInputStream())
                                                             .createScoped(Arrays.asList(SCOPES));

            credentials.refreshIfExpired();

            // Push 헤더 만들기
            HttpHeaders headers = new HttpHeaders();
            headers.add("content-type" , MediaType.APPLICATION_JSON_VALUE);
            headers.add("Authorization", "Bearer " +  credentials.refreshAccessToken().getTokenValue());
            
            // PUSH 기본 메시징 데이터
            HashMap<String,String> notificationData = new HashMap<String,String>(); 
            notificationData.put("body", params.get("body"));  
            notificationData.put("title", params.get("title"));  
            JSONObject notification = new JSONObject(notificationData);

            // 내부 처리를 위한 커스터마이징 데이터 추가
            HashMap<String,Object> data = new HashMap<String,Object>(); 
            data.put("keyId",params.get("keyId"));
            data.put("idx",params.get("idx"));
            data.put("click_action","FLUTTER_NOTIFICATION_CLICK");
            JSONObject jsonData = new JSONObject(data);

            // Push 정상동작을 위한 기본 sound 추가
            HashMap<String,String> sound = new HashMap<String,String>();
            sound.put("sound","default"); 
            // android용 사운드 추가
            HashMap<String,Object> android = new HashMap<String,Object>();
            android.put("notification",sound);
            //IOS용 사운드 추가
            HashMap<String,Object> payload = new HashMap<String,Object>();  
            HashMap<String,String> aps = new HashMap<String,String>();  
            aps.put("sound","default");                                  
            payload.put("aps",aps);
            HashMap<String,Object> apns = new HashMap<String,Object>();
            apns.put("payload",payload);

            //Pusn 발송을 위한 FCM 메시지 구조화
            HashMap<String,Object> messageData = new HashMap<String,Object>();
            messageData.put("token", params.get("userToken"));
            messageData.put("notification", notification);
            messageData.put("data",jsonData);
            messageData.put("android",android);
            messageData.put("apns",apns);
            JSONObject message = new JSONObject(messageData);
            HashMap<String,Object> jsonParamsData = new HashMap<String,Object>();
            jsonParamsData.put("message", message);
            JSONObject jsonParams = new JSONObject(jsonParamsData);

            HttpEntity<JSONObject> httpEntity = new HttpEntity<JSONObject>(jsonParams, headers);
            RestTemplate rt = new RestTemplate();

            System.out.println("파이어베이스 시작::" + LocalTime.now());

            // FCM Push메세지 발송
            ResponseEntity<String> res = rt.exchange("https://fcm.googleapis.com/v1/projects/itda001/messages:send"
                    , HttpMethod.POST
                    , httpEntity
                    , String.class);

            System.out.println("파이어베이스 종료::" + LocalTime.now());


            if (res.getStatusCode() != HttpStatus.OK) {
                result= "fail"; // 발송 실패
            } else {
                result= "success"; // 성공
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }



    @Override
    @Async
    public void aSyncSendPush(HashMap<String, String> params) throws Exception {
        String result="";
        try {
            //FCM처리를 위한 기본 셋팅
            String MESSAGING_SCOPE = "https://www.googleapis.com/auth/firebase.messaging";
            String[] SCOPES = { MESSAGING_SCOPE };
            Resource resource = new ClassPathResource(params.get("firebaseKeyPath"));
            GoogleCredentials credentials = GoogleCredentials.fromStream(resource.getInputStream())
                    .createScoped(Arrays.asList(SCOPES));
            credentials.refreshIfExpired();

            // Push 헤더 만들기
            HttpHeaders headers = new HttpHeaders();
            headers.add("content-type" , MediaType.APPLICATION_JSON_VALUE);
            headers.add("Authorization", "Bearer " +  credentials.refreshAccessToken().getTokenValue());

            // PUSH 기본 메시징 데이터
            HashMap<String,String> notificationData = new HashMap<String,String>();
            notificationData.put("body", params.get("body"));
            notificationData.put("title", params.get("title"));
            JSONObject notification = new JSONObject(notificationData);

            // 내부 처리를 위한 커스터마이징 데이터 추가
            HashMap<String,Object> data = new HashMap<String,Object>();
            data.put("keyId",params.get("keyId"));
            data.put("idx",params.get("idx"));
            data.put("click_action","FLUTTER_NOTIFICATION_CLICK");
            JSONObject jsonData = new JSONObject(data);

            // Push 정상동작을 위한 기본 sound 추가
            HashMap<String,String> sound = new HashMap<String,String>();
            sound.put("sound","default");
            // android용 사운드 추가
            HashMap<String,Object> android = new HashMap<String,Object>();
            android.put("notification",sound);
            //IOS용 사운드 추가
            HashMap<String,Object> payload = new HashMap<String,Object>();
            HashMap<String,String> aps = new HashMap<String,String>();
            aps.put("sound","default");
            payload.put("aps",aps);
            HashMap<String,Object> apns = new HashMap<String,Object>();
            apns.put("payload",payload);

            //Pusn 발송을 위한 FCM 메시지 구조화
            HashMap<String,Object> messageData = new HashMap<String,Object>();
            messageData.put("token", params.get("userToken"));
            messageData.put("notification", notification);
            messageData.put("data",jsonData);
            messageData.put("android",android);
            messageData.put("apns",apns);
            JSONObject message = new JSONObject(messageData);
            HashMap<String,Object> jsonParamsData = new HashMap<String,Object>();
            jsonParamsData.put("message", message);
            JSONObject jsonParams = new JSONObject(jsonParamsData);

            HttpEntity<JSONObject> httpEntity = new HttpEntity<JSONObject>(jsonParams, headers);
            RestTemplate rt = new RestTemplate();

            System.out.println("파이어베이스 시작::" + LocalTime.now());

            // FCM Push메세지 발송
            ResponseEntity<String> res = rt.exchange("https://fcm.googleapis.com/v1/projects/itda001/messages:send"
                    , HttpMethod.POST
                    , httpEntity
                    , String.class);

            System.out.println("파이어베이스 종료::" + LocalTime.now());


            if (res.getStatusCode() != HttpStatus.OK) {
                System.out.println("fail...");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void AsyncSendPushMulti(HashMap<String, String> params) throws Exception {

        String result="";

        //FCM처리를 위한 기본 셋팅
        String MESSAGING_SCOPE = "https://www.googleapis.com/auth/firebase.messaging";
        String[] SCOPES = { MESSAGING_SCOPE };
        Resource resource = new ClassPathResource(params.get("firebaseKeyPath"));
        GoogleCredentials credentials = GoogleCredentials.fromStream(resource.getInputStream())
                .createScoped(Arrays.asList(SCOPES));
        credentials.refreshIfExpired();

        // Push 헤더 만들기
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type" , MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", "Bearer " +  credentials.refreshAccessToken().getTokenValue());

        // PUSH 기본 메시징 데이터
        HashMap<String,String> notificationData = new HashMap<String,String>();
        notificationData.put("body", params.get("body"));
        notificationData.put("title", params.get("title"));
        JSONObject notification = new JSONObject(notificationData);

        // 내부 처리를 위한 커스터마이징 데이터 추가
        HashMap<String,Object> data = new HashMap<String,Object>();
        data.put("keyId",params.get("keyId"));
        data.put("idx",params.get("idx"));
        data.put("click_action","FLUTTER_NOTIFICATION_CLICK");
        JSONObject jsonData = new JSONObject(data);

        // Push 정상동작을 위한 기본 sound 추가
        HashMap<String,String> sound = new HashMap<String,String>();
        sound.put("sound","default");
        // android용 사운드 추가
        HashMap<String,Object> android = new HashMap<String,Object>();
        android.put("notification",sound);
        //IOS용 사운드 추가
        HashMap<String,Object> payload = new HashMap<String,Object>();
        HashMap<String,String> aps = new HashMap<String,String>();
        aps.put("sound","default");
        payload.put("aps",aps);
        HashMap<String,Object> apns = new HashMap<String,Object>();
        apns.put("payload",payload);

        //Pusn 발송을 위한 FCM 메시지 구조화
        HashMap<String,Object> messageData = new HashMap<String,Object>();
        messageData.put("token", params.get("userToken"));
        messageData.put("notification", notification);
        messageData.put("data",jsonData);
        messageData.put("android",android);
        messageData.put("apns",apns);
        JSONObject message = new JSONObject(messageData);
        HashMap<String,Object> jsonParamsData = new HashMap<String,Object>();
        jsonParamsData.put("message", message);
        JSONObject jsonParams = new JSONObject(jsonParamsData);

        HttpEntity<JSONObject> httpEntity = new HttpEntity<JSONObject>(jsonParams, headers);


    }

    @Override
    public void getUserTokenByLocationTest(HashMap<String, String> params) throws Exception {




    }

    @Override
    @Async
    public void sendListPush(HashMap<String, String> params, List<Map<String, usertableVo>> usertokens) throws FirebaseMessagingException, IOException {

        FileInputStream refreshToken = new FileInputStream("/Users/seungheejeon/Desktop/workspace/2021_09/itda_real 2/src/main/resources/firebase/itda001-firebase-adminsdk-fv7cy-5cd6f955bf.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(refreshToken))
                .build();

        if(FirebaseApp.getApps().isEmpty()){
            FirebaseApp.initializeApp(options);
        }

        List<String> failedTokens = new ArrayList<>();
        int tokenCount = 0;

        List<String> tokenList = new ArrayList<>();

        String testToken = "fWZocTqDQxSuOaMliDuqVR:APA91bFjKiVexaOkVfOqtYxAR2bGcpJliX-JAVP7BhHiBBN2LZQqYcDzp8vLw57xoKtThg2sS8lCInNaRAmnK_CMRR9ZTgxHqbww1iPk7siALHzXAI3TOiH6B-8uNfhD_hN4KF6l0EAV";


        LocalTime localTime1 = LocalTime.now();
        System.out.println("보내기시작시간:: " + localTime1);


//        for (int i=0; i < usertokens.size(); i++) {
        for (int i=0; i < 3300; i++) {

            tokenList.add(testToken);
            tokenCount++;

            if ((i % 499) == 1) {

                System.out.println(i + "번째 리셋 토큰카운트::" + tokenCount);
                System.out.println(tokenList.size());

                MulticastMessage message = MulticastMessage.builder()
                        .setNotification(Notification.builder()
                                .setTitle(params.get("title") + i)
                                .setBody(params.get("body"))
                                .build())
                        .setApnsConfig(ApnsConfig.builder()
                                .setAps(Aps.builder()
                                        .setBadge(42)
                                        .setSound("default")
                                        .build())
                                .build())
                        .putData("keyId", params.get("keyId"))
                        .putData("idx", params.get("idx"))
                        .putData("click_action", "FLUTTER_NOTIFICATION_CLICK")
                        .addAllTokens(tokenList)
                        .build();

                BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(message);
                tokenList.clear();

                if(response.getFailureCount() > 0){
                    List<SendResponse> responses = response.getResponses();

                    for (int j = 0; j < responses.size(); j++) {
                        if (!responses.get(j).isSuccessful()) {
                            failedTokens.add(tokenList.get(j));
                        }
                    }

                    System.out.println("List of tokens that caused failures: "
                            + failedTokens);
                }

            }
        }

        if (tokenList.size() > 0) {
            System.out.println("마지막리스트::" + tokenCount);
            System.out.println(tokenList.size());

            MulticastMessage message = MulticastMessage.builder()
                    .setNotification(Notification.builder()
                            .setTitle(params.get("title") + "마지막리스트")
                            .setBody(params.get("body"))
                            .build())
                    .setApnsConfig(ApnsConfig.builder()
                            .setAps(Aps.builder()
                                    .setBadge(42)
                                    .setSound("default")
                                    .build())
                            .build())
                    .putData("keyId", params.get("keyId"))
                    .putData("idx", params.get("idx"))
                    .putData("click_action", "FLUTTER_NOTIFICATION_CLICK")
                    .addAllTokens(tokenList)
                    .build();

            BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(message);

            if(response.getFailureCount() > 0){
                List<SendResponse> responses = response.getResponses();

                for (int j = 0; j < responses.size(); j++) {
                    if (!responses.get(j).isSuccessful()) {
                        failedTokens.add(tokenList.get(j));
                    }
                }

                System.out.println("List of tokens that caused failures: "
                        + failedTokens);
            }


            tokenList.clear();
        }


        LocalTime localTime2 = LocalTime.now();
        System.out.println("보내기시작시간 :: " + localTime1 +", 보내기종료시간 :: " + localTime2);

    }

}

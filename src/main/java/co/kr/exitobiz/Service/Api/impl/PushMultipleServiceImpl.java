package co.kr.exitobiz.Service.Api.impl;


import co.kr.exitobiz.Mappers.Cms.PushMapper;
import co.kr.exitobiz.Service.Api.PushMultipleService;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Configuration
@EnableAsync
public class PushMultipleServiceImpl implements PushMultipleService {

    @Autowired
    PushMapper pushMapper;

    @Override
    public void sendListPush(HashMap<String, String> params, List<Map<String, Object>> usertokens)
            throws FirebaseMessagingException, IOException {

        /* 로컬용 */
//        FileInputStream refreshToken = new FileInputStream("/Users/seungheejeon/Desktop/workspace/2021_09/itda_real 2/src/main/resources/firebase/itda001-firebase-adminsdk-fv7cy-5cd6f955bf.json");

        /* 서버용 */
        FileInputStream refreshToken = new FileInputStream("/var/upload/firebase/itda001-firebase-adminsdk-fv7cy-5cd6f955bf.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(refreshToken))
                .build();

        if(FirebaseApp.getApps().isEmpty()){
            FirebaseApp.initializeApp(options);
        }

        int tokenCount = 0;

        List<String> tokenList = new ArrayList<>();

        LocalTime localTime1 = LocalTime.now();
        System.out.println("보내기시작시간:: " + localTime1);


        for (int i=0; i < usertokens.size(); i++) {

            tokenList.add(usertokens.get(i).get("usertoken").toString());
//            System.out.println("----------------배열에토큰담기------------------" + i + "번째");

            tokenCount++;

            /* 500개 제한. 500개넘어가면 푸시보내고 리스트 다시 초기화 */
            if ((i % 499) == 0 && (i != 0)) {
                //idx ( keyword => keyword, 마감임박 => 지원사업 PK )
                if(params.get("keyId").equals("5")) {
                    // 찜한사업 마감임박 알람용 ( 배열 돌면서 한 지원사업 당 한 명에게 전송 )
                    Message msg = Message.builder()
                            .setNotification(Notification.builder()
                                    .setTitle(params.get("title"))
                                    .setBody(params.get("body"))
                                    .build())
                            .setApnsConfig(ApnsConfig.builder()
                                    .setAps(Aps.builder()
                                            .setSound("default")
                                            .build())
                                    .build())
                            .putData("keyId", params.get("keyId"))
                            .putData("idx", params.get("idx"))
                            .putData("click_action", "FLUTTER_NOTIFICATION_CLICK")
                            .setToken(usertokens.get(0).get("usertoken").toString())
                            .build();
                    String response = FirebaseMessaging.getInstance().send(msg);
                }else{
                    MulticastMessage message = MulticastMessage.builder()
                            .setNotification(Notification.builder()
                                    .setTitle(params.get("title"))
                                    .setBody(params.get("body"))
                                    .build())
                            .setApnsConfig(ApnsConfig.builder()
                                    .setAps(Aps.builder()
//                                    .setBadge(42)
                                            .setSound("default")
                                            .build())
                                    .build())
                            .putData("keyId", params.get("keyId"))
                            .putData("idx", params.get("idx"))
                            .putData("click_action", "FLUTTER_NOTIFICATION_CLICK")
                            .addAllTokens(tokenList)
                            .build();

                    tokenList.clear();
                    BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(message);
                }

//                if(response.getFailureCount() > 0){
//                    List<SendResponse> responses = response.getResponses();
//
//                    for (int j = 0; j < responses.size(); j++) {
//                        if (!responses.get(j).isSuccessful()) {
//                            failedTokens.add(tokenList.get(j));
//                        }
//                    }
//
//                    System.out.println("List of tokens that caused failures: "
//                            + failedTokens);
//                }

            }
        }

        /* 렝스500안되는 남은 토큰도 보내기 */
        if (tokenList.size() > 0) {
            System.out.println("마지막리스트::" + tokenCount);
            System.out.println(tokenList.size());

            //idx ( keyword => keyword, 마감임박 => 지원사업 PK )
            if(params.get("keyId").equals("5")) {
                // 찜한사업 마감임박 알람용 ( 배열 돌면서 한 지원사업 당 한 명에게 전송(multi아님) )
                Message msg = Message.builder()
                        .setNotification(Notification.builder()
                                .setTitle(params.get("title"))
                                .setBody(params.get("body"))
                                .build())
                        .setApnsConfig(ApnsConfig.builder()
                                .setAps(Aps.builder()
                                        .setSound("default")
                                        .build())
                                .build())
                        .putData("keyId", params.get("keyId"))
                        .putData("idx", params.get("idx"))
                        .putData("click_action", "FLUTTER_NOTIFICATION_CLICK")
                        .setToken(usertokens.get(0).get("usertoken").toString())
                        .build();
                String response = FirebaseMessaging.getInstance().send(msg);
            }else{
                MulticastMessage message = MulticastMessage.builder()
                        .setNotification(Notification.builder()
                                .setTitle(params.get("title"))
                                .setBody(params.get("body"))
                                .build())
                        .setApnsConfig(ApnsConfig.builder()
                                .setAps(Aps.builder()
//                                    .setBadge(42)
                                        .setSound("default")
                                        .build())
                                .build())
                        .putData("keyId", params.get("keyId"))
                        .putData("idx", params.get("idx"))
                        .putData("click_action", "FLUTTER_NOTIFICATION_CLICK")
                        .addAllTokens(tokenList)
                        .build();

                BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(message);
            }
//            if(response.getFailureCount() > 0){
//                List<SendResponse> responses = response.getResponses();
//
//                for (int j = 0; j < responses.size(); j++) {
//                    if (!responses.get(j).isSuccessful()) {
//                        failedTokens.add(tokenList.get(j));
//                    }
//                }
//
//                System.out.println("List of tokens that caused failures: "
//                        + failedTokens);
//            }

            tokenList.clear();
        }


        LocalTime localTime2 = LocalTime.now();
        System.out.println("보내기시작시간 :: " + localTime1 +", 보내기종료시간 :: " + localTime2);

    }

    @Override
    public void savePush(HashMap<String, String> params) {
        pushMapper.savePush(params);
    }

    /* 개발자 폰에 테스트 해보는 메서드 */
    @Override
    @Async
    public void sendListPushTest(HashMap<String, String> params, List<Map<String, Object>> usertokens) throws FirebaseMessagingException, IOException {

        FileInputStream refreshToken = new FileInputStream("/var/upload/firebase/itda001-firebase-adminsdk-fv7cy-5cd6f955bf.json");
//        FileInputStream refreshToken = new FileInputStream("/var/upload/firebase/itda001-firebase-adminsdk-fv7cy-5cd6f955bf.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(refreshToken))
                .build();

        if(FirebaseApp.getApps().isEmpty()){
            FirebaseApp.initializeApp(options);
        }

        List<String> failedTokens = new ArrayList<>();
        int tokenCount = 0;

        List<String> tokenList = new ArrayList<>();

        String testToken = "e9Kil8xB6EPInvVlPZ4aVp:APA91bFPmOuF_tte9wU6TwRaQFLvx-YfiLb4v0mA7pLZqp1aKdwJMo3QljAo7jU0B2foIYjYjKhl08KAMlouh2k_WlWwY-8rBfFFbShPGu7ypgNut7OrgJocrOTOg3mG0M7pUto-M1q_";


        LocalTime localTime1 = LocalTime.now();
        System.out.println("보내기시작시간:: " + localTime1);


        for (int i=0; i < 3300; i++) {

            tokenList.add(testToken);
            tokenCount++;

            if ((i % 499) == 0 && (i != 0)) {

                System.out.println(i + "번째 리셋 토큰카운트::" + tokenCount);
                System.out.println(tokenList.size());
                MulticastMessage message;


                //idx ( keyword => keyword, 마감임박 => 지원사업 PK )
                if(params.get("keyId").equals("5")) {

                    message = MulticastMessage.builder()
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
                }else{

                    message = MulticastMessage.builder()
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

                }



                tokenList.clear();
                BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(message);

//                if(response.getFailureCount() > 0){
//
//                    List<SendResponse> responses = response.getResponses();
//
//
//                    for (int j = 0; j < responses.size(); j++) {
//                        if (!responses.get(j).isSuccessful()) {
//                            failedTokens.add(tokenList.get(j));
//                        }
//                    }
//
//                    System.out.println("List of tokens that caused failures: "
//                            + failedTokens);
//                }

            }
        }

        if (tokenList.size() > 0) {
            System.out.println("마지막리스트::" + tokenCount);
            System.out.println(tokenList.size());

            MulticastMessage message;

            //idx ( keyword => keyword, 마감임박 => 지원사업 PK )
            if(params.get("keyId").equals("5")) {
                message = MulticastMessage.builder()
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

            }else{
                message = MulticastMessage.builder()
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

            }

            BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(message);

//            if(response.getFailureCount() > 0){
//                List<SendResponse> responses = response.getResponses();
//
//                for (int j = 0; j < responses.size(); j++) {
//                    if (!responses.get(j).isSuccessful()) {
//                        failedTokens.add(tokenList.get(j));
//                    }
//                }
//
//                System.out.println("List of tokens that caused failures: "
//                        + failedTokens);
//            }


            tokenList.clear();
        }


        LocalTime localTime2 = LocalTime.now();
        System.out.println("보내기시작시간 :: " + localTime1 +", 보내기종료시간 :: " + localTime2);


    }
}

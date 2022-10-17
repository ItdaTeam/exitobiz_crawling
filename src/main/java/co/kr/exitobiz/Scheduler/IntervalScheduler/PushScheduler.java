package co.kr.exitobiz.Scheduler.IntervalScheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import co.kr.exitobiz.Service.Api.PushMultipleService;
import co.kr.exitobiz.Service.Api.PushService;
import co.kr.exitobiz.Vo.Push.PushVo;

@Component
@EnableScheduling
public class PushScheduler{

    @Autowired
    PushService pushService;

    @Autowired
    PushMultipleService pushMultiService;

    /**
     * 키워드 알람용 푸쉬 스케줄러
     * 매일 14:00 에 1회 발송
     * 대상 : user_push_setting 에서 sp_keyword_push 가 'Y' 인 사용자
     * 키워드당 1번만 푸쉬 발송 
     */
    @Scheduled(cron = "0 0 14 * * ?")
    public void KeywordPush() throws InterruptedException, Exception {

        
        List<Map<String, Object>> usertokens = new ArrayList<>();

        System.out.println("=================== 푸쉬 스타트 ====================");


        // 알림 발송할 키워드가 있는지 먼저 체크
        List<PushVo> keywords = pushService.getPushKeywords();

        /**
            키워드별 등록된 지원사업이 있는지 확인하기    
        */
        for(PushVo keyword : keywords ){

            HashMap<String,String> params = new HashMap<>(); // 각 쿼리 조회를 위한 파라미터들
            HashMap<String,String> push = new HashMap<>();  // 푸쉬 발송을 위한 기본 셋팅

            params.put("keyword", keyword.getKeyword());

            int count = 0;
            count = pushService.getKeywordSupportInfo(params); // 지원사업 있는지 확인
            if(count > 0){ // 지원사업이 존재하는 경우

                List<PushVo> users = pushService.getKeywordPushUser(params); //푸쉬 발송을 위한 user_id 가져오기

                // 푸쉬를 위한 기본 셋팅 처리
                push.put("title","[키워드알림]\""+keyword.getKeyword() + "\" 지원사업이 엑시토에 등록되었어요");
                push.put("body","지금 해당 지원사업을 확인해보세요");
                push.put("keyId","2");
                push.put("idx",keyword.getKeyword());
                usertokens.clear();
                for(PushVo user : users){ // 푸쉬를 발송할 사용자 토큰 처리
                    Map<String,Object> usertoken = new HashMap<String,Object>();
                    usertoken.put("usertoken",user.getUsertoken());
                    usertokens.add(usertoken);
                }
                
                //푸쉬 발송
                pushMultiService.sendListPush(push, usertokens);
                
            }
        }
    }
    /**
     * 찜한사업 알람용 푸쉬 스케줄러
     * 매일 10:00 에 1회 발송
     * 대상 : user_push_setting 에서 sp_bookmark_push 가 'Y' 인 사용자
     */
    //2주뒤 업데이트되면 다시 테스트
    @Scheduled(cron = "0 30 16 * * ?")
    public void bookmarkPush() throws InterruptedException, Exception {


        List<Map<String, Object>> usertokens = new ArrayList<>();

        System.out.println("=================== 푸쉬 스타트 ====================");

        /**
         찜한 지원사업이 있는지 확인하기
         */
        List<PushVo> bookmarks = pushService.getBookmarkSupportInfo();

        if(bookmarks.size() > 0){
            for(PushVo bookmark : bookmarks ){
                HashMap<String,String> push = new HashMap<>();  // 푸쉬 발송을 위한 기본 셋팅

                // 푸쉬를 위한 기본 셋팅 처리
                push.put("title","(마감일 임박) 찜한 사업 마감일 임박!!!");
                push.put("body", bookmark.getSiTitle());
                push.put("keyId","5");

                usertokens.clear();

                 // 푸쉬를 발송할 사용자 토큰 처리

                for(Map isUser : usertokens){
                    if(!isUser.containsValue(bookmark.getUsertoken())){
                        Map<String,Object> usertoken = new HashMap<String,Object>();
                        usertoken.put("usertoken",bookmark.getUsertoken());
                        usertokens.add(usertoken);
                    }
                }
                System.out.println("#####" + usertokens);

                //푸쉬 발송
                pushMultiService.sendListPush(push, usertokens);
            }
        }

    }
    
}

package co.kr.exitobiz.Scheduler.IntervalScheduler;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import co.kr.exitobiz.Service.Api.KakaoService;
import co.kr.exitobiz.Vo.Api.KakaoVo;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.model.KakaoOption;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.service.DefaultMessageService;

@Component
@EnableScheduling
public class KakaoScheduler{

    @Autowired
    KakaoService kakaoService;

    @Autowired
    Environment env;

    /**
     * 카카오톡 알림 발송 배치
     * 매일 09:00 에 1회 발송
     * 대상 : content_history 에서 기간종료 + 1일 
     */
    @Scheduled(cron = "0 0 9 * * ?")
    public void KeywordPush() throws InterruptedException, Exception {
        
        DefaultMessageService messageService =  NurigoApp.INSTANCE.initialize(env.getProperty("sola.apiKey"), env.getProperty("sola.apiSecretKey"), "https://api.solapi.com");
        
        List<Map<String, Object>> usertokens = new ArrayList<>();

        // 카카오톡을 보낼 대상이 있는지 확인
        List<KakaoVo> Users = kakaoService.kakaoSendList();

        /**
            키워드별 등록된 지원사업이 있는지 확인하기    
        */
        for(KakaoVo user : Users ){

            HashMap<String,String> params = new HashMap<>();


            String result = "fail";
    
            KakaoOption kakaoOption = new KakaoOption();
            kakaoOption.setPfId(env.getProperty("sola.pfid"));
            kakaoOption.setTemplateId("KA01TP220816145710764amUei6hZavj"); // 공동구매 종료
    
    
                /**
                 * 변수 셋팅
                 */
                HashMap<String,String> variables = new HashMap<>();
    
                DecimalFormat decFormat = new DecimalFormat("###,###"); // 숫자 , 를 위한 형식 선언
                variables.put("#{title}","'" + user.getTitle()+ "'");
                variables.put("#{userName}",user.getUserName());
                variables.put("#{price}",decFormat.format(Integer.parseInt(user.getPrice())) + " 원");
                kakaoOption.setVariables(variables);
    
                Message message = new Message();
                message.setFrom("0317848443");
                message.setTo(user.getUserHp());
                message.setKakaoOptions(kakaoOption);
    
                params.put("title", user.getTitle());
                params.put("userName",user.getUserName());
                params.put("price", user.getPrice());
                params.put("userId",user.getUserId());
                params.put("contentId", user.getContentId());
                params.put("type","ContentEnd");
                params.put("endDate",user.getEndDate());
    
    
                try {
                // send 메소드로 ArrayList<Message> 객체를 넣어도 동작합니다!
                    messageService.send(message);
                    result = "success";
                } catch (NurigoMessageNotReceivedException exception) {
                // 발송에 실패한 메시지 목록을 확인할 수 있습니다!
                } catch (Exception exception) {
                }
                params.put("result",result);
                //발송 로그 남기기
                kakaoService.insertLog(params);
            }
            }
    
}

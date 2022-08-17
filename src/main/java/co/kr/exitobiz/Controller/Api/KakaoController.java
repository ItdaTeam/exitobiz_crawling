package co.kr.exitobiz.Controller.Api;

import java.text.DecimalFormat;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import co.kr.exitobiz.Service.Api.KakaoService;
import co.kr.exitobiz.Vo.Api.KakaoVo;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.model.KakaoOption;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.service.DefaultMessageService;

//카카오톡 템플릿 메세지 발송용 컨트롤러
@RestController
@RequestMapping(value = "/kakao")
public class KakaoController {


    @Autowired
    KakaoService kakaoService;

    @Autowired
    Environment env;

    /**
     * 템플릿  발송
     *  Param : userId, contentId
     */
    @RequestMapping("/send")
    @ResponseBody
    public String sendKakao(@RequestParam HashMap<String,String> params){

        DefaultMessageService messageService =  NurigoApp.INSTANCE.initialize(env.getProperty("sola.apiKey"), env.getProperty("sola.apiSecretKey"), "https://api.solapi.com");

        String result = "fail";

        KakaoOption kakaoOption = new KakaoOption();
        kakaoOption.setPfId(env.getProperty("sola.pfid"));
        kakaoOption.setTemplateId("KA01TP220816145208865W2Qm6yEAlnx"); // 공동구매 신청

        KakaoVo data = kakaoService.getPurchaseData(params); //카카오톡을 보내기 위한 공동구매 데이터 가져오기

        if( data != null ){ // 회원이 존재하고, 컨텐츠가 존재하는 경우 발송

            /**
             * 변수 셋팅
             */
            HashMap<String,String> variables = new HashMap<>();

            DecimalFormat decFormat = new DecimalFormat("###,###"); // 숫자 , 를 위한 형식 선언
            variables.put("#{title}","'" + data.getTitle()+ "'");
            variables.put("#{userName}",data.getUserName());
            variables.put("#{price}",decFormat.format(Integer.parseInt(data.getPrice())) + " 원");
            variables.put("#{endDate}",data.getEndDate());
            kakaoOption.setVariables(variables);

            Message message = new Message();
            message.setFrom("0317848443");
            message.setTo(data.getUserHp());
            message.setKakaoOptions(kakaoOption);

            params.put("title",data.getTitle());
            params.put("userName",data.getUserName());
            params.put("price",data.getPrice());
            params.put("endDate",data.getEndDate());
            params.put("type","Purchase");


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

        return result;
    }

    
}

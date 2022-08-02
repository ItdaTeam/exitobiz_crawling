package co.kr.exitobiz.Controller.Api;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import co.kr.exitobiz.Service.Api.SmsService;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;

@RestController
@RequestMapping(value = "/sms")
public class SmsController {

    final DefaultMessageService messageService;

    @Autowired
    SmsService smsService;

    public SmsController() {
        
        String apiKey = "NCSCTSU8GDVZ2JXR";
        String apiSecret = "EDYKGFFBLDFO0XR0NBLR2KUYW7SKZBUK";
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecret, "https://api.solapi.com");
    }

    /**
     * 단일 메시지 발송
     *  Param : userHp, userId
     */
    @RequestMapping("/sendSms")
    @ResponseBody
    public String sendOne(@RequestParam HashMap<String,String> params) {

        String result = "fail";
        
        //인증된 회원인지 체크
        int cnt = smsService.checkCertInfo(params);
        if(cnt == 0 ){ //인증된 이력이 없는 경우
            // SMS 인증을 위한 임의의 숫자 6자리 생성
            String randomNumber = smsService.getRandomNumber();
            params.put("number",randomNumber);
            
            Message message = new Message();
            // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
            message.setFrom("01099283137");
            // message.setTo("01021878241");
            message.setTo(params.get("userHp"));
            // message.setTo(params.get("to").toString());
            message.setText("[엑시토] 인증번호 ["+ randomNumber + "]를 입력해 주세요.");
            SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
            if(response.getStatusCode().equals("2000")){
                result = "success";
                // sms 문자인증 발송 이력 쌓기
                smsService.insertSmsHistory(params);
            }
        }else{ //인증 완료 사용자
            result = "done";
        }
        return result;
    }

    /*
     * 사용자가 입력한 인증번호 확인하기
     * param : certNumber , userId
     */
    @RequestMapping("/checkCert")
    @ResponseBody
    public String checkCert(@RequestParam HashMap<String,String> params) {

        String result = "fail";
        
        // 사용자가 입력한 인증번호 확인 
        String inputCertNumber = params.get("certNumber");

        //문자 발송 정보 가져오기
        HashMap<String,String> certInfo = smsService.getCertInfo(params);

        //발송한 인증번호
        String randomCertNumber  =  certInfo.get("cert_number");
        System.out.println(">>> certNmber : " + randomCertNumber);
        //업데이트를 위한 seq 셋팅
        params.put("smsSeq", certInfo.get("sms_seq"));

        if(randomCertNumber != null){ // 발송된 인증번호가 있을 때
            if(inputCertNumber.equals(randomCertNumber)){
                result = "success"; //성공
                params.put("certYn","Y");
            }else{
                params.put("certYn","N");
            }
            smsService.updateSmsHistory(params);
        }
        return result;
    }

    /**
     * 문자인증 된 사용자인지 확인하기
     *  Param : userId
     */
    @RequestMapping("/checkUser")
    @ResponseBody
    public String checkUser(@RequestParam HashMap<String,String> params) {

        String result = "No";
        
        int cnt = smsService.checkCertInfo(params);

        if(cnt > 0){
            result = "Yes";
        }

        return result;
    }

}



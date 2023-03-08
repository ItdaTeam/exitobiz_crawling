package co.kr.exitobiz.Scheduler.IntervalScheduler;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;


import co.kr.exitobiz.Service.Mobile.CommunityService;
import co.kr.exitobiz.Service.WebApp.CommonService;
import co.kr.exitobiz.Service.WebApp.UserService;
import org.springframework.core.env.Environment;

import co.kr.exitobiz.Service.WebApp.MainpageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import co.kr.exitobiz.Service.Api.PushMultipleService;
import co.kr.exitobiz.Service.Api.PushService;
import co.kr.exitobiz.Vo.Push.PushVo;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import co.kr.exitobiz.Service.WebApp.SupportInfoService;

@Component
@EnableScheduling
public class PushScheduler {

    @Autowired
    private Environment env;

    @Autowired
    PushService pushService;

    @Autowired
    PushMultipleService pushMultiService;

    @Autowired
    MainpageService mainpageService;

    @Autowired
    UserService userService;

    @Autowired
    CommonService commonService;

    @Autowired
    SupportInfoService supportService;

    @Autowired
    CommunityService communityService;


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
                push.put("keyId","kwrd");
                push.put("idx",keyword.getKeyword());
                push.put("url", "https://exitobiz.co.kr/support/supportList?keyword=" + keyword.getKeyword());
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
    @Scheduled(cron = "0 0 10 * * ?")
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
                push.put("title","(마감임박 " + bookmark.getRestDate() + "일전) 찜한 사업 마감일 임박!!!");
                push.put("body", bookmark.getSiTitle());
                push.put("keyId","day");
                push.put("idx", bookmark.getMbAddidx());
                usertokens.clear();

                // 푸쉬를 발송할 사용자 토큰 처리
                Map<String,Object> usertoken = new HashMap<String,Object>();
                usertoken.put("usertoken",bookmark.getUsertoken());
                usertokens.add(usertoken);



                //푸쉬 발송
                pushMultiService.sendListPush(push, usertokens);
            }
        }

    }

    public List<Map<String,Object>> getCommonCodeDtlList(@RequestParam Map<String,Object> params){
        return commonService.getCommonCodeDtlList(params);
    }

    /**
     * 이메일 정기배송 smtp 스케줄러
     * 매주 월요일 07:00 에 1회 발송
     * 대상 : user_deliver_service 에서 cancel_fl 가 false가 아닌 사용자
     * @return
     */
    @Scheduled(cron = "0 0 10 * * ?")
    public void emailDeliverPush() throws InterruptedException, Exception {
        String host = "smtp.office365.com";
        String user = env.getProperty("outlook.id");
        String password = env.getProperty("outlook.password");
        Properties props = new Properties();
        props.put("mail.transport.protocol","smtp");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", 587);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp..trust", "smtp-mail.outlook.com");

        System.out.println("=================== 이메일 발송 스타트 ====================");

        /**
         발송 이메일 조회
         */
        List<HashMap> emilList = mainpageService.getEmilDeliverList();

        List<HashMap> totalCnt = mainpageService.getTotalCount();

        if(emilList.size() > 0){
            for(HashMap email : emilList ) {
                HashMap<String, Object> userParams = new HashMap<>();
                userParams.put("userid", email.get("user_id"));
                userParams.put("id", email.get("user_id"));

                email.replace("email","itda.korea@gmail.com");

                // 사용자 검색 조건 조회
                HashMap<String, Object> userCompanyInfo = (HashMap<String, Object>) userService.getCompanyInfo(userParams);
                Map<String, Object> userInfo = userService.getUserInfo(userParams);


                //사업자 형태, 기업 형태, 창업 기간, 지역
                List<String> codeArr = Arrays.asList("bizp_type_cd", "biz_type_cd", "prd_cd", "loc_cd");
                List<String> getUsrInfo = Arrays.asList("business_type", "company_type", "start_period", "loc_ctg");
                List<String> usrCodeArr = new ArrayList<>();

                for(int i=0 ; i<codeArr.size(); i++){
                    List<String> output =  new ArrayList<>();
                    HashMap<String, Object> codeParams = new HashMap<>();
                    codeParams.put("ctgCd", codeArr.get(i));

                    List<Map<String, Object>> getCommonCodeDtlList = commonService.getCommonCodeDtlList(codeParams);

                    String[] listStr = userCompanyInfo.get(getUsrInfo.get(i)).toString().split(",");
                    for(int j=0; j<getCommonCodeDtlList.size(); j++){
                        for(String str : listStr){
                            if(str.equals(getCommonCodeDtlList.get(j).get("code").toString())){
                                output.add(getCommonCodeDtlList.get(j).get("code_nm").toString());
                            }
                        }
                    }
                    usrCodeArr.add(String.join(",",output));
                }


                //지원사업 리스트 ( 사업화지원 | 인건비 지원, 시설 공간 | 멘토링 교육, 대출 융자 | 마케팅 홍보, 행사 | RnD, 기타 )
                userCompanyInfo.put("user_id", email.get("user_id"));
                userCompanyInfo.put("mail_service", "true");
                List<String> getSupportData = Arrays.asList("02", "04,03", "06,09", "05,08", "07,10");
                List<String> getSupportList = new ArrayList<>();


                for(String str : getSupportData){
                    userCompanyInfo.put("target_cat_name", str);

                    List<HashMap> supportInfo = supportService.getSupportInfoList(userCompanyInfo);

                    String supportText = "";

                    if(supportInfo.size() != 0){
                        for(HashMap<String, Object> supStr : supportInfo){
                            Timestamp timestamp = (Timestamp) supStr.get("si_end_dt");
                            SimpleDateFormat sdf = new SimpleDateFormat("MM월dd일");
                            String end_dt = sdf.format(timestamp);
                            supportText +=
                            "<div style=\"margin-bottom: 10px;\"><a href=\"" + supStr.get("mobile_url") + "\" style=\"text-decoration:none; color:#797979; display:flex; align-items:center;\">"+
                                    "<div style=\"font-weight:400; background-color:#30D6C2; font-size:14px; display:flex;color:#fff; border-radius:5px; width:92px; height:32px;line-height: 32px;\"><strong style=\"margin: 0 auto;font-weight:400;\">" + supStr.get("target_cat_nm") + "</strong></div>&nbsp;&nbsp;"+
                            "<b style=\"font-weight:400;\">" + supStr.get("locname") + "</b>&nbsp;/&nbsp;<b style=\"font-weight:400;max-width:309px; white-space: nowrap;text-overflow: ellipsis;overflow:hidden;word-break: break-all; -webkit-box-orient: vertical;\">" + supStr.get("si_title") +  "</b>&nbsp;/&nbsp;<b style=\"font-weight:400;\">~" + end_dt+ "</b>"+
                            "</a></div>";
                        }
                    }else{
                        supportText +=  "<p style=\"color: #797979; margin:20px 0 0; font-weight:400; font-size:14px;\">맞춤 지원사업이 없어요.<a href=\"https://exitobiz.co.kr\" style=\"color:#0000FF;\">기업정보를 수정해보세요.</a></p>";
                    }
                    getSupportList.add(supportText);
                }

                //커뮤니티 게시글 조회
                List<HashMap> startUpCommList = communityService.selectDeliverComm();

                    //지난주 날짜 구하기(월-금)
                    Calendar now = Calendar.getInstance();
                    Calendar cal = Calendar.getInstance();
                    String commListTxt = "";
                    cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                    cal.add(Calendar.DATE, -8);

                    int idx = 0;

                    for(int i=0; i<5; i++){
                        cal.add(Calendar.DATE, 1);//월요일 부터 금요일까지의 날짜 더함
                        int nMonthTo  = cal.get(Calendar.MONTH)+1;
                        int dayLastWeekTo = cal.get(Calendar.DATE);
                        String dayLastWeekFromTxt = nMonthTo +"월 "+ cal.get(Calendar.DATE) + "일 (" + cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.NARROW_FORMAT, Locale.KOREAN) + ")";

                        Calendar commCal = Calendar.getInstance();
                        Timestamp timestamp = (Timestamp) startUpCommList.get(idx).get("cret_dt");
                        commCal.setTimeInMillis(timestamp.getTime());

                        String CommUrl = commCal.get(Calendar.DATE) == dayLastWeekTo ? "https://exitobiz.co.kr/community/communityView/" + startUpCommList.get(idx).get("id") : "#none";

                        commListTxt += "<p><a href=\"" + CommUrl + "\" style=\"text-decoration:none; color:#797979; display:flex; align-items:center;\">"+
                                "<strong style=\"font-weight:400; font-size:14px; display:flex; align-items:center; justify-content: center; color:#4949DB; border-radius:5px; width:92px; height:32px;\">" + dayLastWeekFromTxt + "</strong>&nbsp;&nbsp;";
                        if(commCal.get(Calendar.DATE) == dayLastWeekTo){
                            commListTxt += "<b style=\"font-weight:400; max-width:433px; white-space: nowrap;text-overflow: ellipsis;overflow:hidden;word-break: break-all; -webkit-box-orient: vertical;\">" + startUpCommList.get(idx).get("title") + "</b>";
                            if(startUpCommList.size()-1 > idx) idx++;
                        }
                        commListTxt += "</a></p>";
                    }

                try{
                    Session session = Session.getDefaultInstance(props, new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication(){
                            return new PasswordAuthentication(user, password);
                        }
                    });

                    try{
                        MimeMessage message = new MimeMessage(session);
                        message.setFrom(new InternetAddress(user));
                        message.addRecipients(Message.RecipientType.TO, String.valueOf(new InternetAddress((String) email.get("email"))));
                        message.setHeader("content-type", "text/html;charset=UTF-8");
                        String mailText1 = "";
                        String mailText2 = "";
                        String mailText3 = "";

                        message.setSubject("[엑시토] " + (now.get(Calendar.MONTH)+1) + "월 " + now.get(Calendar.WEEK_OF_MONTH) + "주차 맞춤 지원사업 정기배송");

                        mailText1 +=
                        "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"+
                        "<html xmlns=\"http://www.w3.org/1999/xhtml\">                                                                                                                                                                                                                                                                                                                        "+
                                "<head>                                                                                                                                                                                                                                                                                                                                                               "+
                                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />                                                                                                                                                                                                                                                                                        "+
                                "    <title>exito_email</title>                                                                                                                                                                                                                                                                                                                                       "+
                                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>                                                                                                                                                                                                                                                                                      "+
                                "    <link rel=\"stylesheet\" href=\"https://fonts.googleapis.com/css2?family=Material+Symbols+Rounded:opsz,wght,FILL,GRAD@48,400,0,0\" />                                                                                                                                                                                                                            "+
                                "    <style>                                                                                                                                                                                                                                                                                                                                                          "+
                                "          @font-face {\n" +
                                "            font-family: 'GmarketSans';\n" +
                                "            font-weight: 300;\n" +
                                "            font-style: normal;\n" +
                                "            src: url('https://cdn.jsdelivr.net/gh/webfontworld/gmarket/GmarketSansLight.eot');\n" +
                                "            src: url('https://cdn.jsdelivr.net/gh/webfontworld/gmarket/GmarketSansLight.eot?#iefix') format('embedded-opentype'),\n" +
                                "            url('https://cdn.jsdelivr.net/gh/webfontworld/gmarket/GmarketSansLight.woff2') format('woff2'),\n" +
                                "            url('https://cdn.jsdelivr.net/gh/webfontworld/gmarket/GmarketSansLight.woff') format('woff'),\n" +
                                "            url('https://cdn.jsdelivr.net/gh/webfontworld/gmarket/GmarketSansLight.ttf') format(\"truetype\");\n" +
                                "            font-display: swap;\n" +
                                "        }\n" +
                                "        @font-face {\n" +
                                "            font-family: 'GmarketSans';\n" +
                                "            font-weight: 500;\n" +
                                "            font-style: normal;\n" +
                                "            src: url('https://cdn.jsdelivr.net/gh/webfontworld/gmarket/GmarketSansMedium.eot');\n" +
                                "            src: url('https://cdn.jsdelivr.net/gh/webfontworld/gmarket/GmarketSansMedium.eot?#iefix') format('embedded-opentype'),\n" +
                                "            url('https://cdn.jsdelivr.net/gh/webfontworld/gmarket/GmarketSansMedium.woff2') format('woff2'),\n" +
                                "            url('https://cdn.jsdelivr.net/gh/webfontworld/gmarket/GmarketSansMedium.woff') format('woff'),\n" +
                                "            url('https://cdn.jsdelivr.net/gh/webfontworld/gmarket/GmarketSansMedium.ttf') format(\"truetype\");\n" +
                                "            font-display: swap;\n" +
                                "        }\n" +
                                "        @font-face {\n" +
                                "            font-family: 'GmarketSans';\n" +
                                "            font-weight: 700;\n" +
                                "            font-style: normal;\n" +
                                "            src: url('https://cdn.jsdelivr.net/gh/webfontworld/gmarket/GmarketSansBold.eot');\n" +
                                "            src: url('https://cdn.jsdelivr.net/gh/webfontworld/gmarket/GmarketSansBold.eot?#iefix') format('embedded-opentype'),\n" +
                                "            url('https://cdn.jsdelivr.net/gh/webfontworld/gmarket/GmarketSansBold.woff2') format('woff2'),\n" +
                                "            url('https://cdn.jsdelivr.net/gh/webfontworld/gmarket/GmarketSansBold.woff') format('woff'),\n" +
                                "            url('https://cdn.jsdelivr.net/gh/webfontworld/gmarket/GmarketSansBold.ttf') format(\"truetype\");\n" +
                                "            font-display: swap;\n" +
                                "        }                                                                                                                                                                                                                                                                                                                                                            "+
                                "    </style>                                                                                                                                                                                                                                                                                                                                                         "+
                                "</head>                                                                                                                                                                                                                                                                                                                                                              "+
                                "<body style=\"margin: 0; padding: 0; font-family: Arial, Helvetica, sans-serif;\">                                                                                                                                                                                                                                                                                                                              "+
                                "<div id=\"email_wrap\">                                                                                                                                                                                                                                                                                                                                              "+
                                "    <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"                                                                                                                                                                                                                                                                                         "+
                                "           style=\"max-width:630px; margin-left:auto; margin-right:auto; width:630px; font-family: \"GmarketSans\"; color:#151515; font-size: 14px; font-weight:400;\">                                                                                                                                                                                              "+
                                "        <thead style=\"padding:46px 95px 100px; background-color:#4949DB;\">                                                                                                                                                                                                                                                                                         "+
                                "        <tr>                                                                                                                                                                                                                                                                                                                                                         "+
                                "            <td style=\"padding:0px;\"><img src=\"https://api.exitobiz.co.kr/img/webapp_logo.png\" style=\"width:630px; vertical-align:top;\" alt=\"logo\" loading=\"lazy\">                                                                                                                                                                                                                         "+
                                "            </td>                                                                                                                                                                                                                                                                                                                                                    "+
                                "        </tr>                                                                                                                                                                                                                                                                                                                                                        "+
                                "        <tr>                                                                                                                                                                                                                                                                                                                                                         "+
                                "            <td style=\"padding: 0; font-size:18px; color:#fff; line-height:18px; text-align: center;\">                                                                                                                                                                                                                                                             "+
                                "                <p style=\"margin: 0;\">" + now.get(Calendar.YEAR) + "년 " + (now.get(Calendar.MONTH)+1) + "월 0"+ now.get(Calendar.WEEK_OF_MONTH) + "주차</p>                                                                                                                                                                                                                                                                                                            "+
                                "            </td>                                                                                                                                                                                                                                                                                                                                                    "+
                                "        </tr>                                                                                                                                                                                                                                                                                                                                                        "+
                                "        <tr>                                                                                                                                                                                                                                                                                                                                                         "+
                                "            <td style=\"font-size:30px; color:#fff; line-height:18px; text-align: center;\">                                                                                                                                                                                                                                                    "+
                                "                <p><strong style=\"font-size: 35px;\">정기 배송</strong>으로 만나는 <strong style=\"font-size: 35px;\">지원사업</strong></p>                                                                                                                                                                                                                                      "+
                                "            </td>                                                                                                                                                                                                                                                                                                                                                    "+
                                "        </tr>                                                                                                                                                                                                                                                                                                                                                        "+
                                "        <tr style=\"position: relative; align-items: center;\">                                                                                                                                                                                                                                                                                                    "+
                                "            <td style=\"width: calc(100% - 40px);height: 100px;background-color: #fff;border-radius: 5px;display: flex;margin: 20px;\">                                                                                                                        "+
                                "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">                                                                                                                                                                                                                                                                              "+
                                "                    <tr style=\"align-items: center;\">                                                                                                                                                                                                                                                                                                              "+
                                "                        <td width=\"33.333%\" valign=\"top\" style=\"border-right:3px solid #E4E4E4; text-align: center; vertical-align: middle;\">                                                                                                                                                                                                                  "+
                                "                            <h3 style=\"color: #797979; font-size: 12px; font-weight: 400; margin:0 0 20px;\">누적 지원 사업</h3>                                                                                                                                                                                                                                          "+
                                "                            <p style=\"font-size: 12px; margin: 0;\"><strong style=\"font-size: 28px;\">" + totalCnt.get(0).get("total_cnt") + "</strong>개</p>                                                                                                                                                                                                                                         "+
                                "                        </td>                                                                                                                                                                                                                                                                                                                                        "+
                                "                        <td width=\"33.333%\" valign=\"top\" style=\"border-right:3px solid #E4E4E4; text-align: center; vertical-align: middle;\">                                                                                                                                                                                                                  "+
                                "                            <h3 style=\"color: #797979; font-size: 12px; font-weight: 400; margin:0 0 20px;\">새로운 지원 사업</h3>                                                                                                                                                                                                                                         "+
                                "                            <p style=\"font-size: 12px; margin: 0;\"><strong style=\"font-size: 28px;\">" + totalCnt.get(0).get("week_cnt") + "</strong>개</p>                                                                                                                                                                                                                                            "+
                                "                        </td>                                                                                                                                                                                                                                                                                                                                        "+
                                "                        <td width=\"33.333%\" valign=\"top\" style=\"text-align: center; vertical-align: middle;\">                                                                                                                                                                                                                                                  "+
                                "                            <h3 style=\"color: #797979; font-size: 12px; font-weight: 400; margin:0 0 20px;\">정보 제공 기관</h3>                                                                                                                                                                                                                                          "+
                                "                            <p style=\"font-size: 12px; margin: 0;\"><strong style=\"font-size: 28px;\">" + totalCnt.get(0).get("target_cnt") + "</strong>개</p>                                                                                                                                                                                                                                            "+
                                "                        </td>                                                                                                                                                                                                                                                                                                                                        "+
                                "                    </tr>                                                                                                                                                                                                                                                                                                                                            "+
                                "                </table>                                                                                                                                                                                                                                                                                                                                             "+
                                "            </td>                                                                                                                                                                                                                                                                                                                                                    "+
                                "        </tr>                                                                                                                                                                                                                                                                                                                                                        "+
                                "        </thead>                                                                                                                                                                                                                                                                                                                                                     "+
                                "        <tbody>                                                                                                                                                                                                                                                                                                                                                      "+
                                "        <tr>                                                                                                                                                                                                                                                                                                                                                         "+
                                "            <td style=\"width:630px; border:solid 1px #eaeaea; align-items:center;\">                                                                                                                                                                                                                                                                                "+
                                "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"margin:20px auto 40px; bottom:50px; height:auto; text-align:center;\" width=\"92%;\">                                                                                                                                                                                                "+
                                "                    <tbody>                                                                                                                                                                                                                                                                                                                                          "+
                                "                        <tr>                                                                                                                                                                                                                                                                                                                                         "+
                                "                            <td width=\"100%\" valign=\"top\" style=\" text-align: left; vertical-align: middle; height:auto; margin: 0 auto; border: 1px solid #E4E4E4; border-radius: 5px; padding:20px; box-sizing:border-box;\">                                                                                                                                 "+
                                "                                <h4 style=\" font-size:18px; font-weight: 500; margin:0 0 20px;\">[<span>" + userInfo.get("usernickname") + "</span>] 님을 위한 지원사업 배송해드려요.</h4>                                                                                                                                                                                                                         "+
                                "                                <p style=\"color: #797979; margin: 0; font-weight:400;  margin:0 0 10px;\">사업자 형태: <strong style=\"font-weight:400; color:#151515;\">" + usrCodeArr.get(0) + "</strong></p>                                                                                                                                                                             "+
                                "                                <p style=\"color: #797979; margin: 0; font-weight:400; margin:0 0 10px;\">기업 형태:                                                                                                                                                                                                                                                     "+
                                "                                    <strong style=\"font-weight:400; color:#151515;\">" + usrCodeArr.get(1) + "</strong>"+
                                "                                </p>                                                                                                                                                                                                                                                                                                                                 "+
                                "                                <p style=\"color: #797979; margin: 0; font-weight:400; margin:0 0 10px;\">창업 기간: <strong style=\"font-weight:400; color:#151515;\">" + usrCodeArr.get(2) + "</strong></p>                                                                                                                                                                                "+
                                "                                <p style=\"color: #797979; margin: 0; font-weight:400; margin:0;\">지역: <strong style=\"font-weight:400; color:#151515;\">" + usrCodeArr.get(3) + "</strong></p>                                                                                                                                                                                             "+
                                "                                <p style=\"color: #797979; margin:20px 0 0; font-weight:400; font-size:12px;\">정기배송되는 지원사업을 수정하시려면 <a href=\"https://exitobiz.co.kr\" style=\"color:#0000FF;\">기업정보를 수정하세요.</a></p>                                                                                                                                                                        "+
                                "                            </td>                                                                                                                                                                                                                                                                                                                                    "+
                                "                        </tr>                                                                                                                                                                                                                                                                                                                                        "+
                                "                        <tr>                                                                                                                                                                                                                                                                                                                                         "+
                                "                            <td style=\"font-size: 0; line-height: 0;\" height=\"20\">                                                                                                                                                                                                                                                                               "+
                                "                                &nbsp;                                                                                                                                                                                                                                                                                                                               "+
                                "                            </td>                                                                                                                                                                                                                                                                                                                                    "+
                                "                        </tr>                                                                                                                                                                                                                                                                                                                                        ";

                         mailText2 +=  "                  <tr style=\"align-items: center;\">                                                                                                                                                                                                                                                                                                          "+
                                "                            <td valign=\"top\" style=\" text-align: left; vertical-align: middle; height:auto; margin: 0 auto; border: 1px solid #E4E4E4; border-radius: 5px; padding:20px; box-sizing:border-box;\">                                                                                                                                                "+
                                "                                <h4 style=\" font-size:18px; font-weight: 500; margin:0 0 20px;\">사업화 지원</h4>"+
                                                                    getSupportList.get(0)+
                                "                            </td>                                                                                                                                                                                                                                                                                                                                    "+
                                "                        </tr>                                                                                                                                                                                                                                                                                                                                        "+
                                "                        <tr>                                                                                                                                                                                                                                                                                                                                         "+
                                "                            <td style=\"font-size: 0; line-height: 0;\" height=\"20\">                                                                                                                                                                                                                                                                               "+
                                "                                &nbsp;                                                                                                                                                                                                                                                                                                                               "+
                                "                            </td>                                                                                                                                                                                                                                                                                                                                    "+
                                "                        </tr>                                                                                                                                                                                                                                                                                                                                        "+
                                "                        <tr style=\"align-items: center;\">                                                                                                                                                                                                                                                                                                          "+
                                "                            <td valign=\"top\" style=\" text-align: left; vertical-align: middle; height:auto; margin: 0 auto; border: 1px solid #E4E4E4; border-radius: 5px; padding:20px; box-sizing:border-box;\">                                                                                                                                                "+
                                                                "<h4 style=\" font-size:18px; font-weight: 500; margin:0 0 20px;\">인건비 지원 / 시설 공간</h4> "+
                                                                    getSupportList.get(1)+
                                "                            </td>                                                                                                                                                                                                                                                                                                                                    "+
                                "                        </tr>                                                                                                                                                                                                                                                                                                                                        "+
                                "                        <tr>                                                                                                                                                                                                                                                                                                                                         "+
                                "                            <td style=\"font-size: 0; line-height: 0;\" height=\"20\">                                                                                                                                                                                                                                                                               "+
                                "                                &nbsp;                                                                                                                                                                                                                                                                                                                               "+
                                "                            </td>                                                                                                                                                                                                                                                                                                                                    "+
                                "                        </tr>                                                                                                                                                                                                                                                                                                                                        "+
                                "                        <tr style=\"align-items: center;\">                                                                                                                                                                                                                                                                                                          "+
                                "                            <td valign=\"top\" style=\" text-align: left; vertical-align: middle; height:auto; margin: 0 auto; border: 1px solid #E4E4E4; border-radius: 5px; padding:20px; box-sizing:border-box;\">                                                                                                                                                "+
                                "                                <h4 style=\" font-size:18px; font-weight: 500; margin:0 0 20px;\">멘토링, 교육 / 대출, 융자</h4>"+
                                                                    getSupportList.get(2)+
                                "                            </td>                                                                                                                                                                                                                                                                                                                                    "+
                                "                        </tr>                                                                                                                                                                                                                                                                                                                                        "+
                                "                        <tr>                                                                                                                                                                                                                                                                                                                                         "+
                                "                            <td style=\"font-size: 0; line-height: 0;\" height=\"20\">                                                                                                                                                                                                                                                                               "+
                                "                                &nbsp;                                                                                                                                                                                                                                                                                                                               "+
                                "                            </td>                                                                                                                                                                                                                                                                                                                                    "+
                                "                        </tr>                                                                                                                                                                                                                                                                                                                                        "+
                                "                        <tr style=\"align-items: center;\">                                                                                                                                                                                                                                                                                                          "+
                                "                            <td valign=\"top\" style=\" text-align: left; vertical-align: middle; height:auto; margin: 0 auto; border: 1px solid #E4E4E4; border-radius: 5px; padding:20px; box-sizing:border-box;\">                                                                                                                                                "+
                                "                                <h4 style=\" font-size:18px; font-weight: 500; margin:0 0 20px;\">마케팅, 홍보 / 행사</h4>  "+
                                                                    getSupportList.get(3)+
                                "                            </td>                                                                                                                                                                                                                                                                                                                                    "+
                                "                        </tr>       "+
                                "                        <tr>                                                                                                                                                                                                                                                                                                                                         "+
                                "                            <td style=\"font-size: 0; line-height: 0;\" height=\"20\">                                                                                                                                                                                                                                                                               "+
                                "                                &nbsp;                                                                                                                                                                                                                                                                                                                               "+
                                "                            </td>                                                                                                                                                                                                                                                                                                                                    "+
                                "                        </tr>                                                                                                                                                                                                                                                                                                                                        "+
                                "                        <tr style=\"align-items: center;\">                                                                                                                                                                                                                                                                                                          "+
                                "                            <td valign=\"top\" style=\" text-align: left; vertical-align: middle; height:auto; margin: 0 auto; border: 1px solid #E4E4E4; border-radius: 5px; padding:20px; box-sizing:border-box;\">                                                                                                                                                "+
                                "                                <h4 style=\" font-size:18px; font-weight: 500; margin:0 0 20px;\">R&D / 기타</h4>                                                                                                                                                                                                                                                      "+
                                                                    getSupportList.get(4)+
                                "                            </td>                                                                                                                                                                                                                                                                                                                                    "+
                                "                        </tr>                                                                                                                                                                                                                                                                                                                                        "+
                                "                        <tr>                                                                                                                                                                                                                                                                                                                                         "+
                                "                            <td style=\"font-size: 0; line-height: 0;\" height=\"20\">                                                                                                                                                                                                                                                                               "+
                                "                                &nbsp;                                                                                                                                                                                                                                                                                                                               "+
                                "                            </td>                                                                                                                                                                                                                                                                                                                                    "+
                                "                        </tr>" ;
                         mailText3 +="                                                                                                                                                                                                                                                                                                                                "+
                                "                        <tr style=\"align-items: center;\">                                                                                                                                                                                                                                                                                                          "+
                                "                            <td valign=\"top\" style=\" text-align: left; vertical-align: middle; height:auto; margin: 0 auto; border: 1px solid #E4E4E4; border-radius: 5px; padding:20px; box-sizing:border-box;\">                                                                                                                                                "+
                                "                                <h4 style=\" font-size:18px; font-weight: 500; margin:0 0 20px;\">스타트업 뉴스</h4>                                                                                                                                                                                                                                                       "+
                                                                 commListTxt+
                                "                            </td> "+
                                "                        </tr>                                                                                                                                                                                                                                                                                                                                        "+
                                "                        <tr>                                                                                                                                                                                                                                                                                                                                         "+
                                "                            <td style=\"font-size: 0; line-height: 0;\" height=\"20\">                                                                                                                                                                                                                                                                               "+
                                "                                &nbsp;                                                                                                                                                                                                                                                                                                                               "+
                                "                            </td>                                                                                                                                                                                                                                                                                                                                    "+
                                "                        </tr>                                                                                                                                                                                                                                                                                                                                        "+
                                "                        <tr style=\"align-items: center;\">                                                                                                                                                                                                                                                                                                          "+
                                "                            <td valign=\"top\" style=\" text-align: left; vertical-align: middle; height:auto; margin: 0 auto; border: 1px solid #E4E4E4; border-radius: 5px;box-sizing:border-box;\">                                                                                                                                                               "+
                                "                                <a target=\"_blank\" href=\"https://exitobiz.co.kr/Landing/landing\"><img src=\"https://api.exitobiz.co.kr/img/email_banner.png\" style=\"height:174px; width: 100%; object-fit:cover;\" ></a>                                                                                                                                                                                                                                               "+
                                "                            </td>                                                                                                                                                                                                                                                                                                                                    "+
                                "                        </tr>                                                                                                                                                                                                                                                                                                                                        "+
                                "                        <tr style=\"align-items: center;\">                                                                                                                                                                                                                                                                                                          "+
                                "                            <td valign=\"top\" style=\"padding: 40px;\">                                                                                                                                                                                                                                                                                             "+
                                "                                <p style=\"line-height: 20px; display: flex; align-items: center; justify-content: center; color:#0000FF;\">                                                                                                                                                                                                                         "+
                                "                                    <a href=\"https://exitobiz.co.kr\" style=\"color:#0000FF; font-size: 14px;\">지난 정기배송 지원사업 보기</a>                                                                                                                                                                  "+
                                "                                </p>                                                                                                                                                                                                                                                                                                                                 "+
                                "                            </td>                                                                                                                                                                                                                                                                                                                                    "+
                                "                        </tr>                                                                                                                                                                                                                                                                                                                                        "+
                                "                        <tr>                                                                                                                                                                                                                                                                                                                                         "+
                                "                            <td style=\"line-height:12px; text-align:center; font-size: 12px;\">                                                                                                                                                                                                                                                                     "+
                                "                                <a href=\"https://exitobiz.co.kr/\" target=\"_blank\" rel=\"\">                                                                                                                                                                                                                                                                                             "+
                                "                                    <img src=\"https://api.exitobiz.co.kr/img/webapp_home.png\" alt=\"엑시토 홈으로 이동\" style=\"border:0\" loading=\"lazy\"></a>                                                                                                                                                                                                                                          "+
                                "                                <a href=\"https://blog.naver.com/PostList.naver?blogId=ctns0113\" target=\"_blank\" rel=\"\">                                                                                                                                                                                                                                                                                             "+
                                "                                    <img src=\"https://api.exitobiz.co.kr/img/webapp_blog.png\" alt=\"엑시토 blog로 이동\" style=\"border:0;margin-left: 10px;\" loading=\"lazy\"></a>                                                                                                                                                                                                                     "+
                                "                                <a href=\"https://www.instagram.com/startup_exito/\" target=\"_blank\" rel=\"\">                                                                                                                                                                                                                                                                                             "+
                                "                                    <img src=\"https://api.exitobiz.co.kr/img/webapp_insta.png\" alt=\"엑시토 Instargram로 이동\" style=\"border:0;margin-left: 10px;\" loading=\"lazy\"></a>                                                                                                                                                                                                              "+
                                "                            </td>                                                                                                                                                                                                                                                                                                                                    "+
                                "                        </tr>                                                                                                                                                                                                                                                                                                                                        "+
                                "                    </tbody>                                                                                                                                                                                                                                                                                                                                         "+
                                "                </table>                                                                                                                                                                                                                                                                                                                                             "+
                                "            </td>                                                                                                                                                                                                                                                                                                                                                    "+
                                "        </tr>                                                                                                                                                                                                                                                                                                                                                        "+
                                "        </tbody>                                                                                                                                                                                                                                                                                                                                                     "+
                                "        <tfoot>                                                                                                                                                                                                                                                                                                                                                      "+
                                "        <tr>                                                                                                                                                                                                                                                                                                                                                         "+
                                "            <td style=\"background:#f8f8f8; padding:30px 0 0;\">                                                                                                                                                                                                                                                                                                     "+
                                "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:100%;\">                                                                                                                                                                                                                                                                       "+
                                "                    <tbody>                                                                                                                                                                                                                                                                                                                                          "+
                                "                    <tr>                                                                                                                                                                                                                                                                                                                                             "+
                                "                        <td style=\"margin:0; padding:0 0 0 30px; font-size:11px; line-height:20px; color:#797979; text-align:left;\">                                                                                                                                                                                                                               "+
                                "                            엑시토 정기배송을 원치 않으시면  <a href=\"https://dev.exitobiz.co.kr/deliver/deliverService/" + email.get("user_id") + "\" target=\"_blank\" style=\"margin:0; padding:0; font-size:11px; line-:20px; color:#151515; font-weight:500; text-align:left;\">[수신거부]</a> 를 눌러주세요.<br>                                                                                                                                                    "+
                                "                            본 메일은 발신전용 메일이므로 회신을 통한 문의는 처리되지 않습니다.<br>                                                                                                                                                                                                                                                                                               "+
                                "                        </td>                                                                                                                                                                                                                                                                                                                                        "+
                                "                    </tr>                                                                                                                                                                                                                                                                                                                                            "+
                                "                    <tr>                                                                                                                                                                                                                                                                                                                                             "+
                                "                        <td style=\"margin:0; padding:17px 0 0 30px; font-size:11px; line-height:20px; color:#797979; text-align:left;\">                                                                                                                                                                                                                            "+
                                "                            (주)CTNS<br>                                                                                                                                                                                                                                                                                                                              "+
                                "                            exito@myctns.com<br>                                                                                                                                                                                                                                                                                                                     "+
                                "                            본사 : 경상남도 창원시 의창구 평산로 23 신화테크노밸리 6층 639~641호<br>                                                                                                                                                                                                                                                                                         "+
                                "                            지사 : 경기도 화성시 영천동 283-1 금강펜테리움 IX 타워<br>                                                                                                                                                                                                                                                                                          "+
                                "                        </td>                                                                                                                                                                                                                                                                                                                                        "+
                                "                    </tr>                                                                                                                                                                                                                                                                                                                                            "+
                                "                    <tr>                                                                                                                                                                                                                                                                                                                                             "+
                                "                        <td style=\"margin:0; padding:16px 0 40px 30px; background:#f8f8f8; font-size:11px; line-height:20px; color:#BFBFBF; text-align:left;\">                                                                                                                                                                                                     "+
                                "                            Copyright © EXITO. All rights reserved                                                                                                                                                                                                                                                                                                   "+
                                "                        </td>                                                                                                                                                                                                                                                                                                                                        "+
                                "                    </tr>                                                                                                                                                                                                                                                                                                                                            "+
                                "                    </tbody>                                                                                                                                                                                                                                                                                                                                         "+
                                "                </table>                                                                                                                                                                                                                                                                                                                                             "+
                                "            </td>                                                                                                                                                                                                                                                                                                                                                    "+
                                "        </tr>                                                                                                                                                                                                                                                                                                                                                        "+
                                "        </tfoot>                                                                                                                                                                                                                                                                                                                                                     "+
                                "    </table>                                                                                                                                                                                                                                                                                                                                                         "+
                                "</div>                                                                                                                                                                                                                                                                                                                                                               "+
                                "</body>                                                                                                                                                                                                                                                                                                                                                              "+
                                "</html>" ;
                        message.setContent(mailText1 + mailText2 + mailText3, "text/html;charset=UTF-8");
                        Transport.send(message);

                        System.out.println("=================== 이메일 발송 끝 ====================");

                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

    }
    
}

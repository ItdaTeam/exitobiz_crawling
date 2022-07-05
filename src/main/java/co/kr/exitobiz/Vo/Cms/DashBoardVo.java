package co.kr.exitobiz.Vo.Cms;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter @Setter @ToString
public class DashBoardVo {
    private String date;   //날짜
    private Long clickCnt; //클릭수
    private Long inCnt;    //접속수
    private Long joinCnt; //가입수
    private Long totalCnt; //총회원수
    private Long inActiveCnt; //비활성화수
    private Long crawlingCnt; //크롤링수
    private Long activeCrawlingCnt;

    private float totalPer; //전체유저퍼센트
    private float inPer; //금일 접속자 퍼센트
    private float joinPer; //금일 가입자 퍼센트
    private float clickPer; //금일 클릭수 퍼센트
    private float crawlingPer; //금일 크롤링 수 퍼센트

    private Long lastWeekTotalCnt; //전주
    private float lastWeekTotalPer;
    private Long lastWeekClickCnt;
    private float lastWeekClickPer;

    private Long beforeLastWeekTotalCnt; //전전주
    private float beforeLastWeekTotalPer;
    private Long beforeLastWeekClickCnt;
    private float beforeLastWeekClickPer;

    private Long lastMonthTotalCnt; //전월
    private float lastMonthTotalPer;
    private Long lastMonthClickCnt;
    private float lastMonthClickPer;

    private Long beforeLastMonthTotalCnt; //전전월
    private float beforeLastMonthTotalPer;
    private Long beforeLastMonthClickCnt;
    private float beforeLastMonthClickPer;

    private String id;
    private String usernickname;
    private String lastloc;
    private String useremail;
    private String lastTime;
    private String fromDate;
    private String toDate;

    private String supPage;
    private Long supCnt;
    private String proPage;
    private Long proCnt;

    private String index;
    private String month;
    private String goal;
}

package co.kr.exitobiz.Vo.Cms;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter @Setter @ToString
public class SupportVo {
    private Long siIdx;
    private String locCode;
    private String siTitle;
    private String mobileUrl;
    private String pcUrl;
    private String targetName;
    private String targetCatName;
    private Long targetCostValue;
    private String siEndDt;
    private String siCretDt;
    private Integer viewCnt;
    private Integer shareCnt;
    private Integer saveCnt;

    private String targetCatCd;
    private String businessCtg;
    private String techCtg;
    private String businessType;
    private String companyType;
    private String startPeriod;


    //마스터
    private String title;
    private String url;
    private String activeYn;
    private String siActiveYn;
    private String errorYn;


    //메인화면
    private Long totalSupportCnt;
    private Long activeYCnt;
    private Long activeNCnt;
    private Integer allViewCnt;
    private String ViewCntLoc;
    private String SupportCntLoc;

    public SupportVo(){
    }

    public SupportVo(String title, String url, String locCode, String activeYn, String errorYn){
        this.title = title;
        this.url = url;
        this.locCode = locCode;
        this.activeYn = activeYn;
        this.errorYn = errorYn;
    }

    public SupportVo(String title, String targetCatName, String locCode, String siTitle, String mobileUrl, String pcUrl){
        this.title = title;
        this.targetCatName = targetCatName;
        this.locCode = locCode;
        this.siTitle = siTitle;
        this.mobileUrl = mobileUrl;
        this.pcUrl = pcUrl;
    }

}

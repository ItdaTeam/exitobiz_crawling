package co.kr.exitobiz.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "support_info_corp", schema = "itda_app")
@Getter
@Setter
public class SupportCorp {

    @Id
    @Column(name = "si_idx")
    private Long siIdx;

    @Column(name = "target_name")
    private String targetName;

    @Column(name = "target_cat_name")
    private String targetCatName;

    @Column(name = "target_cost_value")
    private Long targetCostValue;

    @Column(name = "loc_code")
    private String locCode;

    @Column(name = "si_title")
    private String siTitle;

    @Column(name = "mobile_url")
    private String mobileUrl;

    @Column(name = "pc_url")
    private String pcUrl;

    @Column(name = "si_end_dt")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime siEndDt;

    @Column(name = "si_cret_dt")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date siCretDt;

    @Column(name = "si_active_yn")
    private String siActiveYn;

    // 추가컬럼 2022-01-11
    @Column(name = "business_ctg")
    private String businessCtg;

    @Column(name = "tech_ctg")
    private String techCtg;

    @Column(name = "business_type")
    private String businessType;

    @Column(name = "company_type")
    private String companyType;

    @Column(name = "start_period")
    private String startPeriod;

    @Column(name = "target_cat_cd")
    private String targetCatCd;
    //추가컬럼 끝

    @Column(name = "view_cnt")
    private Integer viewCnt;

    @Column(name = "share_cnt")
    private Integer shareCnt;

    @Column(name = "save_cnt")
    private Integer saveCnt;

    @Column(name = "from_dt")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date fromDt;

    @Column(name = "to_dt")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date toDt;

    @Column(name = "corp_cd")
    private String corpCd;


    public SupportCorp(String targetName, String targetCatName, String locCode, String siTitle, String mobileUrl, String pcUrl){
        this.targetName = targetName;
        this.targetCatName = targetCatName;
        this.locCode = locCode;
        this.siTitle = siTitle;
        this.mobileUrl = mobileUrl;
        this.pcUrl = pcUrl;
    }

    public SupportCorp() {

    }
}

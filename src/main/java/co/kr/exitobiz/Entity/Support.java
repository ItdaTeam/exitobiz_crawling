package co.kr.exitobiz.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "support_info", schema = "itda_app")
@Getter
@Setter
public class Support {

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
    private LocalDateTime siCretDt;

    @Column(name = "si_active_yn")
    private String siActiveYn;

    @Column(name = "view_cnt")
    private Integer viewCnt;

    @Column(name = "share_cnt")
    private Integer shareCnt;

    @Column(name = "save_cnt")
    private Integer saveCnt;
}

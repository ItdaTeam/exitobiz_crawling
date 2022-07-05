package co.kr.exitobiz.Vo.Cms;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter @Getter
public class AgencyVo {
    private Long id;
    private String title;
    private String url;
    private String activeYn;
    private String errorYn;
    private String locname;
    private Long crawlingcnt;
    private String locCode;
    private String remark;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime lastCrawlingDt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    private Long totalAgencyCnt;
    private Long avtiveAgencyCnt;
    private Long allCrawlingCnt;
    private Long errorCnt;
}

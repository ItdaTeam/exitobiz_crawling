package co.kr.exitobiz.Vo.Cms;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
public class ContentVo {
    private Integer id;
    private String title;
    private String content;
    private String activeYn;
    private String type;
    private String url;
    private MultipartFile upFile;
    private String thumbnail;
    private String remark;
    private Integer viewCnt;

    private Long inCnt;
    private Long outCnt;

    private Integer cost;
    private Integer discountCost;
    private Integer discountRate;
    private String salesFromDt;
    private String salesToDt;
    private Integer person;
}

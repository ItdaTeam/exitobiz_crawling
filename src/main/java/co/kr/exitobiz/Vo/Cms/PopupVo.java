package co.kr.exitobiz.Vo.Cms;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class PopupVo {
    private Integer id;
    private String title;
    private String category;
    private String from;
    private String to;
    private Long noticeId;
    private String active;
    private String image;
    private String link;
    private String createdAt;
    private String updatedAt;

    private MultipartFile upFile;

    private Long totalCnt;
    private Long activeCnt;
}

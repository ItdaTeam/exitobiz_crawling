package co.kr.exitobiz.Vo.Mobile;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CommunityVo {
    private Integer id;
    private String category;
    private String userId;
    private String title;
    private String content;
    private MultipartFile upFile;

    private String cretDt;
    private String updtDt;

    private Integer viewCnt;
}

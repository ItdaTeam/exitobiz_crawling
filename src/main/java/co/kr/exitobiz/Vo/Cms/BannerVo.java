package co.kr.exitobiz.Vo.Cms;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
public class BannerVo {
    private String index;
    private String bannerCtg;
    private String bannerTitle;
    private String bannerLink;
//    private String bannerLoc;
    private String bannerNotiIdx;
    private String bannerImg;
    private String activeYn;
    private String cretId;
    private String cretDt;
    private String updtId;
    private String updtDt;
    private MultipartFile image;
}

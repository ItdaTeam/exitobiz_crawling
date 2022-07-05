package co.kr.exitobiz.Vo.Cms;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDate;

@Data
public class NoticeVo {
    private Integer id;
    private String title;
    private String contents;
    private String mustYn;
    private String activeYn;
    private Integer userId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate createAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate updateAt;

    private Long totalCnt;
    private Long mustCnt;

}

package co.kr.exitobiz.Vo.Cms;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter @Setter
public class StaffVo {

    @NotEmpty(message = "ID는 필수값입니다")
    private String id;

    private String password;
    private String passwordKey; //비밀번호 솔트
    private String name;
    private String phoneNum;
    private String email;
    private String activeYn;
    private String memo;
    private LocalDateTime latestDt;
    private LocalDateTime cretDt;
    private LocalDateTime updtDt;
}

package co.kr.exitobiz.Vo.Cms;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UserVo {
    private Integer indexid;
    private String id;
    private String idprofile;
    private String userbirthday;
    private String useragerange;
    private String usergender;
    private String usernickname;
    private String useremail;
    private String username;
    private String userloc;
    private String userlocname;
    private String usertitle;
    private String userhp;
    private String companyname;
    private String companytype;
    private String companyloc;
    private String mos;
    private String minfo;
    private String adminFlag;
    private String checkFlag;
    private String userToken;
    private String appversion;
    private String lat;
    private String lng;
    private String sido;
    private String sigugun;
    private String dongmyun;
    private String jibun;
    private String lastloc;
    private Long totalMemberCnt;
    private Long logintodaycnt;
    private Long joinTodayCnt;
    private Long loginTodayMemberCnt;
    private Long loginMonthMemberCnt;
    private Long activeMemberCnt;
    private Long totalStaffCnt;
//    private String loginweekcnt;
//    private String loginmonthcnt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime cretTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime lastTime;

    public UserVo(String id){
        this.id = id;
    }
}

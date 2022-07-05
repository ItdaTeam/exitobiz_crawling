package co.kr.exitobiz.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.CodePointLength;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usertable", schema = "itda_app")
@Getter
@Setter
public class Member {

    @GeneratedValue
    @Column(name = "indexid")
    private Integer indexid;

    @Id
    @Column(name="id")
    private String id;

    @Column(name="idprofile")
    private String idprofile;

    @Column(name="userbirthday")
    private String userbirthday;

    @Column(name="useragerange")
    private String useragerange;

    @Column(name="usergender")
    private String usergender;

    @Column(name="usernickname")
    private String usernickname;

    @Column(name="useremail")
    private String useremail;

    @Column(name="username")
    private String username;

    @Column(name="userloc")
    private String userloc;

    @Column(name="userlocname")
    private String userlocname;

    @Column(name="usertitle")
    private String usertitle;

    @Column(name="userhp")
    private String userhp;

    @Column(name="companyname")
    private String companyname;

    @Column(name="companytype")
    private String companytype;

    @Column(name="companyloc")
    private String companyloc;

    @Column(name="mos")
    private String mos;

    @Column(name="minfo")
    private String minfo;

    @Column(name="check_flag")
    private String checkFlag;

    @Column(name="admin_flag")
    private String adminFlag;

    @Column(name="cret_time")
    private LocalDateTime cretTime;

    @Column(name="last_time")
    private LocalDateTime lastTime;

    @Column(name="usertoken")
    private String usertoken;

    @Column(name = "appversion")
    private String appversion;

    @Column(name = "lat")
    private String lat;

    @Column(name = "lng")
    private String lng;

    @Column(name = "sido")
    private String sido;

    @Column(name = "sigugun")
    private String sigugun;

    @Column(name = "dongmyun")
    private String dongmyun;

    @Column(name = "jibun")
    private String jibun;

    @Column(name = "lastloc")
    private String lastloc;

    @OneToMany //일대다 조인일경우 List를 조인걸어야한다.
    @JoinTable(name = "loginsaver",
            joinColumns = @JoinColumn(name = "id"), //외래키
            inverseJoinColumns = @JoinColumn(name="userid") //반대 엔티티의 외래키
    )
    private List<Loginsaver> loginsaver = new ArrayList<Loginsaver>();
}

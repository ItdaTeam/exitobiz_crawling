package co.kr.exitobiz.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "staff", schema = "itda_web")
@Getter
@Setter
public class Staff {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name="password")
    private String password;

    @Column(name="name")
    private String name;

    @Column(name="phone_num")
    private String phoneNum;

    @Column(name="email")
    private String email;

    @Column(name="active_yn")
    private String activeYn;

    @Column(name="memo")
    private String memo;

    @Column(name="lastest_dt")
    private LocalDateTime lastestDt;

    @Column(name="cret_dt")
    private LocalDateTime cretDt;

    @Column(name="updt_dt")
    private LocalDateTime updtDt;

    @Column(name = "password_key")
    private String passwordKey;
}

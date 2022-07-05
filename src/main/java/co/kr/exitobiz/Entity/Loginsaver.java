package co.kr.exitobiz.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "loginsaver", schema = "itda_app")
@Getter
@Setter
public class Loginsaver {

    @GeneratedValue
    @Id
    @Column(name = "idx")
    private Integer idx;

    @Column(name = "userid")
    private String userid;

    @Column(name = "login_time")
    private LocalDateTime loginTime;

}

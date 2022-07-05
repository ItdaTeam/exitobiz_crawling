package co.kr.exitobiz.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "loccode", schema = "itda_app")
@Getter
@Setter
public class Loccode {

    @GeneratedValue
    @Id
    @Column(name = "indexid")
    private Integer indexid;

    @Column(name = "loccode")
    private String loccode;

    @Column(name = "locname")
    private String locname;

    @Column(name = "active_flag")
    private String activeFlag;

}

package co.kr.exitobiz.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(schema = "itda_web", name = "content")
@Getter
@Setter
public class Content {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "type")
    private String type;

    @Column(name = "active_yn")
    private String activeYn;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "content")
    private String content;

    @Column(name = "url")
    private String url;

    @Column(name = "remark")
    private String remark;

    @Column(name = "cret_dt")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    @Temporal(TemporalType.DATE)
    private Date cretDt;

    @Column(name = "updt_dt")
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date updtDt;

    @Column(name = "view_cnt")
    private Integer viewCnt;

    @Column(name = "cost")
    private Integer cost;

    @Column(name = "discount_cost")
    private Integer discountCost;

    @Column(name = "discount_rate")
    private Integer discountRate;

    @Column(name = "sales_from_dt")
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date salesFromDt;

    @Column(name = "sales_to_dt")
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date salesToDt;

    @Column(name = "person")
    private Integer person;
}

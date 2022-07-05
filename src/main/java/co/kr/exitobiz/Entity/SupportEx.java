package co.kr.exitobiz.Entity;

import lombok.Getter;
import org.checkerframework.checker.units.qual.C;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Immutable
@Subselect("select target_name, count('target_name') as crawlingcnt" + " from itda_app.support_info" + " where si_active_yn = 'Y'" + " group by target_name")
@Synchronize("itda_app.support_info")
@Getter
public class SupportEx {

    @Id
    @Column(name = "si_idx")
    private Long siIdx;

    @Column
    private String targetName;

    @Column
    private Long crawlingcnt;

    @Column
    private String siActiveYn;

}

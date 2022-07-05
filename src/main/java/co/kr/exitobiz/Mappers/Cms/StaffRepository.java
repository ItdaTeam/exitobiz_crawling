package co.kr.exitobiz.Mappers.Cms;

import co.kr.exitobiz.Entity.Member;
import co.kr.exitobiz.Entity.Staff;
import co.kr.exitobiz.Vo.Cms.SearchVo;
import co.kr.exitobiz.Vo.Cms.StaffVo;
import co.kr.exitobiz.Vo.Cms.UserVo;
import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

import static co.kr.exitobiz.Entity.QLoginsaver.loginsaver;
import static co.kr.exitobiz.Entity.QMember.member;
import static co.kr.exitobiz.Entity.QStaff.staff;


@Repository
@RequiredArgsConstructor
public class StaffRepository {

    private final JPAQueryFactory jpaQueryFactory;

    private final EntityManager em;

    //서브쿼리 alias order by 하기위해 선언
    Path<Long> logintodaycnt = Expressions.numberPath(Long.class, "logintodaycnt");

    //앱 유저 검색
    public List<UserVo> searchMember(SearchVo searchVo, LocalDateTime now, LocalDateTime monthAgo){
        return jpaQueryFactory.select(
                        Projections.fields(UserVo.class,
                                member.indexid,
                                member.id,
                                member.idprofile,
                                member.userbirthday,
                                member.useragerange,
                                member.usergender,
                                member.usernickname,
                                member.useremail,
                                member.username,
                                member.userloc,
                                member.userlocname,
                                member.usertitle,
                                member.userhp,
                                member.companyname,
                                member.companytype,
                                member.companyloc,
                                member.mos,
                                member.minfo,
                                member.checkFlag,
                                member.adminFlag,
                                member.cretTime,
                                member.lastTime,
                                member.usertoken,
                                member.appversion,
                                member.lat,
                                member.lng,
                                member.sido,
                                member.sigugun,
                                member.dongmyun,
                                member.jibun,
                                member.lastloc,
                                loginsaver.userid.count().as(logintodaycnt)
                        ))
                .from(member)
                .leftJoin(loginsaver).on(member.id.eq(loginsaver.userid))
                .fetchJoin()
                .where(searchMemberEq(searchVo))
                .where(searchCountEq(searchVo, now, monthAgo))
                .groupBy(member)
                .orderBy(orderType(searchVo.getViewType()))
                .fetch();
    }

    //회원관리 화면 초기 정보
    public UserVo getMemberInfo(LocalDateTime now, LocalDateTime monthAgo, LocalDateTime appRenewDay){
        return jpaQueryFactory
                .select(Projections.fields(UserVo.class,
                        member.id.count().as("totalMemberCnt"),
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(member.id.count())
                                        .from(member)
                                        .where(Expressions.dateTimeTemplate(LocalDateTime.class,"TO_CHAR({0}, {1})",member.lastTime, "YYYYMMDD")
                                                .eq(Expressions.dateTimeTemplate(LocalDateTime.class,"TO_CHAR({0}, {1})",Expressions.currentTimestamp(), "YYYYMMDD")))
                                        ,"loginTodayMemberCnt"
                        ),
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(member.id.count())
                                        .from(member)
                                        .where(Expressions.dateTimeTemplate(LocalDateTime.class,"TO_CHAR({0}, {1})",member.cretTime, "YYYYMMDD")
                                                .eq(Expressions.dateTimeTemplate(LocalDateTime.class,"TO_CHAR({0}, {1})",Expressions.currentTimestamp(), "YYYYMMDD")))
                                ,"joinTodayCnt"
                        ),
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(loginsaver.userid.countDistinct())
                                        .from(loginsaver)
                                        .where(Expressions.dateTimeTemplate(LocalDateTime.class,"TO_CHAR({0}, {1})",loginsaver.loginTime, "YYYYMMDD")
                                                .eq(Expressions.dateTimeTemplate(LocalDateTime.class,"TO_CHAR({0}, {1})",Expressions.currentTimestamp(), "YYYYMMDD")))
                                ,"logintodaycnt"
                        ),
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(member.id.count())
                                        .from(member)
                                        .where(member.lastTime.between(monthAgo,now))
                                ,"loginMonthMemberCnt"
                        ),
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(member.id.count())
                                        .from(member)
                                        .where(member.lastTime.lt(appRenewDay))
                                ,"activeMemberCnt"
                        ),
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(staff.id.count())
                                        .from(staff)
                                        .where(staff.activeYn.eq("Y"))
                                ,"totalStaffCnt"
                        )
                        )
                )
                .from(member)
                .fetchOne();
    }

    public Member findById(String id){
        return em.find(Member.class,id);
    }

    public List<Staff> searchStaff(SearchVo searchVo){
        return jpaQueryFactory
                .selectFrom(staff)
                .where(searchStaffEq(searchVo))
                .fetch();
    }

    public Staff findOneStaff(String id){
        return em.find(Staff.class, id);
    }

    public List<Staff> findStaffById(String id){
        return jpaQueryFactory
                .selectFrom(staff)
                .where(staff.id.eq(id))
                .fetch();
    }

    public void joinStaff(Staff staff){
        em.persist(staff);
    }

    public void deleteStaff(String id){
        Staff staff = em.find(Staff.class, id);

        if(staff != null){
            em.remove(staff);
        }
    }

    private BooleanExpression searchMemberEq(SearchVo searchVo){
        if(searchVo.getInq() != ""){
            if(searchVo.getCon().equals("all")){
                return searchVo.getListInq() != null ?
                        Expressions.anyOf(
                                searchVo.getListInq()
                                        .stream()
                                        .map(this::isFilteredAll)
                                        .toArray(BooleanExpression[]::new)) : null;
            } else if(searchVo.getCon().equals("nickname")){
                return searchVo.getListInq() != null ?
                        Expressions.anyOf(
                                searchVo.getListInq()
                                        .stream()
                                        .map(this::isFilteredNickname)
                                        .toArray(BooleanExpression[]::new)) : null;
            } else if(searchVo.getCon().equals("id")){
                return searchVo.getListInq() != null ?
                        Expressions.anyOf(
                                searchVo.getListInq()
                                        .stream()
                                        .map(this::isFilteredId)
                                        .toArray(BooleanExpression[]::new)) : null;
            } else if(searchVo.getCon().equals("age")){
                return searchVo.getListInq() != null ?
                        Expressions.anyOf(
                                searchVo.getListInq()
                                        .stream()
                                        .map(this::isFilteredAge)
                                        .toArray(BooleanExpression[]::new)) : null;
            } else if (searchVo.getCon().equals("gender")){
                return searchVo.getListInq() != null ?
                        Expressions.anyOf(searchVo.
                                getListInq()
                                .stream()
                                .map(this::isFilteredGender)
                                .toArray(BooleanExpression[]::new)) : null;
            } else if (searchVo.getCon().equals("os")){
                return searchVo.getListInq() != null ?
                        Expressions.anyOf(
                                searchVo.getListInq()
                                        .stream()
                                        .map(this::isFilteredOs)
                                        .toArray(BooleanExpression[]::new)) : null;
            } else if (searchVo.getCon().equals("mail")){
                return searchVo.getListInq() != null ?
                        Expressions.anyOf(
                                searchVo.getListInq()
                                        .stream()
                                        .map(this::isFilteredMail)
                                        .toArray(BooleanExpression[]::new)) : null;
            }
        }
        return null;
    }

    private BooleanExpression isFilteredAll(String all){
        return member.usernickname.contains(all)
                .or(member.id.contains(all))
                .or(member.useragerange.contains(all))
                .or(member.useragerange.contains(all))
                .or(member.mos.contains(all))
                .or(member.useremail.contains(all));
    }

    private BooleanExpression isFilteredNickname(String nickname){
        return member.usernickname.contains(nickname);
    }

    private BooleanExpression isFilteredId(String id){
        return member.id.contains(id);
    }

    private BooleanExpression isFilteredAge(String age){
        return member.useragerange.contains(age);
    }

    private BooleanExpression isFilteredGender(String gender){
        return member.useragerange.contains(gender);
    }

    private BooleanExpression isFilteredOs(String os){
        return member.mos.contains(os);
    }

    private BooleanExpression isFilteredMail(String mail){
        return member.useremail.contains(mail);
    }

    private BooleanExpression searchCountEq(SearchVo searchVo, LocalDateTime now, LocalDateTime monthAgo){
        if(searchVo.getViewType().equals("opt2")) {
            return loginsaver.loginTime.gt(now.minusMonths(1));
        }else if(searchVo.getViewType().equals("opt3")){
            return loginsaver.loginTime.gt(now.minusDays(1));
        }
        return null;
    }


    private OrderSpecifier<?> orderType(String viewType){
        if (viewType.equals("opt2")){
            return ((ComparableExpressionBase<Long>) logintodaycnt).desc();
        }
        if (viewType.equals("opt3")){
            return ((ComparableExpressionBase<Long>) logintodaycnt).desc();
        }
        if (viewType.equals("opt4")){
            return member.lastTime.desc();
        }
        if (viewType.equals("opt5")){
            return member.useragerange.desc();
        }
        if (viewType.equals("opt6")){
            return member.useragerange.asc();
        }
        if (viewType.equals("opt7")){
            return member.appversion.desc();
        }
        if (viewType.equals("opt8")){
            return member.adminFlag.desc();
        }
        if (viewType.equals("opt9")){
            return member.cretTime.asc();
        }
        if (viewType.equals("opt10")){
            return member.cretTime.desc();
        }
        return member.cretTime.desc();
    }

    private BooleanExpression searchStaffEq(SearchVo searchVo){
        if(searchVo.getInq() != ""){
            if(searchVo.getCon().equals("all")){
                return staff.id.contains(searchVo.getInq())
                        .or(staff.name.contains(searchVo.getInq()));
            } else if(searchVo.getCon().equals("name")){
                return staff.name.contains(searchVo.getInq());
            } else if(searchVo.getCon().equals("id")){
                return staff.id.contains(searchVo.getInq());
            }
        }
        return null;
    }

}

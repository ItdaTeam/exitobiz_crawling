package co.kr.exitobiz.Mappers.Cms;


import co.kr.exitobiz.Entity.Agency;
import co.kr.exitobiz.Entity.Support;
import co.kr.exitobiz.Vo.Cms.SearchVo;
import co.kr.exitobiz.Vo.Cms.SupportVo;
import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DateTemplate;
import com.querydsl.core.types.dsl.DateTimeTemplate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import static co.kr.exitobiz.Entity.QLoccode.loccode1;
import static co.kr.exitobiz.Entity.QPopup.popup;
import static co.kr.exitobiz.Entity.QSupport.support;


@Repository
@RequiredArgsConstructor
public class SupportRepository {

    private final EntityManager em;

    private final JPAQueryFactory jpaQueryFactory;

    //서브쿼리 alias order by 하기위해 선언
    Path<Long> logintodaycnt = Expressions.numberPath(Long.class, "logintodaycnt");

    //지원사업 관리 화면 초기 정보
    public SupportVo getSupportInfo(){
        return jpaQueryFactory
                .select(Projections.fields(SupportVo.class,
                        support.siIdx.count().as("totalSupportCnt"),
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(support.siIdx.count())
                                        .from(support)
                                        .where(support.siActiveYn.eq("Y"))
                                ,"activeYCnt"
                        ),
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(support.siIdx.count())
                                        .from(support)
                                        .where(support.siActiveYn.eq("N"))
                                ,"activeNCnt"
                        ),
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(support.viewCnt.sum())
                                        .from(support)
                                ,"allViewCnt"
                        )
                     )
                )
                .from(support)
                .fetchOne();
    }

    //조회수 TOP지역
    public String getViewCntLoc(){
        return jpaQueryFactory
                .select(loccode1.locname)
                .from(loccode1)
                .leftJoin(support)
                .on(loccode1.loccode.eq(support.locCode))
                .fetchJoin()
                .groupBy(loccode1.locname)
                .orderBy(support.viewCnt.sum().desc())
                .fetchFirst();
    }

    //공고개수 TOP지역
    public String getSupportCntLoc(){
        return jpaQueryFactory
                .select(loccode1.locname)
                .from(loccode1)
                .leftJoin(support)
                .on(loccode1.loccode.eq(support.locCode))
                .fetchJoin()
                .groupBy(loccode1.locname)
                .orderBy(support.siIdx.count().desc())
                .fetchFirst();
    }

    DateTimeTemplate formattedDate = Expressions.dateTimeTemplate(LocalDateTime.class,"TO_CHAR({0}, {1})",support.siCretDt, "YYYYMMDD");

    //지원사업 검색
    public List<Support> searchSupport(SearchVo searchVo) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("############" + support.siCretDt);

        return jpaQueryFactory
                .select(support)
                .from(support)
                .where(
                        searchSupportEq(searchVo),
                        searchActiveEq(searchVo)

                )
                .where(support.siCretDt.goe(formatter.parse(searchVo.getFrom())))
                .where(support.siCretDt.loe(formatter.parse(searchVo.getTo())))
                .orderBy(orderType(searchVo.getViewType()))
                .fetch();
    }

    public Support findBySiIdx(Long siIdx) {return em.find(Support.class, siIdx);}

    //아이디로 검색
    public Boolean findById(Long id){
        return jpaQueryFactory
                .from(support)
                .where(support.siIdx.eq(id))
                .select(support.siIdx)
                .fetchFirst() != null;
    }

    //저장
    public void save(Support support){
        em.persist(support);
    }

    //업데이트
    public void update(SupportVo vo) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();

        jpaQueryFactory
                .update(support)
                .set(support.targetName, vo.getTargetName())
                .set(support.targetCatName, vo.getTargetCatName())
                .set(support.targetCostValue, vo.getTargetCostValue())
                .set(support.locCode, vo.getLocCode())
                .set(support.siTitle, vo.getSiTitle())
                .set(support.mobileUrl, vo.getMobileUrl())
                .set(support.pcUrl, vo.getPcUrl())
                .set(support.siActiveYn, vo.getSiActiveYn())
                .set(support.siEndDt, LocalDate.parse(vo.getSiEndDt(), DateTimeFormatter.ISO_DATE).atStartOfDay())
                .set(support.siCretDt, formatter.parse(vo.getSiCretDt()))
                .set(support.targetCatCd, vo.getTargetCatCd())
                .set(support.businessCtg, vo.getBusinessCtg())
                .set(support.techCtg, vo.getTechCtg())
                .set(support.businessType, vo.getBusinessType())
                .set(support.companyType, vo.getCompanyType())
                .set(support.startPeriod, vo.getStartPeriod())
                .set(support.updtDt, formatter.parse(vo.getUpdtDt()))
                //.set(support.siCretDt, LocalDate.parse(vo.getSiCretDt(), DateTimeFormatter.ISO_DATE).atStartOfDay())
                .where(support.siIdx.eq(vo.getSiIdx()))
                .execute();
    }

    private BooleanExpression searchSupportEq(SearchVo searchVo){
        if(searchVo.getInq() != ""){
            if(searchVo.getCon().equals("all")){
                return searchVo.getListInq() != null ?
                        Expressions.anyOf(
                                searchVo.getListInq()
                                        .stream()
                                        .map(this::isFilteredAll)
                                        .toArray(BooleanExpression[]::new)) : null;
            } else if(searchVo.getCon().equals("target")){
                return searchVo.getListInq() != null ?
                        Expressions.anyOf(
                                searchVo.getListInq()
                                        .stream()
                                        .map(this::isFilteredTarget)
                                        .toArray(BooleanExpression[]::new)) : null;
            } else if(searchVo.getCon().equals("title")){
                return searchVo.getListInq() != null ?
                        Expressions.anyOf(
                                searchVo.getListInq()
                                        .stream()
                                        .map(this::isFilteredTitle)
                                        .toArray(BooleanExpression[]::new)) : null;
            }
        }
        return null;
    }

    private BooleanExpression searchActiveEq(SearchVo searchVo){
        if(searchVo.getViewType().equals("opt2")){
            return support.siActiveYn.eq("N");
        }else if(searchVo.getViewType().equals("opt3")){
            return support.siActiveYn.eq("Y");
        }else if(searchVo.getViewType().equals("opt4")){
            return support.siEndDt.gt(LocalDateTime.now());
        }
        return null;
    }

    private BooleanExpression isFilteredAll(String all){
        return support.targetName.contains(all)
                .or(support.siTitle.contains(all));
    }

    private BooleanExpression isFilteredTarget(String target){
        return support.targetName.contains(target);
    }

    private BooleanExpression isFilteredTitle(String title){
        return support.siTitle.contains(title);
    }

    private OrderSpecifier<?> orderType(String viewType){
        if (viewType.equals("opt4")){
            return support.siEndDt.asc();
        }
        if (viewType.equals("opt5")){
            return support.viewCnt.desc();
        }
        if (viewType.equals("opt6")){
            return support.targetCostValue.desc().nullsLast();
        }
        return support.siIdx.desc();
    }



}

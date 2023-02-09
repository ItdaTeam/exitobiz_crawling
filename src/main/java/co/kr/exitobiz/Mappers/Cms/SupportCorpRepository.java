package co.kr.exitobiz.Mappers.Cms;


import co.kr.exitobiz.Entity.Support;
import co.kr.exitobiz.Entity.SupportCorp;
import co.kr.exitobiz.Vo.Cms.SearchVo;
import co.kr.exitobiz.Vo.Cms.SupportCorpVo;
import co.kr.exitobiz.Vo.Cms.SupportVo;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DateTimeTemplate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static co.kr.exitobiz.Entity.QLoccode.loccode1;
import static co.kr.exitobiz.Entity.QSupportCorp.supportCorp;

@Repository
@RequiredArgsConstructor
public class SupportCorpRepository {

    private final EntityManager em;

    private final JPAQueryFactory jpaQueryFactory;

    //서브쿼리 alias order by 하기위해 선언
    Path<Long> logintodaycnt = Expressions.numberPath(Long.class, "logintodaycnt");

    //지원사업 관리 화면 초기 정보
    public SupportCorpVo getSupportInfo(){
        return jpaQueryFactory
                .select(Projections.fields(SupportCorpVo.class,
                        supportCorp.siIdx.count().as("totalSupportCnt"),
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(supportCorp.siIdx.count())
                                        .from(supportCorp)
                                        .where(supportCorp.siActiveYn.eq("Y"))
                                ,"activeYCnt"
                        ),
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(supportCorp.siIdx.count())
                                        .from(supportCorp)
                                        .where(supportCorp.siActiveYn.eq("N"))
                                ,"activeNCnt"
                        ),
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(supportCorp.viewCnt.sum())
                                        .from(supportCorp)
                                ,"allViewCnt"
                        )
                     )
                )
                .from(supportCorp)
                .fetchOne();
    }

    DateTimeTemplate formattedDate = Expressions.dateTimeTemplate(LocalDateTime.class,"TO_CHAR({0}, {1})",supportCorp.siCretDt, "YYYYMMDD");

    //지원사업 검색
    public List<SupportCorp> searchSupport(SearchVo searchVo) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return jpaQueryFactory
                .select(supportCorp)
                .from(supportCorp)
                .where(
                        searchSupportEq(searchVo),
                        searchActiveEq(searchVo)

                )
                .where(supportCorp.siCretDt.goe(formatter.parse(searchVo.getFrom())))
                .where(supportCorp.siCretDt.loe(formatter.parse(searchVo.getTo())))
                .orderBy(orderType(searchVo.getViewType()))
                .fetch();
    }

    public SupportCorp findBySiIdx(Long siIdx) {return em.find(SupportCorp.class, siIdx);}

    //아이디로 검색
    public Boolean findById(Long id){
        return jpaQueryFactory
                .from(supportCorp)
                .where(supportCorp.siIdx.eq(id))
                .select(supportCorp.siIdx)
                .fetchFirst() != null;
    }

    //저장
    public void save(SupportCorp support){
        em.persist(support);
    }

    //업데이트
    public void update(SupportCorpVo vo) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        jpaQueryFactory
                .update(supportCorp)
                .set(supportCorp.targetName, vo.getTargetName())
                .set(supportCorp.targetCatName, vo.getTargetCatName())
                .set(supportCorp.targetCostValue, vo.getTargetCostValue())
                .set(supportCorp.locCode, vo.getLocCode())
                .set(supportCorp.siTitle, vo.getSiTitle())
                .set(supportCorp.mobileUrl, vo.getMobileUrl())
                .set(supportCorp.pcUrl, vo.getPcUrl())
                .set(supportCorp.siActiveYn, vo.getSiActiveYn())
                .set(supportCorp.siEndDt, LocalDate.parse(vo.getSiEndDt(), DateTimeFormatter.ISO_DATE).atStartOfDay())
                .set(supportCorp.siCretDt, formatter.parse(vo.getSiCretDt()))
                .set(supportCorp.targetCatCd, vo.getTargetCatCd())
                .set(supportCorp.businessCtg, vo.getBusinessCtg())
                .set(supportCorp.techCtg, vo.getTechCtg())
                .set(supportCorp.businessType, vo.getBusinessType())
                .set(supportCorp.companyType, vo.getCompanyType())
                .set(supportCorp.startPeriod, vo.getStartPeriod())
                //.set(support.siCretDt, LocalDate.parse(vo.getSiCretDt(), DateTimeFormatter.ISO_DATE).atStartOfDay())
                .where(supportCorp.siIdx.eq(vo.getSiIdx()))
                .execute();
    }

    //삭제
    public void delete(SupportCorpVo vo) throws ParseException{
        jpaQueryFactory.delete(supportCorp).where(supportCorp.siIdx.eq(vo.getSiIdx())).execute();
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
            return supportCorp.siActiveYn.eq("N");
        }else if(searchVo.getViewType().equals("opt3")){
            return supportCorp.siActiveYn.eq("Y");
        }else if(searchVo.getViewType().equals("opt4")){
            return supportCorp.siEndDt.gt(LocalDateTime.now());
        }
        return null;
    }

    private BooleanExpression isFilteredAll(String all){
        return supportCorp.targetName.contains(all)
                .or(supportCorp.siTitle.contains(all));
    }

    private BooleanExpression isFilteredTarget(String target){
        return supportCorp.targetName.contains(target);
    }

    private BooleanExpression isFilteredTitle(String title){
        return supportCorp.siTitle.contains(title);
    }

    private OrderSpecifier<?> orderType(String viewType){
        if (viewType.equals("opt4")){
            return supportCorp.siEndDt.asc();
        }
        if (viewType.equals("opt5")){
            return supportCorp.viewCnt.desc();
        }
        if (viewType.equals("opt6")){
            return supportCorp.targetCostValue.desc().nullsLast();
        }
        return supportCorp.siIdx.desc();
    }



}

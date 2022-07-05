package co.kr.exitobiz.Mappers.Cms;

import co.kr.exitobiz.Entity.Agency;
import co.kr.exitobiz.Entity.QAgency;
import co.kr.exitobiz.Entity.QSupport;
import co.kr.exitobiz.Vo.Cms.AgencyVo;
import co.kr.exitobiz.Vo.Cms.SearchVo;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static co.kr.exitobiz.Entity.QAgency.agency;
import static co.kr.exitobiz.Entity.QMember.member;
import static co.kr.exitobiz.Entity.QSupport.support;
import static co.kr.exitobiz.Entity.QSupportEx.supportEx;
import static com.querydsl.core.types.dsl.Expressions.stringPath;

@Repository
@RequiredArgsConstructor
public class AgencyRepository {
    private final JPAQueryFactory jpaQueryFactory;

    private final EntityManager em;

    public AgencyVo agencyData(){
        return jpaQueryFactory.select(
                Projections.fields(AgencyVo.class,
                        agency.id.count().as("totalAgencyCnt"),
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(agency.id.count())
                                        .from(agency)
                                        .where(agency.activeYn.eq("Y"))
                                ,"avtiveAgencyCnt"
                        ),
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(support.siIdx.count())
                                        .from(support)
                                        .where(support.siActiveYn.eq("Y"))
                                ,"allCrawlingCnt"
                        ),
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(agency.id.count())
                                        .from(agency)
                                        .where(agency.errorYn.eq("Y"))
                                ,"errorCnt"
                        ),
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(agency.lastCrawlingDt.max())
                                        .from(agency)
                                ,"lastCrawlingDt"
                        )
                        ))
                .from(agency)
                .fetchOne();
    }

    //검색 그리드데이터 전송
    public List<AgencyVo> searchAgency(SearchVo searchVo) {
        return jpaQueryFactory.select(
                Projections.fields(AgencyVo.class,
                        agency.id,
                        agency.title,
                        agency.url,
                        agency.locCode,
                        agency.activeYn,
                        agency.errorYn,
                        agency.createdAt,
                        agency.lastCrawlingDt,
                        agency.remark,
                        supportEx.crawlingcnt.coalesce((long)0).as("crawlingcnt")
                        ))
                .from(agency)
                .leftJoin(supportEx)
                .on(agency.title.eq(supportEx.targetName))
                .where(searchAgencyEq(searchVo))
                .where(searchActiveEq(searchVo))
                .fetchJoin()
                .orderBy(agency.id.asc())
                .fetch();
    }

    public Agency findById(Long id) {return em.find(Agency.class, id);}

    private BooleanExpression searchAgencyEq(SearchVo searchVo){
        if(searchVo.getInq() != ""){
            if(searchVo.getCon().equals("all")){
                return searchVo.getListInq() != null ?
                        Expressions.anyOf(
                                searchVo.getListInq()
                                        .stream()
                                        .map(this::isFilteredAll)
                                        .toArray(com.querydsl.core.types.dsl.BooleanExpression[]::new)) : null;
            } else if(searchVo.getCon().equals("agency")){
                return searchVo.getListInq() != null ?
                        Expressions.anyOf(
                                searchVo.getListInq()
                                        .stream()
                                        .map(this::isFilteredAgency)
                                        .toArray(com.querydsl.core.types.dsl.BooleanExpression[]::new)) : null;
            } else if(searchVo.getCon().equals("url")){
                return searchVo.getListInq() != null ?
                        Expressions.anyOf(
                                searchVo.getListInq()
                                        .stream()
                                        .map(this::isFilteredUrl)
                                        .toArray(com.querydsl.core.types.dsl.BooleanExpression[]::new)) : null;
            } else if(searchVo.getCon().equals("location")){
                return searchVo.getListInq() != null ?
                        Expressions.anyOf(
                                searchVo.getListInq()
                                        .stream()
                                        .map(this::isFilteredLocation)
                                        .toArray(com.querydsl.core.types.dsl.BooleanExpression[]::new)) : null;
            } else if (searchVo.getCon().equals("remark")){
                return searchVo.getListInq() != null ?
                        Expressions.anyOf(searchVo.
                                getListInq()
                                .stream()
                                .map(this::isFilteredRemark)
                                .toArray(com.querydsl.core.types.dsl.BooleanExpression[]::new)) : null;
            }
        }
        return null;
    }

    private BooleanExpression isFilteredAll(String all){
        return agency.title.contains(all)
                .or(agency.url.contains(all))
                .or(agency.remark.contains(all));
    }

    private BooleanExpression isFilteredAgency(String agency){
        return QAgency.agency.title.contains(agency);
    }

    private BooleanExpression isFilteredUrl(String url){
        return agency.url.contains(url);
    }

    private BooleanExpression isFilteredLocation(String location){
        return agency.locCode.contains(location);
    }

    private BooleanExpression isFilteredRemark(String remark){
        return agency.remark.contains(remark);
    }

    private BooleanExpression searchActiveEq(SearchVo searchVo){
        if(searchVo.getViewType().equals("active_y")) {
            return agency.activeYn.eq("Y");
        }else if(searchVo.getViewType().equals("active_n")){
            return agency.activeYn.eq("N");
        }
        return null;
    }
}

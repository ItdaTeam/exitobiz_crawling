package co.kr.exitobiz.Mappers.Cms;


import co.kr.exitobiz.Entity.Crawling;
import co.kr.exitobiz.Vo.Cms.CrawlingVo;
import co.kr.exitobiz.Vo.Cms.SearchVo;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DateTimeTemplate;
import com.querydsl.core.types.dsl.Expressions;
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

import static co.kr.exitobiz.Entity.QCrawling.crawling;

@Repository
@RequiredArgsConstructor
public class CrawlingRepository {

    private final EntityManager em;

    private final JPAQueryFactory jpaQueryFactory;

    //지원사업 검색
    public List<Crawling> searchCrawling(SearchVo searchVo) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        return jpaQueryFactory
                .select(crawling)
                .from(crawling)
                .where(
                        searchCrawlingEq(searchVo),
                        searchActiveEq(searchVo)

                )
                .where(crawling.siCretDt.goe(formatter.parse(searchVo.getFrom())))
                .where(crawling.siCretDt.loe(formatter.parse(searchVo.getTo())))
                .orderBy(orderType(searchVo.getViewType()))
                .fetch();
    }

    public Crawling findBySiIdx(Long siIdx) {return em.find(Crawling.class, siIdx);}

    //아이디로 검색
    public Boolean findById(Long id){
        return jpaQueryFactory
                .from(crawling)
                .where(crawling.siIdx.eq(id))
                .select(crawling.siIdx)
                .fetchFirst() != null;
    }

    private BooleanExpression searchCrawlingEq(SearchVo searchVo){
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
            }else if(searchVo.getCon().equals("index")){
                BooleanExpression booleanExpression = searchVo.getListInq() != null ?
                        Expressions.anyOf(
                                searchVo.getListInq()
                                        .stream()
                                        .map((String index) -> isFilteredIndex(Long.parseLong(index.replaceAll(",",""))))
                                        .toArray(BooleanExpression[]::new)) : null;
                return booleanExpression;
            }
        }
        return null;
    }

    private BooleanExpression searchActiveEq(SearchVo searchVo){
        if(searchVo.getViewType().equals("opt2")){
            return crawling.siActiveYn.eq("N");
        }else if(searchVo.getViewType().equals("opt3")){
            return crawling.siActiveYn.eq("Y");
        }else if(searchVo.getViewType().equals("opt4")){
            return crawling.siEndDt.gt(LocalDateTime.now());
        }
        return null;
    }

    private BooleanExpression isFilteredAll(String all){
        return crawling.targetName.contains(all)
                .or(crawling.siTitle.contains(all));
    }

    private BooleanExpression isFilteredIndex(Long index){
        return crawling.siIdx.eq(index);
    }

    private BooleanExpression isFilteredTarget(String target){
        return crawling.targetName.contains(target);
    }

    private BooleanExpression isFilteredTitle(String title){
        return crawling.siTitle.contains(title);
    }

    private OrderSpecifier<?> orderType(String viewType){
        if (viewType.equals("opt4")){
            return crawling.siEndDt.asc();
        }
        if (viewType.equals("opt5")){
            return crawling.viewCnt.desc();
        }
        if (viewType.equals("opt6")){
            return crawling.targetCostValue.desc().nullsLast();
        }
        return crawling.siIdx.desc();
    }



}

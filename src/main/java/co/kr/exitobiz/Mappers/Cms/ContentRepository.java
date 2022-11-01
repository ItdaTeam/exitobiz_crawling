package co.kr.exitobiz.Mappers.Cms;

import co.kr.exitobiz.Entity.Content;
import co.kr.exitobiz.Entity.Notice;
import co.kr.exitobiz.Entity.Popup;
import co.kr.exitobiz.Entity.QAgency;
import co.kr.exitobiz.Vo.Cms.ContentVo;
import co.kr.exitobiz.Vo.Cms.NoticeVo;
import co.kr.exitobiz.Vo.Cms.PopupVo;
import co.kr.exitobiz.Vo.Cms.SearchVo;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static co.kr.exitobiz.Entity.QAgency.agency;
import static co.kr.exitobiz.Entity.QNotice.notice;
import static co.kr.exitobiz.Entity.QContent.content1;
import static co.kr.exitobiz.Entity.QPopup.*;

@Repository
@RequiredArgsConstructor
public class ContentRepository {
    private final JPAQueryFactory jpaQueryFactory;

    private final EntityManager em;

    public void createPop(Popup popup){
        em.persist(popup);
    }

    public void createNotice(Notice notice){ em.persist(notice);}

    public void createContent(Content content){ em.persist(content);}

    public List<Notice> getNotice(SearchVo searchVo) throws ParseException {
        BooleanBuilder builder = new BooleanBuilder();

        // 기한조건
        if(searchVo.getFrom() != null) {
            builder.and(Expressions.dateTimeTemplate(Date.class,"{0}",notice.createAt, "YYYY-MM-DD")
                    .goe(Expressions.dateTimeTemplate(Date.class,"TO_DATE({0}, {1})",searchVo.getFrom(), "YYYY-MM-DD")));
        }

        if(searchVo.getTo() != null) {
            builder.and(Expressions.dateTimeTemplate(Date.class,"{0}",notice.createAt, "YYYY-MM-DD")
                    .loe(Expressions.dateTimeTemplate(Date.class,"TO_DATE({0}, {1})",searchVo.getTo(), "YYYY-MM-DD")));
        }

        return jpaQueryFactory
                .selectFrom(notice)
                .where(builder)
                .orderBy(notice.createAt.desc())
                .fetch();
    }

    public List<Content> getContent(SearchVo searchVo) throws ParseException {

        return jpaQueryFactory
                .selectFrom(content1)
                                .where(Expressions.dateTimeTemplate(Date.class,"{0}",content1.cretDt, "YYYY-MM-DD")
                        .goe(Expressions.dateTimeTemplate(Date.class,"TO_DATE({0}, {1})",searchVo.getFrom(), "YYYY-MM-DD")))
                .where(Expressions.dateTimeTemplate(Date.class,"{0}",content1.cretDt, "YYYY-MM-DD")
                        .loe(Expressions.dateTimeTemplate(Date.class,"TO_DATE({0}, {1})",searchVo.getTo(), "YYYY-MM-DD")))
                .where(searchContentEq(searchVo))
                .orderBy(content1.cretDt.desc())
                .fetch();
    }

    private BooleanExpression searchContentEq(SearchVo searchVo){
        //기간조건
        //검색조건
        if(searchVo.getInq() != ""){
            if(searchVo.getCon().equals("all")){
                return searchVo.getListInq() != null ?
                        Expressions.anyOf(
                                searchVo.getListInq()
                                        .stream()
                                        .map(this::isFilteredAll)
                                        .toArray(com.querydsl.core.types.dsl.BooleanExpression[]::new)) : null;
            } else if(searchVo.getCon().equals("title")){
                return searchVo.getListInq() != null ?
                        Expressions.anyOf(
                                searchVo.getListInq()
                                        .stream()
                                        .map(this::isFilteredTitle)
                                        .toArray(com.querydsl.core.types.dsl.BooleanExpression[]::new)) : null;
            }
        }
        return null;
    }

    private BooleanExpression isFilteredAll(String all){
        return content1.title.contains(all);
    }

    private BooleanExpression isFilteredTitle(String title){
        return content1.title.contains(title);
    }

    public void deleteNotice(Notice notice){
        em.remove(notice);
    }

    public List<Popup> getData(SearchVo searchVo) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        return jpaQueryFactory
                .selectFrom(popup)
                .where(popup.createdAt.goe(formatter.parse(searchVo.getFrom())))
                .where(popup.createdAt.loe(formatter.parse(searchVo.getTo())))
                .orderBy(popup.createdAt.desc())
                .fetch();
    }

    public Popup findById(Integer id){
        return em.find(Popup.class, id);
    }

    public Content findContentById(Integer id){return em.find(Content.class, id);}

    public Notice findNoticeById(Integer id){ return em.find(Notice.class, id);}

    public ContentVo getContentInfo(){
        return jpaQueryFactory
                .select(Projections.fields(ContentVo.class,
                        content1.id.count().as("inCnt"),
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(content1.id.count())
                                        .from(content1)
                                        .where(content1.type.eq("외부콘텐츠"))
                                ,"outCnt"
                        )))
                .from(content1)
                .where(content1.type.eq("자체제작"))
                .fetchOne();
    }

    public NoticeVo getNoticeInfo(){
        return jpaQueryFactory
                .select(Projections.fields(NoticeVo.class,
                        notice.id.count().as("totalCnt"),
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(notice.id.count())
                                        .from(notice)
                                        .where(notice.activeYn.eq("Y"))
                                ,"mustCnt")))
                .from(notice)
                .fetchOne();
    }

    public List<NoticeVo> getMustNotice(){
        return jpaQueryFactory
                .select(Projections.fields(NoticeVo.class,
                        notice.id.as("id"),
                        notice.title.as("title"),
                        notice.contents.as("contents"),
                        notice.createAt.as("createAt")
                        ))
                .from(notice)
                .where(notice.mustYn.eq("Y"))
                .where(notice.activeYn.eq("Y"))
                .orderBy(notice.createAt.desc())
                .fetch();
    }

    public List<NoticeVo> getFirstNotice(){
        return jpaQueryFactory
                .select(Projections.fields(NoticeVo.class,
                        notice.id.as("id"),
                        notice.title.as("title"),
                        notice.contents.as("contents"),
                        notice.createAt.as("createAt")
                ))
                .from(notice)
                .where(notice.mustYn.eq("N"))
                .where(notice.activeYn.eq("Y"))
                .orderBy(notice.createAt.desc())
                .fetch();
    }

    public List<NoticeVo> getNoticeList(HashMap<String, String> params){
        return jpaQueryFactory
                .select(Projections.fields(NoticeVo.class,
                        notice.id.as("id"),
                        notice.title.as("title"),
                        notice.contents.as("contents"),
                        notice.createAt.as("createAt")
                ))
                .from(notice)
                .where(notice.mustYn.eq("N"))
                .where(notice.activeYn.eq("Y"))
                .where(notice.id.goe(Integer.parseInt(params.get("lastNo")) - 1))
                .where(notice.id.lt(Integer.parseInt(params.get("lastNo")) - 1 -20))
                .orderBy(notice.createAt.desc())
                .fetch();
    }

    public PopupVo getInfo(){
        return jpaQueryFactory
                .select(Projections.fields(PopupVo.class,
                        popup.id.count().as("totalCnt"),
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(popup.id.count())
                                        .from(popup)
                                        .where(popup.active.eq("Y"))
                                ,"activeCnt")))
                .from(popup)
                .fetchOne();
    }
}

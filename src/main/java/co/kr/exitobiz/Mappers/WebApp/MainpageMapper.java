package co.kr.exitobiz.Mappers.WebApp;

import co.kr.exitobiz.Vo.Mobile.CommunityVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface MainpageMapper {
    public List<HashMap> getSearchHotKeyWord(); // 인기 키워드 리스트

    public List<HashMap> getMyRecentKeyword(HashMap<String, Object> params); // 나의 최근 검색어 리스트

    public void delMyRecentKeyword(HashMap<String, Object> params); // 나의 최근 검색어 단일 삭제

    public void delMyAllRecentKeyword(HashMap<String, Object> params); // 나의 최근 검색어 전체 삭제

    public void insertTimeLine(HashMap<String, Object> params); // 타임라인 추가(공통)

    public List<HashMap> getBannerList(HashMap<String, Object> params); // 배너 웹 리스트

    public List<HashMap> getTotalCount(); // 누적갯수(누적 지원사업 개수, 이번주 지원사업, 정보 제공기관, 누적 가입 기업) 리스트

    public int getLastWeekCnt(); // 누적갯수(지난주 월-금)

    public HashMap<String, Object> getKeyword(HashMap<String, Object> params); // 키워드 정기배송 리스트 (","로 구분)

    public void insertKeyword(HashMap<String, Object> params); // 키워드 추가

    public void delKeyword(HashMap<String, Object> params); // 키워드 단일 삭제

    public void delAllKeyword(HashMap<String, Object> params); // 키워드 전체 삭제

    public List<HashMap> getPopularList(HashMap<String, Object> params); // 엑시토 (실시간 인기 | 찜 인기 ) 리스트

    public List<HashMap> getPushBookList(HashMap<String, Object> params); // 엑시토 추천 리스트

    void upViewCnt(HashMap<String, Object> params);

    public HashMap<String, Object> getSupportableCnt(); // 지원가능 갯수 조회

    void insertEmailDeliver(HashMap<String, Object> params); // 이메일 정기배송 추가

    public HashMap<String, Object> getEmailDeliver(HashMap<String, Object> params); // 이메일 정기배송 조회

    public List<HashMap> getMyEmailDeliver(HashMap<String, Object> params); // 이메일 발송 이력 조회

    public List<HashMap> getEmailDeliverList(); // 발송할 이메일 리스트 조회

    void insertEmailDeliverHst(HashMap<String, Object> params); // 이메일 발송 이력

    void insertEmailContent(HashMap<String, Object> params);    //이메일 내용 저장

}

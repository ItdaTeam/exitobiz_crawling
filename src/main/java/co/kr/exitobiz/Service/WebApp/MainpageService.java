package co.kr.exitobiz.Service.WebApp;

import co.kr.exitobiz.Vo.Mobile.CommunityVo;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

public interface MainpageService {
    List<HashMap> getSearchHotKeyWord() throws ParseException; // 인기 키워드 리스트

    List<HashMap> getMyRecentKeyword(HashMap<String, Object> params) throws ParseException; // 나의 최근 검색어 리스트

    void delMyRecentKeyword(HashMap<String, Object> params) throws ParseException; // 나의 최근 검색어 단일 삭제

    void delMyAllRecentKeyword(HashMap<String, Object> params) throws ParseException;// 나의 최근 검색어 전체 삭제

    void insertTimeLine(HashMap<String, Object> params) throws ParseException; // 타임라인 추가

    List<HashMap> getBannerList() throws ParseException; // 배너리스트

    List<HashMap> getTotalCount() throws ParseException; // 누적갯수(누적 지원사업 개수, 이번주 지원사업, 정보 제공기관, 누적 가입 기업) 리스트

    HashMap<String, Object> getKeyword(HashMap<String, Object> params) throws ParseException; // 키워드 정기배송 리스트 (","로 구분)

    void insertKeyword(HashMap<String, Object> params) throws ParseException; // 키워드 추가

    void delKeyword(HashMap<String, Object> params) throws ParseException; // 키워드 단일 삭제

    void delAllKeyword(HashMap<String, Object> params) throws ParseException; // 키워드 전체 삭제

    List<HashMap> getPopularList(HashMap<String, Object> params); // 엑시토 (실시간 인기 | 찜 인기 ) 리스트

    List<HashMap> getPushBookList(HashMap<String, Object> params) throws ParseException; // 엑시토 추천 리스트
}

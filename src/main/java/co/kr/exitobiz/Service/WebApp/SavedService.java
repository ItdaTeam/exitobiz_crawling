package co.kr.exitobiz.Service.WebApp;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

public interface SavedService {
    List<HashMap> getRecentlyMySavedBook(HashMap<String, Object> params) throws ParseException; // 최근 본 지원사업 리스트

    List<HashMap> getMySavedBook(HashMap<String, Object> params) throws ParseException; // 찜한 사업, 지원한 사업 리스트

    List<HashMap> getCatList(HashMap<String, Object> params) throws ParseException; // 찜 분류 리스트

    void insertSavedMyBook(HashMap<String, Object> params) throws ParseException; // 찜 추가(처음 찜한 사업)

    void updateSavedMyBook(HashMap<String, Object> params) throws ParseException; // 찜 flag 변경

    void updateReqSavedMyBook(HashMap<String, Object> params) throws ParseException; // 지원 flag 변경

    void updateDoneSavedMyBook(HashMap<String, Object> params) throws ParseException; // 선정 flag 변경

    public HashMap getUserNeed(HashMap<String, Object> params); // 우리 회사에서 필요한 것 조회

    public void insertUserNeed(HashMap<String, Object> params); // 우리 회사에서 필요한 것 추가

    public void updateUserNeed(HashMap<String, Object> params); // 우리 회사에서 필요한 것 수정

    public int hasDeliverEmail(HashMap<String, Object> params); // 이메일 정기배송 이메일 주소 확인

    public void insertDeliverEmail(HashMap<String, Object> params); //이메일 정기 배송 이메일 추가
}

package co.kr.exitobiz.Service.WebApp;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

public interface CorpSavedService {

    List<HashMap> getMySavedBook(HashMap<String, Object> params) throws ParseException; // 찜한 사업, 지원한 사업 리스트

    List<HashMap> getCatList(HashMap<String, Object> params) throws ParseException; // 찜 분류 리스트

    void insertSavedMyBook(HashMap<String, Object> params) throws ParseException; // 찜 추가(처음 찜한 사업)

    void updateSavedMyBook(HashMap<String, Object> params) throws ParseException; // 찜 flag 변경

    public HashMap checkSavedFlag(HashMap<String, Object> params) throws ParseException; // 변경 값 리턴
}

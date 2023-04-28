package co.kr.exitobiz.Service.Cms;

import co.kr.exitobiz.Entity.Content;
import co.kr.exitobiz.Entity.Notice;
import co.kr.exitobiz.Entity.Popup;
import co.kr.exitobiz.Vo.Cms.ContentVo;
import co.kr.exitobiz.Vo.Cms.NoticeVo;
import co.kr.exitobiz.Vo.Cms.PopupVo;
import co.kr.exitobiz.Vo.Cms.SearchVo;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

public interface ContentService {
    List<Popup> getData(SearchVo searchVo) throws ParseException;

    List<Notice> getNotice(SearchVo searchVo) throws ParseException;

//    List<Content> getContent(SearchVo searchVo) throws ParseException;

    ContentVo getContentInfo();

    String getContentPage(HashMap<String, String> params);

    Notice getNoticePage(HashMap<String, String> params);

    List<NoticeVo> getMustNotice();

    List<NoticeVo> getFirstNotice();

    List<NoticeVo> getNoticeList(HashMap<String, String> params);

    NoticeVo getNoticeInfo();

    PopupVo getInfo();

    void editContent(ContentVo contentVo);

    void createNotice(NoticeVo noticeVo) throws ParseException;

    void editNotice(NoticeVo noticeVo) throws ParseException;

    void deleteNotice(NoticeVo noticeVo);

    void createPopup(PopupVo popupVo);

    void editPopup(PopupVo popupVo) throws ParseException;

    void createContent(ContentVo contentVo);

    void saveContent(HashMap<String, Object> param);

    List<HashMap> getContentList(HashMap<String, Object> params);

    void updateContent(List<HashMap<String, Object>> params);

    List<HashMap> getTopInfo(HashMap<String, Object> params);

    HashMap<String, Object> getContent(HashMap<String, Object> params);

    void deleteContent(HashMap<String, Object> params);
}

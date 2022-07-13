package co.kr.exitobiz.Service.Cms.impl;

import co.kr.exitobiz.Entity.Content;
import co.kr.exitobiz.Entity.Notice;
import co.kr.exitobiz.Entity.Popup;
import co.kr.exitobiz.Mappers.Cms.ContentRepository;
import co.kr.exitobiz.Service.Cms.ContentService;
import co.kr.exitobiz.Service.Mobile.FileService;
import co.kr.exitobiz.Util.Util;
import co.kr.exitobiz.Vo.Cms.ContentVo;
import co.kr.exitobiz.Vo.Cms.NoticeVo;
import co.kr.exitobiz.Vo.Cms.PopupVo;
import co.kr.exitobiz.Vo.Cms.SearchVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContentServiceImpl implements ContentService {

    private final ContentRepository contentRepository;

    private final FileService fileService;

    @Override
    @Transactional
    public void createPopup(PopupVo popupVo){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        StringBuilder ImgPath = new StringBuilder();
        try {
            if (popupVo.getUpFile() != null) {
                String fileName = fileService.fileNameGenerator(popupVo.getUpFile());
                HashMap<String, String> params = new HashMap<>();
                params.put("filePath", "/img/popup/");
                params.put("fileName", fileName);

                ImgPath.append("https://exitobiz.co.kr/img/popup/").append(fileName);
                try {
                    System.out.println("check");
                    fileService.uploadFile(popupVo.getUpFile(), params);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            Popup popup = new Popup();

            popup.setTitle(popupVo.getTitle());
            popup.setActive(popupVo.getActive());
            popup.setCategory(popupVo.getCategory());
            popup.setFromDt(formatter.parse(popupVo.getFrom()));
            popup.setToDt(formatter.parse(popupVo.getTo()));
            popup.setImage(ImgPath.toString());
            popup.setNoticeId(popupVo.getNoticeId());
            popup.setLink(popupVo.getLink());
            popup.setCreatedAt(timestamp);
            popup.setUpdatedAt(timestamp);

            contentRepository.createPop(popup);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Popup> getData(SearchVo searchVo) throws ParseException {
        return contentRepository.getData(searchVo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Notice> getNotice(SearchVo searchVo) throws ParseException {

        return contentRepository.getNotice(searchVo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Content> getContent(SearchVo searchVo) throws ParseException {
        if(searchVo.getInq() != null)
            searchVo.setListInq(Util.makeForeach(searchVo.getInq(), ","));

        return contentRepository.getContent(searchVo);
    }

    @Override
    @Transactional(readOnly = true)
    public String getContentPage(HashMap<String, String> params) {
        Content content = contentRepository.findContentById(Integer.parseInt(params.get("id")));

        return content.getContent();
    }

    @Override
    @Transactional(readOnly = true)
    public Notice getNoticePage(HashMap<String, String> params) {

        return contentRepository.findNoticeById(Integer.parseInt(params.get("id")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<NoticeVo> getMustNotice() {
        return contentRepository.getMustNotice();
    }

    @Override
    public List<NoticeVo> getFirstNotice() {
        return contentRepository.getFirstNotice();
    }

    @Override
    public List<NoticeVo> getNoticeList(HashMap<String, String> params) {
        return contentRepository.getNoticeList(params);
    }

    @Override
    @Transactional(readOnly = true)
    public ContentVo getContentInfo(){return contentRepository.getContentInfo();}

    @Override
    @Transactional(readOnly = true)
    public NoticeVo getNoticeInfo() {
        return contentRepository.getNoticeInfo();
    }

    @Override
    @Transactional(readOnly = true)
    public PopupVo getInfo() {
        return contentRepository.getInfo();
    }

    @Override
    @Transactional
    public void editContent(ContentVo contentVo) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        StringBuilder ImgPath = new StringBuilder();
        Content content = contentRepository.findContentById(contentVo.getId());

        try {
            if (contentVo.getUpFile() != null) {
                String fileName = fileService.fileNameGenerator(contentVo.getUpFile());
                HashMap<String, String> params = new HashMap<>();
                params.put("filePath", "/img/content/");
                params.put("fileName", fileName);

                ImgPath.append("https://exitobiz.co.kr/img/content/").append(fileName);
                try {
                    fileService.uploadFile(contentVo.getUpFile(), params);
                    content.setThumbnail(ImgPath.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            content.setType(contentVo.getType());
            content.setTitle(contentVo.getTitle());
            content.setUrl(contentVo.getUrl());
            content.setContent(contentVo.getContent());
            content.setRemark(contentVo.getRemark());
            content.setActiveYn(contentVo.getActiveYn());
            content.setUpdtDt(timestamp);
            content.setCost(contentVo.getCost());
            content.setDiscountCost(contentVo.getDiscountCost());
            content.setDiscountRate(contentVo.getDiscountRate());
            content.setSalesToDt(formatter.parse(contentVo.getSalesToDt()));
            content.setSalesFromDt(formatter.parse(contentVo.getSalesFromDt()));
            content.setPerson(contentVo.getPerson());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    @Transactional
    public void createNotice(NoticeVo noticeVo) throws ParseException {
        Notice notice = new Notice();

        notice.setTitle(noticeVo.getTitle());
        notice.setContents(noticeVo.getContents());
        notice.setMustYn(noticeVo.getMustYn());
        notice.setActiveYn(noticeVo.getActiveYn());
        notice.setCreateAt(LocalDate.now());
        notice.setUpdateAt(LocalDate.now());

        contentRepository.createNotice(notice);
    }

    @Override
    @Transactional
    public void editNotice(NoticeVo noticeVo) throws ParseException {
        Notice notice = contentRepository.findNoticeById(noticeVo.getId());

        notice.setTitle(noticeVo.getTitle());
        notice.setContents(noticeVo.getContents());
        notice.setActiveYn(noticeVo.getActiveYn());
        notice.setMustYn(noticeVo.getMustYn());
        notice.setUpdateAt(LocalDate.now());
    }

    @Override
    @Transactional
    public void deleteNotice(NoticeVo noticeVo) {
        Notice notice = contentRepository.findNoticeById(noticeVo.getId());

        contentRepository.deleteNotice(notice);
    }

    @Override
    @Transactional
    public void editPopup(PopupVo popupVo) throws ParseException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        StringBuilder ImgPath = new StringBuilder();

        Popup popup = contentRepository.findById(popupVo.getId());

        try {
            if (popupVo.getUpFile() != null) {
                String fileName = fileService.fileNameGenerator(popupVo.getUpFile());
                HashMap<String, String> params = new HashMap<>();
                params.put("filePath", "/img/popup/");
                params.put("fileName", fileName);

                ImgPath.append("https://exitobiz.co.kr/img/popup/").append(fileName);
                try {
                    fileService.uploadFile(popupVo.getUpFile(), params);
                    popup.setImage(ImgPath.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            popup.setTitle(popupVo.getTitle());
            popup.setActive(popupVo.getActive());
            popup.setCategory(popupVo.getCategory());
            popup.setFromDt(formatter.parse(popupVo.getFrom()));
            popup.setToDt(formatter.parse(popupVo.getTo()));
            popup.setLink(popupVo.getLink());
            popup.setNoticeId(popupVo.getNoticeId());
            popup.setUpdatedAt(timestamp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void createContent(ContentVo contentVo) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        StringBuilder ImgPath = new StringBuilder();
        try {
            if (contentVo.getUpFile() != null) {
                String fileName = fileService.fileNameGenerator(contentVo.getUpFile());
                HashMap<String, String> params = new HashMap<>();
                params.put("filePath", "/img/content/");
                params.put("fileName", fileName);

                ImgPath.append("https://exitobiz.co.kr/img/content/").append(fileName);
                try {
                    fileService.uploadFile(contentVo.getUpFile(), params);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            Content content = new Content();

            content.setThumbnail(ImgPath.toString());
            content.setTitle(contentVo.getTitle());
            content.setType(contentVo.getType());
            content.setContent(contentVo.getContent());
            content.setRemark(contentVo.getRemark());
            content.setUrl(contentVo.getUrl());
            content.setActiveYn(contentVo.getActiveYn());
            content.setCretDt(timestamp);
            content.setUpdtDt(timestamp);
            content.setViewCnt(0);
            content.setCost(contentVo.getCost());
            content.setDiscountRate(contentVo.getDiscountRate());
            content.setDiscountCost(contentVo.getDiscountCost());
            content.setSalesToDt(formatter.parse(contentVo.getSalesToDt()));
            content.setSalesFromDt(formatter.parse(contentVo.getSalesFromDt()));
            content.setPerson(contentVo.getPerson());

            contentRepository.createContent(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

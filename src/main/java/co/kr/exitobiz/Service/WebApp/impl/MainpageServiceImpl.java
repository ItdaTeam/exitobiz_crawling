package co.kr.exitobiz.Service.WebApp.impl;

import co.kr.exitobiz.Mappers.WebApp.MainpageMapper;
import co.kr.exitobiz.Service.WebApp.MainpageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MainpageServiceImpl implements MainpageService {
    private final MainpageMapper mainpageMapper;

    @Override
    public List<HashMap> getSearchHotKeyWord() throws ParseException {
        return mainpageMapper.getSearchHotKeyWord();
    }

    @Override
    public List<HashMap> getMyRecentKeyword(HashMap<String, Object> params) throws ParseException {
        return mainpageMapper.getMyRecentKeyword(params);
    }

    @Override
    public void delMyRecentKeyword(HashMap<String, Object> params) throws ParseException {
        mainpageMapper.delMyRecentKeyword(params);
    }

    @Override
    public void delMyAllRecentKeyword(HashMap<String, Object> params) throws ParseException {
        mainpageMapper.delMyAllRecentKeyword(params);
    }

    @Override
    public void insertTimeLine(HashMap<String, Object> params) throws ParseException {
        mainpageMapper.insertTimeLine(params);
    }

    @Override
    public List<HashMap> getBannerList(HashMap<String, Object> params) throws ParseException {
        return mainpageMapper.getBannerList(params);
    }

    @Override
    public List<HashMap> getTotalCount() throws ParseException {
        return mainpageMapper.getTotalCount();
    }

    @Override
    public int getLastWeekCnt() throws ParseException {
        return mainpageMapper.getLastWeekCnt();
    }

    @Override
    public HashMap<String, Object> getKeyword(HashMap<String, Object> params) throws ParseException {
        return mainpageMapper.getKeyword(params);
    }

    @Override
    public void insertKeyword(HashMap<String, Object> params) throws ParseException {
        mainpageMapper.insertKeyword(params);
    }

    @Override
    public void delKeyword(HashMap<String, Object> params) throws ParseException {
        mainpageMapper.delKeyword(params);
    }

    @Override
    public void delAllKeyword(HashMap<String, Object> params) throws ParseException {
        mainpageMapper.delAllKeyword(params);
    }

    @Override
    public List<HashMap> getPopularList(HashMap<String, Object> params) {
        return mainpageMapper.getPopularList(params);
    }

    @Override
    public List<HashMap> getPushBookList(HashMap<String, Object> params) throws ParseException {
        return mainpageMapper.getPushBookList(params);
    }

    @Override
    public void upViewCnt(HashMap<String, Object> params) {
        mainpageMapper.upViewCnt(params);
    }

    @Override
    public HashMap<String, Object> getSupportableCnt() throws ParseException {
        return mainpageMapper.getSupportableCnt();
    }

    @Override
    public void insertEmailDeliver(HashMap<String, Object> params) throws ParseException {
        mainpageMapper.insertEmailDeliver(params);
    }

    @Override
    public HashMap<String, Object> getEmailDeliver(HashMap<String, Object> params) throws ParseException {
        return mainpageMapper.getEmailDeliver(params);
    }

    @Override
    public List<HashMap> getEmilDeliverList() throws ParseException {
        return mainpageMapper.getEmailDeliverList();
    }

    @Override
    public List<HashMap> getMyEmailDeliver(HashMap<String, Object> params) throws ParseException {
        return mainpageMapper.getMyEmailDeliver(params);
    }

    @Override
    public void insertEmailDeliverHst(HashMap<String, Object> params) throws ParseException {
        mainpageMapper.insertEmailDeliverHst(params);
    }

    @Override
    public void insertEmailContent(HashMap<String, Object> params) throws ParseException {
        mainpageMapper.insertEmailContent(params);
    }
}

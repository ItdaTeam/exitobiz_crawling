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
    public List<HashMap> getBannerList() throws ParseException {
        return mainpageMapper.getBannerList();
    }

    @Override
    public List<HashMap> getTotalCount() throws ParseException {
        return mainpageMapper.getTotalCount();
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
    public List<HashMap> getPushBookList(HashMap<String, Object> params) throws ParseException {
        return mainpageMapper.getPushBookList(params);
    }
}

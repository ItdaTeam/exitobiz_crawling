package co.kr.exitobiz.Service.Mobile.impl;

import co.kr.exitobiz.Mappers.Mobile.CommunityMapper;
import co.kr.exitobiz.Mappers.Mobile.SavedMapper;
import co.kr.exitobiz.Service.Mobile.CommunityService;
import co.kr.exitobiz.Service.Mobile.FileService;
import co.kr.exitobiz.Service.Mobile.SavedService;
import co.kr.exitobiz.Vo.Mobile.CommunityVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SavedServiceImpl implements SavedService {
    private final SavedMapper savedMapper;


    @Override
    public List<HashMap> getRecentlyMySavedBook(HashMap<String, Object> params) {
        return savedMapper.getRecentlyMySavedBook(params);
    }

    @Override
    public List<HashMap> getMySavedBook(HashMap<String, Object> params) throws ParseException {
        return savedMapper.getMySavedBook(params);
    }

    @Override
    public List<HashMap> getCatList(HashMap<String, Object> params) throws ParseException {
        return savedMapper.getCatList(params);
    }

    @Override
    public void insertSavedMyBook(HashMap<String, Object> params) throws ParseException {
        savedMapper.insertSavedMyBook(params);
    }

    @Override
    public void updateSavedMyBook(HashMap<String, Object> params) throws ParseException {
        savedMapper.updateSavedMyBook(params);
    }

    @Override
    public void updateReqSavedMyBook(HashMap<String, Object> params) throws ParseException {
        savedMapper.updateReqSavedMyBook(params);
    }

    @Override
    public void updateDoneSavedMyBook(HashMap<String, Object> params) throws ParseException {
        savedMapper.updateDoneSavedMyBook(params);
    }
}

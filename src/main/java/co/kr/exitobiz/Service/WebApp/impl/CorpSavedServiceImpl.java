package co.kr.exitobiz.Service.WebApp.impl;

import co.kr.exitobiz.Mappers.WebApp.CorpSavedMapper;
import co.kr.exitobiz.Service.WebApp.CorpSavedService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CorpSavedServiceImpl implements CorpSavedService {
    private final CorpSavedMapper corpSavedMapper;

    @Override
    public List<HashMap> getMySavedBook(HashMap<String, Object> params) throws ParseException {
        return corpSavedMapper.getMySavedBook(params);
    }

    @Override
    public List<HashMap> getCatList(HashMap<String, Object> params) throws ParseException {
        return corpSavedMapper.getCatList(params);
    }

    @Override
    public void insertSavedMyBook(HashMap<String, Object> params) throws ParseException {
        corpSavedMapper.insertSavedMyBook(params);
    }

    @Override
    public void updateSavedMyBook(HashMap<String, Object> params) throws ParseException {
        corpSavedMapper.updateSavedMyBook(params);
    }

    @Override
    public HashMap checkSavedFlag(HashMap<String, Object> params) throws ParseException {
        return corpSavedMapper.checkSavedFlag(params);
    }

}

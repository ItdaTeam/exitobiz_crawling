package co.kr.exitobiz.Service.Api.impl;

import co.kr.exitobiz.Mappers.Api.StudyApiMapper;
import co.kr.exitobiz.Service.Api.StudyApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StudyApiImpl implements StudyApiService {
    private final StudyApiMapper studyApiMapper;

    @Override
    public void insertUserData(HashMap<String, Object> params) throws ParseException {
        studyApiMapper.insertUserData(params);
    }

    @Override
    public void updateUserData(HashMap<String, Object> params) throws ParseException {
        studyApiMapper.updateUserData(params);
    }

    @Override
    public void deleteUserData(HashMap<String, Object> params) throws ParseException {
        studyApiMapper.deleteUserData(params);
    }

    @Override
    public List<Map<String, Object>> selectUserData(HashMap<String, Object> params) throws ParseException {
        return studyApiMapper.selectUserData(params);
    }
}

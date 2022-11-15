package co.kr.exitobiz.Service.WebApp.impl;

import co.kr.exitobiz.Mappers.WebApp.SavedMapper;
import co.kr.exitobiz.Mappers.WebApp.SupportInfoMapper;
import co.kr.exitobiz.Service.Cms.SupportService;
import co.kr.exitobiz.Service.WebApp.SavedService;
import co.kr.exitobiz.Service.WebApp.SupportInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SupportInfoServiceImpl implements SupportInfoService {
    private final SupportInfoMapper supportInfoMapper;


    @Override
    public List<HashMap> getSupportInfoList(HashMap<String, Object> params) throws ParseException {
        return supportInfoMapper.getSupportInfoList(params);
    }
}

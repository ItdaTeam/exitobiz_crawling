package co.kr.exitobiz.Service.WebApp.impl;

import co.kr.exitobiz.Mappers.WebApp.EduRegistMapper;
import co.kr.exitobiz.Service.WebApp.EduRegistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class EduRegistServiceImpl implements EduRegistService {


    private final EduRegistMapper eduRegistMapper;

    @Override
    public void saveEduRegist(HashMap<String, Object> params) throws ParseException {
        eduRegistMapper.saveEduRegist(params);
    }

    @Override
    public HashMap chkRegist(HashMap<String, Object> params) throws ParseException {
        return eduRegistMapper.chkRegist(params);
    }
}

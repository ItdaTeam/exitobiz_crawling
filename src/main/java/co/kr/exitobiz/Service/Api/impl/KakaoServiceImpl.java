package co.kr.exitobiz.Service.Api.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.kr.exitobiz.Mappers.Api.KakaoMapper;
import co.kr.exitobiz.Service.Api.KakaoService;
import co.kr.exitobiz.Vo.Api.KakaoVo;

@Service
public class KakaoServiceImpl implements KakaoService{

    @Autowired
    KakaoMapper kakaoMapper;


    @Override
    public KakaoVo getPurchaseData(HashMap<String, String> params) {
        return kakaoMapper.getPurchaseData(params);
    }


    @Override
    public void insertLog(HashMap<String, String> params) {
        kakaoMapper.insertLog(params);
    }


    @Override
    public List<KakaoVo> kakaoSendList() {
        return kakaoMapper.kakaoSendList();
    }

    @Override
    public void login(Map<String, Object> params) {

        kakaoMapper.login(params);

    }

}

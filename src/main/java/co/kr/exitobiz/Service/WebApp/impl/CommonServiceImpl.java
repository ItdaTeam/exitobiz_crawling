package co.kr.exitobiz.Service.WebApp.impl;

import co.kr.exitobiz.Mappers.WebApp.CommonMapper;
import co.kr.exitobiz.Service.WebApp.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    CommonMapper commonMapper;
    public List<Map<String, Object>> getCommonCodeDtlList(Map<String, Object> params) {
        return  commonMapper.getCommonCodeDtlList(params);
    }

    @Override
    public List<Map<String, Object>> getCommonCodeCtgList(Map<String, Object> params) {
        return commonMapper.getCommonCodeCtgList(params);
    }

    @Override
    public Map<String, Object> getCommonCode(Map<String, Object> params) {
        return commonMapper.getCommonCode(params);
    }
}

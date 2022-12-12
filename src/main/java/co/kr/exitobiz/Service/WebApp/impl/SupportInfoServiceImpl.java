package co.kr.exitobiz.Service.WebApp.impl;

import co.kr.exitobiz.Mappers.WebApp.SupportInfoMapper;
import co.kr.exitobiz.Service.WebApp.SupportInfoService;
import co.kr.exitobiz.Util.Util;
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
        if(params.get("loc_code") != null)
            params.replace("loc_code", Util.makeForeach((String)params.get("loc_code"), ","));
        if(params.get("tech_ctg") != null)
            params.replace("tech_ctg", Util.makeForeach((String)params.get("tech_ctg"), ","));
        if(params.get("business_ctg") != null)
            params.replace("business_ctg", Util.makeForeach((String)params.get("business_ctg"), ","));
        if(params.get("target_cat_name") != null)
            params.replace("target_cat_name", Util.makeForeach((String)params.get("target_cat_name"), ","));
        if(params.get("company_type") != null)
            params.replace("company_type", Util.makeForeach((String)params.get("company_type"), ","));
        if(params.get("keyword") != null)
            params.replace("keyword", Util.makeForeach((String)params.get("keyword"), " "));
        return supportInfoMapper.getSupportInfoList(params);
    }
}

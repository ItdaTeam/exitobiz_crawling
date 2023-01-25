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
        if(params.get("business_type") != null && !params.get("business_type").equals("01"))
            params.replace("business_type", Util.makeForeach((String)params.get("business_type"), ","));
        if(params.get("loc_code") != null)
            params.replace("loc_code", Util.makeForeach((String)params.get("loc_code"), ","));
        if(params.get("tech_ctg") != null && params.get("tech_ctg") != "")
            params.replace("tech_ctg", Util.makeForeach((String)params.get("tech_ctg"), ","));
        if(params.get("business_ctg") != null)
            params.replace("business_ctg", Util.makeForeach((String)params.get("business_ctg"), ","));
        if(params.get("target_cat_name") != null && params.get("target_cat_name") != "")
            params.replace("target_cat_name", Util.makeForeach((String)params.get("target_cat_name"), ","));
        if(params.get("company_type") != null && params.get("company_type") != "")
            params.replace("company_type", Util.makeForeach((String)params.get("company_type"), ","));
        if(params.get("keyword") != null)
            params.replace("keyword", Util.makeForeach((String)params.get("keyword"), ","));
        if(params.get("start_period") != null && params.get("start_period") != "")
            params.replace("start_period", Util.makeForeach((String)params.get("start_period"), ","));
        return supportInfoMapper.getSupportInfoList(params);
    }

    @Override
    public HashMap<String, Object> getSPDetailData(HashMap<String, Object> params) throws ParseException {
        return supportInfoMapper.getSPDetailData(params);
    }


}

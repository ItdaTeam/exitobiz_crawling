package co.kr.exitobiz.Service.Cms.impl;

import co.kr.exitobiz.Mappers.Cms.TermsMapper;
import co.kr.exitobiz.Service.Cms.TermsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class TermsServiceImpl implements TermsService {

    @Autowired
    TermsMapper termsMapper;

    @Override
    public String getTermService() {
        return termsMapper.getTermService();
    }

    @Override
    public void saveTermService(HashMap<String, String> params) {
        termsMapper.saveTermService(params);
    }

    @Override
    public String getTermPrivacy() {
        return termsMapper.getTermPrivacy();
    }

    @Override
    public void saveTermPrivacy(HashMap<String, String> params) {
        termsMapper.saveTermPrivacy(params);
    }

    @Override
    public String getTermLocate() {
        return termsMapper.getTermLocate();
    }

    @Override
    public String getPersonMobile() {
        return termsMapper.getPersonMobile();
    }

    @Override
    public void saveTermLocate(HashMap<String, String> params) {
        termsMapper.saveTermLocate(params);
    }
}

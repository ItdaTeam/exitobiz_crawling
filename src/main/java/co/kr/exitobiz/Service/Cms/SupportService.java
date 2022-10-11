package co.kr.exitobiz.Service.Cms;

import co.kr.exitobiz.Entity.Support;
import co.kr.exitobiz.Vo.Cms.AgencyVo;
import co.kr.exitobiz.Vo.Cms.SearchVo;
import co.kr.exitobiz.Vo.Cms.SupportVo;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

public interface SupportService {
    public HashMap<String, Object> getSupportInfo();

    public List<Support> findByIdx(Integer idx);

    public List<Support> searchSupport(SearchVo searchVo) throws ParseException;

    public void editSupportData(SupportVo supportVo) throws ParseException;

    public Integer uploadExcelSupport(List<SupportVo> supportVos);

    public AgencyVo agencyData();

    public List<AgencyVo> searchData(SearchVo searchVo);

    public void editAgencyData(AgencyVo agencyVo);
}

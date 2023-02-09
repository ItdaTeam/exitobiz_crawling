package co.kr.exitobiz.Service.Cms;

import co.kr.exitobiz.Entity.SupportCorp;
import co.kr.exitobiz.Vo.Cms.SearchVo;
import co.kr.exitobiz.Vo.Cms.SupportCorpVo;

import java.text.ParseException;
import java.util.List;

public interface SupportCorpService {
    public List<SupportCorp> findByIdx(Integer idx);

    public List<SupportCorp> searchSupport(SearchVo searchVo) throws ParseException;

    public void editSupportData(SupportCorpVo supportCorpVo) throws ParseException;

    public Integer uploadExcelSupport(List<SupportCorpVo> supportVos);

    public Integer deleteSupport(List<SupportCorpVo> supportCorpVos);

}

package co.kr.exitobiz.Service.Cms.impl;

import co.kr.exitobiz.Entity.SupportCorp;
import co.kr.exitobiz.Mappers.Cms.SupportCorpRepository;
import co.kr.exitobiz.Service.Cms.SupportCorpService;
import co.kr.exitobiz.Util.DateUtil;
import co.kr.exitobiz.Util.Util;
import co.kr.exitobiz.Vo.Cms.SearchVo;
import co.kr.exitobiz.Vo.Cms.SupportCorpVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class SupportCorpServiceImpl implements SupportCorpService {

    private final SupportCorpRepository supportCorpRepository;

    @Override
    public List<SupportCorp> findByIdx(Integer idx) {
        return null;
    }

    @Override
    public List<SupportCorp> searchSupport(SearchVo searchVo) throws ParseException {
        if(searchVo.getInq() != null) {
            if(searchVo.getCon().equals("index"))
                searchVo.setListInq(Util.makeForeach(searchVo.getInq(), " "));
            else
                searchVo.setListInq(Util.makeForeach(searchVo.getInq(), ","));
        }

        return supportCorpRepository.searchSupport(searchVo);
    }

    @Override
    @Transactional
    public void editSupportData(SupportCorpVo supportCorpVo) throws ParseException {
        SupportCorp support = supportCorpRepository.findBySiIdx(supportCorpVo.getSiIdx());

        //타입 변환
        LocalDateTime localDateTime = LocalDateTime.ofInstant(DateUtil.parse(supportCorpVo.getSiEndDt()).toInstant(), ZoneId.systemDefault());

        if(!supportCorpVo.getSiEndDt().equals("")){
            support.setSiEndDt(localDateTime);
        }
    }

    @Override
    @Transactional
    public Integer uploadExcelSupport(List<SupportCorpVo> supportVos) {

        int cnt = 0;

        for(SupportCorpVo vo : supportVos){
            try {

                //디비에 존재하는지 확인
                Boolean checkFlag = supportCorpRepository.findById(vo.getSiIdx());

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                if(checkFlag){
                    supportCorpRepository.update(vo);
                }else {
                    SupportCorp support = new SupportCorp();
                    support.setSiIdx(vo.getSiIdx());
                    support.setTargetName(vo.getTargetName());
                    support.setTargetCatName(vo.getTargetCatName());
                    support.setTargetCostValue(vo.getTargetCostValue());
                    support.setLocCode(vo.getLocCode());
                    support.setSiTitle(vo.getSiTitle());
                    support.setMobileUrl(vo.getMobileUrl());
                    support.setPcUrl(vo.getPcUrl());
                    support.setSiCretDt(formatter.parse(vo.getSiCretDt()));
                   // support.setSiCretDt(LocalDate.parse(vo.getSiCretDt(), DateTimeFormatter.ISO_DATE).atStartOfDay());
                    support.setSiEndDt(LocalDate.parse(vo.getSiEndDt(), DateTimeFormatter.ISO_DATE).atStartOfDay());
                    support.setSiActiveYn(vo.getSiActiveYn());
                    support.setViewCnt(0);
                    support.setShareCnt(0);
                    support.setSaveCnt(0);

                    support.setTargetCatCd(vo.getTargetCatCd());
                    support.setBusinessCtg(vo.getBusinessCtg());
                    support.setTechCtg(vo.getTechCtg());
                    support.setBusinessType(vo.getBusinessType());
                    support.setCompanyType(vo.getCompanyType());
                    support.setStartPeriod(vo.getStartPeriod());
                    support.setCorpCd(vo.getCorpCd());


                    supportCorpRepository.save(support);
                }

                cnt++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return cnt;
    }

    @Override
    public Integer deleteSupport(List<SupportCorpVo> supportCorpVos) {
        int cnt = 0;

        for(SupportCorpVo vo : supportCorpVos) {
            try {
                supportCorpRepository.delete(vo);
                cnt++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return cnt;
    }
}

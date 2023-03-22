package co.kr.exitobiz.Service.Cms.impl;

import co.kr.exitobiz.Entity.Agency;
import co.kr.exitobiz.Entity.Support;
import co.kr.exitobiz.Mappers.Cms.AgencyRepository;
import co.kr.exitobiz.Mappers.Cms.SupportRepository;
import co.kr.exitobiz.Service.Cms.SupportService;
import co.kr.exitobiz.Util.DateUtil;
import co.kr.exitobiz.Util.Util;
import co.kr.exitobiz.Vo.Cms.AgencyVo;
import co.kr.exitobiz.Vo.Cms.SearchVo;
import co.kr.exitobiz.Vo.Cms.SupportVo;
import co.kr.exitobiz.Vo.Cms.UserVo;
import io.netty.util.internal.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static co.kr.exitobiz.Entity.QSupport.support;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class SupportServiceImpl implements SupportService {

    private final SupportRepository supportRepository;

    private final AgencyRepository agencyRepository;

    @Override
    public HashMap<String, Object> getSupportInfo() {
        SupportVo vo = supportRepository.getSupportInfo();
        HashMap<String, Object> info = new HashMap<>();

        info.put("totalSupportCnt", vo.getTotalSupportCnt());
        info.put("activeYCnt", vo.getActiveYCnt());
        info.put("activeNCnt", vo.getActiveNCnt());
        info.put("allViewCnt", vo.getAllViewCnt());
        info.put("viewCntLoc", supportRepository.getViewCntLoc());
        info.put("supportCntLoc", supportRepository.getSupportCntLoc());


        return info;
    }

    @Override
    public List<Support> findByIdx(Integer idx) {
        return null;
    }

    @Override
    public List<Support> searchSupport(SearchVo searchVo) throws ParseException {
        if(searchVo.getInq() != null)
            searchVo.setListInq(Util.makeForeach(searchVo.getInq(), ","));

        return supportRepository.searchSupport(searchVo);
    }

    @Override
    @Transactional
    public void editSupportData(SupportVo supportVo) throws ParseException {
        Support support = supportRepository.findBySiIdx(supportVo.getSiIdx());

        //타입 변환
        LocalDateTime localDateTime = LocalDateTime.ofInstant(DateUtil.parse(supportVo.getSiEndDt()).toInstant(), ZoneId.systemDefault());

        if(!supportVo.getSiEndDt().equals("")){
            support.setSiEndDt(localDateTime);
        }
    }

    @Override
    @Transactional
    public Integer uploadExcelSupport(List<SupportVo> supportVos) {

        int cnt = 0;

        for(SupportVo vo : supportVos){
            try {

                //디비에 존재하는지 확인
                Boolean checkFlag = supportRepository.findById(vo.getSiIdx());

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                if(checkFlag){
                    vo.setTargetCostValue(Long.parseLong(vo.getTargetCostValue().toString().replace(",","")));
                    vo.setUpdtDt(formatter.format(new Date()));
                    supportRepository.update(vo);
                }else {
                    Support support = new Support();
                    support.setSiIdx(vo.getSiIdx());
                    support.setTargetName(vo.getTargetName());
                    support.setTargetCatName(vo.getTargetCatName());
                    support.setTargetCostValue(vo.getTargetCostValue());
                    support.setLocCode(vo.getLocCode());
                    support.setSiTitle(vo.getSiTitle());
                    support.setMobileUrl(vo.getMobileUrl());
                    support.setPcUrl(vo.getPcUrl());
                    support.setSiCretDt(formatter.parse(vo.getSiCretDt()));
                    support.setTargetCostValue(Long.parseLong(vo.getTargetCostValue().toString().replace(",","")));
                   // support.setSiCretDt(LocalDate.parse(vo.getSiCretDt(), DateTimeFormatter.ISO_DATE).atStartOfDay());
                    support.setSiEndDt(LocalDate.parse(vo.getSiEndDt(), DateTimeFormatter.ISO_DATE).atStartOfDay());

//                    support.setUpdtDt(LocalDate.parse(DateTimeFormatter.ofPattern("yyyy-mm-dd").new Date(), DateTimeFormatter.ISO_DATE).atStartOfDay());
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


                   supportRepository.save(support);
                }

                cnt++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return cnt;
    }

    @Override
    public AgencyVo agencyData() {
        return agencyRepository.agencyData();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgencyVo> searchData(SearchVo searchVo) {
        if(searchVo.getInq() != null)
            searchVo.setListInq(Util.makeForeach(searchVo.getInq(), ","));

        return agencyRepository.searchAgency(searchVo);
    }

    @Override
    @Transactional
    public void editAgencyData(AgencyVo agencyVo) {
        Agency agency = agencyRepository.findById(agencyVo.getId());

        if(agencyVo.getActiveYn() != null & agencyVo.getActiveYn() != ""){
            agency.setActiveYn(agencyVo.getActiveYn());
        }

        if(agencyVo.getErrorYn() != null & agencyVo.getErrorYn() != ""){
            agency.setErrorYn(agencyVo.getErrorYn());
        }
    }
}

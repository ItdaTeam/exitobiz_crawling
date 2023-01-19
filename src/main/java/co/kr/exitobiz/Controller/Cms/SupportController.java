package co.kr.exitobiz.Controller.Cms;

import co.kr.exitobiz.Entity.Support;
import co.kr.exitobiz.Service.Cms.SupportService;
import co.kr.exitobiz.Vo.Cms.AgencyVo;
import co.kr.exitobiz.Vo.Cms.SearchVo;
import co.kr.exitobiz.Vo.Cms.SupportVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/cms")
@RequiredArgsConstructor
public class SupportController {

    private final SupportService supportService;

    @GetMapping("/support")
    public String disSupport(Model model){

        HashMap<String, Object> info = supportService.getSupportInfo();

        DecimalFormat formatter = new DecimalFormat("###,###");

        model.addAttribute("totalSupportCnt", formatter.format(info.get("totalSupportCnt")));
        model.addAttribute("activeYCnt", formatter.format(info.get("activeYCnt")));
        model.addAttribute("activeNCnt", formatter.format(info.get("activeNCnt")));
        model.addAttribute("allViewCnt", formatter.format(info.get("allViewCnt")));
        model.addAttribute("viewCntLoc", info.get("viewCntLoc"));
        model.addAttribute("supportCntLoc", info.get("supportCntLoc"));

        return "/cms/support/support";
    }

    @PutMapping("/support")
    @ResponseBody
    public void editSupportData(@RequestBody List<SupportVo> supportVos) {
        supportVos.forEach( vo -> {
            try {
                supportService.editSupportData(vo);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
    }

    @GetMapping("/allSupport")
    @ResponseBody
    public List<Support> getSupportData(SearchVo searchVo) throws ParseException{
        return supportService.searchSupport(searchVo);
    }

    @PostMapping("/support/uploadExcel")
    @ResponseBody
    public Integer uploadExcelSupport(@RequestBody List<SupportVo> supportVos) {
        return supportService.uploadExcelSupport(supportVos);
    }

    @GetMapping("/support/agency")
    public String disAgency(Model model){

        DecimalFormat formatter = new DecimalFormat("###,###");

        AgencyVo vo = supportService.agencyData();

        HashMap<String, Object> info = new HashMap<String, Object>();

        info.put("allCrawlingCnt", formatter.format(vo.getAllCrawlingCnt()));

        model.addAttribute("allCrawlingCnt", info.get("allCrawlingCnt"));
        model.addAttribute("agencyData", vo);

        return "/cms/support/support_agency";
    }

    @GetMapping("/support/agency/api")
    @ResponseBody
    public List<AgencyVo> getAgencyData(SearchVo searchVo) {
        return supportService.searchData(searchVo);
    }

    @PutMapping("/support/agency/api")
    @ResponseBody
    public void editAgencyData(@RequestBody List<AgencyVo> agencyVos){
        agencyVos.forEach( vo -> {
            supportService.editAgencyData(vo);
        });
    }

    @GetMapping("/business")
    public String businessTmp(Model model){

        return "/cms/Business/Business";
    }

}

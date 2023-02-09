package co.kr.exitobiz.Controller.Cms;

import co.kr.exitobiz.Entity.SupportCorp;
import co.kr.exitobiz.Service.Cms.SupportCorpService;
import co.kr.exitobiz.Vo.Cms.SearchVo;
import co.kr.exitobiz.Vo.Cms.SupportCorpVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping("/cms/corp")
@RequiredArgsConstructor
public class SupportCorpController {

    private final SupportCorpService supportCorpService;

    @GetMapping("/support")
    public String businessTmp(){

        return "/cms/Business/Business";
    }

    @PutMapping("/support")
    @ResponseBody
    public void editSupportData(@RequestBody List<SupportCorpVo> supportVos) {
        supportVos.forEach( vo -> {
            try {
                supportCorpService.editSupportData(vo);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
    }

    @GetMapping("/allSupport")
    @ResponseBody
    public List<SupportCorp> getSupportData(SearchVo searchVo) throws ParseException{
        return supportCorpService.searchSupport(searchVo);
    }

    @PostMapping("/support/uploadExcel")
    @ResponseBody
    public Integer uploadExcelSupport(@RequestBody List<SupportCorpVo> supportVos) {
        return supportCorpService.uploadExcelSupport(supportVos);
    }

    @PostMapping("/support/delete")
    @ResponseBody
    public Integer deleteSupport(@RequestBody List<SupportCorpVo> supportCorpVos){
        return supportCorpService.deleteSupport(supportCorpVos);
    }


}

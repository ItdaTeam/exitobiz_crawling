package co.kr.exitobiz.Controller.Cms;

import co.kr.exitobiz.Service.Cms.DashBoardService;
import co.kr.exitobiz.Vo.Cms.DashBoardVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/cms")
@RequiredArgsConstructor
public class IntroController {

    private final DashBoardService dashBoardService;

    @GetMapping(value = "")
    public String cms() {
        return "/cms/login/login";
    }

    @GetMapping(value = "/main")
    public String main(Model model)
    {
        List<DashBoardVo> topUser = dashBoardService.getInTopUser();

        List<DashBoardVo> lowPage = dashBoardService.getLowPageCnt();

        DashBoardVo dashBoard = dashBoardService.onloadBoard();

        model.addAttribute("dashBoard", dashBoard);

        model.addAttribute("topUser", topUser);

        model.addAttribute("lowPage", lowPage);

        return "/cms/dashboard/dashboard";
    }

    @GetMapping("/getAccessUserList")
    @ResponseBody
    public List<DashBoardVo> getAccessUserList(@RequestParam HashMap<String, Object> params) {
        return dashBoardService.getAccessUserList(params);
    }

    @GetMapping("/getSupButton")
    @ResponseBody
    public List<DashBoardVo> getSupButton(@RequestParam HashMap<String, String> params) {
        return dashBoardService.getSupButton(params);
    }

    @GetMapping("/getProButton")
    @ResponseBody
    public List<DashBoardVo> getProButton(@RequestParam HashMap<String, String> params) {
        return dashBoardService.getProButton(params);
    }

    @GetMapping("/goal")
    @ResponseBody
    public List<DashBoardVo> getGoal() {
        return dashBoardService.getGoal();
    }

    @PostMapping("/goal")
    @ResponseBody
    public void updateGoal(@RequestBody List<DashBoardVo> dashBoardVos) {
        System.out.println(dashBoardVos.toString());

        dashBoardService.updateGoal(dashBoardVos);
    }

    @GetMapping(value = "/getDauChart")
    @ResponseBody
    public List<DashBoardVo> getDauChart(DashBoardVo dashBoardVo) {
        return dashBoardService.getDauChart(dashBoardVo);
    }

    @GetMapping(value = "/getWauChart")
    @ResponseBody
    public List<DashBoardVo> getWauChart(DashBoardVo dashBoardVo) {
        return dashBoardService.getWauChart(dashBoardVo);
    }

    @GetMapping(value = "/getMauChart")
    @ResponseBody
    public List<DashBoardVo> getMauChart(DashBoardVo dashBoardVo) {
        return dashBoardService.getMauChart(dashBoardVo);
    }
}

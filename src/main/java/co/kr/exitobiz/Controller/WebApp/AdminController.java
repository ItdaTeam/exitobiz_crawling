package co.kr.exitobiz.Controller.WebApp;


import co.kr.exitobiz.Service.WebApp.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
@RequestMapping("/admin")
public class AdminController {
    

    @Autowired
    AdminService adminService;


    // 출입 통계 6개월간
    @RequestMapping("/getComingMonthStats")
    @ResponseBody
    public HashMap<String,Object> getComingMonthStats(){
        return adminService.getComingMonthStats();
    }


    // 회원가입 6개월간
    @RequestMapping("/getJoinMonthStats")
    @ResponseBody
    public HashMap<String,Object> getJoinMonthStats(){
        return adminService.getJoinMonthStats();
    }

    //출입통계 8주간
    @RequestMapping("/getComingWeeksStats")
    @ResponseBody
    public HashMap<String,Object> getComingWeeksStats(){
        return adminService.getComingWeeksStats();
    }

    // 회원가입 8주간
    @RequestMapping("/getJoinWeeksStats")
    @ResponseBody
    public HashMap<String,Object> getJoinWeeksStats(){
        return adminService.getJoinWeeksStats();
    }

    //출입 통계 2주간
    @RequestMapping("/getComingTwoWeeks")
    @ResponseBody
    public HashMap<String,Object> getComingTwoWeeks(){
        return adminService.getComingTwoWeeks();
    }

    //출입 통계 1주간
    @RequestMapping("/getComingWeek")
    @ResponseBody
    public HashMap<String,Object> getComingWeek(){
        return adminService.getComingWeek();
    }

    //회원가입 2주간
    @RequestMapping("/getJoinTwoWeeks")
    @ResponseBody
    public HashMap<String,Object> getJoinTwoWeeks(){
        return adminService.getJoinTwoWeeks();
    }

    //회원가입 1주간
    @RequestMapping("/getJoinWeek")
    @ResponseBody
    public HashMap<String,Object> getJoinWeek(){
        return adminService.getJoinWeek();
    }


}

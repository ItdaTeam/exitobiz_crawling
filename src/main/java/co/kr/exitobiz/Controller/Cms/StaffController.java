package co.kr.exitobiz.Controller.Cms;

import co.kr.exitobiz.Entity.Staff;
import co.kr.exitobiz.Service.Cms.StaffService;
import co.kr.exitobiz.Util.Encrypt;
import co.kr.exitobiz.Vo.Cms.SearchVo;
import co.kr.exitobiz.Vo.Cms.StaffVo;
import co.kr.exitobiz.Vo.Cms.UserVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.HashMap;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class StaffController {

    private final StaffService staffService;

    @GetMapping("/cms/staff")
    public String disStaff(){
        return "/cms/user/staff";
    }

    @GetMapping("/cms/member")
    public String disMember(Model model){

        DecimalFormat formatter = new DecimalFormat("###,###");

        HashMap<String, Object> info = staffService.getMemberInfo();

        model.addAttribute("totalMemberCnt", formatter.format(info.get("totalMemberCnt")));
        model.addAttribute("loginTodayCnt", info.get("loginTodayCnt"));
        model.addAttribute("joinTodayCnt", info.get("joinTodayCnt"));
        model.addAttribute("loginTodayMemberCnt", info.get("loginTodayMemberCnt"));
        model.addAttribute("loginMonthMemberCnt", formatter.format(info.get("loginMonthMemberCnt")));
        model.addAttribute("activeMemberCnt", formatter.format(info.get("activeMemberCnt")));
        model.addAttribute("totalStaffCnt", info.get("totalStaffCnt"));

        return "/cms/user/member";
    }

    @GetMapping("/allMember")
    @ResponseBody
    public List<UserVo> getAllMember(SearchVo searchVo, HttpSession session){

        log.info(session.getId());

        return staffService.searchMember(searchVo);
    }

    @PostMapping("/member/updateAdminFlag")
    @ResponseBody
    public void updateAdminFlag(@RequestParam String id, @RequestParam String adminFlag){
        staffService.updateAdminFlag(id,adminFlag);
    }

    /** 아이디 중복확인 */
    @RequestMapping(value = "/staff/dupCheckId")
    @ResponseBody
    public String dupCheckId(@RequestParam String id){
        return staffService.dupCheckId(id);
    }

    @GetMapping("/all-staff")
    @ResponseBody
    public List<Staff> getAllStaff(SearchVo searchVo){
        return staffService.searchStaff(searchVo);
    }

    @PostMapping("/staff")
    @ResponseBody
    public void saveNewStaff(@Valid StaffVo vo){

        // salt + SHA512 암호화 적용
        String password = vo.getPassword();
        String password_key = Encrypt.getSaltKey();
        password = Encrypt.setSHA512(password, password_key);

        LocalDateTime now = LocalDateTime.now();

        Staff staff = new Staff();

        staff.setId(vo.getId());
        staff.setEmail(vo.getEmail());
        staff.setName(vo.getName());
        staff.setMemo(vo.getMemo());
        staff.setPhoneNum(vo.getPhoneNum());
        staff.setPassword(password);
        staff.setPasswordKey(password_key);
        staff.setActiveYn("Y");
        staff.setCretDt(now);
        staff.setUpdtDt(now);
        staff.setLastestDt(now);

        staffService.joinStaff(staff);
    }

    @PutMapping("/staff")
    @ResponseBody
    public void updateStaff(@Valid StaffVo vo){

        LocalDateTime now = LocalDateTime.now();

        vo.setUpdtDt(now);

        staffService.updateStaff(vo);
    }

    @DeleteMapping("/staff")
    @ResponseBody
    public void deleteStaff(@RequestParam String id){
        staffService.deleteStaff(id);
    }
}

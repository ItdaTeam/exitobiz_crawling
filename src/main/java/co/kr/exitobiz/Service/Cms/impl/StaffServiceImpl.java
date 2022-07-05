package co.kr.exitobiz.Service.Cms.impl;

import co.kr.exitobiz.Entity.Member;
import co.kr.exitobiz.Entity.Staff;
import co.kr.exitobiz.Mappers.Cms.StaffRepository;
import co.kr.exitobiz.Service.Cms.StaffService;
import co.kr.exitobiz.Util.Encrypt;
import co.kr.exitobiz.Util.Util;
import co.kr.exitobiz.Vo.Cms.SearchVo;
import co.kr.exitobiz.Vo.Cms.StaffVo;
import co.kr.exitobiz.Vo.Cms.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.http.HttpRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {

    private final StaffRepository staffRepository;

    @Override
    public HashMap<String, Object> getMemberInfo() {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime monthAgo = now.minusMonths(1);
        LocalDateTime appRenewDay = LocalDateTime.of(2022, 2, 24, 0, 0, 0);

        UserVo vo = staffRepository.getMemberInfo(now,monthAgo,appRenewDay);
        HashMap<String, Object> info = new HashMap<>();

        info.put("totalMemberCnt", vo.getTotalMemberCnt());
        info.put("loginTodayCnt", vo.getLogintodaycnt());
        info.put("joinTodayCnt", vo.getJoinTodayCnt());
        info.put("loginMonthMemberCnt", vo.getLoginMonthMemberCnt());
        info.put("loginTodayMemberCnt", vo.getLoginTodayMemberCnt());
        info.put("activeMemberCnt", vo.getActiveMemberCnt());
        info.put("totalStaffCnt", vo.getTotalStaffCnt());

        return info;
    }

    @Override
    public List<UserVo> searchMember(SearchVo searchVo) {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime monthAgo = now.minusMonths(1);

        if(searchVo.getInq() != null)
            searchVo.setListInq(Util.makeForeach(searchVo.getInq(), ","));

        return staffRepository.searchMember(searchVo,now,monthAgo);
    }

    @Override
    @Transactional
    public void updateAdminFlag(String id, String adminFlag) {
        Member member = staffRepository.findById(id);

        member.setAdminFlag(adminFlag);
    }

    @Override
    public List<Staff> searchStaff(SearchVo searchVo) {
        return staffRepository.searchStaff(searchVo);
    }

    @Override
    public String dupCheckId(String id) {
        List<Staff> staffList = staffRepository.findStaffById(id);

        if(staffList.isEmpty()){
            return null;
        }else {
            return staffList.get(0).getId();
        }
    }

    @Override
    public void save() {

    }

    @Override
    @Transactional
    public String joinStaff(Staff staff) {
        staffRepository.joinStaff(staff);

        return staff.getId();
    }

    @Override
    @Transactional
    public void deleteStaff(String id) {
        staffRepository.deleteStaff(id);
    }

    @Override
    @Transactional
    public void updateStaff(StaffVo vo) {
        Staff staff = staffRepository.findOneStaff(vo.getId());

        staff.setName(vo.getName());
        staff.setActiveYn(vo.getActiveYn());
        staff.setPhoneNum(vo.getPhoneNum());
        staff.setEmail(vo.getEmail());
        staff.setMemo(vo.getMemo());
        staff.setUpdtDt(vo.getUpdtDt());

        if(vo.getPassword() != ""){
            String shaPwd = Encrypt.setSHA512(vo.getPassword(), staff.getPasswordKey());
            staff.setPassword(shaPwd);
        }
    }
}

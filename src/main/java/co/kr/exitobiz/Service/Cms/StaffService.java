package co.kr.exitobiz.Service.Cms;

import co.kr.exitobiz.Entity.Member;
import co.kr.exitobiz.Entity.Staff;
import co.kr.exitobiz.Vo.Cms.SearchVo;
import co.kr.exitobiz.Vo.Cms.StaffVo;
import co.kr.exitobiz.Vo.Cms.UserVo;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

public interface StaffService {

    public HashMap<String, Object> getMemberInfo();

    public List<UserVo> searchMember(SearchVo searchVo);

    public void updateAdminFlag(String id, String adminFlag);

    public List<Staff> searchStaff(SearchVo searchVo);

    public String dupCheckId(String id);

    public void save();

    public String joinStaff(Staff staff);

    public void deleteStaff(String id);

    public void updateStaff(StaffVo vo);

}

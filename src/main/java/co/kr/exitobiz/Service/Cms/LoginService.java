package co.kr.exitobiz.Service.Cms;

import co.kr.exitobiz.Core.AuthToken;
import co.kr.exitobiz.Entity.Staff;
import co.kr.exitobiz.Vo.Cms.StaffVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface LoginService {
    public String getPasswordCheck(StaffVo vo, HttpServletRequest request, HttpServletResponse response);

    public AuthToken createAuthToken(Staff staff);

    public void updateLoginTime(StaffVo vo);

    public void autoLogin(String id, HttpServletRequest request);

    public String logOut(HttpServletRequest request);
}

package co.kr.exitobiz.Controller.Cms;

import co.kr.exitobiz.Service.Cms.LoginService;
import co.kr.exitobiz.Vo.Cms.StaffVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService ;

    @PostMapping(value = "/login")
    @ResponseBody
    public String login(@ModelAttribute StaffVo vo, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

        String returnVal = "";
        String autoLogin = request.getParameter("autoLogin");
        request.setAttribute("autoLogin", autoLogin);
        String pwdCheck = loginService.getPasswordCheck(vo, request, response);

        switch(pwdCheck){
            case "login_fail" :  // 아이디 , 패스워드 오류
                returnVal = "아이디 또는 비밀번호를 확인해주세요.";
                break;
            case "login_auth_fail" :  // 관리자가 아닌경우 / 비활성화 상태인 경우
                returnVal = "해당 계정은 관리자 화면에 접속이 불가합니다.\n로그인 필요 시, 관리자에게 문의바랍니다.";
                break;
            case "login" :  // 로그인 성공
                loginService.updateLoginTime(vo);

                if(autoLogin.equals("on")) {
                    Cookie cookie = new Cookie("id", vo.getId());
                    cookie.setMaxAge(60*60*24*7);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }

                returnVal = "/cms/main";
                break;
        }

        return returnVal;
    }

    @RequestMapping(value = "/autoLogin", method = RequestMethod.POST)
    public String autoLogin(String id, HttpServletRequest  request, HttpServletResponse response) throws Exception {
        loginService.autoLogin(id, request);
        return "home/home";
    }


    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(@ModelAttribute StaffVo vo, HttpServletRequest  request, HttpServletResponse response) throws Exception {
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie tmpCookie : cookies){
                if(tmpCookie.getName().equals("staff_id")){
                    tmpCookie.setMaxAge(0);
                    tmpCookie.setPath("/");
                    response.addCookie(tmpCookie);
                }
            }
        }
        return "redirect:"+loginService.logOut(request);

    }
}

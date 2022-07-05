package co.kr.exitobiz.Service.Cms.impl;

import co.kr.exitobiz.Core.AuthToken;
import co.kr.exitobiz.Entity.Staff;
import co.kr.exitobiz.Mappers.Cms.LoginMapper;
import co.kr.exitobiz.Mappers.Cms.StaffMapper;
import co.kr.exitobiz.Mappers.Cms.StaffRepository;
import co.kr.exitobiz.Service.Cms.LoginService;
import co.kr.exitobiz.Util.Encrypt;
import co.kr.exitobiz.Util.RedisUtil;
import co.kr.exitobiz.Vo.Cms.StaffVo;
import co.kr.exitobiz.jwt.JwtAuthToken;
import co.kr.exitobiz.jwt.JwtAuthTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final StaffRepository staffRepository;

    private final RedisUtil redisUtil;

    private final JwtAuthTokenProvider jwtAuthTokenProvider;
    private final static long LOGIN_RETENTION_MINUTES = 30;


    @Autowired
    private LoginMapper loginMapper;

    @Autowired
    private StaffMapper staffMapper;

    @Override
    public String getPasswordCheck(StaffVo vo, HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        StaffVo regStaff = loginMapper.getPassword(vo);
        String url = null;

        if(regStaff == null){
            url = "login_fail";
        }else{
            String sha512Pwd = Encrypt.setSHA512(vo.getPassword(), regStaff.getPasswordKey());
            if(!regStaff.getId().equals(null) && sha512Pwd.equals(regStaff.getPassword())){ // 비밀번호 일치 하는 경우

                Staff staff = staffRepository.findOneStaff(vo.getId());

                JwtAuthToken jwtAuthToken = (JwtAuthToken) createAuthToken(staff);

                //jwt토큰 header에 설정
                response.setHeader("X-AUTH-TOKEN", jwtAuthToken.getToken());

                //세션아이디를 키값으로 redis에 토큰 저장
                redisUtil.setData(request.getSession().getId(), jwtAuthToken.getToken());

                log.info(String.valueOf(jwtAuthToken));

//                session.setAttribute("id", staff.getId());
//                session.setAttribute("password", staff.getPassword());
//                session.setAttribute("name", staff.getName());
//                session.setAttribute("phoneNum", staff.getPhoneNum());
//                session.setAttribute("activeYn", staff.getActiveYn());
//                session.setAttribute("latestDt", staff.getLastestDt());

                if( !staff.getActiveYn().equals("Y") ){
                    url = "login_auth_fail";
                    session.invalidate();
                }else{
                    url = "login";
                }

            }else{  // 비밀번호 불일치
                session.invalidate();
                url = "login_fail";
            }
        }
        return url;
    }

    @Override
    public AuthToken createAuthToken(Staff staff) {

        Date expiredDate = Date.from(LocalDateTime.now().plusMinutes(LOGIN_RETENTION_MINUTES).atZone(ZoneId.systemDefault()).toInstant());
        return jwtAuthTokenProvider.createAuthToken(staff.getId(), staff.getName(), expiredDate);
    }

    @Override
    public void updateLoginTime(StaffVo vo) {
//        SimpleDateFormat timeFormat = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
//        Date time = new Date();
//        String loginTime = timeFormat.format(time);
//        vo.setLatestDt(loginTime);
//        loginMapper.updateLoginTime(vo);
    }

    @Override
    public void autoLogin(String id, HttpServletRequest request) {
        HttpSession session = request.getSession();

        StaffVo staffVo = new StaffVo();
        staffVo.setId(id);
        staffVo = staffMapper.getStaffInfo(staffVo);

        if(staffVo != null){
            session.setAttribute("id", staffVo.getId());
            session.setAttribute("password", staffVo.getPassword());
            session.setAttribute("name", staffVo.getName());
            session.setAttribute("phoneNum", staffVo.getPhoneNum());
            session.setAttribute("email", staffVo.getEmail());
            session.setAttribute("activeYn", staffVo.getActiveYn());
            session.setAttribute("latestDt", staffVo.getLatestDt());
        }
    }

    @Override
    public String logOut(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "/cms";
    }
}

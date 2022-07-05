package co.kr.exitobiz.Mappers.Cms;

import co.kr.exitobiz.Vo.Cms.StaffVo;
import co.kr.exitobiz.Vo.Cms.UserVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface StaffMapper {
    public StaffVo getStaffInfo(StaffVo vo);

    public int getTotalStaff();

    public int getTotalUser();

    public List<StaffVo> getStaffList(HashMap<String, Object> params);

    public String dupCheckId(HashMap<String,String>params);

    public void saveNewStaff(StaffVo vo);

    public void updateStaff(HashMap<String, String> parmas);

    public void deleteStaff(HashMap<String, String> parmas);

    public List<UserVo> getUserList(HashMap<String, Object> params);

    public List<UserVo> getUserHisList(HashMap<String, Object> params);

    public void updateAdminYn(HashMap<String, String> params);

    public String getTermService();

    public void saveTermService(HashMap<String,String> params);

    public String getTermPrivacy();

    public void saveTermPrivacy(HashMap<String,String> params);

    public void updateAppversion(HashMap<String, String> params);
}

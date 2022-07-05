package co.kr.exitobiz.Service.Cms;

import co.kr.exitobiz.Vo.Cms.DashBoardVo;

import java.util.HashMap;
import java.util.List;

public interface DashBoardService {

    public DashBoardVo onloadBoard();

    public List<DashBoardVo> getSupButton(HashMap<String, String> params);

    public List<DashBoardVo> getProButton(HashMap<String, String> params);

    public List<DashBoardVo> getAccessUserList(HashMap<String, Object> params);

    public List<DashBoardVo> getDauChart(DashBoardVo dashBoardVo);

    public List<DashBoardVo> getWauChart(DashBoardVo dashBoardVo);

    public List<DashBoardVo> getMauChart(DashBoardVo dashBoardVo);

    public List<DashBoardVo> getInTopUser();

    public List<DashBoardVo> getLowPageCnt();

    public List<DashBoardVo> getGoal();

    public void updateGoal(List<DashBoardVo> dashBoardVos);
}

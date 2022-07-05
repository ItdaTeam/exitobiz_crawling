package co.kr.exitobiz.Service.Cms.impl;

import co.kr.exitobiz.Mappers.Cms.DashBoardMapper;
import co.kr.exitobiz.Service.Cms.DashBoardService;
import co.kr.exitobiz.Util.Util;
import co.kr.exitobiz.Vo.Cms.DashBoardVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DashBoardServiceImpl implements DashBoardService {

    private final DashBoardMapper dashBoardMapper;

    @Override
    public DashBoardVo onloadBoard() {
        return dashBoardMapper.onloadBoard();
    }

    @Override
    public List<DashBoardVo> getSupButton(HashMap<String, String> params) {
        return dashBoardMapper.getSupButton(params);
    }

    @Override
    public List<DashBoardVo> getProButton(HashMap<String, String> params) {
        return dashBoardMapper.getProButton(params);
    }

    @Override
    public List<DashBoardVo> getAccessUserList(HashMap<String, Object> params) {
        if(params.get("inq") != null)
            params.replace("inq", Util.makeForeach((String)params.get("inq"), ","));
        return dashBoardMapper.getAccessUserList(params);
    }

    @Override
    public List<DashBoardVo> getDauChart(DashBoardVo dashBoardVo) {
        return dashBoardMapper.getDauChart(dashBoardVo);
    }

    @Override
    public List<DashBoardVo> getWauChart(DashBoardVo dashBoardVo) {
        return dashBoardMapper.getWauChart(dashBoardVo);
    }

    @Override
    public List<DashBoardVo> getMauChart(DashBoardVo dashBoardVo) {
        return dashBoardMapper.getMauChart(dashBoardVo);
    }

    @Override
    public List<DashBoardVo> getInTopUser() {
        return dashBoardMapper.getInTopUser();
    }

    @Override
    public List<DashBoardVo> getLowPageCnt() {
        return dashBoardMapper.getLowPageCnt();
    }

    @Override
    public List<DashBoardVo> getGoal() {
        return dashBoardMapper.getGoal();
    }

    @Override
    public void updateGoal(List<DashBoardVo> dashBoardVos) {

        for( DashBoardVo dashBoardVo :dashBoardVos){
            dashBoardMapper.updateGoal(dashBoardVo);
        }

    }
}

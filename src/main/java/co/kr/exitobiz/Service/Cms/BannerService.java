package co.kr.exitobiz.Service.Cms;

import co.kr.exitobiz.Vo.Cms.*;

import java.util.HashMap;
import java.util.List;

public interface BannerService {
    public int getTotalBanner();

    public int getTotalActiveBanner();

    public List<BannerVo> getBannerList(HashMap<String, Object> params);

    public void saveNewBanner(BannerVo vo);

    public void updateBanner(BannerVo vo);

    public void deleteBanner(HashMap<String, String> params) throws Exception;
}

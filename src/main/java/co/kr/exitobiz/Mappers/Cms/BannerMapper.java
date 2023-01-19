package co.kr.exitobiz.Mappers.Cms;

import co.kr.exitobiz.Vo.Cms.BannerVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
@Mapper
public interface BannerMapper {
    public int getTotalBanner();

    public int getTotalActiveBanner();

    public List<BannerVo> getBannerList(HashMap<String, Object> params);

    public HashMap<String, Object> getBanner(HashMap<String, Object> params);

    public void saveNewBanner(BannerVo vo);

    public void updateBanner(BannerVo vo);

    public void deleteBanner(HashMap<String, String> params);
}

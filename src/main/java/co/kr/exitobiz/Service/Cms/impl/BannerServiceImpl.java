package co.kr.exitobiz.Service.Cms.impl;

import co.kr.exitobiz.Mappers.Cms.BannerMapper;
import co.kr.exitobiz.Service.Cms.BannerService;
import co.kr.exitobiz.Vo.Cms.BannerVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BannerServiceImpl implements BannerService {

    private final BannerMapper bannerMapper;

    @Override
    public int getTotalBanner() {
        return bannerMapper.getTotalBanner();
    }

    @Override
    public int getTotalActiveBanner() {
        return bannerMapper.getTotalActiveBanner();
    }

    @Override
    public List<BannerVo> getBannerList(HashMap<String, Object> params) {
        return bannerMapper.getBannerList(params);
    }

    @Override
    public HashMap<String, Object> getBanner(HashMap<String, Object> params) {
        return bannerMapper.getBanner(params);
    }

    @Override
    public void saveNewBanner(BannerVo vo) {

        bannerMapper.saveNewBanner(vo);
    }

    @Override
    public void updateBanner(BannerVo vo) {
        if(vo.getBannerNotiIdx().isEmpty()){
            vo.setBannerNotiIdx(null);
        }
        bannerMapper.updateBanner(vo);
    }

    @Override
    public void deleteBanner(HashMap<String, String> params) throws Exception{
        bannerMapper.deleteBanner(params);
    }
}

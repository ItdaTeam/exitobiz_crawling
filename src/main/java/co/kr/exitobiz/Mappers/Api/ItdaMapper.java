package co.kr.exitobiz.Mappers.Api;

import co.kr.exitobiz.Vo.Cms.ProviderVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface ItdaMapper {
    public void addQuestion(HashMap<String,String> params);

    public List<ProviderVo> getProvider();

    public void updateLoc(HashMap<String, String> params);

    public void updatePhone(HashMap<String, String> params);
}
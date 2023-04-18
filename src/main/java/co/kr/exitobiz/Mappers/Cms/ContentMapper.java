package co.kr.exitobiz.Mappers.Cms;

import co.kr.exitobiz.Vo.Cms.BannerVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface ContentMapper {
    void saveContent(HashMap<String, Object> param);

    List<HashMap> getContentList(HashMap<String, Object> params);

    void updateContent(HashMap<String, Object> params);

    List<HashMap> getTopInfo(HashMap<String, Object> params);
}

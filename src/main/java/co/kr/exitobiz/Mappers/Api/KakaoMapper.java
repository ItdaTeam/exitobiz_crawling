package co.kr.exitobiz.Mappers.Api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import co.kr.exitobiz.Vo.Api.KakaoVo;

@Mapper
public interface KakaoMapper {

    KakaoVo getPurchaseData(HashMap<String, String> params);

    void insertLog(HashMap<String, String> params);

    List<KakaoVo> kakaoSendList();

    void login(Map<String, Object> params);
}

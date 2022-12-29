package co.kr.exitobiz.Service.Api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.kr.exitobiz.Vo.Api.KakaoVo;

public interface KakaoService {

    KakaoVo getPurchaseData(HashMap<String, String> params);

    void insertLog(HashMap<String, String> params);

    List<KakaoVo> kakaoSendList();

    void login(Map<String, Object> params);
}

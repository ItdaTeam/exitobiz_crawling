package co.kr.exitobiz.Service.WebApp;

import java.util.List;
import java.util.Map;

public interface CommonService {
    List<Map<String, Object>> getCommonCodeDtlList(Map<String, Object> params);

    List<Map<String, Object>> getCommonCodeCtgList(Map<String, Object> params);

    Map<String, Object> getCommonCode(Map<String, Object> params);
}

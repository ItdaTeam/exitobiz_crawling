package co.kr.exitobiz.Service.Batch;

import java.util.List;
import java.util.Map;

public interface BatchService {
    List<Map<String, Object>> getOverDeviceList();

    void deleteDevice(Map<String, Object> device);
}

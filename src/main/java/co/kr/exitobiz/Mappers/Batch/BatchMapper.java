package co.kr.exitobiz.Mappers.Batch;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BatchMapper {

    List<Map<String, Object>> getOverDeviceList();

    void deleteDevice(Map<String, Object> device);
}


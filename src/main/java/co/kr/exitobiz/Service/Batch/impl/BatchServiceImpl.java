package co.kr.exitobiz.Service.Batch.impl;

import co.kr.exitobiz.Mappers.Batch.BatchMapper;
import co.kr.exitobiz.Service.Batch.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BatchServiceImpl implements BatchService {

    @Autowired
    BatchMapper batchMapper;


    @Override
    public List<Map<String, Object>> getOverDeviceList() {
        return batchMapper.getOverDeviceList();
    }

    @Override
    public void deleteDevice(Map<String, Object> device) {
        batchMapper.deleteDevice(device);
    }
}

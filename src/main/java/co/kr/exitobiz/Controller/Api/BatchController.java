package co.kr.exitobiz.Controller.Api;

import co.kr.exitobiz.Service.Batch.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

@Controller
public class BatchController {

    @Autowired
    BatchService batchService;

    @Scheduled(cron = "0 0 0 * * *")
    public void deleteUserDevice(){
        List<Map<String, Object>> deviceList = batchService.getOverDeviceList();

        for(Map<String, Object> device: deviceList){

            batchService.deleteDevice(device);
        }
    }
}

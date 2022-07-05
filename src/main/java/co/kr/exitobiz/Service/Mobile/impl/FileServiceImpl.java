
package co.kr.exitobiz.Service.Mobile.impl;

import co.kr.exitobiz.Service.Mobile.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    // 파일 업로드
    @Override
    public String uploadFile(MultipartFile file, HashMap<String, String> params) throws Exception {
        String filePath = "/var/upload" + params.get("filePath"); // 파일경로 (upload/img가 기본 경로). 파일경로는 docker-tomcat에 볼륨 지정된 경로로 해야 한다.
        String fileName = params.get("fileName"); // 저장할 파일명
        String result = ""; // 결과 메세지
        try{
            File dest = new File(filePath + "/"+ fileName);
            if(!dest.exists()) // 해당 경로가 없을 경우 
                dest.mkdirs();  // 폴더 생성
            file.transferTo(dest);
            result = "success";
        }catch (Exception e){ // 실패시 예외
            e.printStackTrace(); 
            result = "fail";
        }
        return result;
    }

    // 파일 삭제
    @Override
    public String deleteFile(HashMap<String, String> params) throws Exception {
        String filePath = "/var/upload/img" + params.get("filePath");  // 삭제할 파일 경로
        String fileName = params.get("fileName"); // 삭제할 파일명
        String result ; // 결과 메세지
        try{
            File dest = new File(filePath + "/" + fileName);
            if(dest.exists()){   // 파일이 존재하는 경우
                dest.delete();
                result = "file delete success";
            }else{  // 파일이 존재하지 않는 경우
                result = "file is not exist";
            }
        }catch (Exception e){ // 실패시 예외처리
            e.printStackTrace();
            result = "file delete fail";
        }
        return result;
    }

    @Override
    public String fileNameGenerator(MultipartFile file) {

        String originFilename = file.getOriginalFilename();
        String extName = originFilename.substring(originFilename.lastIndexOf("."),originFilename.length());

        SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
        Date time = new Date();
        String time1 = format.format(time);
        String uuid = UUID.randomUUID().toString();
        return time1 + "_" + uuid + extName;
    }
    
}
package co.kr.exitobiz.Service.Mobile;

import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

public interface FileService {

    public String uploadFile(MultipartFile file, HashMap<String,String>params) throws Exception;

    public String deleteFile(HashMap<String,String>params) throws Exception;

    String fileNameGenerator(MultipartFile file);
}
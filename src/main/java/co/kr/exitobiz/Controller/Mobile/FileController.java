package co.kr.exitobiz.Controller.Mobile;

import co.kr.exitobiz.Service.Mobile.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

@Controller
@RequestMapping(value = "/file")
public class FileController {
    
    @Autowired
    FileService fileService;

    //앱에서 파일 업로드
    @RequestMapping(value = "/uploadFile")
    @ResponseBody
    public String uploadFile( MultipartFile file , @RequestParam HashMap<String,String> params) throws Exception{
        return fileService.uploadFile(file, params);
    }

    //앱에서 파일 삭제
    @RequestMapping(value = "/deleteFile")
    @ResponseBody
    public String deleteFile(@RequestParam HashMap<String,String> params) throws Exception{
        return fileService.deleteFile(params);
    }
}
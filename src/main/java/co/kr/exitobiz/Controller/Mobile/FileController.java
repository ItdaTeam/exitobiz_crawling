package co.kr.exitobiz.Controller.Mobile;

import co.kr.exitobiz.Service.Mobile.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

@Controller
@RequiredArgsConstructor
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

    //앱에서 파일 삭제 (params : filePath, fileName[arr])
    @PostMapping(value = "/deleteFile")
    @ResponseBody
    public String deleteFile(@RequestBody HashMap<String,Object> params) throws Exception{
        return fileService.deleteFile(params);
    }
}
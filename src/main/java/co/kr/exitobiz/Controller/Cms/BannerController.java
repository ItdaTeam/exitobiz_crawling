package co.kr.exitobiz.Controller.Cms;

import co.kr.exitobiz.Service.Cms.BannerService;
import co.kr.exitobiz.Service.Cms.ContentService;
import co.kr.exitobiz.Vo.Cms.BannerVo;
import co.kr.exitobiz.Vo.Cms.PopupVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cms")
public class BannerController {

    private final BannerService bannerService;

    @GetMapping("/banner")
    public String disBanner(HttpServletRequest req) {

        req.setAttribute("totalBanner", bannerService.getTotalBanner());
        req.setAttribute("totalActiveBanner", bannerService.getTotalActiveBanner());

        return "/cms/banner/banner";
    }

    @ResponseBody
    @PostMapping("/banner")
    public void saveNewBanner(BannerVo vo, HttpServletRequest req) {
        try {
            String uploadPath = "/var/upload/img/banner";
            String originFilename = vo.getImage().getOriginalFilename();
            String extName = originFilename.substring(originFilename.lastIndexOf("."),originFilename.length());
            long size = vo.getImage().getSize();
            String saveFileName = genSaveFileName(extName);

            System.out.println("uploadpath : " + uploadPath);

            System.out.println("originFilename : " + originFilename);
            System.out.println("extensionName : " + extName);
            System.out.println("size : " + size);
            System.out.println("saveFileName : " + saveFileName);

            if(!vo.getImage().isEmpty())
            {
                File file = new File(uploadPath, saveFileName);
                if(!file.exists()) // 해당 경로가 없을 경우
                    file.mkdirs();  // 폴더 생성
                vo.getImage().transferTo(file);
                String url = "https://api.exitobiz.co.kr/img/banner/"+saveFileName;
                vo.setBannerImg("https://api.exitobiz.co.kr/img/banner/"+saveFileName);
            }
            bannerService.saveNewBanner(vo);

        }catch(Exception e)
        {
            System.out.println(e);
        }
    }

    @ResponseBody
    @PutMapping("/banner")
    public void updateBanner(BannerVo vo, HttpServletRequest req) {
        try {
            if(!vo.getImage().isEmpty())
            {
                String uploadPath = "/var/upload/img/banner";
                String originFilename = vo.getImage().getOriginalFilename();
                String extName = originFilename.substring(originFilename.lastIndexOf("."),originFilename.length());
                long size = vo.getImage().getSize();
                String saveFileName = genSaveFileName(extName);
                System.out.println("uploadpath : " + uploadPath);
                System.out.println("originFilename : " + originFilename);
                System.out.println("extensionName : " + extName);
                System.out.println("size : " + size);
                System.out.println("saveFileName : " + saveFileName);

                File file = new File(uploadPath, saveFileName);
                if(!file.exists()) // 해당 경로가 없을 경우
                    file.mkdirs();  // 폴더 생성
                vo.getImage().transferTo(file);
                String url = "https://api.exitobiz.co.kr/img/banner/"+saveFileName;
                vo.setBannerImg(url);
            }else {
                vo.setBannerImg(null);
            }
            bannerService.updateBanner(vo);
        }catch(Exception e)
        {
            System.out.println(e);
        }
    }

    @PutMapping("/updateGrid")
    @ResponseBody
    public void updateBannerGrid(@RequestBody List<BannerVo> vos){
        bannerService.updateBannerGrid(vos);
    }

    @GetMapping("/getBannerList")
    @ResponseBody
    public List<BannerVo> getBannerList(@RequestParam HashMap<String, Object> params){
        return bannerService.getBannerList(params);
    }

    @DeleteMapping("/banner")
    @ResponseBody
    public void deleteBanner(@RequestParam HashMap<String, String> params) throws Exception {
        String filePath = "/var/upload/img" + params.get("filePath");  // 삭제할 파일 경로
        String fileName = params.get("fileName"); // 삭제할 파일명
        String result = null; // 결과 메세지
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
        }
        System.out.println(result);
        bannerService.deleteBanner(params);
    }

    private String genSaveFileName(String extName) {
        String fileName = "";

        Calendar calendar = Calendar.getInstance();
        fileName += calendar.get(Calendar.YEAR);
        fileName += calendar.get(Calendar.MONTH);
        fileName += calendar.get(Calendar.DATE);
        fileName += calendar.get(Calendar.HOUR);
        fileName += calendar.get(Calendar.MINUTE);
        fileName += calendar.get(Calendar.SECOND);
        fileName += calendar.get(Calendar.MILLISECOND);
        fileName += extName;

        return fileName;
    }
}

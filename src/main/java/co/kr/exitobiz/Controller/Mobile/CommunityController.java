package co.kr.exitobiz.Controller.Mobile;

import co.kr.exitobiz.Service.Mobile.CommunityService;
import co.kr.exitobiz.Service.Mobile.FileService;
import co.kr.exitobiz.Vo.Cms.ImageLink;
import co.kr.exitobiz.Vo.Mobile.CommunityVo;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import javax.validation.Valid;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mobile")
public class CommunityController {

    private final CommunityService communityService;

    private final FileService fileService;

    //상세페이지
    @RequestMapping(value = "/community/detail", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
    public String disCommunity(@Valid HttpServletRequest req, Model model, HttpServletResponse res) throws Exception {
        int id = Integer.parseInt(req.getParameter("id"));

        model.addAttribute("id", id);
        model.addAttribute("userId", req.getParameter("userId"));

        // 저장된 쿠키 불러오기
        Cookie cookies[] = req.getCookies();
        Map mapCookie = new HashMap();
        if(req.getCookies() != null){
            for(int i=0; i<cookies.length; i++){
                Cookie obj = cookies[i];
                mapCookie.put(obj.getName(), obj.getValue());
            }
        }

        //저장된 쿠키 중에 readCount만 불러오기
        String readCount = (String) mapCookie.get("replyCount");

        //저장된 새로운 쿠키값 생성
        String newReadCount = "|" + id;

        // 저장된 쿠키에 새로운 쿠키값이 존재하는 지 검사
        if(readCount == null || readCount.indexOf(newReadCount) == -1){
            // 없을 경우 쿠키 생성
            Cookie cookie = new Cookie("replyCount", readCount + newReadCount);

            res.addCookie(cookie);

            communityService.reviewViews(id); // 조회수 업데이트
        }
//        if(StringUtils.indexOfIgnoreCase(readCount, newReadCount) == -1){
//
//        }

        return "/community/community2";
    }

    //수정, 등록페이지
    @RequestMapping(value = "/community/edit", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
    public String disCommunityEdit(@Valid HttpServletRequest request, Model model){
        model.addAttribute("id", request.getParameter("id"));
        model.addAttribute("userId", request.getParameter("userId"));
        return "/community/community";
    }

    @GetMapping("/community/all")
    @ResponseBody
    public List<CommunityVo> getCommunityList(@RequestParam HashMap<String, String> params) throws ParseException {
        return communityService.getCommunityList(params);
    }

    @GetMapping("/community/one")
    @ResponseBody
    public HashMap<String, Object> getCommunityDetail(CommunityVo communityVo) throws ParseException {
        return communityService.getCommunityDetail(communityVo);
    }

    @PostMapping("/community")
    @ResponseBody
    public int createCommunity(CommunityVo communityVo) throws ParseException {
        communityService.insertCommunity(communityVo);
        return communityService.getNewId();
    }

    @PutMapping("/community/edit")
    @ResponseBody
    public void editCommunity(CommunityVo communityVo) throws ParseException {
            communityService.updateCommunity(communityVo);
    }

    @RequestMapping(value = "/community/delete", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    @ResponseBody
    public void delCommunity(@RequestBody @Valid CommunityVo communityVo) throws Exception{
        communityService.deleteCommunity(communityVo);
    }

    @PutMapping("/community/declare")
    @ResponseBody
    public void decCommunity(CommunityVo communityVo) throws ParseException {
        communityService.declareCommunity(communityVo);
    }

    // ckeditor 이미지 업로드
    @PostMapping(value = "/community/uploadImg")
    @ResponseBody
    public ImageLink uploadImage(@RequestParam("file") MultipartFile file) throws Exception{
        StringBuilder ImgPath = new StringBuilder();
        ImageLink link = new ImageLink();

        if (!file.isEmpty()) {
            String fileName = fileService.fileNameGenerator(file);
            String formatName = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            HashMap<String, String> params = new HashMap<>();
            params.put("filePath", "/img/community/");
            params.put("fileName", fileName);

            System.out.println("########" +  fileName);

            ImgPath.append("https://exitobiz.co.kr/img/community/").append(fileName);

            try {
                fileService.uploadFile(file, params);
                //resizeImageFile(file, String.valueOf(ImgPath), formatName);
                link.setLink(ImgPath.toString());
                link.setFileName(fileName);
                return link;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    // 이미지 크기 줄이기
    private void resizeImageFile(MultipartFile files, String filePath, String formatName) throws Exception {
        // 이미지 읽어 오기
        BufferedImage inputImage = ImageIO.read(files.getInputStream());
        // 이미지 세로 가로 측정
        int originWidth = inputImage.getWidth();
        int originHeight = inputImage.getHeight();
        // 변경할 가로 길이
        int newWidth = originWidth / 10;

        if (originWidth > newWidth) {
            // 기존 이미지 비율을 유지하여 세로 길이 설정
            int newHeight = (originHeight * newWidth) / originWidth;
            // 이미지 품질 설정
            // Image.SCALE_DEFAULT : 기본 이미지 스케일링 알고리즘 사용
            // Image.SCALE_FAST : 이미지 부드러움보다 속도 우선
            // Image.SCALE_REPLICATE : ReplicateScaleFilter 클래스로 구체화 된 이미지 크기 조절 알고리즘
            // Image.SCALE_SMOOTH : 속도보다 이미지 부드러움을 우선
            // Image.SCALE_AREA_AVERAGING : 평균 알고리즘 사용
            Image resizeImage = inputImage.getScaledInstance(newWidth, newHeight, Image.SCALE_FAST);
            BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
            Graphics graphics = newImage.getGraphics();
            graphics.drawImage(resizeImage, 0, 0, null);
            graphics.dispose();
            // 이미지 저장
            File newFile = new File(filePath);
            ImageIO.write(newImage, formatName, newFile);
        } else {
            files.transferTo(new java.io.File(filePath));
        }
    }



}

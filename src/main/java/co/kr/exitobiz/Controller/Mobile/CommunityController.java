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

   // @PostMapping("/community")
    @RequestMapping(value = "/community", method = RequestMethod.POST)
    @ResponseBody
    public int createCommunity(@Valid CommunityVo communityVo) throws ParseException {
        communityService.insertCommunity(communityVo);
        return communityService.getNewId();
    }

    //@PutMapping("/community/edit")
    @RequestMapping(value = "/community/edit", method = RequestMethod.PUT)
    @ResponseBody
    public void editCommunity(@Valid CommunityVo communityVo) throws ParseException {
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
    @PostMapping(value = "/community/UploadImg")
    @ResponseBody
    public ImageLink uploadImg(@RequestParam("file") MultipartFile file) throws IOException {
        StringBuilder ImgPath = new StringBuilder();
        ImageLink link = new ImageLink();

        if (!file.isEmpty()) {
            String fileName = fileService.fileNameGenerator(file);
            HashMap<String, String> params = new HashMap<>();
            params.put("filePath", "/img/community/");
            params.put("fileName", fileName);

            ImgPath.append("https://exitobiz.co.kr/img/community/").append(fileName);
            imageResize(ImgPath);

            try {
                fileService.uploadFile(file, params);
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

    /* 파일 정보와 리사이즈 값 정하는 메소드 */
    public static void imageResize(StringBuilder imgPath) throws IOException {
        File file = new File(String.valueOf(imgPath));
        InputStream inputStream = new FileInputStream(file);
        Image img = new ImageIcon(file.toString()).getImage(); // 파일 정보 추출

        System.out.println("사진의 가로길이 : " + img.getWidth(null)); // 파일의 가로
        System.out.println("사진의 세로길이 : " + img.getHeight(null)); // 파일의 세로
        /* 파일의 길이 혹은 세로길이에 따라 if(분기)를 통해서 응용할 수 있습니다.
         * '예를 들어 파일의 가로 해상도가 1000이 넘을 경우 1000으로 리사이즈 한다. 같은 분기' */
        int width = img.getWidth(null) / 10; // 리사이즈할 가로 길이
        int height = img.getWidth(null) / 10; // 리사이즈할 세로 길이

        BufferedImage resizedImage = resize(inputStream ,width , height );
        // 리사이즈 실행 메소드에 값을 넘겨준다.
    }

    /* 리사이즈 실행 메소드 */
    public static BufferedImage resize(InputStream inputStream, int width, int height)
            throws IOException {

        BufferedImage inputImage = ImageIO.read(inputStream);  // 받은 이미지 읽기

        BufferedImage outputImage = new BufferedImage(width, height, inputImage.getType());
        // 입력받은 리사이즈 길이와 높이

        Graphics2D graphics2D = outputImage.createGraphics();
        graphics2D.drawImage(inputImage, 0, 0, width, height, null); // 그리기
        graphics2D.dispose(); // 자원해제

        return outputImage;
    }


}

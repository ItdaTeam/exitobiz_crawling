package co.kr.exitobiz.Controller.Cms;

import co.kr.exitobiz.Entity.Content;
import co.kr.exitobiz.Entity.Notice;
import co.kr.exitobiz.Entity.Popup;
import co.kr.exitobiz.Service.Cms.ContentService;
import co.kr.exitobiz.Service.Mobile.FileService;
import co.kr.exitobiz.Vo.Cms.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cms")
public class ContentController {

    private final ContentService contentService;

    private final FileService fileService;

    @GetMapping("/popup")
    public String disPopUp(Model model) {

        PopupVo popupVo = contentService.getInfo();

        model.addAttribute("totalCnt", popupVo.getTotalCnt());
        model.addAttribute("activeCnt", popupVo.getActiveCnt());

        return "/cms/content/popup";
    }

    @GetMapping("/notice")
    public String disNotice(Model model){
        NoticeVo noticeVo = contentService.getNoticeInfo();

        model.addAttribute("totalCnt", noticeVo.getTotalCnt());
        model.addAttribute("mustCnt", noticeVo.getMustCnt());

        return "/cms/content/notice";
    }

    @GetMapping("/content")
    public String disContent(Model model)
    {
        ContentVo contentVo = contentService.getContentInfo();

        model.addAttribute("inCnt", contentVo.getInCnt());
        model.addAttribute("outCnt", contentVo.getOutCnt());

        return "/cms/content/content";
    }

    @PostMapping("/content")
    @ResponseBody
    public void createContent(ContentVo contentVo) {
        contentService.createContent(contentVo);
    }

    @PutMapping("/content")
    @ResponseBody
    public void editContent(ContentVo contentVo) {
        contentService.editContent(contentVo);
    }

    @GetMapping("/content/api")
    @ResponseBody
    public List<Content> getContent(SearchVo searchVo) throws ParseException {
        return contentService.getContent(searchVo);
    }

    @PostMapping("/notice")
    @ResponseBody
    public void createNotice(NoticeVo noticeVo) throws ParseException {
        contentService.createNotice(noticeVo);
    }

    @PutMapping("/notice")
    @ResponseBody
    public void editNotice(NoticeVo noticeVo) throws ParseException{
        contentService.editNotice(noticeVo);
    }

    @DeleteMapping("/notice")
    @ResponseBody
    public void deleteNotice(NoticeVo noticeVo) {
        contentService.deleteNotice(noticeVo);
    }

    @GetMapping("/notice/api")
    @ResponseBody
    public List<Notice> getNotice(SearchVo searchVo) throws ParseException {
        return contentService.getNotice(searchVo);
    }

    @PostMapping("/popup")
    @ResponseBody
    public void createPop(PopupVo popupVo) {
        contentService.createPopup(popupVo);
    }

    @PutMapping("/popup")
    @ResponseBody
    public void editPop(PopupVo popupVo) throws ParseException{
        contentService.editPopup(popupVo);
    }

    @GetMapping("/popup/api")
    @ResponseBody
    public List<Popup> getData(SearchVo searchVo) throws ParseException {
        return contentService.getData(searchVo);
    }

    @PostMapping(value = "/ckeditor")
    @ResponseBody
    public ImageLink uploadImage(@RequestParam("file") MultipartFile file){
        StringBuilder ImgPath = new StringBuilder();
        ImageLink link = new ImageLink();

        if (!file.isEmpty()) {
            String fileName = fileService.fileNameGenerator(file);
            HashMap<String, String> params = new HashMap<>();
            params.put("filePath", "/img/editor/");
            params.put("fileName", fileName);

            ImgPath.append("https://dev.exitobiz.co.kr:8443/img/editor/").append(fileName);
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

}

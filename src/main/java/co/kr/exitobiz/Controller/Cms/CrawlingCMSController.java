package co.kr.exitobiz.Controller.Cms;

import co.kr.exitobiz.Entity.Crawling;
import co.kr.exitobiz.Service.Cms.CrawlingService;
import co.kr.exitobiz.Vo.Cms.SearchVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping("/cms/crawling")
@RequiredArgsConstructor
public class CrawlingCMSController {

    private final CrawlingService crawlingService;

    @GetMapping("/allCrawling")
    @ResponseBody
    public List<Crawling> getSupportData(SearchVo searchVo) throws ParseException{
        return crawlingService.searchCrawling(searchVo);
    }
}

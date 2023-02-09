package co.kr.exitobiz.Service.Cms;

import co.kr.exitobiz.Entity.Crawling;
import co.kr.exitobiz.Vo.Cms.SearchVo;

import java.text.ParseException;
import java.util.List;

public interface CrawlingService {;

    public List<Crawling> findByIdx(Integer idx);

    public List<Crawling> searchCrawling(SearchVo searchVo) throws ParseException;


}

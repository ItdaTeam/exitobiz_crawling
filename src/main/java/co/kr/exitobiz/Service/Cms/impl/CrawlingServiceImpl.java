package co.kr.exitobiz.Service.Cms.impl;

import co.kr.exitobiz.Entity.Crawling;
import co.kr.exitobiz.Mappers.Cms.CrawlingRepository;
import co.kr.exitobiz.Service.Cms.CrawlingService;
import co.kr.exitobiz.Util.Util;
import co.kr.exitobiz.Vo.Cms.SearchVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CrawlingServiceImpl implements CrawlingService {

    private final CrawlingRepository crawlingRepository;

    @Override
    public List<Crawling> findByIdx(Integer idx) {
        return null;
    }

    @Override
    public List<Crawling> searchCrawling(SearchVo searchVo) throws ParseException {
        if(searchVo.getInq() != null){
            if(searchVo.getCon().equals("index"))
                searchVo.setListInq(Util.makeForeach(searchVo.getInq(), " "));
            else
                searchVo.setListInq(Util.makeForeach(searchVo.getInq(), ","));
        }

        return crawlingRepository.searchCrawling(searchVo);
    }

}

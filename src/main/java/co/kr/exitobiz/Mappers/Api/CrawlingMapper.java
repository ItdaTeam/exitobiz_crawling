package co.kr.exitobiz.Mappers.Api;

import co.kr.exitobiz.Vo.Cms.SupportVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface CrawlingMapper {
    public void create(List<SupportVo> supportVos);  //지원사원 삽입

    public boolean isUrl(HashMap<String, String> params); //URL 중복체크

    public void createMaster(SupportVo supportVo); //크롤링 마스터 삽입

    public void removeLNew(); //지원사업 리스트 N 제거

    public void removeRNew(); //지원사업 리스트 NEW 제거

    public void removeBlank(); //지원사업 앞 공백 제거
}

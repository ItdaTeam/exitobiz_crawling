package co.kr.exitobiz.Mappers.Cms;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;

@Mapper
public interface TermsMapper {
    public String getTermService();

    public void saveTermService(HashMap<String,String> params);

    public String getTermPrivacy();

    public void saveTermPrivacy(HashMap<String,String> params);

    public String getTermLocate();

    public void saveTermLocate(HashMap<String,String> params);

    public String getPersonMobile();
}

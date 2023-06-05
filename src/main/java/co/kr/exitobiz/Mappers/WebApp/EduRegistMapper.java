package co.kr.exitobiz.Mappers.WebApp;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;

@Mapper
public interface EduRegistMapper {

    public void saveEduRegist(HashMap<String, Object> params); // 교육 신청

    public HashMap chkRegist(HashMap<String, Object> params); // 신청 확인

}

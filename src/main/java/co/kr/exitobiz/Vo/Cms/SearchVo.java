package co.kr.exitobiz.Vo.Cms;

import lombok.Data;

import java.util.List;

@Data
public class SearchVo {
    String inq;
    String con;
    List<String> listInq;
    String viewType;
    String viewNum;
    String from;
    String to;
}

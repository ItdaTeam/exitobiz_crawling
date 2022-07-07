package co.kr.exitobiz.Vo.Crawling;

import lombok.Data;

@Data
public class CeciCrawVo {
    private String baseUrl;
    private int page;
    private String title;
    private String locCode;

    public CeciCrawVo(String title, String baseUrl, int page, String locCode){
        this.title = title;
        this.baseUrl = baseUrl;
        this.page = page;
        this.locCode = locCode;
    }
}

package co.kr.exitobiz.Vo.Crawling;

import lombok.Data;

@Data
public class CeciCrawVo {
    private String baseUrl;
    private int page;
    private String targetName;
    private String locCode;

    public CeciCrawVo(String targetName, String baseUrl, int page, String locCode){
        this.targetName = targetName;
        this.baseUrl = baseUrl;
        this.page = page;
        this.locCode = locCode;
    }
}

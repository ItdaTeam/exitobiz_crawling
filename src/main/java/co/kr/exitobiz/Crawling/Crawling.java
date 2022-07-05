package co.kr.exitobiz.Crawling;

public interface Crawling {

    String url = "";
    int page = 10;
    boolean action = false;

    void setPage(int page);
    void craw() throws InterruptedException;


}

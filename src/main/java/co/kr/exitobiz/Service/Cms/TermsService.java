package co.kr.exitobiz.Service.Cms;

import java.util.HashMap;

public interface TermsService {
    public String getTermService();

    public void saveTermService(HashMap<String,String> params);

    public String getTermPrivacy();

    public void saveTermPrivacy(HashMap<String,String> params);

    public String getTermLocate();

    public String getPersonMobile();

    public void saveTermLocate(HashMap<String,String> params);
    
    public String getTermMarket();

    public void saveTermMarket(HashMap<String,String> params);
}

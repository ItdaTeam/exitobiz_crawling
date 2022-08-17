package co.kr.exitobiz.Vo.Api;

public class KakaoVo {
    
    private String userId;
   
    private String userName;
    private String price;
    private String endDate;
    private String title;
    private String userHp;
    private String contentId;


    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getContentId() {
        return contentId;
    }
    public void setContentId(String contentId) {
        this.contentId = contentId;
    }
    public String getUserHp() {
        return userHp;
    }
    public void setUserHp(String userHp) {
        this.userHp = userHp;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    

}

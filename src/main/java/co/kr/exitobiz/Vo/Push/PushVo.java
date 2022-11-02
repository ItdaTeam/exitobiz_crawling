package co.kr.exitobiz.Vo.Push;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class PushVo {
    
    String userId; //사용자 아이디
    String siTitle; // 지원사업 공고 제목
    String keyword; //푸쉬용 키워드
    String usertoken; //사용자 토큰
    String restDate; // 마감 남은 일자

    // 마감임박용
    String mbAddidx; // 지원사업 PK
}

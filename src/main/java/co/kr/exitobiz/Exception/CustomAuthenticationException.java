package co.kr.exitobiz.Exception;

public class CustomAuthenticationException extends RuntimeException{

    public CustomAuthenticationException() {
        super(ErrorCode.INVALID_JWT_TOKEN.getMessage());
    }

    public CustomAuthenticationException(Exception ex){
        super(ex);
    }
}

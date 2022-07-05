package co.kr.exitobiz.Core;

public interface AuthToken<T> {
    boolean validate();
    T getData();
}

package next.support.context;

/**
 *  체크 예외 : 컴파일 시점에 예외처리하지 않으면 컴파일 에러 발생
 *  - 사용자에게 에러 메시지를 전달해야하거나,
 *  - 사용자에게 다른 작업을 유도하는 경우 사용
 */
public class CannotDeleteException extends Exception {
    public CannotDeleteException(String message) {
        super(message);
    }
}

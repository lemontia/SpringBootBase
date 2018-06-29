package kr.demonic.util.error;

public class CustomException extends Exception {
    private String ERR_CODE = "";

    /**
     * 메세지만 받음
     * @param msg
     */
    public CustomException(String msg){
        super(msg);
    }

    /**
     * 코드와 메세지를 둘다 받음
     * @param errCode
     * @param msg
     */
    public CustomException(String errCode, String msg){
        super(msg);
        ERR_CODE = errCode;
    }
}

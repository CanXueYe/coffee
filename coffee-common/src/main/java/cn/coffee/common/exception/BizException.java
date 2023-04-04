package cn.coffee.common.exception;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

/**
 * @author lhz
 * @Title: 业务异常类
 * @Description:
 */
public class BizException extends AppException {

    private static final long serialVersionUID = -1L;

    /**
     * 异常编号如： 00001
     */
    protected String errorCode;

    /**
     * 开发人员排除的异常信息
     */
    protected String devMsg;
    protected Object[] devMsgParams;

    protected Integer httpStatus;

    /**
     * 客户异常信息查看
     */
    protected String userMsg;
    protected Object[] userMsgParams;


    public BizException(Throwable cause, String errorCode, String devMsg, Object[] devMsgParams, String userMsg, Object[] userMsgParams, int httpStatus) {
        super(devMsg, cause);
        this.errorCode = errorCode;
        this.devMsg = devMsg;
        this.devMsgParams = devMsgParams;
        this.userMsg = userMsg;
        this.userMsgParams = userMsgParams;
        this.httpStatus = Integer.valueOf(httpStatus);
    }

    public BizException(String errorCode, String devMsg, Object[] devMsgParams, String userMsg, Object[] userMsgParams, int httpStatus) {
        this(null, errorCode, devMsg, devMsgParams, userMsg, userMsgParams, httpStatus);
    }

    public BizException(String errorCode, String devMsg, String userMsg, int httpStatus) {
        this(null, errorCode, devMsg, null, userMsg, null, httpStatus);
    }

    public BizException(String devMsg, Object[] devMsgParams, String userMsg, Object[] userMsgParams, int httpStatus) {
        this(null, null, devMsg, devMsgParams, userMsg, userMsgParams, httpStatus);
    }

    public BizException(String devMsg, Object[] devMsgParams) {
        this(null, null, devMsg, devMsgParams, null, null, HttpStatus.BAD_REQUEST.value());
    }

    public BizException(String devMsg) {
        this(null, null, devMsg, null, null, null, HttpStatus.BAD_REQUEST.value());
    }

    public BizException() {
        super();
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getDevMsg() {
        return devMsg;
    }

    public void setDevMsg(String devMsg) {
        this.devMsg = devMsg;
    }

    public Object[] getDevMsgParams() {
        return devMsgParams;
    }

    public void setDevMsgParams(Object[] devMsgParams) {
        this.devMsgParams = devMsgParams;
    }

    public String getUserMsg() {
        return userMsg;
    }

    public void setUserMsg(String userMsg) {
        this.userMsg = userMsg;
    }

    public Object[] getUserMsgParams() {
        return userMsgParams;
    }

    public void setUserMsgParams(Object[] userMsgParams) {
        this.userMsgParams = userMsgParams;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpStatus httpStatus() {
        if (httpStatus == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(this.httpStatus);
    }
    public String getCode()
    {
        return getErrorCode();
    }

    public String getCodeDesc()
    {
        String msg = null;
        if (!StringUtils.isBlank(this.devMsg)) {
            msg = this.devMsg;
        } else if (!StringUtils.isBlank(this.userMsg)) {
            msg = this.userMsg;
        }
        return msg;
    }

    public Object[] getCodeParams()
    {
        if ((this.devMsgParams != null) && (this.devMsgParams.length > 0)) {
            return this.devMsgParams;
        }
        return this.userMsgParams;
    }
}
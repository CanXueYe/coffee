package cn.coffee.common.exception;

import org.apache.commons.lang3.StringUtils;
import org.springside.modules.utils.base.ExceptionUtil;

/**
 * @author lhz
 * @Title:
 * @Description:
 */
public abstract class AppException extends RuntimeException {
    public AppException() {
        super();
    }

    public AppException(Throwable cause)
    {
        super(cause);
    }

    public AppException(String message) {
        super(message);
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }


    private static final long serialVersionUID = 9219397056955424456L;

    public static final int UNKNOWN_EXCEPTION = 0;

    public static final int BIZ_EXCEPTION = 1;
    public static final int TIMEOUT_EXCEPTION = 2;
    public static final int FORBIDDEN_EXCEPTION = 3;
    public static final int CISYS_EXCEPTION = 4;

    private int typecode = BIZ_EXCEPTION;// CIBaseException，异常类型用tpecode表示
    private String errCode;

    public String getMessage()
    {
        if (StringUtils.isBlank(super.getMessage()))
        {
            StringBuilder sb = new StringBuilder();
            sb.append("code=" + getCode());
            if (!StringUtils.isBlank(getCodeDesc())) {
                sb.append(", ").append(getCodeDesc());
            }
            if ((getCodeParams() != null) && (getCodeParams().length > 1))
            {
                sb.append(", params=[");
                for (int i = 0; i < getCodeParams().length; i++)
                {
                    Object param = getCodeParams()[i];
                    if (i > 0) {
                        sb.append(",");
                    }
                    sb.append(param);
                }
                sb.append("]");
            }
            return sb.toString();
        }
        return super.getMessage();
    }

    public abstract String getCode();

    public abstract String getCodeDesc();

    public abstract Object[] getCodeParams();

    public String getStackLog()
    {
        if (super.getCause() != null)
        {
            Throwable cause = super.getCause();
            String stackLog = ExceptionUtil.toStringWithShortName(cause);
            return stackLog;
        }
        return null;
    }

    public String toString()
    {
        String msg = null;
        String code = getCode();
        if ((code != null) && (!"".equals(code.trim()))) {
            msg = code;
        }
        String message = getLocalizedMessage();
        if ((message != null) && (!"".equals(message.trim())))
        {
            if (msg != null) {
                msg = msg + "\t";
                msg = msg + message;
            }else{
                msg = message;
            }
        }
        String s = getClass().getName();
        return msg != null ? s + ": " + msg : s;
    }
}

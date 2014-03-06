package taglib;

import org.springframework.context.NoSuchMessageException;
import org.springframework.web.servlet.tags.MessageTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

/**
 * @author Kamill Sokol dev@sokol-web.de
 */
public class SwitchableMessageTag extends MessageTag {

    private String code;

    @Override
    protected String resolveMessage() throws JspException, NoSuchMessageException {
        if(showMessageKeys() && hasPermission()) {
            return this.code;
        }
        return super.resolveMessage();
    }

    protected boolean showMessageKeys() {
        //decision whether message keys should be shown or not can be everything
        //in this example it is computed on a per request basis
        HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
        String value = req.getParameter("messagekeys");
        if(value instanceof String && "enabled".equals(value)) {
            return true;
        }
        return false;
    }

    protected boolean hasPermission() {
        //check if current principal has permission to inspect message keys
        return true;
    }

    @Override
    public void setCode(String code) {
        super.setCode(code);
        this.code = code;
    }
}

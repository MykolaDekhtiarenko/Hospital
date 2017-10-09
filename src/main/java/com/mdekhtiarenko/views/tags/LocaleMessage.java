package com.mdekhtiarenko.views.tags;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Created by mykola.dekhtiarenko on 04.10.17.
 */
public class LocaleMessage extends TagSupport {
    private String key;
    private String bundle;

    public void setKey(String key) {
        this.key = key;
    }

    public LocaleMessage(String bundle) {
        this.bundle = bundle;
    }

    public int doStartTag() {
        JspWriter out = pageContext.getOut();
        ResourceBundle resourceBundle = ResourceBundle.getBundle(bundle);
        try {
            out.print(resourceBundle.getString(key));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SKIP_BODY;
    }

    public int doEndTag() {
        return SKIP_PAGE;
    }
}

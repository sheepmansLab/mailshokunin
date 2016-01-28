package jp.sheepman.mailshokunin.form;

import jp.sheepman.common.form.BaseForm;

public class F001TemplateForm extends BaseForm{
    private int template_id;
    private String template_name;

    public String getTemplate_name() {
        return template_name;
    }

    public void setTemplate_name(String template_name) {
        this.template_name = template_name;
    }

    public int getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(int template_id) {
        this.template_id = template_id;
    }

}

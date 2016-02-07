package jp.sheepman.mailshokunin.form;

import jp.sheepman.common.form.BaseForm;

public class F002LayoutListForm extends BaseForm{
    private int layout_id;
    private String layout_name;

    public String getLayout_name() {
        return layout_name;
    }

    public void setLayout_name(String layout_name) {
        this.layout_name = layout_name;
    }

    public int getLayout_id() {
        return layout_id;
    }

    public void setLayout_id(int layout_id) {
        this.layout_id = layout_id;
    }

}

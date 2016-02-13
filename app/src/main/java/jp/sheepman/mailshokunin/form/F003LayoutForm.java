package jp.sheepman.mailshokunin.form;

import jp.sheepman.common.form.BaseForm;

public class F003LayoutForm extends BaseForm{
    private int layout_id;
    private String layout_name;
    private int to_list_id;
    private int cc_list_id;
    private int bcc_list_id;

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

    public int getTo_list_id() {
        return to_list_id;
    }

    public void setTo_list_id(int to_list_id) {
        this.to_list_id = to_list_id;
    }

    public int getCc_list_id() {
        return cc_list_id;
    }

    public void setCc_list_id(int cc_list_id) {
        this.cc_list_id = cc_list_id;
    }

    public int getBcc_list_id() {
        return bcc_list_id;
    }

    public void setBcc_list_id(int bcc_list_id) {
        this.bcc_list_id = bcc_list_id;
    }
}

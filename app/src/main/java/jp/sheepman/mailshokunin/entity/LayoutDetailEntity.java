package jp.sheepman.mailshokunin.entity;

import jp.sheepman.common.entity.BaseEntity;

public class LayoutDetailEntity extends BaseEntity{
    private int layout_id;
    private int layout_seq;
    private int object_id;
    private String object_value;
    private int object_type_id;
    private String object_type_name;
    private String object_class;

    public int getLayout_id() {
        return layout_id;
    }

    public void setLayout_id(int layout_id) {
        this.layout_id = layout_id;
    }

    public int getLayout_seq() {
        return layout_seq;
    }

    public void setLayout_seq(int layout_seq) {
        this.layout_seq = layout_seq;
    }

    public int getObject_id() {
        return object_id;
    }

    public void setObject_id(int object_id) {
        this.object_id = object_id;
    }

    public String getObject_value() {
        return object_value;
    }

    public void setObject_value(String object_value) {
        this.object_value = object_value;
    }

    public int getObject_type_id() {
        return object_type_id;
    }

    public void setObject_type_id(int object_type_id) {
        this.object_type_id = object_type_id;
    }

    public String getObject_type_name() {
        return object_type_name;
    }

    public void setObject_type_name(String object_type_name) {
        this.object_type_name = object_type_name;
    }

    public String getObject_class() {
        return object_class;
    }

    public void setObject_class(String object_class) {
        this.object_class = object_class;
    }
}

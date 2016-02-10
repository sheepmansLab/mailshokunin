package jp.sheepman.mailshokunin.entity;

import jp.sheepman.common.entity.BaseEntity;

public class ObjectsEntity extends BaseEntity{
    @MaxIdTargetColumn
    private int object_id;
    private String object_name;
    private String object_value;
    private int object_type_id;

    public int getObject_id() {
        return object_id;
    }

    public void setObject_id(int object_id) {
        this.object_id = object_id;
    }

    public String getObject_name() {
        return object_name;
    }

    public void setObject_name(String object_name) {
        this.object_name = object_name;
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
}

package jp.sheepman.mailshokunin.entity;

import jp.sheepman.common.entity.BaseEntity;

public class ObjectTypeEntity extends BaseEntity{
    @MaxIdTargetColumn
    private int object_type_id;
    private String object_type_name;
    private String object_type_class;

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

    public String getObject_type_class() {
        return object_type_class;
    }

    public void setObject_type_class(String object_type_class) {
        this.object_type_class = object_type_class;
    }
}

package jp.sheepman.mailshokunin.entity;

import jp.sheepman.common.entity.BaseEntity;

public class LayoutDetailEntity extends BaseEntity{
    @MaxIdTargetColumn
    private int layout_id;
    private int layout_seq;
    private int object_id;

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
}

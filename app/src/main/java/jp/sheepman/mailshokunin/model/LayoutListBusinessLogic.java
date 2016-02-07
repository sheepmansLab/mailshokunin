package jp.sheepman.mailshokunin.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import jp.sheepman.common.entity.BaseEntity;
import jp.sheepman.common.util.DatabaseUtil;
import jp.sheepman.mailshokunin.entity.LayoutEntity;
import jp.sheepman.mailshokunin.form.F002LayoutListForm;

public class LayoutListBusinessLogic {
    public List<F002LayoutListForm> selectLayoutList(Context context) {
        final String sql = "SELECT layout_id, layout_name FROM t_layout";
        List<F002LayoutListForm> list = new ArrayList<>();
        List<String> params = new ArrayList<>();

        DatabaseUtil util = new DatabaseUtil(context);
        util.open();
        try {
            List<BaseEntity> entities = util.select(sql, params, LayoutEntity.class);
            for(BaseEntity e : entities){
                LayoutEntity entity = (LayoutEntity)e;
                F002LayoutListForm form = new F002LayoutListForm();
                form.setLayout_id(entity.getLayout_id());
                form.setLayout_name(entity.getLayout_name());
                list.add(form);
            }
        } finally {
            util.close();
        }
        return list;
    }
}

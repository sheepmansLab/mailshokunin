package jp.sheepman.mailshokunin.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import jp.sheepman.common.entity.BaseEntity;
import jp.sheepman.common.model.BaseModel;
import jp.sheepman.common.util.DatabaseUtil;
import jp.sheepman.mailshokunin.common.CommonConst;
import jp.sheepman.mailshokunin.entity.LayoutDetailEntity;
import jp.sheepman.mailshokunin.entity.LayoutEntity;
import jp.sheepman.mailshokunin.entity.ObjectsEntity;
import jp.sheepman.mailshokunin.form.F001LayoutForm;

public class LayoutEditBusinessLogic extends BaseModel {
    public List<F001LayoutForm> selectLayoutData(Context context, int layout_id){
        final String sql = "SELECT " +
                            "tl.layout_id " +
                            ",tl.layout_seq " +
                            ",toj.object_id " +
                            ",toj.object_value " +
                            ",tot.object_type_id " +
                            ",tot.object_type_name " +
                            ",tot.object_class " +
                            "FROM t_layout tl " +
                            "LEFT OUTER JOIN t_objects toj " +
                            "ON tl.object_id = toj.object_id " +
                            "LEFT OUTER JOIN t_object_type tot " +
                            "ON toj.object_type_id = tot.object_type_id " +
                            "WHERE tl.layout_id = ? " +
                            "ORDER BY tl.layout_seq";

        List<String> params = new ArrayList<String>();
        params.add(String.valueOf(layout_id));

        List<F001LayoutForm> list = new ArrayList<F001LayoutForm>();
        DatabaseUtil dbutil = new DatabaseUtil(context);
        try {
            dbutil.open();

            List<BaseEntity> layouts = dbutil.select(sql, params, LayoutDetailEntity.class);

            for (BaseEntity e : layouts) {
                LayoutDetailEntity l = (LayoutDetailEntity) e;
                F001LayoutForm form = new F001LayoutForm();
                form.setLayout_id(l.getLayout_id());
                form.setObject_id(l.getObject_id());
                form.setObject_type_id(l.getObject_type_id());
                form.setObject_class(l.getObject_class());
                form.setObject_value(l.getObject_value());
                list.add(form);
            }
        } finally {
            dbutil.close();
        }
        return list;
    }

    public void saveLayout(Context context, List<LayoutDetailEntity> entityList){
        final String WHERE_S1 = "object_id = ? ";
        final String WHERE_U1 = "object_id = ? ";
        final String WHERE_D1 = "layout_id = ? ";
        List<String> param_s1 = new ArrayList<String>();
        List<String> param_u1 = new ArrayList<String>();
        List<String> param_d1 = new ArrayList<String>();

        int layout_id = entityList.get(0).getLayout_id();

        DatabaseUtil dbUtil = new DatabaseUtil(context);
        try{
            dbUtil.open();
            if(layout_id < 1) {
                layout_id = dbUtil.maxId(CommonConst.TABLE_NAME_T_LAYOUT, LayoutEntity.class) + 1;
            } else {
                //レイアウトの削除
                param_d1.add(String.valueOf(layout_id));
                dbUtil.delete(CommonConst.TABLE_NAME_T_LAYOUT, WHERE_D1, param_d1);
            }
            //オブジェクトの保存
            for(LayoutDetailEntity entity : entityList){
                int object_id = entity.getObject_id();

                ObjectsEntity objectsEntity = new ObjectsEntity();
                objectsEntity.setObject_id(object_id);
                objectsEntity.setObject_value(entity.getObject_value());
                objectsEntity.setObject_type_id(entity.getObject_type_id());

                //object_idに紐付くデータ件数を取得
                param_s1.clear();
                param_s1.add(String.valueOf(object_id));
                int count = dbUtil.count(CommonConst.TABLE_NAME_T_OBJECTS, WHERE_S1, param_s1);
                if(count < 1){
                //存在しない場合INSERT
                    object_id = dbUtil.maxId(CommonConst.TABLE_NAME_T_OBJECTS, ObjectsEntity.class) + 1;
                    //IDを差し替える
                    objectsEntity.setObject_id(object_id);
                    dbUtil.insert(CommonConst.TABLE_NAME_T_OBJECTS, objectsEntity);
                } else {
                //存在する場合Update
                    param_u1.clear();
                    param_u1.add(String.valueOf(entity.getObject_id()));
                    dbUtil.update(CommonConst.TABLE_NAME_T_OBJECTS, WHERE_U1, objectsEntity, param_u1);
                }
                //Layoutデータの挿入
                LayoutEntity layoutEntity = new LayoutEntity();
                layoutEntity.setLayout_id(layout_id);
                layoutEntity.setLayout_seq(entity.getLayout_seq());
                layoutEntity.setObject_id(object_id);
                dbUtil.insert(CommonConst.TABLE_NAME_T_LAYOUT, layoutEntity);
            }
        } finally {
            dbUtil.close();
        }
    }
}

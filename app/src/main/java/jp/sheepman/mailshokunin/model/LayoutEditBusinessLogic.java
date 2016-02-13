package jp.sheepman.mailshokunin.model;

import android.content.Context;
import android.widget.Toast;

import com.androidquery.util.Common;

import java.util.ArrayList;
import java.util.List;

import jp.sheepman.common.entity.BaseEntity;
import jp.sheepman.common.model.BaseModel;
import jp.sheepman.common.util.DatabaseUtil;
import jp.sheepman.mailshokunin.common.CommonConst;
import jp.sheepman.mailshokunin.entity.LayoutEntity;
import jp.sheepman.mailshokunin.entity.LayoutItemEntity;
import jp.sheepman.mailshokunin.entity.LayoutDetailEntity;
import jp.sheepman.mailshokunin.entity.ObjectTypeEntity;
import jp.sheepman.mailshokunin.entity.ObjectsEntity;
import jp.sheepman.mailshokunin.form.F001LayoutForm;
import jp.sheepman.mailshokunin.form.F001LayoutItemForm;

public class LayoutEditBusinessLogic extends BaseModel {

    private final String SQL_S1 ="SELECT " +
            "layout_id" +
            ", layout_name" +
            ", to_list_id " +
            ", cc_list_id " +
            ", bcc_list_id " +
            "FROM t_layout " +
            "WHERE layout_id = ?";

    private final String SQL_S2 = "SELECT " +
            "tl.layout_id " +
            ",tl.layout_seq " +
            ",toj.object_id " +
            ",toj.object_name " +
            ",toj.object_value " +
            ",tot.object_type_id " +
            ",tot.object_type_name " +
            ",tot.object_class " +
            "FROM t_layout_detail tl " +
            "LEFT OUTER JOIN t_objects toj " +
            "ON tl.object_id = toj.object_id " +
            "INNER JOIN t_object_type tot " +
            "ON toj.object_type_id = tot.object_type_id " +
            "WHERE tl.layout_id = ? " +
            "ORDER BY tl.layout_seq";

    private final String SQL_S3 ="SELECT " +
            "object_type_id" +
            ", object_type_name" +
            ", object_class " +
            "FROM t_object_type " +
            "WHERE object_type_id = ?";

    public F001LayoutForm selectLayout(Context context, int layout_id){
        F001LayoutForm form = new F001LayoutForm();

        DatabaseUtil util = new DatabaseUtil(context);
        util.open();
        try{
            List<String> params = new ArrayList<>();
            params.add(String.valueOf(layout_id));

            List<BaseEntity> list = util.select(SQL_S1, params, LayoutEntity.class);
            if(list.size() > 0) {
                LayoutEntity entity = (LayoutEntity) list.get(0);

                form.setLayout_id(entity.getLayout_id());
                form.setLayout_name(entity.getLayout_name());
                form.setTo_list_id(entity.getTo_list_id());
                form.setCc_list_id(entity.getTo_list_id());
                form.setBcc_list_id(entity.getBcc_list_id());
            }
        } finally {
          util.close();
        }
        return form;
    }


    public List<F001LayoutItemForm> selectLayoutItems(Context context, int layout_id){

        List<String> params = new ArrayList<>();
        params.add(String.valueOf(layout_id));

        List<F001LayoutItemForm> list = new ArrayList<>();
        DatabaseUtil util = new DatabaseUtil(context);
        try {
            util.open();

            List<BaseEntity> layouts = util.select(SQL_S2, params, LayoutItemEntity.class);

            for (BaseEntity e : layouts) {
                LayoutItemEntity l = (LayoutItemEntity) e;
                F001LayoutItemForm form = new F001LayoutItemForm();
                form.setLayout_id(l.getLayout_id());
                form.setObject_id(l.getObject_id());
                form.setObject_name(l.getObject_name());
                form.setObject_type_id(l.getObject_type_id());
                form.setObject_type_name(l.getObject_type_name());
                form.setObject_class(l.getObject_class());
                form.setObject_value(l.getObject_value());
                list.add(form);
            }
        } finally {
            util.close();
        }
        return list;
    }

    public F001LayoutItemForm selectObjectTypeById(Context context, int object_id){
        F001LayoutItemForm form = new F001LayoutItemForm();
        DatabaseUtil util = new DatabaseUtil(context);
        List<String> params = new ArrayList<>();
        try {
            util.open();
            params.add(String.valueOf(object_id));
            List<BaseEntity> list = util.select(SQL_S3, params, ObjectTypeEntity.class);
            for(BaseEntity e : list){
                if(e instanceof ObjectTypeEntity){
                    form.setObject_type_id(((ObjectTypeEntity) e).getObject_type_id());
                    form.setObject_type_name(((ObjectTypeEntity) e).getObject_type_name());
                    form.setObject_class(((ObjectTypeEntity) e).getObject_class());
                }
            }
        } finally {
            util.close();
        }
        return form;
    }

    /**
     * レイアウトの保存
     * @param context
     * @param entity
     */
    public int saveLayout(Context context, LayoutEntity entity){
        final String WHERE_S1 = "layout_id = ?";
        int layout_id = 0;
        int count = 0;

        DatabaseUtil util = new DatabaseUtil(context);
        util.open();
        try {
            if(entity != null){
                layout_id = entity.getLayout_id();
                List<String> params = new ArrayList<>();
                params.add(String.valueOf(layout_id));

                //存在確認
                count = util.count(CommonConst.TABLE_NAME_T_LAYOUT, WHERE_S1, params);
                if(count == 0){
                    layout_id = util.maxId(CommonConst.TABLE_NAME_T_LAYOUT, LayoutEntity.class) + 1;
                    entity.setLayout_id(layout_id);
                    util.insert(CommonConst.TABLE_NAME_T_LAYOUT, entity);
                } else {
                    util.update(CommonConst.TABLE_NAME_T_LAYOUT, WHERE_S1, entity, params);
                }
            }
        } finally {
            util.close();
        }
        return layout_id;
    }

    public void saveLayoutItems(Context context, List<LayoutItemEntity> entityList, int layout_id){
        final String WHERE_S1 = "object_id = ? ";
        final String WHERE_U1 = "object_id = ? ";
        final String WHERE_D1 = "layout_id = ? ";
        List<String> param_s1 = new ArrayList<>();
        List<String> param_u1 = new ArrayList<>();
        List<String> param_d1 = new ArrayList<>();

        DatabaseUtil util = new DatabaseUtil(context);
        try{
            util.open();
            if(layout_id < 1) {
                layout_id = util.maxId(CommonConst.TABLE_NAME_T_LAYOUT_DETAIL, LayoutDetailEntity.class) + 1;
            } else {
                //レイアウトの削除
                param_d1.add(String.valueOf(layout_id));
                util.delete(CommonConst.TABLE_NAME_T_LAYOUT_DETAIL, WHERE_D1, param_d1);
            }
            //オブジェクトの保存
            for(LayoutItemEntity entity : entityList){
                int object_id = entity.getObject_id();

                ObjectsEntity objectsEntity = new ObjectsEntity();
                objectsEntity.setObject_id(object_id);
                objectsEntity.setObject_name(entity.getObject_name());
                objectsEntity.setObject_value(entity.getObject_value());
                objectsEntity.setObject_type_id(entity.getObject_type_id());

                //object_idに紐付くデータ件数を取得
                param_s1.clear();
                param_s1.add(String.valueOf(object_id));
                int count = util.count(CommonConst.TABLE_NAME_T_OBJECTS, WHERE_S1, param_s1);
                if(count < 1){
                //存在しない場合INSERT
                    object_id = util.maxId(CommonConst.TABLE_NAME_T_OBJECTS, ObjectsEntity.class) + 1;
                    //IDを差し替える
                    objectsEntity.setObject_id(object_id);
                    util.insert(CommonConst.TABLE_NAME_T_OBJECTS, objectsEntity);
                } else {
                //存在する場合Update
                    param_u1.clear();
                    param_u1.add(String.valueOf(entity.getObject_id()));
                    util.update(CommonConst.TABLE_NAME_T_OBJECTS, WHERE_U1, objectsEntity, param_u1);
                }
                //Layoutデータの挿入
                LayoutDetailEntity layoutDetailEntity = new LayoutDetailEntity();
                layoutDetailEntity.setLayout_id(layout_id);
                layoutDetailEntity.setLayout_seq(entity.getLayout_seq());
                layoutDetailEntity.setObject_id(object_id);
                util.insert(CommonConst.TABLE_NAME_T_LAYOUT_DETAIL, layoutDetailEntity);
            }
        } finally {
            util.close();
        }
    }
}

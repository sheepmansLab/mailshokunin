package jp.sheepman.mailshokunin.fragment;


import android.content.ClipData;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.androidquery.AQuery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.sheepman.common.form.BaseForm;
import jp.sheepman.common.fragment.BaseFragment;
import jp.sheepman.mailshokunin.R;
import jp.sheepman.mailshokunin.common.CommonConst;
import jp.sheepman.mailshokunin.entity.LayoutEntity;
import jp.sheepman.mailshokunin.entity.LayoutItemEntity;
import jp.sheepman.mailshokunin.form.F001LayoutForm;
import jp.sheepman.mailshokunin.form.F001LayoutItemForm;
import jp.sheepman.mailshokunin.model.LayoutEditBusinessLogic;
import jp.sheepman.mailshokunin.util.ViewCreaterUtil;
import jp.sheepman.mailshokunin.view.LayoutContentView;

public class F001LayoutEditFragment extends BaseFragment {

    private AQuery aq;
    private View root;

    private int mLayout_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_f001_layout_edit, container, false);
        }
        aq = new AQuery(root);

        aq.id(R.id.F001_sv_main).getView().setOnDragListener(dragListener);
        aq.id(R.id.F001_iv_text).getView().setOnTouchListener(touchListener);
        aq.id(R.id.F001_iv_edit).getView().setOnTouchListener(touchListener);
        aq.id(R.id.F001_iv_list).getView().setOnTouchListener(touchListener);
        aq.id(R.id.F001_btn_save).getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        reload();
    }

    /**
     * 画面をリロードする
     */
    private void reload(){
        //BusinessLogic
        LayoutEditBusinessLogic logic = new LayoutEditBusinessLogic();

        Bundle bundle = getArguments();
        if(bundle != null){
            if(mLayout_id == 0){
                mLayout_id = bundle.getInt(CommonConst.BUNDLE_KEY_F001_LAYOUT_ID, 0);
            }
        }
        //レイアウト情報の取得
        F001LayoutForm layoutForm = logic.selectLayout(getActivity(), mLayout_id);
        aq.id(R.id.F001_et_title).text(layoutForm.getLayout_name());
        //TODO 連絡先
        //TO
        //CC
        //BCC

        //リストの初期化
        ((LinearLayout) aq.id(R.id.F001_ll_main).getView()).removeAllViews();

        List<F001LayoutItemForm> list = logic.selectLayoutItems(getActivity(), mLayout_id);
        //Viewの配置
        for(F001LayoutItemForm form : list){
            View v = createNewItem(form);
            if(v != null) {
                if (v instanceof LayoutContentView) {
                    ((LayoutContentView) v).setText(form.getObject_value());
                    ((LayoutContentView) v).setTitle(form.getObject_type_name());
                }
                v.setOnLongClickListener(longClickListener);
                ((LinearLayout)aq.id(R.id.F001_ll_main).getView()).addView(v);
            }
        }
    }

    /**
     * レイアウトデータを保存する
     */
    private void save(){
        LayoutEditBusinessLogic logic = new LayoutEditBusinessLogic();
        LayoutEntity layoutEntity = new LayoutEntity();
        List<LayoutItemEntity> entityList = new ArrayList<>();

        layoutEntity.setLayout_id(mLayout_id);
        layoutEntity.setLayout_name(aq.id(R.id.F001_et_title).getText().toString());
//TODO 連絡先の保存
//        layoutEntity.setTo_list_id();
//        layoutEntity.setCc_list_id();
//        layoutEntity.setBcc_list_id();
        mLayout_id = logic.saveLayout(getActivity(), layoutEntity);

        LinearLayout llMain = ((LinearLayout)aq.id(R.id.F001_ll_main).getView());
        for(int i = 0; i < llMain.getChildCount(); i ++){
            //Layout内のオブジェクトを取得
            View v = llMain.getChildAt(i);
            //formデータを取得
            F001LayoutItemForm form = (F001LayoutItemForm)v.getTag();
            //保存用のEntity
            LayoutItemEntity entity = new LayoutItemEntity();
            entity.setObject_type_id(form.getObject_type_id());
            if(v instanceof LayoutContentView){
                entity.setObject_value(((LayoutContentView)v).getText());
            } else {
                entity.setObject_value(form.getObject_value());
            }
            entity.setObject_id(form.getObject_id());
            entity.setLayout_id(form.getLayout_id());
            entity.setLayout_seq(i);
            entityList.add(entity);
        }
        logic.saveLayoutItems(getActivity(), entityList);
        Toast.makeText(getActivity(),"保存しました", Toast.LENGTH_SHORT).show();
        reload();
    }

    /**
     * タッチイベントのリスナー
     */
    private View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            LayoutEditBusinessLogic logic = new LayoutEditBusinessLogic();
            F001LayoutItemForm form = logic.selectObjectTypeById(getActivity(), 1);//TODO オブジェクトタイプID
            form.setLayout_id(mLayout_id);

            View viewDrag = createNewItem(form);
            if(viewDrag != null) {
                if (viewDrag instanceof LayoutContentView) {
                    String message = new Date().toString(); //TODO
                    ((LayoutContentView) viewDrag)
                            .setTitle(form.getObject_type_name())
                            .setText(message)
                            .setDeleteButtonOnClickListener(null)
                            .setEditButtonOnClickListener(null);
                    form.setObject_value(message);
                }
                viewDrag.setTag(form);
                ClipData clipData = ClipData.newPlainText("dummy", "");
                view.startDrag(clipData, new DragShadow(view), viewDrag, 0);
            }
            return false;
        }
    };

    /**
     * 長押しイベントリスナー
     */
    private View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            ClipData clipData = ClipData.newPlainText("dummy","");
            view.startDrag(clipData, new DragShadow(view), view, 0);
            return false;
        }
    };

    /**
     * ドラッグ＆ドロップリスナー
     */
    private View.OnDragListener dragListener = new View.OnDragListener() {

        @Override
        public boolean onDrag(View view, DragEvent dragEvent) {
            boolean flg = false;
            LinearLayout llMain = (LinearLayout)aq.id(R.id.F001_ll_main).getView();
            if(dragEvent.getLocalState() instanceof View) {
                View viewDrag = (View)dragEvent.getLocalState();

                switch (dragEvent.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        Log.d("onDrag", "STARTED");
                        viewDrag.setAlpha(0.5f);
                        flg = true;
                        break;
                    case DragEvent.ACTION_DROP:
                        Log.d("onDrag", "DROP");
                        viewDrag.setAlpha(1.0f);
                        //ドロップ時の座標
                        int x = (int) dragEvent.getX();
                        int y = (int) dragEvent.getY();

                        //対象のオブジェクトを削除
                        llMain.removeView(viewDrag);
                        //入替先のポジション
                        int pos = -1;
                        //被ったオブジェクトがあるかを判断
                        for (int i = 0; i < llMain.getChildCount(); i++) {
                            View child = llMain.getChildAt(i);
                            Rect rect = new Rect();
                            child.getHitRect(rect);
                            //重なっていた場合入替
                            if (rect.contains(x, y)) {
                                //半分より上だった場合
                                if ((int) (child.getY() + (child.getHeight() / 2)) > y) {
                                    Log.d("onDrag", "UP");
                                    pos = i;
                                    //半分より下だった場合
                                } else {
                                    Log.d("onDrag", "DOWN");
                                    pos = i + 1;
                                }
                                break;
                            }
                        }
                        //重ならない場合
                        if (pos < 0) {
                            pos = llMain.getChildCount();
                        }
                        Log.d("onDrag", "POSITION:" + pos);
                        //オブジェクトを追加
                        llMain.addView(viewDrag, pos);
                        viewDrag.setOnLongClickListener(longClickListener);
                        break;
                    default:
                        break;
                }
            }
            return flg;
        }
    };

    /**
     * 新規にレイアウトのアイテムを作成する
     * @param form Form
     * @return View
     */
    private View createNewItem(F001LayoutItemForm form){
        View view = null;
        final int item_width = LinearLayout.LayoutParams.MATCH_PARENT;
        final int item_height = LinearLayout.LayoutParams.WRAP_CONTENT;
        LinearLayout.LayoutParams params
                = new LinearLayout.LayoutParams(item_width, item_height);

        if(form != null){
            view = ViewCreaterUtil.createViewObject(getActivity(), form.getObject_class());
            view.setLayoutParams(params);
            view.setTag(form);
        }

        return view;
    }

    /**
     * ドラッグ時のイメージ描画用
     */
    private class DragShadow extends View.DragShadowBuilder {
        public DragShadow(View view){
            super(view);
        }

        @Override
        public void onDrawShadow(Canvas canvas) {
            if(getView() instanceof ImageView){
                Rect viewRect = new Rect(0, 0, aq.id(R.id.F001_ll_main).getView().getWidth()
                        , getView().getHeight());

                //背景色
                Paint paint = new Paint();
                paint.setAntiAlias(true);
                paint.setStyle(Paint.Style.FILL);
                paint.setColor(Color.BLUE);
                paint.setShadowLayer(10.0f, 10.0f, 10.0f, 0xff888888);

                canvas.drawRect(viewRect, paint);
            } else {
                getView().draw(canvas);
            }
        }

        @Override
        public void onProvideShadowMetrics(Point shadowSize, Point shadowTouchPoint) {
            int margin = 20;
            //影の分の領域を含めたサイズを設定
            shadowSize.set(aq.id(R.id.F001_ll_main).getView().getWidth() + margin
                            , getView().getHeight() + margin);
            //viewの中央に設定
            shadowTouchPoint.set(getView().getWidth() / 2, getView().getHeight() / 2);
        }
    }

    @Override
    public void callback() {
    }

    @Override
    public void callback(BaseForm baseForm) {
    }
}

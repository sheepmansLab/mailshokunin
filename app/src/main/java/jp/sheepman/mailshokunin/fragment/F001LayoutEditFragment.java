package jp.sheepman.mailshokunin.fragment;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidquery.AQuery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.sheepman.common.form.BaseForm;
import jp.sheepman.common.fragment.BaseFragment;
import jp.sheepman.common.util.DatabaseUtil;
import jp.sheepman.mailshokunin.R;
import jp.sheepman.mailshokunin.common.CommonConst;
import jp.sheepman.mailshokunin.entity.LayoutDetailEntity;
import jp.sheepman.mailshokunin.form.F001LayoutForm;
import jp.sheepman.mailshokunin.model.LayoutEditBusinessLogic;
import jp.sheepman.mailshokunin.util.ViewCreaterUtil;

public class F001LayoutEditFragment extends BaseFragment {
    private AQuery aq;
    private View root;

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
        aq.id(R.id.F001_ll_main).getView().setOnDragListener(dragListener);
        aq.id(R.id.F001_iv_text).getView().setOnTouchListener(touchListener);
        aq.id(R.id.F001_iv_edit).getView().setOnTouchListener(touchListener);
        aq.id(R.id.F001_iv_list).getView().setOnTouchListener(touchListener);
        aq.id(R.id.F001_btn_save).getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });

        this.reload();

        return root;
    }

    /**
     * 画面をリロードする
     */
    private void reload(){
        LinearLayout.LayoutParams params
                = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT
                                                ,LinearLayout.LayoutParams.WRAP_CONTENT);
        //
        Bundle bundle = getArguments();
        if(bundle != null){
            aq.id(R.id.F001_et_title).text(bundle.getString(CommonConst.BUNDLE_KEY_F001_TEMPLATE_NAME));
        }
        //データのロード
        LayoutEditBusinessLogic logic = new LayoutEditBusinessLogic();
        List<F001LayoutForm> list = logic.selectLayoutData(getActivity(), 1);//TODO レイアウト番号固定
        //Viewの配置
        for(F001LayoutForm form : list){
            View v = ViewCreaterUtil.createViewObject(getActivity(), form.getObject_class());
            v.setLayoutParams(params);
            v.setTag(form);
            if(v instanceof TextView){
                ((TextView)v).setText(form.getObject_value());
            }
            ((LinearLayout)aq.id(R.id.F001_ll_main).getView()).addView(v);
        }
    }

    /**
     * レイアウトデータを保存する
     */
    private void save(){
        LayoutEditBusinessLogic logic = new LayoutEditBusinessLogic();
        List<LayoutDetailEntity> entityList = new ArrayList<LayoutDetailEntity>();

        LinearLayout llMain = ((LinearLayout)aq.id(R.id.F001_ll_main).getView());
        for(int i = 0; i < llMain.getChildCount(); i ++){
            F001LayoutForm form = (F001LayoutForm)llMain.getChildAt(i).getTag();
            LayoutDetailEntity entity = new LayoutDetailEntity();
            //entity.setObject_type_id(form.getObject_type_id());
            entity.setObject_value(form.getObject_value());
            entity.setObject_id(form.getObject_id());
            entity.setLayout_id(form.getLayout_id());
            entity.setLayout_seq(i);
            entityList.add(entity);
        }
        logic.saveLayout(getActivity(), entityList);
    }

    /**
     * タッチイベントのリスナー
     */
    private View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            View viewDrag = view;
            int width = aq.id(R.id.F001_ll_main).getView().getWidth();
            int height = aq.id(R.id.F001_iv_list).getView().getHeight();

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
            viewDrag = ViewCreaterUtil.createViewObject(getActivity(), "android.widget.TextView");//TODO
            viewDrag.setLayoutParams(params);
            viewDrag.setBackgroundColor(Color.DKGRAY);
            F001LayoutForm form = new F001LayoutForm();
            form.setObject_type_id(1);//TODO
            form.setLayout_id(1); //TODO
            viewDrag.setTag(form);

            if(viewDrag instanceof TextView){
                ((TextView) viewDrag).setText(new Date().toString());
            }
            view.startDrag(null, new DragShadow(view), viewDrag, 0);
            return false;
        }
    };

    /**
     * 長押しイベントリスナー
     */
    private View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            view.startDrag(null, new DragShadow(view), view, 0);
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

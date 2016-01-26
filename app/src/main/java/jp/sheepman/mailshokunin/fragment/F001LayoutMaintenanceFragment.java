package jp.sheepman.mailshokunin.fragment;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidquery.AQuery;

import java.util.Date;

import jp.sheepman.common.form.BaseForm;
import jp.sheepman.common.fragment.BaseFragment;
import jp.sheepman.mailshokunin.R;
import jp.sheepman.mailshokunin.util.ViewCreaterUtil;

public class F001LayoutMaintenanceFragment extends BaseFragment {
    private AQuery aq;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_f001, container, false);
        aq = new AQuery(root);
        aq.id(R.id.F001_ll_main).getView().setOnDragListener(dragListener);
        aq.id(R.id.F001_iv_text).getView().setOnLongClickListener(longClickListener);
        aq.id(R.id.F001_iv_edit).getView().setOnLongClickListener(longClickListener);
        aq.id(R.id.F001_iv_list).getView().setOnLongClickListener(longClickListener);

        return root;
    }

    private void initial(){
        //データのロード
        //Viewの配置
    }

    private View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            View viewDrag = view;
            if(view instanceof ImageView){
                int width = aq.id(R.id.F001_ll_main).getView().getWidth();
                int height = aq.id(R.id.F001_iv_list).getView().getHeight();
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
                viewDrag = ViewCreaterUtil.createViewObject(getActivity(), "android.widget.TextView");
                viewDrag.setLayoutParams(params);
                viewDrag.setBackgroundColor(Color.DKGRAY);
                if(viewDrag instanceof TextView){
                    ((TextView) viewDrag).setText(new Date().toString());
                }
            }
            view.startDrag(null, new DragShadow(view), viewDrag, 0);
            return false;
        }
    };

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

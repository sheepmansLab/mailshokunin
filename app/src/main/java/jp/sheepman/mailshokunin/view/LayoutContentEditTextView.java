package jp.sheepman.mailshokunin.view;

import android.content.ClipData;
import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.androidquery.AQuery;

import jp.sheepman.mailshokunin.R;

public class LayoutContentEditTextView extends RelativeLayout implements ILayoutContentView, View.OnLongClickListener {
    private AQuery aq;
    private ILayoutContentOwner owner;

    public LayoutContentEditTextView(Context context) {
        this(context, null);
    }

    public LayoutContentEditTextView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.layout_content_view);
    }

    /**
     * コンストラクタ
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public LayoutContentEditTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View root = View.inflate(context, R.layout.layout_v001_layout_content, this);
        aq = new AQuery(root);
        init();
    }

    /**
     * 初期処理
     */
    private void init(){
        aq.id(R.id.V001_btn_edit).getView().setOnClickListener(new OnClickListener() {
            private boolean isEnable = false;

            @Override
            public void onClick(View v) {
                isEnable = !isEnable;
                changeMode(isEnable);
            }
        });
        aq.id(R.id.V001_btn_delete).getView().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (owner != null) {
                    owner.deleteView(LayoutContentEditTextView.this);
                }
            }
        });

        for(int i =0; i < getChildCount(); i ++){
            View v = getChildAt(i);
            if(!(v instanceof Button)){
                v.setOnLongClickListener(this);
                v.setOnDragListener(new OnDragListener() {
                    @Override
                    public boolean onDrag(View view, DragEvent dragEvent) {
                        return true;
                    }
                });
            }
        }
        this.setOnLongClickListener(this);
    }

    @Override
    public LayoutContentEditTextView setOwner(ILayoutContentOwner owner) {
        this.owner = owner;
        return this;
    }

    @Override
    public LayoutContentEditTextView setTitle(String title){
        aq.id(R.id.V001_et_title).text(title);
        return this;
    }

    @Override
    public LayoutContentEditTextView setText(String text){
        aq.id(R.id.V001_et_value).text(text);
        return this;
    }

    @Override
    public String getText(){
        return aq.id(R.id.V001_et_value).getText().toString();
    }

    @Override
    public LayoutContentEditTextView changeMode(boolean isEnable) {
        for(int i = 0; i < getChildCount(); i ++){
            if(getChildAt(i) instanceof EditText){
                getChildAt(i).setEnabled(isEnable);
            }
        }
        return this;
    }

    @Override
    public boolean onLongClick(View view) {
        this.startDrag(ClipData.newPlainText("dummy", ""), new DragShadowBuilder(this), this, 0);
        return true;
    }

    @Override
    public void setOnDragListener(OnDragListener l) {
        for(int i = 0; i < getChildCount(); i ++){
            View v = getChildAt(i);
            if(!(v instanceof Button)){
                v.setOnDragListener(l);
            }
        }
    }
}

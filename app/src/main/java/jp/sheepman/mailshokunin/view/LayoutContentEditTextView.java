package jp.sheepman.mailshokunin.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.androidquery.AQuery;

import jp.sheepman.mailshokunin.R;

public class LayoutContentEditTextView extends RelativeLayout implements ILayoutContentView {
    private AQuery aq;

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
        //入力欄を非活性化
        aq.id(R.id.V001_et_value).getView().setEnabled(false);
        aq.id(R.id.V001_btn_edit).getView().setOnClickListener(new View.OnClickListener() {
            private boolean isEnable = false;

            @Override
            public void onClick(View v) {
                Log.d("test", "test!!!!!");
                isEnable = !isEnable;
                changeMode(isEnable);
            }
        });
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
            getChildAt(i).setEnabled(isEnable);
        }
        return this;
    }
}

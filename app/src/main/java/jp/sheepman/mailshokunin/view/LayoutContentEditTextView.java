package jp.sheepman.mailshokunin.view;

import android.content.Context;
import android.util.AttributeSet;
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
        aq.id(R.id.V001_tv_value).getView().setEnabled(false);
    }

    public LayoutContentEditTextView setTitle(String title){
        aq.id(R.id.V001_tv_title).text(title);
        return this;
    }

    public LayoutContentEditTextView setText(String text){
        aq.id(R.id.V001_tv_value).text(text);
        return this;
    }

    public LayoutContentEditTextView setEditButtonOnClickListener(OnClickListener listener){
        aq.id(R.id.V001_btn_edit).getView().setOnClickListener(listener);
        return this;
    }

    public LayoutContentEditTextView setDeleteButtonOnClickListener(OnClickListener listener){
        aq.id(R.id.V001_btn_delete).getView().setOnClickListener(listener);
        return this;
    }

    public String getText(){
        return aq.id(R.id.V001_tv_value).getText().toString();
    }

}

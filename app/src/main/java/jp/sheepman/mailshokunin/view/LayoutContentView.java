package jp.sheepman.mailshokunin.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.androidquery.AQuery;

import jp.sheepman.mailshokunin.R;

public class LayoutContentView extends RelativeLayout {
    private AQuery aq;

    public LayoutContentView(Context context) {
        this(context, null);
    }

    public LayoutContentView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.layout_content_view);
    }

    public LayoutContentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View root = View.inflate(context, R.layout.layout_v001_layout_content, this);
        aq = new AQuery(root);
    }

    public LayoutContentView setTitle(String title){
        aq.id(R.id.V001_tv_title).text(title);
        return this;
    }

    public LayoutContentView setText(String text){
        aq.id(R.id.V001_tv_value).text(text);
        return this;
    }

    public LayoutContentView setEditButtonOnClickListener(OnClickListener listener){
        aq.id(R.id.V001_btn_edit).clicked(listener);
        return this;
    }

    public LayoutContentView setDeleteButtonOnClickListener(OnClickListener listener){
        aq.id(R.id.V001_btn_delete).clicked(listener);
        return this;
    }

    public String getText(){
        return aq.id(R.id.V001_tv_value).getText().toString();
    }
}

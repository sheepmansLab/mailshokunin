package jp.sheepman.mailshokunin.view;

import android.view.View;

public interface ILayoutContentView {

    public LayoutContentEditTextView setTitle(String title);

    public LayoutContentEditTextView setText(String text);

    public LayoutContentEditTextView setEditButtonOnClickListener(View.OnClickListener listener);

    public LayoutContentEditTextView setDeleteButtonOnClickListener(View.OnClickListener listener);

    public String getText();
}

package jp.sheepman.mailshokunin.view;

import android.view.View;

public interface ILayoutContentView {

    public LayoutContentEditTextView setTitle(String title);

    public LayoutContentEditTextView setText(String text);

    public LayoutContentEditTextView changeMode(boolean isEnable);

    public String getText();
}

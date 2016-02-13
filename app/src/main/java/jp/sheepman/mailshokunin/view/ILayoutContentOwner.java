package jp.sheepman.mailshokunin.view;

import android.view.View;

public interface ILayoutContentOwner {
    public void deleteView(View view);
    public void changeMode(View view, boolean isEnable);
}

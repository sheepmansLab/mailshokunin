package jp.sheepman.mailshokunin.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.androidquery.AQuery;

import java.util.List;

import jp.sheepman.common.form.BaseForm;
import jp.sheepman.common.fragment.BaseFragment;
import jp.sheepman.mailshokunin.R;
import jp.sheepman.mailshokunin.common.CommonConst;
import jp.sheepman.mailshokunin.form.F003LayoutForm;
import jp.sheepman.mailshokunin.form.F003LayoutItemForm;
import jp.sheepman.mailshokunin.model.LayoutEditBusinessLogic;
import jp.sheepman.mailshokunin.model.MailEditBusinessLogic;
import jp.sheepman.mailshokunin.util.ViewCreateUtil;


public class F003MailEditFragment extends BaseFragment {
    private AQuery aq;
    private int mLayout_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);
        this.aq = new AQuery(root);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        reload();
    }

    /**
     * 表示
     */
    private void reload(){
        if(getArguments() != null){
            if(mLayout_id < 1) {
                mLayout_id = getArguments().getInt(CommonConst.BUNDLE_KEY_F003_LAYOUT_ID);
            }
        }

        MailEditBusinessLogic logic = new MailEditBusinessLogic();
        F003LayoutForm layoutForm = logic.selectLayout(getActivity(), mLayout_id);

        List<F003LayoutItemForm> itemList = logic.selectLayoutItems(getActivity(), mLayout_id);

        LinearLayout llMain = (LinearLayout)aq.id(R.id.F003_ll_Main).getView();
        for(F003LayoutItemForm item : itemList){
            View v = ViewCreateUtil.createViewObject(getActivity(), item.getObject_class());
            llMain.addView(v);
        }
    }

    @Override
    public void callback() {

    }

    @Override
    public void callback(BaseForm baseForm) {

    }
}

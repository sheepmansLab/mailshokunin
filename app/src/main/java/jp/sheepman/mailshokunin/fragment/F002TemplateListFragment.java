package jp.sheepman.mailshokunin.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.androidquery.AQuery;

import java.util.ArrayList;
import java.util.List;

import jp.sheepman.common.activity.BaseActivity;
import jp.sheepman.common.form.BaseForm;
import jp.sheepman.common.fragment.BaseFragment;
import jp.sheepman.mailshokunin.MainActivity;
import jp.sheepman.mailshokunin.R;
import jp.sheepman.mailshokunin.common.CommonConst;
import jp.sheepman.mailshokunin.form.F002TemplateListForm;

public class F002TemplateListFragment extends BaseFragment {
    private AQuery aq;
    private ArrayAdapter<String> adapter;
    private BaseActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_f002_template_list, container, false);
        aq = new AQuery(root);
        activity = (BaseActivity)getActivity();
        adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1);
        //アダプタをセット
        aq.id(R.id.F002_lv_list).adapter(adapter);
        ((ListView)aq.id(R.id.F002_lv_list).getView()).setOnItemClickListener(itemClickListener);

        reload();

        return root;
    }

    private void reload(){
        //TODO サンプル実装
        F002TemplateListForm form = new F002TemplateListForm();
        form.setTemplate_id(0);
        form.setTemplate_name("Sample");
        List<F002TemplateListForm> list = new ArrayList<F002TemplateListForm>();
        list.add(form);

        for(F002TemplateListForm f : list){
            adapter.add(f.getTemplate_name());
            adapter.notifyDataSetChanged();
        }
    }

    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if(activity instanceof MainActivity){
                Bundle bundle = new Bundle();
                //TODO 値をアイテムから取得するように変更する
                bundle.putInt(CommonConst.BUNDLE_KEY_F001_TEMPLATE_ID, 0);
                bundle.putString(CommonConst.BUNDLE_KEY_F001_TEMPLATE_NAME, "Sample Template");

                F001LayoutEditFragment fragment = new F001LayoutEditFragment();
                ((MainActivity) activity).changeFragment(bundle, fragment, CommonConst.FRAGMENT_TAG_F001);
            }
        }
    };


    @Override
    public void callback() {
    }

    @Override
    public void callback(BaseForm baseForm) {
    }
}

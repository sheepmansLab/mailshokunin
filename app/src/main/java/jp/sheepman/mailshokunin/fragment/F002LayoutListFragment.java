package jp.sheepman.mailshokunin.fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.androidquery.AQuery;

import java.util.List;

import jp.sheepman.common.activity.BaseActivity;
import jp.sheepman.common.adapter.BaseCustomAdapter;
import jp.sheepman.common.form.BaseForm;
import jp.sheepman.common.fragment.BaseFragment;
import jp.sheepman.mailshokunin.MainActivity;
import jp.sheepman.mailshokunin.R;
import jp.sheepman.mailshokunin.common.CommonConst;
import jp.sheepman.mailshokunin.form.F002LayoutListForm;
import jp.sheepman.mailshokunin.model.LayoutListBusinessLogic;

public class F002LayoutListFragment extends BaseFragment {
    private AQuery aq;
    private F002LayoutListAdapter adapter;
    private BaseActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_f002_layout_list, container, false);
        aq = new AQuery(root);
        activity = (BaseActivity)getActivity();
        adapter = new F002LayoutListAdapter(getActivity());

        reload();

        return root;
    }

    private void reload(){
        LayoutListBusinessLogic logic = new LayoutListBusinessLogic();
        List<F002LayoutListForm> list = logic.selectLayoutList(getActivity());
        //新規作成ボタン
        aq.id(R.id.F002_btn_create).getView().setOnClickListener(clickListener);

        //アダプタをセット
        aq.id(R.id.F002_lv_list).adapter(adapter);
        ((ListView)aq.id(R.id.F002_lv_list).getView()).setOnItemClickListener(itemClickListener);
        for(F002LayoutListForm f : list){
            adapter.add(f);
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * 新規作成ボタンのクリックイベント
     */
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            changeFragment(0);
        }
    };

    /**
     * ListViewのアイテムのクリックイベント
     */
    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            int layout_id = (int)view.getTag();
            changeFragment(layout_id);
        }
    };

    /**
     * 画面遷移
     * @param layout_id
     */
    private void changeFragment(int layout_id){
        if(activity instanceof MainActivity){
            Bundle bundle = new Bundle();
            bundle.putInt(CommonConst.BUNDLE_KEY_F001_LAYOUT_ID, layout_id);
            F001LayoutEditFragment fragment = new F001LayoutEditFragment();
            ((MainActivity) activity).changeFragment(bundle, fragment, CommonConst.FRAGMENT_TAG_F001);
        }
    }

    /**
     * カスタムアダプタ
     */
    private class F002LayoutListAdapter extends BaseCustomAdapter{
        public F002LayoutListAdapter(Context context) {
            super(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView view;
            if(convertView == null){
                convertView = new TextView(getActivity());
            }
            F002LayoutListForm form = (F002LayoutListForm)list.get(position);
            ((TextView) convertView).setText(form.getLayout_name());
            convertView.setTag(form.getLayout_id());

            return convertView;
        }
    }


    @Override
    public void callback() {
    }

    @Override
    public void callback(BaseForm baseForm) {
    }
}

package jp.sheepman.mailshokunin;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

import com.androidquery.AQuery;

import jp.sheepman.common.activity.BaseActivity;
import jp.sheepman.common.fragment.BaseFragment;
import jp.sheepman.mailshokunin.fragment.F002TemplateListFragment;

public class MainActivity extends BaseActivity {
    private AQuery aq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        aq = new AQuery(this);
        FragmentTransaction tran = getFragmentManager().beginTransaction();
        if(savedInstanceState == null){
            tran.replace(R.id.M000_frm_main, new F002TemplateListFragment(), "F002");
        }
        tran.commit();
    }

    public void changeFragment(Bundle bundle, BaseFragment fragment, String tag){
        fragment.setArguments(bundle);
        getFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .replace(R.id.M000_frm_main, fragment, tag)
                .commit();
    }

    @Override
    public void callback() {

    }
}

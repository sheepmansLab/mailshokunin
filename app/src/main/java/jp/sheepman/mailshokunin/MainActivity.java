package jp.sheepman.mailshokunin;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.androidquery.AQuery;

import jp.sheepman.mailshokunin.fragment.F001LayoutMaintenanceFragment;

public class MainActivity extends AppCompatActivity {
    private AQuery aq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        aq = new AQuery(this);
        FragmentTransaction tran = getFragmentManager().beginTransaction();
        tran.replace(R.id.M000_frm_main, new F001LayoutMaintenanceFragment(),"F001");
        tran.commit();
    }
}

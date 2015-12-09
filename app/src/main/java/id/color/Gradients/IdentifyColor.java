package id.color.Gradients;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;


public class IdentifyColor extends ActionBarActivity {

    public static int domColor=0;
    private TabHost o_TabHost;
    private ViewPager o_ViewPager;
    private TabsAdapter o_TabsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.identify_color);

        o_TabHost = (TabHost) findViewById(android.R.id.tabhost);
        o_TabHost.setup();
        o_ViewPager = (ViewPager) findViewById(R.id.pager);
        o_TabsAdapter = new TabsAdapter(this, o_TabHost, o_ViewPager);
        o_TabsAdapter.addTab(o_TabHost.newTabSpec("exactColor").setIndicator("Exact COLOR"), ExactFragment.class, null);
        o_TabsAdapter.addTab(o_TabHost.newTabSpec("similar_color").setIndicator("Similar COLOR"), SimilarFragment.class, null);
        if (savedInstanceState != null) {
            o_TabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}


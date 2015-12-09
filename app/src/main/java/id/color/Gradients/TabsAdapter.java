package id.color.Gradients;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabWidget;

import java.util.ArrayList;

public class TabsAdapter extends FragmentPagerAdapter implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener{

    private final Context o_Context;
    private final TabHost o_TabHost;
    private final ViewPager o_ViewPager;
    private final ArrayList<TabInfo> o_Tabs = new ArrayList<TabInfo>();

    @Override
    public void onPageScrolled(int i, float v, int i2) {

    }

    @Override
    public void onPageSelected(int position) {
        TabWidget widget = o_TabHost.getTabWidget();
        int oldFocusability = widget.getDescendantFocusability();
        widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        o_TabHost.setCurrentTab(position);
        widget.setDescendantFocusability(oldFocusability);

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onTabChanged(final String tabId) {
        int position = o_TabHost.getCurrentTab();
        o_ViewPager.setCurrentItem(position);
    }

    static final class TabInfo {
        private final String tag;
        private final Class<?> clss;
        private final Bundle args;

        TabInfo(final  String _tag, final Class<?> _class, final Bundle _args) {
            tag = _tag;
            clss = _class;
            args = _args;
        }
    }

    static class DummyTabFactory implements TabHost.TabContentFactory {

        private final Context o_Context;

        public DummyTabFactory(final Context context) {
            o_Context = context;
        }

        @Override
        public View createTabContent(String tag) {
            View rootview = new View(o_Context);
            rootview.setMinimumWidth(0);
            rootview.setMinimumHeight(0);
            return rootview;
        }
    }

    public TabsAdapter(final FragmentActivity activity, final TabHost tabHost, final ViewPager pager) {
        super(activity.getSupportFragmentManager());
        o_Context = activity;
        o_TabHost = tabHost;
        o_ViewPager = pager;
        o_TabHost.setOnTabChangedListener(this);
        o_ViewPager.setAdapter(this);
        o_ViewPager.setOnPageChangeListener(this);
    }

    public void addTab(final TabHost.TabSpec tabSpec, final Class<?> clss, final Bundle args) {
        tabSpec.setContent(new DummyTabFactory(o_Context));
        String tag = tabSpec.getTag();
        TabInfo info = new TabInfo(tag,clss, args);
        o_Tabs.add(info);
        o_TabHost.addTab(tabSpec);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(final int position) {
        TabInfo info = o_Tabs.get(position);
        return Fragment.instantiate(o_Context, info.clss.getName(), info.args);
    }

    @Override
    public int getCount() {
        return o_Tabs.size();
    }
}
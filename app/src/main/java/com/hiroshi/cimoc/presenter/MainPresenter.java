package com.hiroshi.cimoc.presenter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.view.MenuItem;

import com.hiroshi.cimoc.R;
import com.hiroshi.cimoc.ui.activity.MainActivity;
import com.hiroshi.cimoc.ui.fragment.CimocFragment;
import com.hiroshi.cimoc.ui.fragment.FavoriteFragment;
import com.hiroshi.cimoc.ui.fragment.HistoryFragment;
import com.hiroshi.cimoc.ui.fragment.PlugFragment;
import com.hiroshi.cimoc.utils.EventMessage;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Hiroshi on 2016/7/1.
 */
public class MainPresenter extends BasePresenter {

    private MainActivity mMainActivity;
    private long mExitTime;

    private int mCheckedItem;
    private FragmentManager mFragmentManager;
    private CimocFragment mCimocFragment;
    private FavoriteFragment mFavoriteFragment;
    private HistoryFragment mHistoryFragment;
    private PlugFragment mPlugFragment;
    private Fragment mCurrentFragment;
    
    public MainPresenter(MainActivity activity) {
        mMainActivity = activity;
        mExitTime = 0;
        initFragment();
    }

    private void initFragment() {
        mFragmentManager = mMainActivity.getFragmentManager();
        mCimocFragment = new CimocFragment();
        mFavoriteFragment = new FavoriteFragment();
        mFragmentManager.beginTransaction()
                .add(R.id.main_fragment_container, mCimocFragment)
                .add(R.id.main_fragment_container, mFavoriteFragment)
                .hide(mFavoriteFragment)
                .commit();
        mCurrentFragment = mCimocFragment;
        mCheckedItem = R.id.drawer_main;
        mMainActivity.setCheckedItem(mCheckedItem);
    }

    public void onBackPressed() {
        if (mMainActivity.isDrawerOpen()) {
            mMainActivity.closeDrawer();
        } else if (System.currentTimeMillis() - mExitTime > 2000) {
            mMainActivity.showSnackbar("再按一次退出程序");
            mExitTime = System.currentTimeMillis();
        } else {
            mMainActivity.finish();
        }
    }

    public void transFragment() {
        switch (mCheckedItem) {
            default:
            case R.id.drawer_main:
                mCurrentFragment = mCimocFragment;
                break;
            case R.id.drawer_comic:
                mCurrentFragment = mFavoriteFragment;
                break;
        }
        mFragmentManager.beginTransaction().show(mCurrentFragment).commit();
        mMainActivity.hideProgressBar();
    }

    public boolean onNavigationItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == mCheckedItem) {
            return false;
        }
        mCheckedItem = menuItem.getItemId();
        mMainActivity.showProgressBar();
        mFragmentManager.beginTransaction().hide(mCurrentFragment).commit();
        mMainActivity.setToolbarTitle(menuItem.getTitle().toString());
        mMainActivity.closeDrawer();
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventMessage msg) {

    }

}

package com.mygps.unrelated_to_function.main.adapter;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by 10397 on 2016/4/22.
 */
public class MainViewPaperAdapter extends PagerAdapter {
    private static final String TAG = "MainViewPaperAdapter";
    private static final boolean DEBUG = true;

    private final FragmentManager mFragmentManager;
    private FragmentTransaction mCurTransaction = null;
    private Fragment mCurrentPrimaryItem = null;

    ArrayList<Map<String, Object>> viewList = new ArrayList<>();//Map{"title","view"}

    public MainViewPaperAdapter(ArrayList<Map<String, Object>> viewList, FragmentManager fm) {
        mFragmentManager = fm;
        this.viewList = viewList;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {


        if (viewList.get(position).get("view") instanceof android.support.v4.app.Fragment) {
            if (mCurTransaction == null) {
                mCurTransaction = mFragmentManager.beginTransaction();
            }

            final long itemId = getItemId(position);

            // Do we already have this fragment?
            String name = makeFragmentName(container.getId(), itemId);
            Fragment fragment = mFragmentManager.findFragmentByTag(name);
            if (fragment != null) {
                if (DEBUG) Log.v(TAG, "Attaching item #" + itemId + ": f=" + fragment);
                mCurTransaction.attach(fragment);
            } else {
                fragment = getItem(position);
                if (DEBUG) Log.v(TAG, "Adding item #" + itemId + ": f=" + fragment);
                mCurTransaction.add(container.getId(), fragment, makeFragmentName(container.getId(), itemId));
            }
            Log.v(TAG, "Attaching item #" + itemId + ": f=" + fragment);
            if (fragment != mCurrentPrimaryItem) {
                fragment.setMenuVisibility(false);
                fragment.setUserVisibleHint(false);
            }
            return fragment;
        } else {
            container.addView((View) viewList.get(position).get("view"));
            return viewList.get(position).get("view");
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (object instanceof android.support.v4.app.Fragment) {
            if (mCurTransaction == null) {
                mCurTransaction = mFragmentManager.beginTransaction();
            }
            if (DEBUG)
                Log.v(TAG, "Detaching item #" + getItemId(position) + ": f=" + object + " v=" + ((Fragment) object).getView());
            mCurTransaction.detach((Fragment) object);
        } else {
            container.removeView((View) viewList.get(position).get("view"));
        }
    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    public Fragment getItem(int position) {

        return (Fragment) viewList.get(position).get("view");
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        if (object instanceof android.support.v4.app.Fragment)
            return ((Fragment) object).getView() == view;
        else
            return object == view;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return (String) viewList.get(position).get("title");
    }

    public long getItemId(int position) {
        return position;
    }

    private static String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (object instanceof android.support.v4.app.Fragment) {
            Fragment fragment = (Fragment) object;
            if (fragment != mCurrentPrimaryItem) {
                if (mCurrentPrimaryItem != null) {
                    mCurrentPrimaryItem.setMenuVisibility(false);
                    mCurrentPrimaryItem.setUserVisibleHint(false);
                }
                if (fragment != null) {
                    fragment.setMenuVisibility(true);
                    fragment.setUserVisibleHint(true);
                }
                mCurrentPrimaryItem = fragment;
            }
        } else {
            super.setPrimaryItem(container, position, object);
        }
    }


    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }


    @Override
    public void finishUpdate(ViewGroup container) {
        if (mCurTransaction != null) {
            mCurTransaction.commitAllowingStateLoss();
            mCurTransaction = null;
            mFragmentManager.executePendingTransactions();
        }
    }

}

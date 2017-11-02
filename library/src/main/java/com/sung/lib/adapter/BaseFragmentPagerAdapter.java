package com.sung.lib.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

/**
 * Created by sung on 2017-11-02.
 */

public class BaseFragmentPagerAdapter <T extends Fragment> extends FragmentStatePagerAdapter {

    //프래그먼트 리스트
    private SparseArray<T> fragments = new SparseArray<T>();
    //private final List<T> fragments = new ArrayList<>();
    //타이틀 리스트
    private SparseArray<String> titles = new SparseArray<String>();
    //private final List<String> titles = new ArrayList<>();
    //현재 선택 된 프래그먼트
    private T currentFragment;
    //현재 선택 된 인덱스
    private int currentPosition = -1;

    public BaseFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (getCurrentFragment() != object) {
            this.currentFragment = (T) object;
            this.currentPosition = position;
        }
        super.setPrimaryItem(container, position, object);
    }


    @Override
    public T getItem(int position) {
        try{
            return this.fragments.get(position);
        }
        catch(IllegalArgumentException ex){
            throw ex;
        }
    }

    @Override
    public int getCount() {
        if( null == this.fragments )
            return 0;

        return this.fragments.size();
    }



    /*
    //다른 단편이 표시 될 때 강제로 페이지 새로 고침
    @Override
    public int getItemPosition(Object object) {
        // this method will be called for every fragment in the ViewPager
        if (object instanceof SomePermanantCachedFragment) {
            return POSITION_UNCHANGED; // don't force a reload
        } else {
            // POSITION_NONE means something like: this fragment is no longer valid
            // triggering the ViewPager to re-build the instance of this fragment.
            return POSITION_NONE;
        }
    }


    // Register the fragment when the item is instantiated
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        T fragment = (T) super.instantiateItem(container, position);
        this.fragments.put(position, fragment);
        return fragment;
    }

    //항목이 비활성 상태 일 때 등록 취소
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        this.fragments.remove(position);
        super.destroyItem(container, position, object);
    }*/


    public T getCurrentFragment() {
        return this.currentFragment;
    }
    public int getCurrentPosition() {
        return this.currentPosition;
    }
    public void addItem(T fragment, String title) {
        try{
            this.fragments.append(this.fragments.size(),fragment);
            this.titles.append(this.titles.size(), title);
        }catch (Exception ex){
            throw ex;
        }
    }
    public void removeItem(int position) throws Exception {
        try{
            if( this.fragments.size() > position )
                throw new Exception("[예외발생] : if( this.fragments.size() > position )");
            else if( this.fragments.size() != this.titles.size())
                throw new Exception("[예외발생] : if( this.fragments.size() != this.titles.size())");
            else{
                this.fragments.remove(position);
                this.titles.remove(position);
            }

        }catch (Exception ex){
            throw ex;
        }
    }
}
package com.sung.lib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.sung.lib.R;
import com.sung.lib.model.ExTabModel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sung on 2017-11-02.
 */

public class ExTabLayout extends RelativeLayout implements ViewPager.OnPageChangeListener{


    protected final static int INVALID_INDEX = -1;



    ////////////////////////////////////////////////////////////////////////////////////////////////
    //[뷰]
    //탭 레이아웃
    private TabLayout tab;
    //
    private RelativeLayout layoutWrap;
    //선택 된 탭 레이아웃
    private RelativeLayout layoutSelectedTab;
    private FloatingActionButton fab;
    //탭 아이템 리스트
    private List<TabLayout.Tab> tabs = new ArrayList<>();

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //[데이터&속성]
    //탭 모델(탭아이템)
    protected final List<ExTabModel> mModels = new ArrayList<>();
    private boolean mIsViewPagerMode;
    protected int mIndex = INVALID_INDEX;
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //[파라미터]
    private ViewPager mViewPager;



    //Android Studio와 뷰가 상호 작용하려면, 최소한 Context와 AttributeSet 객체를 파라미터로 가지는 생성자를 제공해야 한다.
    //이 생성자는 레아이웃 편집기기가 뷰의 인스턴스를 만들고 편집할 수 있게 한다
    public ExTabLayout(Context context) {
        super(context);
    }
    public ExTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
        this.initAttrs(attrs);
    }

    public ExTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initAttrs(attrs);
        this.init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_wrap, this);
        //
        this.layoutWrap = (RelativeLayout) findViewById(R.id.layoutWrap);
        this.layoutSelectedTab = (RelativeLayout) findViewById(R.id.layoutSelectedTab);
        this.fab = (FloatingActionButton) findViewById(R.id.fab);
        this.tab = (TabLayout) findViewById(R.id.tab);

        //탭
        this.tab.addTab(tab.newTab().setText("Tab 1"));
        this.tab.addTab(tab.newTab().setText("Tab 2"));
        this.tab.addTab(tab.newTab().setText("Tab 3"));
        this.tab.setTabGravity(TabLayout.GRAVITY_FILL);


    }

    //사용자 인터페이스에 기본 제공되는 View를 추가하려면, 이를 XML 요소에 지정하고 속성으로 모양과 동작을 제어해야 한다.
    //잘 작성된 custom view는 XML을 통해 값을 추가하고 스타일을 지정할 수 있다.
    //custom view에서 이 동작을 사용하려면
    //리소스는 res/values/attrs.xml 파일에 저장하는 것이 일반적이다.
    //<declare-styleable> 리소스 요소에서 뷰의 custom 속성을 정의 한다.
    private void initAttrs(AttributeSet attrs)
    {
        //미리 정의된 상수를 사용하여 TypedArray 에서 특성을 읽는다.
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ComplexTabBar);

        try {


            //속성을 가져온다
            boolean mShowText = typedArray.getBoolean(R.styleable.ComplexTabBar_is, false);

        }catch(Exception ex){

        }finally {

            //주의사항!
            //TypedArray 객체는 공유 resource이므로 사용 후 반드시 재활용해야 한다. 이를 위해 try-finally에서 recycle()을 호출한다.
            //할당되어 있던 메모리를 풀에 즉시 돌려줘서 GC 될 때까지 기다릴 필요가 없게 된다.
            typedArray.recycle();
        }

    }

    public void setViewPager(final ViewPager viewPager, int index) {
        setViewPager(viewPager);

        this.mIndex = index;
        if (this.mIsViewPagerMode)
            this.mViewPager.setCurrentItem(index, true);

        this.postInvalidate();
    }

    private boolean SCROLL_STATE_DRAGGING = false;
    public void setViewPager(final ViewPager viewPager) {
        // Detect whether ViewPager mode
        if (viewPager == null) {
            this.mIsViewPagerMode = false;
            return;
        }

        if (viewPager.equals(this.mViewPager))
            return;

        if (this.mViewPager != null) //noinspection deprecation
            this.mViewPager.setOnPageChangeListener(null);
        if (viewPager.getAdapter() == null)
            throw new IllegalStateException("[예외] 뷰페이저에 아답터가 초기화 되어 있지 않습니다. ViewPager does not provide adapter instance.");


        this.mIsViewPagerMode = true;
        this.mViewPager = viewPager;
        /*
        this.mViewPager.removeOnPageChangeListener(this);
        this.mViewPager.addOnPageChangeListener(this);
        resetScroller();
        postInvalidate();*/
        //this.tab.setupWithViewPager(this.mViewPager);


        this.mViewPager.setCurrentItem(0);


    }

    protected void resetScroller() {
        if (mViewPager == null) return;
        try {
            final Field scrollerField = ViewPager.class.getDeclaredField("mScroller");
            scrollerField.setAccessible(true);
            //final ResizeViewPagerScroller scroller = new ResizeViewPagerScroller(getContext());
            //scrollerField.set(mViewPager, scroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    public List<ExTabModel> getModels() {
        return this.mModels;
    }

    public void setModels(final List<ExTabModel> models) {
        //Set update listeners to badge model animation
        for (final ExTabModel model : models){
            /*
            model.mBadgeAnimator.removeAllUpdateListeners();
            model.mBadgeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(final ValueAnimator animation) {
                    model.mBadgeFraction = (float) animation.getAnimatedValue();
                    postInvalidate();
                }
            });*/
        }

        this.mModels.clear();
        this.mModels.addAll(models);
        requestLayout();
    }



    protected ViewPager.OnPageChangeListener mOnPageChangeListener;
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        if (mOnPageChangeListener != null)
            mOnPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}

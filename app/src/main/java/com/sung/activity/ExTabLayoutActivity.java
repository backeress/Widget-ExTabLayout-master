package com.sung.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.sung.R;
import com.sung.fragment.FragmentOne;
import com.sung.fragment.FragmentThree;
import com.sung.fragment.FragmentTwo;
import com.sung.lib.adapter.ExFragmentPagerAdapter;
import com.sung.lib.model.ExTabModel;
import com.sung.lib.widget.ExTabLayout;

import java.util.ArrayList;

/**
 * Created by sung on 2017-11-02.
 */

public class ExTabLayoutActivity extends AppCompatActivity {

    //뷰페이저
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_tablayout);

        //
        this.viewPager = (ViewPager) findViewById(R.id.viewPager);
        this.init();
    }


    void init() {

        //탭 아이템 구현하는거 이어서 진행 해야 한다.

        //[탭 아이템 초기화]
        final String[] colors = getResources().getStringArray(R.array.default_preview);
        final ExTabLayout exTabLayout = (ExTabLayout) findViewById(R.id.exTabLayout);
        final ArrayList<ExTabModel> models = new ArrayList<>();
        models.add(new ExTabModel.Builder(
                getResources().getDrawable(R.drawable.ic_first),
                Color.parseColor(colors[0]))
                .title("하나")
                .build()
        );
        models.add(new ExTabModel.Builder(
                getResources().getDrawable(R.drawable.ic_second),
                Color.parseColor(colors[1]))
                .title("둘")
                .build()
        );
        models.add(new ExTabModel.Builder(
                getResources().getDrawable(R.drawable.ic_third),
                Color.parseColor(colors[2]))
                .title("셋")
                .build()
        );


        //현재 뷰가 초기화 될 때 탭을 추가해놨다.
        //아래의 코드로 프래그먼트를 추가할 수 있도록 구현 하자.
        //뷰를 소스코드만 구현 할 지 레이아웃과 복합적으로 구현 할 것인지 결정한 뒤 이어서 진행 하도록 한다.
        ExFragmentPagerAdapter exfragmentPagerAdapter = new ExFragmentPagerAdapter(getSupportFragmentManager());
        /*
        //프래그먼트를 출력하기 위해서는 뷰페이저를 컨틀로하는 코드가 필요하다.
        //뷰페이저를 파라미터로 넘겨서 초기화 할 수 있도록 해야 한다.
        //[아답터 생성 및 프래그먼트 초기화]
        exfragmentPagerAdapter.addItem(new FragmentOne(), "FragmentOne");
        exfragmentPagerAdapter.addItem(new FragmentTwo(), "FragmentTwo");
        exfragmentPagerAdapter.addItem(new FragmentThree(), "FragmentThree");*/


        //뷰페이저와 프래그먼트아답터 연동
        this.viewPager.setAdapter(exfragmentPagerAdapter);
        //뷰페이저 첫번째 선택
        this.viewPager.setCurrentItem(0);

        //
        exTabLayout.setModels(models);
        exTabLayout.setViewPager(this.viewPager, 2);

    }


}
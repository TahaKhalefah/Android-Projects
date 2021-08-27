package com.tahadroid.quraanapp.ui;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.tahadroid.quraanapp.R;
import com.tahadroid.quraanapp.adapter.SuraAdapter;
import com.tahadroid.quraanapp.adapter.TabAdapter;
import com.tahadroid.quraanapp.pojo.Category;
import com.tahadroid.quraanapp.pojo.MyTab;
import com.tahadroid.quraanapp.ui.fragment.AhadeethFragment;
import com.tahadroid.quraanapp.ui.fragment.Estema3Fragment;
import com.tahadroid.quraanapp.ui.fragment.SurasFragment;
import com.tahadroid.quraanapp.ui.fragment.TasbeehFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity implements SuraAdapter.OnClickListenerxxxx {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabAdapter tabAdapter;



    public static final int SURAS = 0;
    public static final int AHADEETH = 1;
    public static final int TSBEEH = 2;
    public static final int ESTEMA3 = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);

        tabAdapter = new TabAdapter(getSupportFragmentManager(), TabAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        Category category1 = new Category("Suras", SURAS);
        Category category2 = new Category("ahadeeth", AHADEETH);
        Category category3 = new Category("tsbeeh", TSBEEH);
        Category category4 = new Category("estema3", ESTEMA3);

        SurasFragment surasFragment = new SurasFragment();
        AhadeethFragment ahadeethFragment = new AhadeethFragment();
        TasbeehFragment tasbeehFragment = new TasbeehFragment();
        Estema3Fragment estema3Fragment = new Estema3Fragment();

        MyTab myTab1 = new MyTab(surasFragment, category1);
        MyTab myTab2 = new MyTab(ahadeethFragment, category2);
        MyTab myTab3 = new MyTab(tasbeehFragment, category3);
        MyTab myTab4 = new MyTab(estema3Fragment, category4);

        tabAdapter.addTab(myTab1);
        tabAdapter.addTab(myTab2);
        tabAdapter.addTab(myTab3);
        tabAdapter.addTab(myTab4);

        viewPager.setAdapter(tabAdapter);

        tabLayout.setupWithViewPager(viewPager);


    }

    @Override
    public void xxxx(int p, String s) {
        Intent intent = new Intent(this, VersesActivity.class);
        intent.putExtra("position", p);
        intent.putExtra("title", s);
       this.startActivity(intent);
    }
}

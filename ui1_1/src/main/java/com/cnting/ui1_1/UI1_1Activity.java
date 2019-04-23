package com.cnting.ui1_1;

import android.os.Bundle;
import android.view.Menu;
import androidx.annotation.LayoutRes;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class UI1_1Activity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager pager;
    List<PageModel> pageModels = new ArrayList<>();

    {
        pageModels.add(new PageModel(R.layout.ui_1_1_sample_color, R.string.title_draw_color, R.layout.ui_1_1_practice_color));
        pageModels.add(new PageModel(R.layout.ui_1_1_sample_circle, R.string.title_draw_circle, R.layout.ui_1_1_practice_circle));
        pageModels.add(new PageModel(R.layout.ui_1_1_sample_rect, R.string.title_draw_rect, R.layout.ui_1_1_practice_rect));
        pageModels.add(new PageModel(R.layout.ui_1_1_sample_point, R.string.title_draw_point, R.layout.ui_1_1_practice_point));
        pageModels.add(new PageModel(R.layout.ui_1_1_sample_oval, R.string.title_draw_oval, R.layout.ui_1_1_practice_oval));
        pageModels.add(new PageModel(R.layout.ui_1_1_sample_line, R.string.title_draw_line, R.layout.ui_1_1_practice_line));
        pageModels.add(new PageModel(R.layout.ui_1_1_sample_round_rect, R.string.title_draw_round_rect, R.layout.ui_1_1_practice_round_rect));
        pageModels.add(new PageModel(R.layout.ui_1_1_sample_arc, R.string.title_draw_arc, R.layout.ui_1_1_practice_arc));
        pageModels.add(new PageModel(R.layout.ui_1_1_sample_path, R.string.title_draw_path, R.layout.ui_1_1_practice_path));
        pageModels.add(new PageModel(R.layout.ui_1_1_sample_histogram, R.string.title_draw_histogram, R.layout.ui_1_1_practice_histogram));
        pageModels.add(new PageModel(R.layout.ui_1_1_sample_pie_chart, R.string.title_draw_pie_chart, R.layout.ui_1_1_practice_pie_chart));
        pageModels.add(new PageModel(R.layout.ui_1_1_sample_dashboard, R.string.title_draw_dashboard, R.layout.ui_1_1_practice_dashboard));
        pageModels.add(new PageModel(R.layout.ui_1_1_sample_dashboard, R.string.title_bitmap, R.layout.ui_1_1_practice_bitmap));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_1_1_activity_main);

        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                PageModel pageModel = pageModels.get(position);
                return PageFragment.newInstance(pageModel.sampleLayoutRes, pageModel.practiceLayoutRes);
            }

            @Override
            public int getCount() {
                return pageModels.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return getString(pageModels.get(position).titleRes);
            }
        });

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(pager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    private class PageModel {
        @LayoutRes
        int sampleLayoutRes;
        @StringRes
        int titleRes;
        @LayoutRes
        int practiceLayoutRes;

        PageModel(@LayoutRes int sampleLayoutRes, @StringRes int titleRes, @LayoutRes int practiceLayoutRes) {
            this.sampleLayoutRes = sampleLayoutRes;
            this.titleRes = titleRes;
            this.practiceLayoutRes = practiceLayoutRes;
        }
    }
}

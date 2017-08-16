package com.boss.yobo1.first.fourth;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.boss.yobo1.R;
import com.boss.yobo1.first.fourth.adapter.MyAdapter;
import com.boss.yobo1.first.fourth.view.DividerItemDecoration;
import com.boss.yobo1.first.fourth.view.HorizontalPageLayoutManager;
import com.boss.yobo1.first.fourth.view.PageIndicator;
import com.boss.yobo1.first.fourth.view.PagingItemDecoration;
import com.boss.yobo1.first.fourth.view.PagingScrollHelper;
import java.util.ArrayList;
/**
 * Created by YoBo on 2017/8/15.
 */

public class FourActivity extends AppCompatActivity implements PagingScrollHelper.onPageChangeListener, PageIndicator.OnIndicatorItemClickListener {
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    TextView tv_title;
    PagingScrollHelper scrollHelper = new PagingScrollHelper();
    RadioGroup rg_layout;
    private ArrayList<String> dataList;
    private PageIndicator indicator;
    private int pageCount;
    private int row = 3;
    private int columns = 4;
    private int currentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four);
        init();
        initData();
        rg_layout = (RadioGroup) findViewById(R.id.rg_layout);
        rg_layout.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switchLayout(checkedId);
            }
        });
        indicator = (PageIndicator) findViewById(R.id.indicator);
        indicator.setOnIndicatorClickListener(this);
        indicator.InitIndicatorItems(pageCount);
        indicator.onPageSelected(0);
        tv_title = (TextView) findViewById(R.id.tv_title);
        myAdapter = new MyAdapter(dataList);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setAdapter(myAdapter);
        scrollHelper.setUpRecycleView(recyclerView);
        scrollHelper.setOnPageChangeListener(this);
        switchLayout(R.id.rb_horizontal_page);

    }


    private void initData() {
        dataList = new ArrayList<>();

        for (int i = 1; i <= 50; i++) {
            dataList.add(i + "");
        }
        double pageSize = row * columns;
        Log.e("yobo___________1",Math.ceil(dataList.size() / pageSize)+"");
        Log.e("yobo___________2",dataList.size() / pageSize+"");
        pageCount = (int)Math.ceil(dataList.size() / pageSize);

    }

    private RecyclerView.ItemDecoration lastItemDecoration = null;
    private HorizontalPageLayoutManager horizontalPageLayoutManager = null;
    private LinearLayoutManager hLinearLayoutManager = null;
    private LinearLayoutManager vLinearLayoutManager = null;
    private DividerItemDecoration hDividerItemDecoration = null;
    private DividerItemDecoration vDividerItemDecoration = null;
    private PagingItemDecoration pagingItemDecoration = null;

    private void init() {
        hLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        hDividerItemDecoration = new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL);
        vDividerItemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        vLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        horizontalPageLayoutManager = new HorizontalPageLayoutManager(row, columns);
        pagingItemDecoration = new PagingItemDecoration(this, horizontalPageLayoutManager);

    }

    private void switchLayout(int checkedId) {
        RecyclerView.LayoutManager layoutManager = null;
        RecyclerView.ItemDecoration itemDecoration = null;
        switch (checkedId) {
            case R.id.rb_horizontal_page:
                layoutManager = horizontalPageLayoutManager;
                itemDecoration = pagingItemDecoration;
                break;
            case R.id.rb_vertical_page:
                layoutManager = vLinearLayoutManager;
                itemDecoration = vDividerItemDecoration;
                break;
            case R.id.rb_vertical_page2:
                layoutManager = hLinearLayoutManager;
                itemDecoration = hDividerItemDecoration;
                break;
        }
        if (layoutManager != null) {
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.removeItemDecoration(lastItemDecoration);
            recyclerView.addItemDecoration(itemDecoration);
            scrollHelper.updateLayoutManger();
            lastItemDecoration = itemDecoration;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onPageChange(int index) {
       indicator.onPageSelected(index);
        tv_title.setText("行" + (index + 1));
    }

    @Override
    public void IndicatorItemClick(int index) {

        Toast.makeText(this,"yobo+"+index,Toast.LENGTH_SHORT).show();
        scrollHelper.setCurrentPage(index);


    }
}

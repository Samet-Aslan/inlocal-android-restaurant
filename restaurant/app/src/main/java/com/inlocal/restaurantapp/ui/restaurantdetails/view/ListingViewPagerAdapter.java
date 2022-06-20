package com.inlocal.restaurantapp.ui.restaurantdetails.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.FeedWallItem;
import com.inlocal.restaurantapp.ui.restaurantdetails.model.PagerModel;
import com.inlocal.restaurantapp.ui.userdetails.view.CustomerPostImagesAdapter;
import com.inlocal.restaurantapp.util.Constants;

import java.util.List;

public class ListingViewPagerAdapter extends PagerAdapter implements CustomerPostImagesAdapter.ImagesCallback {

    private Context context;
    private List<PagerModel> pagerModels;
    private ListPagerItemClick listner;
    private int currentPost = 0;

    public interface ListPagerItemClick {
        void onPagerItemClick(Integer showType, int listItemPos, FeedWallItem item);

        void onPagerListScroll(Integer mPage, int showType);
    }

    public ListingViewPagerAdapter(Context context, List<PagerModel> pagerModels, ListPagerItemClick listner) {
        this.context = context;
        this.pagerModels = pagerModels;
        this.listner = listner;
    }

    @Override
    public Object instantiateItem(@NonNull ViewGroup collection, int position) {
        currentPost = position;
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.layout_listing, collection, false);
        RecyclerView recyclerItem = (RecyclerView) layout.findViewById(R.id.recyclerItem);
        TextView tvNoRecord = (TextView) layout.findViewById(R.id.tvNoRecord);
        CustomerPostImagesAdapter adapter = new CustomerPostImagesAdapter(this);
        adapter.setItemList(pagerModels.get(position).getFeedWallItems());
        adapter.setShowType(pagerModels.get(position).getShowType());
        recyclerItem.setAdapter(adapter);
        recyclerItem.setNestedScrollingEnabled(false);
        if (pagerModels.get(position).getFeedWallItems() != null && pagerModels.get(position).getFeedWallItems().size() > 0) {
            tvNoRecord.setVisibility(View.GONE);
            recyclerItem.setVisibility(View.VISIBLE);
        } else {
            recyclerItem.setVisibility(View.GONE);
            tvNoRecord.setVisibility(View.VISIBLE);
        }

        recyclerItem.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (pagerModels.get(position).getShowType() == Constants.ListShowType.POST) {
                        if (pagerModels.get(position).getFeedWallItems().size() < pagerModels.get(position).getTotalCount()) {
                            pagerModels.get(position).setPageIndex(pagerModels.get(position).getPageIndex());
                            listner.onPagerListScroll(pagerModels.get(position).getPageIndex(),pagerModels.get(position).getShowType());
                        }
                    }else {
                        if (pagerModels.get(position).getFeedWallItems().size() < pagerModels.get(position).getTotalCount()) {
                            pagerModels.get(position).setPageIndex(pagerModels.get(position).getPageIndex());
                            listner.onPagerListScroll(pagerModels.get(position).getPageIndex(),pagerModels.get(position).getShowType());
                        }
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object view) {
        container.removeView((View) view);
    }

    @Override
    public int getCount() {
        return this.pagerModels.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void click(int pos, FeedWallItem item) {

    }

    @Override
    public void clickForPagerAdapterItem(Integer showType, int pos, FeedWallItem item) {
        this.listner.onPagerItemClick(showType, pos, item);
    }

    public void addRow(int viewType, FeedWallItem item) {
        for (int i = 0; i < pagerModels.size(); i++) {
            if (pagerModels.get(i).getShowType() == viewType) {
                pagerModels.get(i).getFeedWallItems().add(item);
            }
        }
        notifyDataSetChanged();
    }
}

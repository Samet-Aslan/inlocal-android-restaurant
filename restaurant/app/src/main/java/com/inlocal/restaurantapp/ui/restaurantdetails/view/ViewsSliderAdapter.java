package com.inlocal.restaurantapp.ui.restaurantdetails.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inlocal.restaurantapp.commonmodel.PostItem;
import com.inlocal.restaurantapp.commonmodel.StoryItem;
import com.inlocal.restaurantapp.databinding.LayoutListingBinding;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.FeedWallItem;
import com.inlocal.restaurantapp.ui.restaurantdetails.model.ViewPageModel;

import java.util.List;


public class ViewsSliderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ViewPageModel> list;
    private ViewPagerItemClickListener listener;

    public interface ViewPagerItemClickListener {
        void onPagerItemClick(int pagePosition, ViewPageModel item, int itemPosition);
    }

    public ViewsSliderAdapter(List<ViewPageModel> list, ViewPagerItemClickListener listener) {
        this.list=list;
        this.listener= listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        if(viewType== ViewPageModel.VIEW_POST) {
            return new SliderViewHolderOne(LayoutListingBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }else if(viewType== ViewPageModel.VIEW_INSIGHT) {
            return new SliderViewHolderTwo(LayoutListingBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }else{
            return new SliderViewHolderOne(LayoutListingBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(list.get(position).getViewType()==ViewPageModel.VIEW_POST){
            ((SliderViewHolderOne)holder).bind(list.get(position));
        }else{
            ((SliderViewHolderTwo)holder).bind(list.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getViewType();
    }

    @Override
    public int getItemCount() {
        //Log.e("sliderCount",((list!=null&&list.size()>0)?list.size():0)+"");
        return list.size();
    }

    public class SliderViewHolderOne extends RecyclerView.ViewHolder implements NewImagesAdapter.ImagesCallback{
        LayoutListingBinding binding;
        private NewImagesAdapter adapter;
        public SliderViewHolderOne(LayoutListingBinding itemView) {
            super(itemView.getRoot());
            binding =itemView;
        }

        void bind(ViewPageModel item){
            adapter = new NewImagesAdapter(this, "1");
            adapter.setItemList(item.getMyPost());
            binding.recyclerPosts.setAdapter(adapter);
        }

        @Override
        public void click(int pos, FeedWallItem item) {
            listener.onPagerItemClick(getAdapterPosition(), list.get(getAdapterPosition()),pos);
        }
    }

    public class SliderViewHolderTwo extends RecyclerView.ViewHolder implements NewStoryImagesAdapter.NewStoryImagesCallback {
        LayoutListingBinding binding;
        private NewStoryImagesAdapter storyAdapter;
        public SliderViewHolderTwo(LayoutListingBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
        void bind(ViewPageModel item){
            storyAdapter = new NewStoryImagesAdapter(this);
            storyAdapter.setStoryItemList(item.getMyInsight());
            binding.recyclerPosts.setAdapter(storyAdapter);
        }

        @Override
        public void onStoryItemClick(int pos, FeedWallItem storyItem) {
            listener.onPagerItemClick(getAdapterPosition(), list.get(getAdapterPosition()),pos);
        }
    }

    public void setListData(List<ViewPageModel> list){
        this.list=list;
        notifyDataSetChanged();
    }
}

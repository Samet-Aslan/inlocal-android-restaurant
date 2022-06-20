package com.inlocal.restaurantapp.ui.restaurantdetails.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inlocal.restaurantapp.commonmodel.PostItem;
import com.inlocal.restaurantapp.commonmodel.StoryItem;
import com.inlocal.restaurantapp.databinding.ItemImageBinding;
import com.inlocal.restaurantapp.databinding.StoryItemImageBinding;

import java.util.List;

public class StoryImagesAdapter extends RecyclerView.Adapter<StoryImagesAdapter.Holder> {
    private StoryImagesCallback mImagesCallback;
    private List<StoryItem> itemList;

    public StoryImagesAdapter(StoryImagesCallback mImagesCallback) {
        this.mImagesCallback = mImagesCallback;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(StoryItemImageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(itemList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemList != null ? itemList.size() : 0;
    }

    public class Holder extends RecyclerView.ViewHolder {
        StoryItemImageBinding binding;

        public Holder(@NonNull StoryItemImageBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        void bind(StoryItem item) {
            binding.setData(item);
           /* if (type.equals("fav")) {
                binding.imgTag.setVisibility(View.VISIBLE);
            } else {
                binding.imgTag.setVisibility(View.GONE);
            }
            binding.getRoot().setOnClickListener(v -> mImagesCallback.click(getAdapterPosition()));
            if ((getAdapterPosition() % 3) == 0) {
                binding.imgFood.setImageResource(R.drawable.food);
                binding.icPlay.setVisibility(View.GONE);
            } else if ((getAdapterPosition() % 3) == 1) {
                binding.imgFood.setImageResource(R.drawable.place_four);
                binding.icPlay.setVisibility(View.VISIBLE);
            } else {
                binding.imgFood.setImageResource(R.drawable.place_one);
                binding.icPlay.setVisibility(View.GONE);
            }*/
        }
    }

    public interface StoryImagesCallback {
        void onStoryItemClick(int pos, StoryItem storyItem);
    }

    public void setStoryItemList(List<StoryItem> list) {
        this.itemList = list;
        notifyDataSetChanged();
    }

    public void addRow(StoryItem item) {
        this.itemList.add(item);
        notifyItemInserted(this.itemList.size() - 1);
    }
}

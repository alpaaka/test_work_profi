package ru.alpaaka.testprofi.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.vk.sdk.api.model.VKApiUser;

import java.util.ArrayList;

import ru.alpaaka.testprofi.R;
import ru.alpaaka.testprofi.data.source.images.ImageLoader;

public class FriendsRecyclerViewAdapter extends
        RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int LOADING = 0;
    private static final int USER = 1;

    private ArrayList<Object> list;
    private Context context;

    public FriendsRecyclerViewAdapter(ArrayList<Object> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i) {
            case LOADING:
                View loadView = LayoutInflater.from(context)
                        .inflate(R.layout.rv_item_load, viewGroup, false);
                return new LoadingHolder(loadView);
            default:
            case USER:
                View userView = LayoutInflater.from(context)
                        .inflate(R.layout.rv_item_user, viewGroup, false);
                return new UserHolder(userView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (getItemViewType(i) == USER) {
            ((UserHolder) viewHolder).bind((VKApiUser) list.get(i));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position) instanceof VKApiUser) {
            return USER;
        }
        return LOADING;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void showLoading(boolean loading) {
        int position = this.list.size();
        if (loading) {
            this.list.add(new Object());
            notifyItemInserted(position);
        } else {
            notifyItemRemoved(position - 1);
            this.list.remove(position - 1);
        }
    }

    public void displayResult(ArrayList<VKApiUser> list) {
        int position = this.list.size();
        this.list.addAll(list);
        notifyItemRangeInserted(position, list.size());
    }

    class UserHolder extends RecyclerView.ViewHolder {

        private TextView tvFullname;
        private ImageView ivIcon;

        UserHolder(@NonNull View itemView) {
            super(itemView);
            tvFullname = itemView.findViewById(R.id.tv_fullname);
            ivIcon = itemView.findViewById(R.id.iv_photo);
        }

        void bind(VKApiUser user) {
            tvFullname
                    .setText(context.getString(R.string.fullname, user.first_name, user.last_name));
            ImageLoader.getInstance().bind(ivIcon, user.photo_100);
        }
    }

    class LoadingHolder extends RecyclerView.ViewHolder {

        private ProgressBar progressBar;

        LoadingHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

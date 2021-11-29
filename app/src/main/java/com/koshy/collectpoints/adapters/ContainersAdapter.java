package com.koshy.collectpoints.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.koshy.collectpoints.R;
import com.koshy.collectpoints.models.ContainerModel;

import java.util.ArrayList;

public class ContainersAdapter extends RecyclerView.Adapter<ContainersAdapter.ContainerVH> {
    Context context;
    ArrayList<ContainerModel> phoneDataModelArrayList;
    ContainersAdapter.TOnPopupMenuClickListener onPopupClickListener;

    public ContainersAdapter(Context context, ArrayList<ContainerModel> matchItemArrayList) {
        this.context = context;
        this.phoneDataModelArrayList = matchItemArrayList;
    }

    @NonNull
    @Override
    public ContainersAdapter.ContainerVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.container_item, parent, false);
        return new ContainersAdapter.ContainerVH(v);
    }

    public void removeItem(int pos) {
//        Toast.makeText(context, "removed item from adapter: " + pos, Toast.LENGTH_SHORT).show();
        phoneDataModelArrayList.remove(pos);
        notifyItemRemoved(pos);
    }

    public void addItem(int pos, ContainerModel PhoneDataModel) {
        int i = phoneDataModelArrayList.size();
        if (i > 0) {
            phoneDataModelArrayList.add(i, PhoneDataModel);
        } else {
            phoneDataModelArrayList.add(PhoneDataModel);
        }
//        notifyItemInserted(i);
        notifyDataSetChanged();
    }

    public void editItem(int pos, ContainerModel dollarModel) {
        Log.d("cocacola", "bbbbbbbbbbbbyessssssss: " + pos);
//        phoneDataModelArrayList.get(pos).setTeam_a_name(match.getTeam_a_name());

        notifyItemChanged(pos);
    }


    @Override
    public void onBindViewHolder(@NonNull ContainersAdapter.ContainerVH holder, int position) {
        ContainerModel PhoneDataModel = phoneDataModelArrayList.get(position);
//        holder.teamAImage.setImageResource(context.getResources().getIdentifier(dollarModel.getTeam_a_image(), "drawable", context.getPackageName()));
        Glide.with(context).load(PhoneDataModel.getImg()).into(holder.cImage);
        holder.cName.setText(PhoneDataModel.getName());
    }

    @Override
    public int getItemCount() {
        return phoneDataModelArrayList.size();
    }

    class ContainerVH extends RecyclerView.ViewHolder {
        CardView parent;
        ImageView cImage;
        TextView cName;

        public ContainerVH(@NonNull View itemView) {
            super(itemView);

            parent = (CardView) itemView.findViewById(R.id.container_card);
            cImage = (ImageView) itemView.findViewById(R.id.img);
            cName = (TextView) itemView.findViewById(R.id.comName);
            parent.setOnClickListener(v->{
                onPopupClickListener.onConClicked(getAdapterPosition(), phoneDataModelArrayList.get(getAdapterPosition()));
            });
            /*parent.setOnLongClickListener(view -> {
//                showPopupMenu(view, getAdapterPosition());
                return false;
            });*/
        }
    }

    /*public void showPopupMenu(View view, int pos) {
        PopupMenu popup = new PopupMenu(context, (LinearLayout) view);
        popup.getMenuInflater().inflate(R.menu.dollar_popup_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.popup_delete) {
                onPopupClickListener.onDeleteClicked(pos, phoneDataModelArrayList.get(pos).getPhoneFireId());
            } else if (item.getItemId() == R.id.popup_edit) {
                onPopupClickListener.onEditClicked(pos, phoneDataModelArrayList.get(pos));
            }
            return false;
        });
        popup.show();
    }*/

    public interface TOnPopupMenuClickListener {
        void onDeleteClicked(int pos, String matchID);

        void onEditClicked(int pos, ContainerModel model);
        void onConClicked(int pos, ContainerModel model);
    }

    public void setOnPopupClickListener(ContainersAdapter.TOnPopupMenuClickListener onPopupClickListener) {
        this.onPopupClickListener = onPopupClickListener;
    }
}

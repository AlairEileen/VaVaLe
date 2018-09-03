package space.alair.vavale.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import space.alair.vavale.R;
import space.alair.vavale.models.MsgModel;

/**
 * Created by Alair on 3/22/2018.
 */

public class MsgDetailAdapter extends BaseLoadMoreAdapter<MsgModel> {


    public MsgDetailAdapter(List<MsgModel> models, Context context) {
        super(models, context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.load_more_msg_detail_item, parent, false);
        return new MsgDetailAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MsgDetailAdapter.RecyclerViewHolder recyclerViewHolder = (MsgDetailAdapter.RecyclerViewHolder) holder;

        recyclerViewHolder.tv_msg_detail_date.setText(models.get(position).getDate());
        recyclerViewHolder.tv_msg_content.setText(models.get(position).getContent());
        recyclerViewHolder.tv_msg_detail_receive_number.setText(models.get(position).getReceiveNumber());
        recyclerViewHolder.tv_msg_detail_price.setText(models.get(position).getPrice() + "");
    }


    private class RecyclerViewHolder extends RecyclerView.ViewHolder {


        TextView tv_msg_detail_date;
        TextView tv_msg_detail_receive_number;
        TextView tv_msg_content;
        TextView tv_msg_detail_price;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            tv_msg_detail_date = (TextView) itemView.findViewById(R.id.tv_msg_detail_date);
            tv_msg_detail_receive_number = (TextView) itemView.findViewById(R.id.tv_msg_detail_receive_number);
            tv_msg_content = (TextView) itemView.findViewById(R.id.tv_msg_detail_content);
            tv_msg_detail_price = (TextView) itemView.findViewById(R.id.tv_msg_detail_price);
        }
    }
}

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

public class MsgListAdapter extends BaseLoadMoreAdapter<MsgModel> {


    public MsgListAdapter(List<MsgModel> models, Context context) {
        super(models, context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.load_more_msgs_item, parent, false);
        return new MsgListAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MsgListAdapter.RecyclerViewHolder recyclerViewHolder = (MsgListAdapter.RecyclerViewHolder) holder;
//        holder.itemView.setOnClickListener(view -> {
//
//            Intent intent = new Intent(context, MsgDetailActivity.class);
//            intent.putExtra(MsgDetailActivity.TITLE, models.get(position).getPhone());
//            context.startActivity(intent);
//        });
        recyclerViewHolder.tv_msg_phone.setText(models.get(position).getPhone());
        recyclerViewHolder.tv_msg_content.setText(models.get(position).getContent());
        recyclerViewHolder.tv_msg_date.setText(models.get(position).getDate());
    }


    private class RecyclerViewHolder extends RecyclerView.ViewHolder {


        TextView tv_msg_phone;
        TextView tv_msg_date;
        TextView tv_msg_content;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            tv_msg_phone = (TextView) itemView.findViewById(R.id.tv_msg_phone);
            tv_msg_date = (TextView) itemView.findViewById(R.id.tv_msg_date);
            tv_msg_content = (TextView) itemView.findViewById(R.id.tv_msg_content);
        }
    }
}

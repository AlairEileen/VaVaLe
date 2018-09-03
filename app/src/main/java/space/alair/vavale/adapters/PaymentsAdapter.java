package space.alair.vavale.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import space.alair.vavale.R;
import space.alair.vavale.models.PaymentModel;

/**
 * Created by Alair on 3/22/2018.
 */

public class PaymentsAdapter extends BaseLoadMoreAdapter<PaymentModel> {

    public PaymentsAdapter(List<PaymentModel> models, Context context) {
        super(models, context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.load_more_payments_item, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        holder.itemView.setBackgroundColor(context.getResources().getColor(position % 2 == 0 ? R.color.theme_control_bg_black : R.color.theme_bg));

        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
        recyclerViewHolder.tv_payment_date.setText(models.get(position).getPayment_date());
        recyclerViewHolder.tv_payment.setText(models.get(position).getPayment() + "");
    }



    private class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView tv_payment;
        TextView tv_payment_date;
        RecyclerViewHolder(View itemView) {
            super(itemView);
            tv_payment = (TextView) itemView.findViewById(R.id.tv_payment);
            tv_payment_date = (TextView) itemView.findViewById(R.id.tv_payment_date);
        }
    }
}

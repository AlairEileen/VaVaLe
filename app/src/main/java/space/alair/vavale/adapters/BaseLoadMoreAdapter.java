package space.alair.vavale.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Created by Alair on 3/22/2018.
 */

public abstract class BaseLoadMoreAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected List<T> models;
    protected Context context;

    public BaseLoadMoreAdapter(List<T> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}

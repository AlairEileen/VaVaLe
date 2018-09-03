package space.alair.vavale.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import space.alair.vavale.HomeActivity;
import space.alair.vavale.ProjectsActivity;
import space.alair.vavale.R;
import space.alair.vavale.models.ProjectModel;
import space.alair.vavale.view_tools.Pop;

/**
 * Created by Alair on 3/22/2018.
 */

public class ProjectsAdapter extends BaseLoadMoreAdapter<ProjectModel> {

    public ProjectsAdapter(List<ProjectModel> models, Context context) {
        super(models, context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.load_more_projects_item, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(view -> {

            ProjectsActivity projectsActivity = (ProjectsActivity) context;
            Pop.createOkCancelPop(projectsActivity, R.layout.pop_ok_cancel, view, () -> {
                projectsActivity.setResult(0, new Intent().putExtra(HomeActivity.PROJECT_ID, models.get(position).getId()));
                projectsActivity.finish();
            }, "确定选择此项目吗？");
        });
        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
        recyclerViewHolder.tv_project_price.setText(models.get(position).getPrice() + "");
        recyclerViewHolder.tv_project_word.setText(models.get(position).getWord());
        recyclerViewHolder.tv_project_content.setText(models.get(position).getContent());
    }


    private class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView tv_project_word;
        TextView tv_project_price;
        TextView tv_project_content;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            tv_project_word = (TextView) itemView.findViewById(R.id.tv_project_word);
            tv_project_price = (TextView) itemView.findViewById(R.id.tv_project_price);
            tv_project_content = (TextView) itemView.findViewById(R.id.tv_project_content);
        }
    }
}

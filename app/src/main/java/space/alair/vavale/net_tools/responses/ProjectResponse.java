package space.alair.vavale.net_tools.responses;

import android.content.Context;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import space.alair.vavale.models.BaseModel;
import space.alair.vavale.models.DataExecutor;
import space.alair.vavale.models.ProjectContextModel;
import space.alair.vavale.net_tools.requests.ProjectAction;

public class ProjectResponse extends BaseResponse<ProjectAction> {


    private static ProjectResponse projectResponse;

    public static ProjectResponse getRes() {
        if (projectResponse == null) projectResponse = new ProjectResponse();
        return projectResponse;
    }


    @Override
    protected RetrofitCreateType setRetrofitCreateType() {
        return RetrofitCreateType.hasHeader;
    }

    public void getProjectList(Context context, int pageIndex, ResponseListener<ProjectContextModel> projectResListener) {
        getAction().getProjectList(pageIndex).enqueue(doResponse(context, projectResListener, () -> {
            getProjectList(context, pageIndex, projectResListener);
        }));
    }

    public void getProjectList(Context context, int pageIndex, String queryWord, ResponseListener<ProjectContextModel> projectResListener) {
        getAction().getProjectList(pageIndex, queryWord).enqueue(doResponse(context, projectResListener, () -> {

        }));

    }


}

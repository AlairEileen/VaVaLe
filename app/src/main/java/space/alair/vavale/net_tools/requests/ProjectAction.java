package space.alair.vavale.net_tools.requests;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import space.alair.vavale.models.BaseModel;
import space.alair.vavale.models.ProjectContextModel;
import space.alair.vavale.net_tools.RequestUrl;

/**
 * Created by Alair on 3/26/2018.
 */

public interface ProjectAction {

    @GET("xcx/v1/project")
    Call<BaseModel<ProjectContextModel>> getProjectList(@Query("page") int pageIndex, @Query("query") String queryWord);

    @GET("xcx/v1/project")
    Call<BaseModel<ProjectContextModel>> getProjectList(@Query("page") int pageIndex);

   }


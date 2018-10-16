import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RequestGet {
  @GET("auth/access_token")
  Call<ResponseBody> callAuth(@Query("user_id")String userId , @Query("user_secret") String userSecret);
  @GET("organization/list")
  Call<ResponseBody> callOrganization(@Query("access_token")String accessToken , @Query("request_timeout") int requestTimeout);


}

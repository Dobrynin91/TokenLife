import okhttp3.ResponseBody;
import org.joda.time.DateTime;
import org.joda.time.Minutes;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;

public class GetToken {
  private static RequestGet service;

  public static void main(String[] args) {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://iiko.biz:9900/api/0/")
            .build();
    service = retrofit.create(RequestGet.class);
    System.out.println("The token is " + getToken());
    DateTime d1 = new DateTime();
    if (getToken() != null) {
      while (true) {
        String info = getOrganization(getToken());
        if (info.contains("Рабоче-Крестьянская,")) {
          continue;
        }
      }
    }
    DateTime d2 = new DateTime();
    int difTime = Minutes.minutesBetween(d2, d1).getMinutes();
    System.out.println("The token lives is " + difTime);
  }

  public static String getToken() {
    Call<ResponseBody> auth = service.callAuth("SovyNezhnye_API", "m2jbp3SpVw4B");
    try {
      Response<ResponseBody> response = auth.execute();
      String responseBody = response.body().string().replace("\"", "");
      return responseBody;
    } catch (IOException e) {
      System.out.println("getToken exception is " + e);
    }
    return null;
  }

  public static String getOrganization(String token) {
    Call<ResponseBody> organization = service.callOrganization(token, 10000);
    try {
      Response<ResponseBody> response = organization.execute();
      String responseBody = response.body().string();
      return responseBody;
    } catch (IOException e) {
      System.out.println("getOrganization xception is " + e);
    }
    return null;
  }
}

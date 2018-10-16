import okhttp3.ResponseBody;
import org.joda.time.DateTime;
import org.joda.time.Minutes;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;

public class GetToken {
  private static RequestGet service;

  public static void main(String[] args) throws InterruptedException {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://iiko.biz:9900/api/0/")
            .build();
    service = retrofit.create(RequestGet.class);
    String token = getToken();
    System.out.println("The token is " + token);
    DateTime d1 = new DateTime();
    if (getToken() != null) {
      try {
        while (getOrganization(token).contains("Рабоче-Крестьянская,")) {
          String info = getOrganization(token);
          System.out.println(info);
          Thread.sleep(60000);
        }
      } catch (NullPointerException e) {
        DateTime d2 = new DateTime();
        int difTime = Minutes.minutesBetween(d1, d2).getMinutes();
        System.out.println("The token lives " + difTime + " minutes");
      }
    }
  }

  private static String getToken() {
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

  private static String getOrganization(String token) {
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

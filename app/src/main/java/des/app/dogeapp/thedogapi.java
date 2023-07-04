package des.app.dogeapp;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface thedogapi {
    @GET("breeds")
    Call<List<ClassDog>> getDogBreeds(@Header("x-api-key") String apiKey);
}

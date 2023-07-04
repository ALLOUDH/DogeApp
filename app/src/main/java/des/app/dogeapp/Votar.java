package des.app.dogeapp;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import des.app.dogeapp.R;

public class Votar extends AppCompatActivity {

    private ImageView img_perro,imglike,imgdislike;
    private Button btnFavorite;
    ImageButton btnMenu;
    private List<String> imageUrls;
    private int currentImageIndex = -1;


    private static final String API_IMAGES_URL = "https://api.thedogapi.com/v1/images/search?limit=10&page=0";
    private static final String API_KEY = "AIzaSyD07SXCPDYkE8Hhf2XGywgYkjuT8xrUSxE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_votar);

        btnMenu=findViewById(R.id.btn_menu);
        img_perro=findViewById(R.id.img_perro);
        imglike = findViewById(R.id.img_like);
        imgdislike = findViewById(R.id.img_dislike);
        btnFavorite = findViewById(R.id.btn_favoritos);

        imageUrls = new ArrayList<>();

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Votar.this,MainActivity.class);
                startActivity(intent);
            }
        });

        imglike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likeImage();
            }
        });

        imgdislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dislikeImage();
            }
        });

        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToFavorites();
            }
        });

        new des.app.dogeapp.Votar.FetchBreedImagesTask().execute();
    }

    private void likeImage() {
        if (!imageUrls.isEmpty()) {
            Toast.makeText(this, "Me gusta", Toast.LENGTH_SHORT).show();
            updateRandomImage();
        }
    }

    private void dislikeImage() {
        if (!imageUrls.isEmpty()) {
            Toast.makeText(this, "No me gusta", Toast.LENGTH_SHORT).show();
            updateRandomImage();
        }
    }

    private void addToFavorites() {
        if (currentImageIndex >= 0 && currentImageIndex < imageUrls.size()) {
            Toast.makeText(this, "Añadido a favoritos", Toast.LENGTH_SHORT).show();

        }
    }

    private void loadImage(String imageUrl) {
        Picasso.get()
                .load(imageUrl)
                .fit()
                .centerInside()
                .into(img_perro);
    }

    private class FetchBreedImagesTask extends AsyncTask<Void, Void, List<String>> {

        @Override
        protected List<String> doInBackground(Void... voids) {
            List<String> urls = new ArrayList<>();

            try {
                URL url = new URL(API_IMAGES_URL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("x-api-key", API_KEY);
                connection.connect();

                InputStream inputStream = connection.getInputStream();
                String result = convertInputStreamToString(inputStream);

                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String imageUrl = jsonObject.getString("url");
                    urls.add(imageUrl);
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return urls;
        }

        @Override
        protected void onPostExecute(List<String> urls) {
            imageUrls.clear();
            imageUrls.addAll(urls);
            updateRandomImage();
        }
    }

    private void updateRandomImage() {
        if (!imageUrls.isEmpty()) {
            Random random = new Random();
            currentImageIndex = random.nextInt(imageUrls.size());
            loadImage(imageUrls.get(currentImageIndex));
        }
    }

    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        byte[] buffer = new byte[1024];

        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            stringBuilder.append(new String(buffer, 0, length));
        }

        inputStream.close();
        return stringBuilder.toString();
    }
}
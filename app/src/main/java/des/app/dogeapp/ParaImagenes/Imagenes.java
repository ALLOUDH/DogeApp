package des.app.dogeapp.ParaImagenes;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import des.app.dogeapp.AgrandarImagen;
import des.app.dogeapp.MainActivity;
import des.app.dogeapp.ParaImagenes.ImageAdapter;
import des.app.dogeapp.R;

public class Imagenes extends AppCompatActivity {

    private Spinner spinner;
    private RecyclerView recyclerView;
    private ArrayAdapter<String> spinnerAdapter;
    private List<String> breedList;
    private List<String> imageUrls;
    private ImageAdapter imageAdapter;
    String save_image_url;
    ImageView img_menu;

    private static final String API_URL = "https://api.thedogapi.com/v1/breeds";
    private static final String API_IMAGES_URL = "https://api.thedogapi.com/v1/images/search";
    private static final String API_KEY = "AIzaSyD07SXCPDYkE8Hhf2XGywgYkjuT8xrUSxE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagenes);

        spinner = findViewById(R.id.spinner);
        recyclerView = findViewById(R.id.recyclerView);
        img_menu = findViewById(R.id.btn_menu);

        breedList = new ArrayList<>();
        imageUrls = new ArrayList<>();
        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, breedList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        imageAdapter = new ImageAdapter(this, imageUrls);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        recyclerView.setAdapter(imageAdapter);

        img_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Imagenes.this, MainActivity.class);
                startActivity(i);
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedBreed = breedList.get(position);
                new FetchBreedImagesTask().execute(selectedBreed);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        new FetchBreedsTask().execute();

    }

    private class FetchBreedsTask extends AsyncTask<Void, Void, List<String>> {

        @Override
        protected List<String> doInBackground(Void... voids) {
            List<String> breeds = new ArrayList<>();

            try {
                URL url = new URL(API_URL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("x-api-key", API_KEY);
                connection.connect();

                InputStream inputStream = connection.getInputStream();
                String result = convertInputStreamToString(inputStream);


                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String breedName = jsonObject.getString("name");
                    breeds.add(breedName);
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return breeds;
        }

        @Override
        protected void onPostExecute(List<String> breeds) {
            breedList.clear();
            breedList.addAll(breeds);
            spinnerAdapter.notifyDataSetChanged();
        }
    }

    private class FetchBreedImagesTask extends AsyncTask<String, Void, List<String>> {

        @Override
        protected List<String> doInBackground(String... strings) {
            List<String> urls = new ArrayList<>();
            String breedName = strings[0];
            List<String> breedIds = getBreedIds(breedName);

            if (breedIds.isEmpty()) {
                return urls;
            }

            try {
                for (String breedId : breedIds) {
                    URL url = new URL(API_IMAGES_URL + "?breed_id=" + breedId + "&limit=10");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestProperty("x-api-key", API_KEY);
                    connection.connect();

                    InputStream inputStream = connection.getInputStream();
                    String result = convertInputStreamToString(inputStream);
                    Log.d("API Response", result);

                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String imageUrl = jsonObject.getString("url");
                        urls.add(imageUrl);
                        save_image_url = imageUrl;
                    }
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
            imageAdapter.notifyDataSetChanged();
        }
    }

    private List<String> getBreedIds(String breedName) {
        List<String> breedIds = new ArrayList<>();
        for (String breed : breedList) {
            if (breed.equalsIgnoreCase(breedName)) {
                int position = breedList.indexOf(breed);
                String breedId = getBreedIdFromAPI(position + 0);
                if (breedId != null) {
                    breedIds.add(breedId);
                }
            }
        }
        return breedIds;
    }

    private String getBreedIdFromAPI(int position) {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("x-api-key", API_KEY);
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            String result = convertInputStreamToString(inputStream);

            JSONArray jsonArray = new JSONArray(result);
            if (position >= 0 && position < jsonArray.length()) {
                JSONObject jsonObject = jsonArray.getJSONObject(position);
                return jsonObject.getString("id");
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
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
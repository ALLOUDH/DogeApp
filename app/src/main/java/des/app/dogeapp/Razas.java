package des.app.dogeapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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



import des.app.dogeapp.R;

public class Razas extends AppCompatActivity {

    private Spinner spnRaza;
    ImageView btnMenu;
    private ImageView imgPerro;
    private TextView txtRaza, txtCriadoPara, txtGrupoRaza, txtVida, txtTemperamento, txtOrigen;

    private List<String> breedList;
    private ArrayAdapter<String> adapter;
    private String selectedBreed,Save_Url;

    private static final String API_URL = "https://api.thedogapi.com/v1/breeds";
    private static final String API_IMAGES_URL = "https://api.thedogapi.com/v1/images/search";
    private static final String API_KEY = "AIzaSyD07SXCPDYkE8Hhf2XGywgYkjuT8xrUSxE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_razas);

        spnRaza = findViewById(R.id.spnRaza);
        imgPerro = findViewById(R.id.imgPerroRaza);
        txtRaza = findViewById(R.id.txt_nombre);
        txtCriadoPara = findViewById(R.id.txtCriadoPara);
        txtGrupoRaza = findViewById(R.id.txtGrupoRaza);
        txtVida = findViewById(R.id.txtVida);
        txtTemperamento = findViewById(R.id.txtTemperamento);
        txtOrigen = findViewById(R.id.txtOrigen);
        btnMenu=findViewById(R.id.btn_menu);

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Razas.this,MainActivity.class);
                startActivity(intent);
            }
        });

        breedList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, breedList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnRaza.setAdapter(adapter);

        spnRaza.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                selectedBreed = breedList.get(position);
                new Razas.FetchBreedDetailsTask().execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        new Razas.FetchBreedsTask().execute();

        imgPerro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Razas.this, AgrandarImagen.class);
                intent.putExtra("url_imagen",Save_Url);
                startActivity(intent);
            }
        });

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
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        }
    }

    private class FetchBreedDetailsTask extends AsyncTask<Void, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(Void... voids) {
            JSONObject breedDetails = null;

            try {
                String breedSearchUrl = API_URL + "/search?q=" + selectedBreed.replace(" ", "%20");

                URL url = new URL(breedSearchUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("x-api-key", API_KEY);
                connection.connect();

                InputStream inputStream = connection.getInputStream();
                String result = convertInputStreamToString(inputStream);

                JSONArray breedDetailsArray = new JSONArray(result);
                if (breedDetailsArray.length() > 0) {
                    breedDetails = breedDetailsArray.getJSONObject(0);
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return breedDetails;
        }

        @Override
        protected void onPostExecute(JSONObject breedDetails) {
            if (breedDetails != null) {
                try {
                    String nombre = breedDetails.getString("name");
                    String criadoPara = breedDetails.optString("bred_for");
                    String grupoRaza = breedDetails.optString("breed_group");
                    String esperanzaVida = breedDetails.optString("life_span");
                    String Temperamento = breedDetails.optString("temperament");
                    String Origen = breedDetails.optString("origin");
                    String breedId = breedDetails.optString("id");

                    txtRaza.setText("Nombre: " + nombre);
                    txtCriadoPara.setText("Criado para: " + criadoPara);
                    txtGrupoRaza.setText("Grupo de raza: " + grupoRaza);
                    txtVida.setText("Esperanza de vida: " + esperanzaVida);
                    txtTemperamento.setText("Temperamento: " + Temperamento);
                    txtOrigen.setText("Origen: " + Origen);

                    if (breedId != null && !breedId.isEmpty()) {
                        new Razas.FetchBreedImageTask().execute(breedId);
                    } else {
                        imgPerro.setImageDrawable(null);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class FetchBreedImageTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String imageUrl = null;
            String breedId = strings[0];

            try {
                URL url = new URL(API_IMAGES_URL + "?breed_ids=" + breedId);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("x-api-key", API_KEY);
                connection.connect();

                InputStream inputStream = connection.getInputStream();
                String result = convertInputStreamToString(inputStream);

                JSONArray imageArray = new JSONArray(result);
                if (imageArray.length() > 0) {
                    JSONObject imageObject = imageArray.getJSONObject(0);
                    imageUrl = imageObject.optString("url");
                    Save_Url = imageUrl;
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return imageUrl;
        }

        @Override
        protected void onPostExecute(String imageUrl) {
            if (imageUrl != null && !imageUrl.isEmpty()) {
                Picasso.get().load(imageUrl).into(imgPerro);
            } else {
                imgPerro.setImageDrawable(null);
            }
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

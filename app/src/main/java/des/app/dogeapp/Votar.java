package des.app.dogeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Votar extends AppCompatActivity{

    ImageView img_menu;
    ImageView img_like,img_dislike;
    Button btn_favoritos;
    ImageView img_perro;
    String cuerpo, save_url, save_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        img_menu = findViewById(R.id.btn_menu);
        img_perro = findViewById(R.id.img_perro);
        img_like = findViewById(R.id.img_like);
        img_dislike = findViewById(R.id.img_dislike);
        btn_favoritos = findViewById(R.id.btn_favoritos);
        solicitar_imagenes();


        img_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Votar.this,MainActivity.class);
                startActivity(intent);
            }
        });

        img_perro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Votar.this, AgrandarImagen.class);
                intent.putExtra("url_imagen", save_url);
                startActivity(intent);
            }
        });

        btn_favoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Votar.this, Favoritos.class);
                startActivity(intent);
            }
        });

        img_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                solicitar_imagenes();

                SharedPreferences preferences = getSharedPreferences("Favoritos", MODE_PRIVATE);
                Set<String> set_image_url = preferences.getStringSet("url_imagenes_favoritos", new HashSet<>());

                set_image_url.add(save_url);

                SharedPreferences.Editor editor = preferences.edit();
                editor.putStringSet("url_imagenes_favoritos", set_image_url);
                editor.apply();
            }
        });

        img_dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                solicitar_imagenes();
            }
        });
    }

    public void  solicitar_imagenes(){
        OkHttpClient okHttpClient = new OkHttpClient();
        String url = "https://api.thedogapi.com/v1/images/search";

        Request request = new Request.Builder().url(url).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("Error","No se pudo llamar a las imagenes");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    cuerpo = response.body().string();
                    try {
                        JSONArray jsonArray = new JSONArray(cuerpo);
                        if (jsonArray.length() > 0){
                            int imagenes = new Random().nextInt(jsonArray.length());
                            JSONObject img_obj = jsonArray.getJSONObject(imagenes);
                            String url_imagen = img_obj.getString("url");
                            String id_imagen = img_obj.getString("id");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Picasso.get().load(url_imagen).into(img_perro);
                                    save_url = url_imagen;
                                    save_id = id_imagen;
                                }
                            });
                        }

                    } catch (JSONException e) {
                        Log.e("Error","Fallo la consulta de datos");
                    }
                }else {
                    Toast.makeText(Votar.this,"No se pudo consultar las imagenes",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
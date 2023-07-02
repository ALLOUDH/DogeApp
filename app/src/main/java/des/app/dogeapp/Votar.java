package des.app.dogeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.gson.Gson;

public class Votar extends AppCompatActivity{

    ImageButton imgmenu;
    ImageView img_perro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        imgmenu=findViewById(R.id.btn_menu);
        img_perro = findViewById(R.id.img_perro);


        imgmenu.setOnClickListener(new View.OnClickListener() {
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
                startActivity(intent);
            }
        });
    }

    public void call_api(View view) {
        String url= "https://api.thecatapi.com/v1/images/search?breed_ids=beng";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest string = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Votar.this, "" + response, Toast.LENGTH_SHORT).show();
                Gson gson = new Gson();
                clasePerro clasePerro = gson.fromJson(response, clasePerro.class);
                String imagen = clasePerro.getUrl();
                Toast.makeText(Votar.this, ""+imagen, Toast.LENGTH_SHORT).show();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Votar.this, "Hubo un error", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(string);
    }
}
package des.app.dogeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.List;

import des.app.dogeapp.Peticiones.Raza;

public class Razas extends AppCompatActivity {

    ImageView imagenPerro, btnMenu;
    EditText inputRaza;
    TextView txtRaza, txtAltura, txtEdad, txtDescripcion, txtPeso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_razas);
        btnMenu = findViewById(R.id.btn_menu);

        inputRaza = findViewById(R.id.edt_nombre_ra);
        imagenPerro = findViewById(R.id.img_perro_ra);

        txtPeso = findViewById(R.id.peso);
        txtDescripcion = findViewById(R.id.descripcion);
        txtEdad = findViewById(R.id.edad);
        txtRaza = findViewById(R.id.raza);
        txtAltura = findViewById(R.id.altura);

        imagenPerro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Razas.this, AgrandarImagen.class);
                startActivity(i);
            }
        });

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Razas.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    public void btnBuscarRaza(View view) {
        String url = "https://api.thedogapi.com/v1/breeds/search?q=" + inputRaza.getText().toString().toLowerCase();
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                Type dogListType = new TypeToken<List<Raza>>() {
                }.getType();
                List<Raza> perro = gson.fromJson(response, dogListType);

                if (perro.get(0).getReferenceImageId() == null) {
                    Toast.makeText(Razas.this, "No tiene imagen", Toast.LENGTH_SHORT).show();
                }

                String peso = perro.get(0).getWeightMetric();
                String descripcion = perro.get(0).getTemperament();
                String edad = perro.get(0).getLifeSpan();
                String raza = perro.get(0).getName();
                String altura = perro.get(0).getHeightImperial();

                txtPeso.append(peso);
                txtEdad.append(edad);
                txtRaza.append(raza);
                txtAltura.append(altura);
                txtDescripcion.append(descripcion);

                System.out.println("Peso: " + peso);
                System.out.println("Descripcion: " + descripcion);
                System.out.println("Edad: " + edad);
                System.out.println("Raza: " + raza);
                System.out.println("Altura: " + altura);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Razas.this, "Hubo un error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);

    }
}
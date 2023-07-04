package des.app.dogeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Favoritos extends AppCompatActivity {

    private static final int STORAGE_PERMISSION_REQUEST_CODE = 1;
    RecyclerView rcv_fav;
    private List<String> lista_url_imagenes;
    private FavoritosAdaptador favoritosAdaptador;
    ImageView btn_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);
        btn_menu = findViewById(R.id.btn_menu);

        rcv_fav = findViewById(R.id.rcv_fav);
        rcv_fav.setLayoutManager(new GridLayoutManager(this,2));
        rcv_fav.setHasFixedSize(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        STORAGE_PERMISSION_REQUEST_CODE);
            } else {
                cargar_favoritos();
            }
        } else {
            cargar_favoritos();
        }

        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Favoritos.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void cargar_favoritos() {
        SharedPreferences preferences = getSharedPreferences("Favoritos", Context.MODE_PRIVATE);
        Set<String> listar_url_imagen = preferences.getStringSet("url_imagenes_favoritos", new HashSet<>());

        lista_url_imagenes = new ArrayList<>(listar_url_imagen);

        if (lista_url_imagenes.isEmpty()){
            Toast.makeText(Favoritos.this,"Usted no tiene imagenes favoritas", Toast.LENGTH_SHORT).show();
        }
        favoritosAdaptador = new FavoritosAdaptador(lista_url_imagenes);
        rcv_fav.setAdapter(favoritosAdaptador);
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences preferences = getSharedPreferences("Favoritos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putStringSet("url_imagenes_favoritos", new HashSet<>(lista_url_imagenes));
        editor.apply();
    }

}
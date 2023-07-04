package des.app.dogeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import java.util.HashSet;
import java.util.Set;

public class AgrandarImagen extends AppCompatActivity {

    PhotoView img_perro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agrandar_imagen);
        img_perro = findViewById(R.id.img_perro);
        cargar_imagen();

        img_perro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_perro.setScaleType(PhotoView.ScaleType.FIT_CENTER);
            }
        });
    }

    public void cargar_imagen(){
        Intent intent = getIntent();
        String imagen = intent.getStringExtra("url_imagen");

        Picasso.get().load(imagen).into(img_perro);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
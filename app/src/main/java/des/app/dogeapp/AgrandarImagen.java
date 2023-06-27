package des.app.dogeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.PhotoView;

public class AgrandarImagen extends AppCompatActivity {

    PhotoView img_perro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agrandar_imagen);
        img_perro = findViewById(R.id.img_perro);

        img_perro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_perro.setScaleType(PhotoView.ScaleType.FIT_CENTER);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
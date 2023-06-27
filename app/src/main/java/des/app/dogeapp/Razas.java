package des.app.dogeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Razas extends AppCompatActivity {

    ImageView img_perro,img_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_razas);
        img_perro = findViewById(R.id.img_perro_ra);
        img_btn = findViewById(R.id.btn_menu);

        img_perro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Razas.this, AgrandarImagen.class);
                startActivity(i);
            }
        });

        img_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Razas.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}
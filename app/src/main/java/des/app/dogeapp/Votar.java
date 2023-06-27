package des.app.dogeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.PhotoView;

public class Votar extends AppCompatActivity {

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
}
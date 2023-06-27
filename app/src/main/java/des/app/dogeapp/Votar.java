package des.app.dogeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Votar extends AppCompatActivity {

    ImageButton imgmenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        imgmenu=findViewById(R.id.btn_menu);


        imgmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Votar.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
package des.app.dogeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;

public class Config extends AppCompatActivity {
Switch swtichtheme;
ImageButton imgmenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        setTheme(R.style.AppTheme_Light);
        imgmenu=findViewById(R.id.btn_menu);
        swtichtheme = findViewById(R.id.switch_custom);
        swtichtheme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int mode = isChecked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO;
                AppCompatDelegate.setDefaultNightMode(mode); // Reinicia la actividad para aplicar el nuevo tema
            }
        });
        imgmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Config.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
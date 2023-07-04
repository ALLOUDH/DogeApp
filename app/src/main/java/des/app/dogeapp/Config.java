package des.app.dogeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Locale;

public class Config extends AppCompatActivity {
    Switch swtichtheme, switchlanguage;
    ImageView imgmenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        setTheme(R.style.AppTheme_Light);
        imgmenu = findViewById(R.id.btn_menu);
        swtichtheme = findViewById(R.id.switch_custom);
        switchlanguage = findViewById(R.id.switch_language);
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
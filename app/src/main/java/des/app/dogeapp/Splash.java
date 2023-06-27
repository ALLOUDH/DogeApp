package des.app.dogeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;

public class Splash extends AppCompatActivity implements View.OnClickListener  {
    ImageView logo;

    Animator animador;

    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        logo = findViewById(R.id.img_splash);
        Animation animacion;
        animacion = android.view.animation.AnimationUtils.loadAnimation(this, R.anim.aparecerlogo);
        logo.startAnimation(animacion);
        rotacion(logo,2500);
        logo.setOnClickListener(this);
        esperarYcerrar(3000);
    }

    private void esperarYcerrar(int tiempo) {
        handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run() {
                llamar();
            }
        },tiempo);
    }
    public void llamar(){
        Intent intent = new Intent(Splash.this, Login.class);
        startActivity(intent);
    }

    private void rotacion(View view, int dur) {
        animador = ObjectAnimator.ofFloat(view, "rotation", 0, 360);
        animador.setDuration(dur);
        //la animacion se ejecuta al mismo tiempo
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animador);
        animatorSet.start();
    }

    @Override
    public void onClick(View view) {
        rotacion(logo,3000);
    }
}
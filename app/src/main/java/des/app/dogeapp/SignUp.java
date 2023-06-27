package des.app.dogeapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUp extends AppCompatActivity {

    EditText edtNombre, edtApellidoPaterno, edtApellidoMaterno, edtCorreo, edtPass;
    Button btnRegistrar, btnAtras;
    FirebaseFirestore mFirestore;
    DatabaseReference Doge;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        edtNombre = findViewById(R.id.edt_Nombre);
        edtApellidoPaterno = findViewById(R.id.edt_appaterno_Login);
        edtApellidoMaterno = findViewById(R.id.edt_apmaterno_Login);
        edtCorreo = findViewById(R.id.edt_correo_Signup);
        edtPass = findViewById(R.id.edt_contrasena_Login);
        btnRegistrar = findViewById(R.id.btnRegistrarUsuario);
        btnAtras = findViewById(R.id.btnAtras);
        Doge = FirebaseDatabase.getInstance().getReference();

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this, Login.class));
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario();
            }
        });
    }

    private void registrarUsuario() {
        final String nombre = edtNombre.getText().toString().trim();
        final String apellidoPa = edtApellidoPaterno.getText().toString().trim();
        final String apellidoMa = edtApellidoMaterno.getText().toString().trim();
        String correo = edtCorreo.getText().toString().trim();
        String pass = edtPass.getText().toString().trim();
        if (TextUtils.isEmpty(nombre)) {
            Toast.makeText(this, "Por favor, ingresa tu nombre", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(apellidoPa)) {
            Toast.makeText(this, "Por favor, ingresa su apellido paterno", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(apellidoMa)) {
            Toast.makeText(this, "Por favor, ingresa su apellido materno", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(correo)) {
            Toast.makeText(this, "Por favor, ingresa tu correo electrónico", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "Por favor, ingresa tu contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(correo, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            guardarDatos(user.getUid(), nombre,apellidoPa,apellidoMa,correo);
                            Intent intent = new Intent(SignUp.this, Login.class);
                            startActivity(intent);

                            Toast.makeText(SignUp.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SignUp.this, "Error en el registro. Por favor, intenta de nuevo", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    private void guardarDatos(String uid, String nombre, String apellidoPaterno, String apellidoMaterno,String correo) {
        DatabaseReference userRef = Doge.child("usuarios").child(uid);
        userRef.child("nombre").setValue(nombre);
        userRef.child("apellidoPaterno").setValue(apellidoPaterno);
        userRef.child("apellidoMaterno").setValue(apellidoMaterno);
        userRef.child("Correo").setValue(correo);

    }
}
package br.com.exemplo.appclientevip.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.exemplo.appclientevip.R;

public class RecuperarSenhaActivity extends AppCompatActivity {
    EditText editEmail;
    Button btnRecuperar, btnVoltar;
    boolean isFormularioOK;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_senha);

        initFormulario();

        btnRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFormularioOK = validarFormulario()) {
                    Toast.makeText(getApplicationContext(), "Sua senha foi enviada para o " +
                            "email informado...", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RecuperarSenhaActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecuperarSenhaActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initFormulario() {
        editEmail = findViewById(R.id.editEmail);
        btnRecuperar = findViewById(R.id.btnVoltar);
        btnRecuperar = findViewById(R.id.btnVoltar);

    }

    private boolean validarFormulario() {
        boolean retorno = true;

        if(TextUtils.isEmpty(editEmail.getText().toString())) {
            editEmail.setError("Insira o seu email!");
            editEmail.requestFocus();
            retorno = false;
        }

        return retorno;
    }

}
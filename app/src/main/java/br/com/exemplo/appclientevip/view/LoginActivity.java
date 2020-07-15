package br.com.exemplo.appclientevip.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.FancyAlertDialogListener;
import com.shashank.sony.fancydialoglib.Icon;

import br.com.exemplo.appclientevip.Controller.ClienteController;
import br.com.exemplo.appclientevip.R;
import br.com.exemplo.appclientevip.api.AppUtil;
import br.com.exemplo.appclientevip.model.Cliente;

public class LoginActivity extends AppCompatActivity {

    Cliente cliente;
    private SharedPreferences preferences;
    TextView txtRecuperarSenha, txtLerPolitica;
    EditText editEmail, editSenha;
    CheckBox chlembrar;
    Button btnAcessar, btnSejaVip;
    boolean isFormularioOK, isLembrarSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initFormulario();

        //Permite o acesso ao sistema, caso as credenciasi de acesso estejam corretas
        btnAcessar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFormularioOK = validarFormulario()) {
                    salvarSharedPreferences();

                    if (validarDadosDoCliente()) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        return;
                    } else {
                        Toast.makeText(getApplicationContext(), "Email ou senha incorretos.",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        btnSejaVip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ClienteVip.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        txtRecuperarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Carregando tela de " +
                        "recuperação de senha...", Toast.LENGTH_LONG).show();
            }
        });

        txtLerPolitica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FancyAlertDialog.Builder(LoginActivity.this)
                        .setTitle("Política de Privacidade & Termos de Uso")
                        .setBackgroundColor(Color.parseColor("#303F9F"))  //Don't pass R.color.colorvalue
                        .setMessage("texto texto texto texto texto texto texto texto texto texto texto texto texto texto texto texto texto texto texto texto texto texto texto texto texto texto texto texto texto texto")
                        .setNegativeBtnText("Discordo")
                        .setPositiveBtnBackground(Color.parseColor("#FF4081"))  //Don't pass R.color.colorvalue
                        .setPositiveBtnText("Concordo")
                        .setNegativeBtnBackground(Color.parseColor("#FFA9A7A8"))  //Don't pass R.color.colorvalue
                        .setAnimation(Animation.POP)
                        .isCancellable(true)
                        .setIcon(R.mipmap.ic_launcher_round, Icon.Visible)
                        .OnPositiveClicked(new FancyAlertDialogListener() {
                            @Override
                            public void OnClick() {
                                Toast.makeText(getApplicationContext(),"Obrigado, seja bem vindo " +
                                        "e conclua seu cadastro para usar o aplicativo.",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .OnNegativeClicked(new FancyAlertDialogListener() {
                            @Override
                            public void OnClick() {
                                Toast.makeText(getApplicationContext(),"Lamento, mas é necessário " +
                                        "concordar com os termos de uso para utilizar este aplicativo",
                                        Toast.LENGTH_LONG).show();
                                finish();
                                return;
                            }
                        })
                        .build();
            }
        });
    }

    private boolean validarFormulario() {
        boolean retorno = true;

        if(TextUtils.isEmpty(editEmail.getText().toString())) {
            editEmail.setError("Insira o email!");
            editEmail.requestFocus();
            retorno = false;
        }

        if(TextUtils.isEmpty(editSenha.getText().toString())) {
            editSenha.setError("Insira o email!");
            editSenha.requestFocus();
            retorno = false;
        }

        return retorno;
    }

    private void initFormulario() {
        txtRecuperarSenha = findViewById(R.id.txtRecuperarSenha);
        txtLerPolitica = findViewById(R.id.txtLerPolitica);
        editSenha = findViewById(R.id.editSenha);
        editEmail = findViewById(R.id.editEmail);
        chlembrar = findViewById(R.id.chLembrar);
        btnAcessar = findViewById(R.id.btnAcessar);
        btnSejaVip = findViewById(R.id.btnSejaVip);

        isFormularioOK = false;
        cliente = new Cliente();

        restaurarSharedPreferences();
    }

    //Se estiver checado, irá lembrar a senha do usuario
    public void lembrarSenha(View view) {
        isLembrarSenha = chlembrar.isChecked();
    }

    //Cria função para validar nome e senha do usuario na pasta Controller
    public boolean validarDadosDoCliente() {
        return ClienteController.validarDadosDoCliente(cliente,
                editEmail.getText().toString(),
                editSenha.getText().toString());
    }

    private void salvarSharedPreferences() {
        preferences = getSharedPreferences(AppUtil.PREF_APP, MODE_PRIVATE);
        SharedPreferences.Editor dados = preferences.edit();

        dados.putBoolean("loginAutomatico", isLembrarSenha);
        dados.putString("emailCliente", editEmail.getText().toString());
        dados.apply();
    }

    private void restaurarSharedPreferences() {
        preferences = getSharedPreferences(AppUtil.PREF_APP, MODE_PRIVATE);

        cliente.setEmail(preferences.getString("email", "teste@teste.com"));
        cliente.setSenha(preferences.getString("senha", "12345"));
        cliente.setPrimeiroNome(preferences.getString("primeiroNome", "Cliente"));
        cliente.setSobreNome(preferences.getString("sobreNome", "Fake"));
        cliente.setPessoaFisica(preferences.getBoolean("pessoaFisica", true));

        isLembrarSenha = preferences.getBoolean("loginAutomatico", false);
    }
}
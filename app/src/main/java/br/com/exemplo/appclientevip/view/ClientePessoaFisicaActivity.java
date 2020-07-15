package br.com.exemplo.appclientevip.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.FancyAlertDialogListener;
import com.shashank.sony.fancydialoglib.Icon;

import br.com.exemplo.appclientevip.R;
import br.com.exemplo.appclientevip.api.AppUtil;
import br.com.exemplo.appclientevip.model.Cliente;
import br.com.exemplo.appclientevip.model.ClientePF;

public class ClientePessoaFisicaActivity extends AppCompatActivity {
    Cliente novoVip;
    ClientePF novaClientePF;
    private SharedPreferences preferences;

    EditText editCPF, editNomeCompleto;
    Button btnSalvarContinuar, btnVoltar, btnCancelar;
    boolean isFormularioOK, isPessoaFisica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_pessoa_fisica);
        initFormulario();

        btnSalvarContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFormularioOK = validarFormulario()) {
                    novaClientePF.setCpf(editCPF.getText().toString());
                    novaClientePF.setNomeCompleto(editNomeCompleto.getText().toString());
                    salvarSharedPreferences();

                    Intent intent;
                    if (isPessoaFisica) {
                        intent = new Intent(ClientePessoaFisicaActivity.this, CredencialDeAcessoActivity.class);
                    } else {
                        intent = new Intent(ClientePessoaFisicaActivity.this, ClientePessoaJuridicaActivity.class);
                    }
                    startActivity(intent);
                }
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClientePessoaFisicaActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FancyAlertDialog.Builder(ClientePessoaFisicaActivity.this)
                        .setTitle("Confirme o Cancelamento")
                        .setBackgroundColor(Color.parseColor("#303F9F"))  //Don't pass R.color.colorvalue
                        .setMessage("Deseja realmente Cancelar?")
                        .setNegativeBtnText("NÃO")
                        .setPositiveBtnBackground(Color.parseColor("#FF4081"))  //Don't pass R.color.colorvalue
                        .setPositiveBtnText("SIM")
                        .setNegativeBtnBackground(Color.parseColor("#FFA9A7A8"))  //Don't pass R.color.colorvalue
                        .setAnimation(Animation.POP)
                        .isCancellable(true)
                        .setIcon(R.mipmap.ic_launcher_round, Icon.Visible)
                        .OnPositiveClicked(new FancyAlertDialogListener() {
                            @Override
                            public void OnClick() {
                                Toast.makeText(getApplicationContext(), "Cancelado com sucesso...",
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .OnNegativeClicked(new FancyAlertDialogListener() {
                            @Override
                            public void OnClick() {
                                Toast.makeText(getApplicationContext(), "Continue seu cadastro...",
                                        Toast.LENGTH_LONG).show();
                            }
                        })
                        .build();
            }
        });
    }

    private void initFormulario() {
        editCPF = findViewById(R.id.editCPF);
        editNomeCompleto = findViewById(R.id.editNomeCompleto);
        btnSalvarContinuar = findViewById(R.id.btnSalvarContinuar);
        btnVoltar = findViewById(R.id.btnVoltar);
        btnCancelar = findViewById(R.id.btnCancelar);

        novaClientePF = new ClientePF();
        novoVip = new Cliente();

        restaurarSharedPreferences();
    }

    private void salvarSharedPreferences() {
        preferences = getSharedPreferences(AppUtil.PREF_APP, MODE_PRIVATE);
        SharedPreferences.Editor dados = preferences.edit();

        dados.putString("cpf", editCPF.getText().toString());
        dados.putString("nomeCompleto", editNomeCompleto.getText().toString());
        dados.apply();
    }

    private void restaurarSharedPreferences() {
        preferences = getSharedPreferences(AppUtil.PREF_APP, MODE_PRIVATE);
        isPessoaFisica = preferences.getBoolean("pessoaFisica", true);
    }

    private boolean validarFormulario() {
        boolean retorno = true;

        if (TextUtils.isEmpty(editCPF.getText().toString())) {
            editCPF.setError("Insira o seu CPF!");
            editCPF.requestFocus();
            retorno = false;
        }

        if (TextUtils.isEmpty(editNomeCompleto.getText().toString())) {
            editNomeCompleto.setError("Insira o seu nome completo!");
            editNomeCompleto.requestFocus();
            retorno = false;
        }

        return retorno;
    }
}
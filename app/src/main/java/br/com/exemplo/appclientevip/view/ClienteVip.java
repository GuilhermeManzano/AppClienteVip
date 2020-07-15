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
import android.widget.Toast;

import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.FancyAlertDialogListener;
import com.shashank.sony.fancydialoglib.Icon;

import br.com.exemplo.appclientevip.R;
import br.com.exemplo.appclientevip.api.AppUtil;
import br.com.exemplo.appclientevip.model.Cliente;

public class ClienteVip extends AppCompatActivity {
    //Declarar Objetos
    Cliente novoVip;
    private SharedPreferences preferences;

    //1° passo a partir do Layout
    EditText editPrimeiroNome, editSobreNome;
    CheckBox ckPessoaFisica;
    Button btnSalvarContinuar, btnCancelar;
    boolean isFormularioOK, isPessoaFisica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_vip);
        
        initFormulario();

        btnSalvarContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFormularioOK = validarFormulario()) {
                    novoVip.setPrimeiroNome(editPrimeiroNome.getText().toString());
                    novoVip.setSobreNome(editSobreNome.getText().toString());
                    novoVip.setPessoaFisica(isPessoaFisica);
                    salvarSharedPreferences();
                    Intent intent = new Intent(ClienteVip.this, ClientePessoaFisicaActivity.class);
                    startActivity(intent);
                }
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FancyAlertDialog.Builder(ClienteVip.this)
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
                                Toast.makeText(getApplicationContext(),"Cancelado com sucesso...",
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .OnNegativeClicked(new FancyAlertDialogListener() {
                            @Override
                            public void OnClick() {
                                Toast.makeText(getApplicationContext(),"Continue seu cadastro...",
                                        Toast.LENGTH_LONG).show();
                            }
                        })
                        .build();
            }
        });
    }

    private void initFormulario() {
        editPrimeiroNome = findViewById(R.id.editPrimeiroNome);
        editSobreNome = findViewById(R.id.editSobrenome);
        ckPessoaFisica = findViewById(R.id.ckPessoaFisica);
        btnSalvarContinuar = findViewById(R.id.btnSalvarContinuar);
        btnCancelar = findViewById(R.id.btnCancelar);
        isFormularioOK = false;
        
        novoVip = new Cliente();
        restaurarSharedPreferences();
    }

    private boolean validarFormulario() {
        boolean retorno = true;

        if(TextUtils.isEmpty(editPrimeiroNome.getText().toString())) {
            editPrimeiroNome.setError("Insira o seu nome!");
            editPrimeiroNome.requestFocus();
            retorno = false;
        }

        if(TextUtils.isEmpty(editSobreNome.getText().toString())) {
            editSobreNome.setError("Insira o seu sobrenome!");
            editSobreNome.requestFocus();
            retorno = false;
        }

        return retorno;
    }

    public void pessoaFisica(View view) {
        isPessoaFisica = ckPessoaFisica.isChecked();
    }

    private void salvarSharedPreferences() {
        preferences = getSharedPreferences(AppUtil.PREF_APP, MODE_PRIVATE);
        SharedPreferences.Editor dados = preferences.edit();

        dados.putString("primeiroNome", editPrimeiroNome.getText().toString());
        dados.putString("sobreNome", editSobreNome.getText().toString());
        dados.putBoolean("pessoaFisica", isPessoaFisica);
        dados.apply();
    }

    private void restaurarSharedPreferences() {
        preferences = getSharedPreferences(AppUtil.PREF_APP, MODE_PRIVATE);

    }
}
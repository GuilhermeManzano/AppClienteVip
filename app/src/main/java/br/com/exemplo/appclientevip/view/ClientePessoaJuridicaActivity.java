package br.com.exemplo.appclientevip.view;

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

import androidx.appcompat.app.AppCompatActivity;

import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.FancyAlertDialogListener;
import com.shashank.sony.fancydialoglib.Icon;

import br.com.exemplo.appclientevip.R;
import br.com.exemplo.appclientevip.api.AppUtil;
import br.com.exemplo.appclientevip.model.Cliente;
import br.com.exemplo.appclientevip.model.ClientePJ;

public class ClientePessoaJuridicaActivity extends AppCompatActivity {
    Cliente novoVIP;
    ClientePJ novoClientePJ;
    private SharedPreferences preferences;

    EditText editCNPJ, editRazaoSocial, editDataAberturaPJ;
    CheckBox chSimplesNacional,chMEI;
    Button btnSalvarContinuar, btnVoltar, btnCancelar;
    boolean isFormularioOK, isSimplesNacional, isMEI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_pessoa_juridica);

        initFormulario();

        btnSalvarContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFormularioOK = validarFormulario()) {
                    novoClientePJ.setCnpj(editCNPJ.getText().toString());
                    novoClientePJ.setRazaoSocial(editRazaoSocial.getText().toString());
                    novoClientePJ.setDataAbertura(editDataAberturaPJ.getText().toString());
                    novoClientePJ.setSimplesNacional(isSimplesNacional);
                    novoClientePJ.setMei(isMEI);
                    salvarSharedPreferences();

                    Intent intent = new Intent(ClientePessoaJuridicaActivity.this,
                            CredencialDeAcessoActivity.class);
                    startActivity(intent);
                }
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClientePessoaJuridicaActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FancyAlertDialog.Builder(ClientePessoaJuridicaActivity.this)
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

    public void simplesNacional(View view) {
        isSimplesNacional = chSimplesNacional.isChecked();
    }

    public void mei(View view) {
        isMEI = chMEI.isChecked();
    }

    private void initFormulario() {
        editCNPJ = findViewById(R.id.editCNPJ);
        editRazaoSocial = findViewById(R.id.editRazaoSocial);
        editDataAberturaPJ = findViewById(R.id.editDataAberturaPJ);
        chSimplesNacional = findViewById(R.id.chSimplesNacional);
        chMEI = findViewById(R.id.chMEI);
        btnSalvarContinuar = findViewById(R.id.btnSalvarContinuar);
        btnVoltar = findViewById(R.id.btnVoltar);
        btnCancelar = findViewById(R.id.btnCancelar);

        novoClientePJ = new ClientePJ();
        novoVIP = new Cliente();

        restaurarSharedPreferences();
    }

    private boolean validarFormulario() {
        boolean retorno = true;

        if(TextUtils.isEmpty(editDataAberturaPJ.getText().toString())) {
            editDataAberturaPJ.setError("Insira a data de abertura!");
            editDataAberturaPJ.requestFocus();
            retorno = false;
        }

        if(TextUtils.isEmpty(editCNPJ.getText().toString())) {
            editCNPJ.setError("Insira o CNPJ!");
            editCNPJ.requestFocus();
            retorno = false;
        }

        if(TextUtils.isEmpty(editRazaoSocial.getText().toString())) {
            editRazaoSocial.setError("Insira a razao social");
            editRazaoSocial.requestFocus();
            retorno = false;
        }

        return retorno;
    }

    private void salvarSharedPreferences() {
        preferences = getSharedPreferences(AppUtil.PREF_APP, MODE_PRIVATE);
        SharedPreferences.Editor dados = preferences.edit();

        dados.putString("cnpj", editCNPJ.getText().toString());
        dados.putString("dataAbertura", editDataAberturaPJ.getText().toString());
        dados.putString("razaoSocial", editRazaoSocial.getText().toString());
        dados.putBoolean("simplesNacional", isSimplesNacional);
        dados.putBoolean("mei", isMEI);
        dados.apply();
    }

    private void restaurarSharedPreferences() {
        preferences = getSharedPreferences(AppUtil.PREF_APP, MODE_PRIVATE);
    }
}
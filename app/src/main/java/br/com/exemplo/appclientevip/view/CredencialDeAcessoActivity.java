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

public class CredencialDeAcessoActivity extends AppCompatActivity {
    Button btnCadastro;
    EditText editNome, editEmail, editSenhaA, editSenhaB;
    CheckBox ckTermo;
    boolean isFormularioOK, isPessoaFisica;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        initFormulario();

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Nesta logica, se algum campo deixar de ser preenchido, nao deixara clicar no botao e
                //ir para outra tela, apenas se todos os dados forem preenchidos e os termos checked
                isFormularioOK = true;

                if(TextUtils.isEmpty(editNome.getText().toString())){
                    editNome.setError("O nome é obrigatório!");
                    editNome.requestFocus();
                    isFormularioOK = false;
                }

                if(TextUtils.isEmpty(editEmail.getText().toString())){
                    editEmail.setError("O email é obrigatório!");
                    editEmail.requestFocus();
                    isFormularioOK = false;
                }

                if(TextUtils.isEmpty(editSenhaA.getText().toString())){
                    editSenhaA.setError("A senha é obrigatória!");
                    editSenhaA.requestFocus();
                    isFormularioOK = false;
                }

                if(TextUtils.isEmpty(editSenhaB.getText().toString())){
                    editSenhaB.setError("A confirmação da senha é obrigatória!");
                    editSenhaB.requestFocus();
                    isFormularioOK = false;
                }

                //Se os termos de uso nao tiver marcado, desativa botao de finalizar
                if(!ckTermo.isChecked()){
                   isFormularioOK = false;
                }

                if(isFormularioOK) {
                    if(!validarSenha()){
                        editSenhaA.setError("As senhas não conferem!");
                        editSenhaB.setError("As senhas não conferem!");
                        editSenhaA.requestFocus();

                        new FancyAlertDialog.Builder(CredencialDeAcessoActivity.this)
                                .setTitle("ATENÇÃO!")
                                .setBackgroundColor(Color.parseColor("#303F9F"))  //Don't pass R.color.colorvalue
                                .setMessage("As senhas digitadas não são iguais. Por favor, tente novamente.")
                                .setPositiveBtnBackground(Color.parseColor("#FF4081"))  //Don't pass R.color.colorvalue
                                .setPositiveBtnText("CONTINUAR")
                                .setAnimation(Animation.POP)
                                .isCancellable(true)
                                .setIcon(R.mipmap.ic_launcher_round, Icon.Visible)
                                .OnPositiveClicked(new FancyAlertDialogListener() {
                                    @Override
                                    public void OnClick() { }
                                })
                                .build();
                    }else {
                        salvarSharedPreferences();

                        Intent iMenuPrincipal = new Intent(CredencialDeAcessoActivity.this,
                                LoginActivity.class);
                        startActivity(iMenuPrincipal);
                        finish();
                        return;
                    }
                }
            }
        });
    }

    //Inicialização das variáveis
    private void initFormulario() {
        btnCadastro = findViewById(R.id.btnCadastro);
        editNome = findViewById(R.id.editNome);
        editEmail = findViewById(R.id.editEmail);
        editSenhaA = findViewById(R.id.editSenhaA);
        editSenhaB = findViewById(R.id.editSenhaB);
        ckTermo = findViewById(R.id.ckTermo);

        isFormularioOK = false;

        restaurarSharedPreferences();
    }

    public void validarTermo(View view) {
        if(!ckTermo.isChecked()){
            Toast.makeText(getApplicationContext(), "É necessário aceitar os termos de uso para continuar o cadastro...",
                    Toast.LENGTH_LONG).show();
        }
    }

    public boolean validarSenha() {
        boolean retorno = false;
        String senhaA, senhaB;

        senhaA = editSenhaA.getText().toString();
        senhaB = editSenhaB.getText().toString();
        retorno = (senhaA.equals(senhaB));

        return retorno;
    }

    private void restaurarSharedPreferences() {
        preferences = getSharedPreferences(AppUtil.PREF_APP, MODE_PRIVATE);
        isPessoaFisica = preferences.getBoolean("pessoaFisica", true);
        if (isPessoaFisica) {
            editNome.setText(preferences.getString("nomeCompleto", "Verifique os dados"));
        } else
            editNome.setText(preferences.getString("razaoSocial", "Verifique os dados"));
    }

    private void salvarSharedPreferences() {
        preferences = getSharedPreferences(AppUtil.PREF_APP, MODE_PRIVATE);
        SharedPreferences.Editor dados = preferences.edit();

        dados.putString("email", editEmail.getText().toString());
        dados.putString("senha", editSenhaA.getText().toString());
        dados.apply();
    }
}

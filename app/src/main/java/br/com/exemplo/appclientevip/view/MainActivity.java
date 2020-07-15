package br.com.exemplo.appclientevip.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.FancyAlertDialogListener;
import com.shashank.sony.fancydialoglib.Icon;

import java.util.ArrayList;
import java.util.List;

import br.com.exemplo.appclientevip.R;
import br.com.exemplo.appclientevip.api.AppUtil;
import br.com.exemplo.appclientevip.model.Cliente;
import br.com.exemplo.appclientevip.model.ClientePF;
import br.com.exemplo.appclientevip.model.ClientePJ;

public class MainActivity extends AppCompatActivity {
    Cliente cliente;
    ClientePF clientePF;
    ClientePJ clientePJ;
    TextView txtNomeCliente;

    private SharedPreferences preferences;
    List<Cliente> clientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFormulario();
        
        buscarListaDeClientes();
    }

    private void buscarListaDeClientes() {
        clientes = new ArrayList<>();

        //Gerando lista com 10 clientes
        for(int i = 0; i < 10; i++) {
            clientes.add(cliente);
            cliente = new Cliente();
            cliente.setPrimeiroNome("Cliente n° " + i);
        }

        for(Cliente obj: clientes) {
            Log.i(AppUtil.LOG_APP, "Obj: " + obj.getPrimeiroNome());
        }
    }

    private void initFormulario() {
        cliente = new Cliente();
        clientePF = new ClientePF();
        clientePJ = new ClientePJ();

        txtNomeCliente = findViewById(R.id.txtNomeCliente);

        restaurarSharedPreferences();
        txtNomeCliente.setText("Bem vindo, " + cliente.getPrimeiroNome());
    }

    private void salvarSharedPreferences() {
        preferences = getSharedPreferences(AppUtil.PREF_APP, MODE_PRIVATE);
        SharedPreferences.Editor dados = preferences.edit();
    }

    private void restaurarSharedPreferences() {
        preferences = getSharedPreferences(AppUtil.PREF_APP, MODE_PRIVATE);

        cliente.setPrimeiroNome(preferences.getString("primeiroNome", "NULO"));
        cliente.setSobreNome(preferences.getString("sobreNome", "NULO"));
        cliente.setEmail(preferences.getString("email", "NULO"));
        cliente.setSenha(preferences.getString("senha", "NULO"));
        cliente.setPessoaFisica(preferences.getBoolean("pessoaFisica", true));

        clientePF.setCpf(preferences.getString("cpf", "NULO"));
        clientePF.setNomeCompleto(preferences.getString("nomeCompleto", "NULO"));

        clientePJ.setCnpj(preferences.getString("cnpj", "NULO"));
        clientePJ.setRazaoSocial(preferences.getString("razaoSocial", "NULO"));
        clientePJ.setSimplesNacional(preferences.getBoolean("simplesNacional", false));
        clientePJ.setMei(preferences.getBoolean("mei", false));
        clientePJ.setDataAbertura(preferences.getString("dataAbertura", "NULO"));
    }

    public void meusDados(View view) {
        Log.i(AppUtil.LOG_APP, "*** DADOS CLIENTE ***");
        Log.i(AppUtil.LOG_APP, "ID: "+ cliente.getId());
        Log.i(AppUtil.LOG_APP, "Primeiro Nome: "+ cliente.getPrimeiroNome());
        Log.i(AppUtil.LOG_APP, "Sobrenome: "+ cliente.getSobreNome());
        Log.i(AppUtil.LOG_APP, "Email: "+ cliente.getEmail());
        Log.i(AppUtil.LOG_APP, "Senha: "+ cliente.getSenha());
        Log.i(AppUtil.LOG_APP, "*** DADOS CLIENTE PESSOA FISICA***");
        Log.i(AppUtil.LOG_APP, "Nome Completo: "+ clientePF.getNomeCompleto());
        Log.i(AppUtil.LOG_APP, "CPF: "+ clientePF.getCpf());

        if(!cliente.isPessoaFisica()){
            Log.i(AppUtil.LOG_APP, "*** DADOS CLIENTE PESSOA JURIDICA***");
            Log.i(AppUtil.LOG_APP, "CNPJ: "+ clientePJ.getCnpj());
            Log.i(AppUtil.LOG_APP, "Data Abertura: "+ clientePJ.getDataAbertura());
            Log.i(AppUtil.LOG_APP, "Razão Social: "+ clientePJ.getRazaoSocial());
            Log.i(AppUtil.LOG_APP, "É MEI?: "+ clientePJ.isMei());
            Log.i(AppUtil.LOG_APP, "É simples nacional?: "+ clientePJ.isSimplesNacional());
        }
    }

    public void atualizarMeusDados(View view) {
        if(cliente.isPessoaFisica()){
            cliente.setPrimeiroNome("Marco A");
            cliente.setSobreNome("Oliveira");
            clientePF.setNomeCompleto("Marco A Oliveira");

            //salvarSharedPreferences();

            Log.i(AppUtil.LOG_APP, "*** ALTERANDO DADOS CLIENTE PESSOA FISICA***");
            Log.i(AppUtil.LOG_APP, "Primeiro Nome: "+ cliente.getPrimeiroNome());
            Log.i(AppUtil.LOG_APP, "Sobrenome: "+ cliente.getSobreNome());
            Log.i(AppUtil.LOG_APP, "Nome Completo: "+ clientePF.getNomeCompleto());
        } else {
            clientePJ.setRazaoSocial("MADDO ME");
            Log.i(AppUtil.LOG_APP, "*** ALTERANDO DADOS CLIENTE PESSOA JURIDICA***");
            Log.i(AppUtil.LOG_APP, "Razao Social: "+ clientePJ.getRazaoSocial());
        }
    }

    /**
     * Para excluir a conta, basta declarar os objetos clientes novamente(que virão zerados) e, depois,
     * salvar os dados em branco. Isso sobrescreverá os dados antigos do usuário.
     * @param view
     */
    public void excluirMinhaConta(View view) {
        new FancyAlertDialog.Builder(MainActivity.this)
                .setTitle("EXCLUIR CONTA")
                .setBackgroundColor(Color.parseColor("#303F9F"))  //Don't pass R.color.colorvalue
                .setMessage("Deseja realmente excluir sua conta?")
                .setNegativeBtnText("CANCELAR")
                .setPositiveBtnBackground(Color.parseColor("#FF4081"))  //Don't pass R.color.colorvalue
                .setPositiveBtnText("SIM")
                .setNegativeBtnBackground(Color.parseColor("#FFA9A7A8"))  //Don't pass R.color.colorvalue
                .setAnimation(Animation.POP)
                .isCancellable(true)
                .setIcon(R.mipmap.ic_launcher_round, Icon.Visible)
                .OnPositiveClicked(new FancyAlertDialogListener() {
                    @Override
                    public void OnClick() {
                        Toast.makeText(getApplicationContext(), cliente.getPrimeiroNome() +
                                ", conta excluída com sucesso!", Toast.LENGTH_SHORT).show();

                        cliente = new Cliente();
                        clientePF = new ClientePF();
                        clientePJ = new ClientePJ();
                        //salvarSharedPreferences();

                        finish();
                        return;
                    }
                })
                .OnNegativeClicked(new FancyAlertDialogListener() {
                    @Override
                    public void OnClick() {
                        Toast.makeText(getApplicationContext(), cliente.getPrimeiroNome() +
                                ", acão cancelada...", Toast.LENGTH_SHORT).show();
                    }
                })
                .build();
    }

    public void consultarClientesVip(View view) {
        Intent intent = new Intent(MainActivity.this, ConsultarClientesActivity.class);
        startActivity(intent);
        finish();
    }

    public void sairDoAplicativo(View view) {
        new FancyAlertDialog.Builder(MainActivity.this)
                .setTitle("SAIR DO APLICATIVO")
                .setBackgroundColor(Color.parseColor("#303F9F"))  //Don't pass R.color.colorvalue
                .setMessage("Confirma sua saída do aplicativo?")
                .setNegativeBtnText("RETORNAR")
                .setPositiveBtnBackground(Color.parseColor("#FF4081"))  //Don't pass R.color.colorvalue
                .setPositiveBtnText("SIM")
                .setNegativeBtnBackground(Color.parseColor("#FFA9A7A8"))  //Don't pass R.color.colorvalue
                .setAnimation(Animation.POP)
                .isCancellable(true)
                .setIcon(R.mipmap.ic_launcher_round, Icon.Visible)
                .OnPositiveClicked(new FancyAlertDialogListener() {
                    @Override
                    public void OnClick() {
                        Toast.makeText(getApplicationContext(), cliente.getPrimeiroNome() +
                                        ", volte sempre e obrigado!", Toast.LENGTH_SHORT).show();
                        finish();
                        return;
                    }
                })
                .OnNegativeClicked(new FancyAlertDialogListener() {
                    @Override
                    public void OnClick() {
                        Toast.makeText(getApplicationContext(), cliente.getPrimeiroNome() +
                                ", divirta-se com as opções do aplicativo...", Toast.LENGTH_SHORT).show();
                    }
                })
                .build();
    }
}

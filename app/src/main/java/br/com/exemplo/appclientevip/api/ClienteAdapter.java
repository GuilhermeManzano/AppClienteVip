package br.com.exemplo.appclientevip.api;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.exemplo.appclientevip.R;
import br.com.exemplo.appclientevip.model.Cliente;

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ViewHolder>  {

    private List<Cliente> aClientes;
    private Context aContext;

    public ClienteAdapter(List<Cliente> aClientes, Context aContext) {
        this.aClientes = aClientes;
        this.aContext = aContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View linhaView = inflater.inflate(R.layout.activity_linha_detalhe, parent, false);
        ViewHolder viewHolder = new ViewHolder(linhaView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ClienteAdapter.ViewHolder holder, int position) {
        Cliente objDaLinha = aClientes.get(position);
        TextView txtPrimeiroNome = holder.rvPrimeiroNome;
        txtPrimeiroNome.setText(objDaLinha.getPrimeiroNome());

        Button btnPessoaFisica = holder.rvPessoaFisica;
        btnPessoaFisica.setText(objDaLinha.isPessoaFisica() ? "CPF" : "CNPJ");
    }

    @Override
    public int getItemCount() {
        return aClientes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView rvPrimeiroNome;
        public Button rvPessoaFisica;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rvPrimeiroNome = itemView.findViewById(R.id.rvPrimeiroNome);
            rvPessoaFisica = itemView.findViewById(R.id.rvPessoaFisica);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Cliente clienteSelecionado = aClientes.get(position);

            if(position != RecyclerView.NO_POSITION) {
                Log.i(AppUtil.LOG_APP, "Cliente ID " + position + " Primeiro nome: "
                        + clienteSelecionado.getPrimeiroNome());
                Toast.makeText(aContext,"Cliente ID " + position + " Primeiro nome: "
                        + clienteSelecionado.getPrimeiroNome(), Toast.LENGTH_LONG).show();

            }
        }
    }
}

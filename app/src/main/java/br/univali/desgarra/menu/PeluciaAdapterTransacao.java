package br.univali.desgarra.menu;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.univali.desgarra.R;
import br.univali.desgarra.database.DatabaseHelper;
import br.univali.desgarra.inicial.Usuario;


public class PeluciaAdapterTransacao extends RecyclerView.Adapter<PeluciaAdapterTransacao.PeluciaViewHolder>{
    private final List<Transacao> transacao;
    private final FragmentActivity activity;

    PeluciaAdapterTransacao(List<Transacao> Transacao, FragmentActivity activity){
        this.transacao   = Transacao;
        this.activity   = activity;
    }
    static class PeluciaViewHolder extends RecyclerView.ViewHolder {
        private final TextView nomeView;
        private final TextView tipoView;
        private final TextView usuarioView;
        PeluciaViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeView     = itemView.findViewById(R.id.tvListPeluciaNome);
            tipoView = itemView.findViewById(R.id.tvListTipoNome);
            usuarioView = itemView.findViewById(R.id.tvListUsuarioNome);
        }
    }

    @NonNull
    @Override
    public PeluciaAdapterTransacao.PeluciaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pelucia_transacao_item, parent, false);
        return new PeluciaAdapterTransacao.PeluciaViewHolder(v);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(PeluciaAdapterTransacao.PeluciaViewHolder viewHolder, int i) {


        DatabaseHelper databaseHelper = new DatabaseHelper(activity);
        Pelucia pelucia = databaseHelper.getByIdPelucia(transacao.get(i).getId_pelucia());
        Usuario recebedor =  databaseHelper.getByIdUsuario(transacao.get(i).getId_usuario_recebedor());

        viewHolder.nomeView.setText(pelucia.getNome());
        viewHolder.tipoView.setText((transacao.get(i).tipo_transacao));
        viewHolder.usuarioView.setText((recebedor.getNome()));
    }

    @Override
    public int getItemCount() {
        return transacao.size();
    }
}

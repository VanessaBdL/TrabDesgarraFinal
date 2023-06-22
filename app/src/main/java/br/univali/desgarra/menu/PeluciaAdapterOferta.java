package br.univali.desgarra.menu;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.univali.desgarra.R;
import br.univali.desgarra.database.DatabaseHelper;
import br.univali.desgarra.inicial.Usuario;


public class PeluciaAdapterOferta extends RecyclerView.Adapter<PeluciaAdapterOferta.PeluciaViewHolder>{
    private final List<Pelucia> pelucias;
    private int id_pelucia;
    private final FragmentActivity activity;

    private Usuario u;

    PeluciaAdapterOferta(List<Pelucia> pelucias, FragmentActivity activity, Usuario u){
        this.pelucias   = pelucias;
        this.activity   = activity;
        this.u          = u;
    }
    static class PeluciaViewHolder extends RecyclerView.ViewHolder {
        private final TextView nomeView;
        private final TextView vendedorView;
        PeluciaViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeView     = itemView.findViewById(R.id.tvListPeluciaNome);
            vendedorView = itemView.findViewById(R.id.tvListVendedorNome);
        }
    }

    @NonNull
    @Override
    public PeluciaAdapterOferta.PeluciaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pelucia_oferta_item, parent, false);
        return new PeluciaAdapterOferta.PeluciaViewHolder(v);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(PeluciaAdapterOferta.PeluciaViewHolder viewHolder, int i) {
        viewHolder.nomeView.setText(pelucias.get(i).getNome());

        DatabaseHelper databaseHelper = new DatabaseHelper(activity);
        Usuario vendedor =  databaseHelper.getByIdUsuario(pelucias.get(i).getId_usuario());

        viewHolder.vendedorView.setText((vendedor.getNome()));

        id_pelucia = pelucias.get(i).getId();

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putInt("id", id_pelucia);
                //Toast.makeText(activity, pelucias.get(i).getNome(),, Toast.LENGTH_LONG).show();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new TransacaoPeluciaFragment(u,pelucias.get(i).getId())).commit();
/*
                br.univali.desgarra.menu.PeluciaAdapter.PeluciaViewHolder.EditarFragment editarFragment = new br.univali.desgarra.menu.EditarFragment();
                FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
                editarFragment.setArguments(b);
                ft.replace(R.id.framePelucia, editarFragment).commit();*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return pelucias.size();
    }
}

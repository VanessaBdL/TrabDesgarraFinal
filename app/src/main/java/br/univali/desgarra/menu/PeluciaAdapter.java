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


public class PeluciaAdapter extends RecyclerView.Adapter<br.univali.desgarra.menu.PeluciaAdapter.PeluciaViewHolder>{
    private final List<Pelucia> pelucias;
    private int id_pelucia;
    private Usuario u;
    private final FragmentActivity activity;

    PeluciaAdapter(List<Pelucia> pelucias, FragmentActivity activity, Usuario u){
        this.pelucias = pelucias;
        this.activity = activity;
        this.u = u;
    }

    static class PeluciaViewHolder extends RecyclerView.ViewHolder {
        private final TextView nomeView;
        PeluciaViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeView = itemView.findViewById(R.id.tvListPeluciaNome);
        }
    }

    @NonNull
    @Override
    public br.univali.desgarra.menu.PeluciaAdapter.PeluciaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pelucia_item, parent, false);
        return new br.univali.desgarra.menu.PeluciaAdapter.PeluciaViewHolder(v);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(br.univali.desgarra.menu.PeluciaAdapter.PeluciaViewHolder viewHolder, int i) {
        viewHolder.nomeView.setText(pelucias.get(i).getNome());

        id_pelucia = pelucias.get(i).getId();

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new EditarPeluciaFragment(u,pelucias.get(i).getId())).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return pelucias.size();
    }
}

package br.univali.desgarra.menu;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.univali.desgarra.R;
import br.univali.desgarra.database.DatabaseHelper;
import br.univali.desgarra.inicial.Usuario;


public class FeedFragment extends Fragment {

    Usuario u;
    public FeedFragment(Usuario usu) {
        this.u = usu;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_feed, container, false);

        /*
        AppCompatImageButton btnPelucia = v.findViewById(R.id.descricaopelucia);
        btnPelucia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new DescricaoPeluciaFragment(u)).commit();
            }
        });

        AppCompatImageButton btnVendedor = v.findViewById(R.id.Vendedor);
        btnVendedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new PerfilFragment(u)).commit();
            }
        });*/

        AppCompatImageButton btnUpgrade = v.findViewById(R.id.upgrade);
        if(u.getChave_pix().equals(" ")){
            btnUpgrade.setVisibility(View.VISIBLE);
        }
        else {
            btnUpgrade.setVisibility(View.GONE);
        }
        btnUpgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new VendedorRegistroFragment(u)).commit();
            }
        });


        AppCompatImageButton btnHome = v.findViewById(R.id.home);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new FeedFragment(u)).commit();
            }
        });

        AppCompatImageButton btnPerfil = v.findViewById(R.id.perfil);
        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new PerfilFragment(u)).commit();
            }
        });

        AppCompatImageButton btnPlus = v.findViewById(R.id.plus);
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pelucia pelucia = new Pelucia();
                pelucia.setId(0);
                pelucia.setId_usuario(u.getId());
                pelucia.setNome(" ");
                pelucia.setCor(" ");
                pelucia.setDescricao(" ");
                pelucia.setDoar(" ");
                pelucia.setTrocar(" ");
                pelucia.setVender(" ");
                pelucia.setTamanho(0);
                pelucia.setPreco(0);
                pelucia.setData_aquisicao(" ");
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new AdicionarInicioPeluciaFragment(u, pelucia)).commit();
            }
        });

        if(u.getChave_pix().equals(" ")){
            btnPlus.setVisibility(View.GONE);
        }
        else {
            btnPlus.setVisibility(View.VISIBLE);
        }

        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        RecyclerView recyclerViewPelucias = v.findViewById(R.id.recyclerViewPelucia);

        LinearLayoutManager manager = new LinearLayoutManager(v.getContext());
        recyclerViewPelucias.setLayoutManager(manager);
        recyclerViewPelucias.addItemDecoration(new DividerItemDecoration(v.getContext(), LinearLayoutManager.VERTICAL));
        recyclerViewPelucias.setHasFixedSize(true);

        //Cursor dataPelucia = databaseHelper.getAllPelucia();

        ArrayList<Integer> listPeluciaId = new ArrayList<Integer>();
        databaseHelper.getAllNamePeluciaTransacao (u.getId(), listPeluciaId);

        Cursor dataPelucia = databaseHelper.getAllPeluciaNotUsuario(u.getId());
        List<Pelucia> pelucias = new ArrayList<Pelucia>();
        while (dataPelucia.moveToNext()) {
            Pelucia p = new Pelucia();
            int idColumnIndex = dataPelucia.getColumnIndex("_id");
            p.setId(Integer.parseInt(dataPelucia.getString(idColumnIndex)));
            int nameColumnIndex = dataPelucia.getColumnIndex("nome");
            p.setNome(dataPelucia.getString(nameColumnIndex));
            int UsuarioColumnIndex = dataPelucia.getColumnIndex("id_usuario");
            p.setId_usuario(Integer.parseInt(dataPelucia.getString(UsuarioColumnIndex)));
            /*int usuColumnIndex = dataPelucia.getColumnIndex("id_usuario");
            c.setId_usuario(dataPelucia.getInt(usuColumnIndex));*/

            if (!listPeluciaId.contains(p.getId())){
                pelucias.add(p);
            }

        }
        dataPelucia.close();
        databaseHelper.closeDBConnection();

        PeluciaAdapterOferta adapterPelucias = new PeluciaAdapterOferta(pelucias, getActivity(), u);
        recyclerViewPelucias.setAdapter(adapterPelucias);



        return v;
    }
}
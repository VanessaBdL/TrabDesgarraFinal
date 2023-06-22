package br.univali.desgarra.menu;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.univali.desgarra.R;
import br.univali.desgarra.inicial.Usuario;

public class DescricaoPeluciaFragment extends Fragment {

    Usuario u;
    public DescricaoPeluciaFragment(Usuario usu) {
        this.u = usu;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_descricao_pelucia, container, false);

        AppCompatImageButton btnHome = v.findViewById(R.id.home);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new FeedFragment(u)).commit();
            }
        });


        AppCompatImageButton btnUpgrade = v.findViewById(R.id.upgrade);
        btnUpgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new VendedorRegistroFragment(u)).commit();
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

        AppCompatImageButton btnPerfil = v.findViewById(R.id.perfil);
        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new PerfilFragment(u)).commit();
            }
        });

        return v;
    }
}
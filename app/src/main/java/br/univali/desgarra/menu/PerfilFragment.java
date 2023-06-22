package br.univali.desgarra.menu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.univali.desgarra.R;
import br.univali.desgarra.database.DatabaseHelper;
import br.univali.desgarra.inicial.TelaInicialFragment;
import br.univali.desgarra.inicial.Usuario;

public class PerfilFragment extends Fragment {

    Usuario u;
    DatabaseHelper databaseHelper;
    TextView etNome;
    TextView etTelefone;
    TextView etEmail;
    TextView etChavePix;
    public PerfilFragment(Usuario usu) {
        this.u = usu;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_perfil, container, false);

        etNome      = v.findViewById(R.id.textViewNomeUsuario);
        etTelefone      = v.findViewById(R.id.textViewTelefone);
        etEmail      = v.findViewById(R.id.textViewEmail);
        etChavePix      = v.findViewById(R.id.textViewChavePix);

        databaseHelper = new DatabaseHelper(getActivity());
        etNome.setText(u.getNome());
        etTelefone.setText(u.getTelefone());
        etEmail.setText(u.getEmail());
        etChavePix.setText(u.getChave_pix());


        Button btnEditar = v.findViewById(R.id.buttonEditar);
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new EditarUsuarioFragment(u)).commit();
            }
        });

        AppCompatImageButton btnUpgrade = v.findViewById(R.id.upgrade);

        AppCompatImageButton btnHome = v.findViewById(R.id.home);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new FeedFragment(u)).commit();
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
            btnUpgrade.setVisibility(View.VISIBLE);
            btnPlus.setVisibility(View.GONE);
        }
        else {
            btnUpgrade.setVisibility(View.GONE);
            btnPlus.setVisibility(View.VISIBLE);
        }
        btnUpgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new VendedorRegistroFragment(u)).commit();
            }
        });

        AppCompatImageButton btnPerfil = v.findViewById(R.id.perfil);
        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new PerfilFragment(u)).commit();
            }
        });

        Button btnSair = v.findViewById(R.id.buttonSair);
        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new TelaInicialFragment()).commit();
            }
        });

        Button btnExcluir = v.findViewById(R.id.buttonExcluir);
        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.dialog_excluir_usuario);
                builder.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Cursor dataPelucia = databaseHelper.getAllPeluciaUsuario(u.getId());
                        while (dataPelucia.moveToNext()) {
                            Pelucia p = new Pelucia();
                            int idColumnIndex = dataPelucia.getColumnIndex("_id");
                            p.setId(Integer.parseInt(dataPelucia.getString(idColumnIndex)));
                            databaseHelper.deletePelucia(p);
                        }
                        dataPelucia.close();
                        databaseHelper.closeDBConnection();

                        databaseHelper.deleteUsuario(u);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new TelaInicialFragment()).commit();
                    }
                });
                builder.setNegativeButton(R.string.nao, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Não faz nada
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });



        RecyclerView recyclerViewPelucias = v.findViewById(R.id.recyclerViewPelucia);

        LinearLayoutManager manager = new LinearLayoutManager(v.getContext());
        recyclerViewPelucias.setLayoutManager(manager);
        recyclerViewPelucias.addItemDecoration(new DividerItemDecoration(v.getContext(), LinearLayoutManager.VERTICAL));
        recyclerViewPelucias.setHasFixedSize(true);

        ArrayList<Integer> listPeluciaId = new ArrayList<Integer>();
        databaseHelper.getAllNamePeluciaTransacao (u.getId(), listPeluciaId);


        //Cursor dataPelucia = databaseHelper.getAllPelucia();
        Cursor dataPelucia = databaseHelper.getAllPeluciaUsuario(u.getId());
        List<Pelucia> pelucias = new ArrayList<Pelucia>();
        while (dataPelucia.moveToNext()) {
            Pelucia p = new Pelucia();
            int idColumnIndex = dataPelucia.getColumnIndex("_id");
            p.setId(Integer.parseInt(dataPelucia.getString(idColumnIndex)));
            int nameColumnIndex = dataPelucia.getColumnIndex("nome");
            p.setNome(dataPelucia.getString(nameColumnIndex));

            /*int usuColumnIndex = dataPelucia.getColumnIndex("id_usuario");
            c.setId_usuario(dataPelucia.getInt(usuColumnIndex));*/

            if (!listPeluciaId.contains(p.getId())){
                pelucias.add(p);
            }
        }
        dataPelucia.close();
        databaseHelper.closeDBConnection();

        PeluciaAdapter adapterPelucias = new PeluciaAdapter(pelucias, getActivity(),u);
        recyclerViewPelucias.setAdapter(adapterPelucias);




        RecyclerView recyclerViewPeluciasTransacao = v.findViewById(R.id.recyclerViewPeluciaTransacao);

        LinearLayoutManager manager2 = new LinearLayoutManager(v.getContext());
        recyclerViewPeluciasTransacao.setLayoutManager(manager2);
        recyclerViewPeluciasTransacao.addItemDecoration(new DividerItemDecoration(v.getContext(), LinearLayoutManager.VERTICAL));
        recyclerViewPeluciasTransacao.setHasFixedSize(true);

        //Cursor dataPelucia = databaseHelper.getAllPelucia();

        Cursor dataTransacao = databaseHelper.getAllPeluciaNotUsuario(u.getId());
        List<Transacao> transacoes = new ArrayList<Transacao>();
        while (dataPelucia.moveToNext()) {

            Transacao t = new Transacao();
            int idColumnIndex = dataTransacao.getColumnIndex("_id");
            t.setId(Integer.parseInt(dataTransacao.getString(idColumnIndex)));
            int nameColumnIndex = dataTransacao.getColumnIndex("id_usuario_vendedor");
            t.setId_usuario_vendedor(Integer.parseInt(dataTransacao.getString(nameColumnIndex)));
            int PeluciaColumnIndex = dataTransacao.getColumnIndex("id_pelucia");
            t.setId_pelucia(Integer.parseInt(dataTransacao.getString(PeluciaColumnIndex)));
            int UsuarioColumnIndex = dataTransacao.getColumnIndex("id_usuario_recebedor");
            t.setId_usuario_recebedor(Integer.parseInt(dataTransacao.getString(UsuarioColumnIndex)));
            int TransacaoColumnIndex = dataTransacao.getColumnIndex("tipo_transacao");
            t.setTipo_transacao(dataTransacao.getString(TransacaoColumnIndex));

            transacoes.add(t);

        }
        dataPelucia.close();
        databaseHelper.closeDBConnection();

        PeluciaAdapterTransacao adapterTransacao = new PeluciaAdapterTransacao(transacoes, getActivity());
        recyclerViewPeluciasTransacao.setAdapter(adapterTransacao);

        return v;
    }
}
package br.univali.desgarra.inicial;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import br.univali.desgarra.R;
import br.univali.desgarra.database.DatabaseHelper;
import br.univali.desgarra.menu.FeedFragment;


public class LoginFragment extends Fragment {

    EditText etUsuario;
    EditText etSenha;


    public LoginFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_login, container, false);

        etUsuario     = v.findViewById(R.id.usuario);
        etSenha    = v.findViewById(R.id.senha);

        AppCompatImageButton btnSair = v.findViewById(R.id.voltar);
        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new TelaInicialFragment()).commit();
            }
        });

        Button btnEntrar = v.findViewById(R.id.continuar);
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());

               /* Usuario u = new Usuario();
                u.setNome("vanessa");
                u.setSenha("123");
                u.setEmail("va");
                u.setData_nascimento("12");
                u.setTelefone("12");
                u.setEndereco("12");
                u.setComplemento("12");
                u.setNumero(875);
                u.setCep("12");
                u.setRg("12");
                u.setCpf("12");
                u.setChave_pix("12");
                databaseHelper.createUsuario(u);*/

                Usuario logado = new Usuario();

                boolean LoginValido = false;
                Cursor dataUsuario = databaseHelper.getAllUsuario();
                while (dataUsuario.moveToNext()) {
                    int ColumnIndex = dataUsuario.getColumnIndex("nome");
                    String nome = dataUsuario.getString(ColumnIndex);

                    ColumnIndex = dataUsuario.getColumnIndex("email");
                    String email = dataUsuario.getString(ColumnIndex);

                    ColumnIndex = dataUsuario.getColumnIndex("senha");
                    String senha = dataUsuario.getString(ColumnIndex);

                    if ((etUsuario.getText().toString().equals(email) || etUsuario.getText().toString().equals(nome)) && etSenha.getText().toString().equals(senha)){
                        LoginValido = true;

                        ColumnIndex = dataUsuario.getColumnIndex("_id");
                        int id = Integer.parseInt(dataUsuario.getString(ColumnIndex));
                        logado = databaseHelper.getByIdUsuario(id);
                        break;
                    }
                }
                dataUsuario.close();
                databaseHelper.closeDBConnection();

                if (LoginValido){
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new FeedFragment(logado)).commit();
                }else{
                    Toast.makeText(getActivity(), "Campos incorretos!", Toast.LENGTH_LONG).show();
                }
            }
        });

        Button btnEntrarG = v.findViewById(R.id.continuarg);
        btnEntrarG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new FeedFragment()).commit();
                Toast.makeText(getActivity(), "Faz o login em cima pra testar, professor c:", Toast.LENGTH_LONG).show();
            }
        });

        Button btnEntrarF = v.findViewById(R.id.continuarf);
        btnEntrarF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new FeedFragment()).commit();
                Toast.makeText(getActivity(), "Faz o login em cima pra testar, professor c:", Toast.LENGTH_LONG).show();
            }
        });

        return v;
    }
}
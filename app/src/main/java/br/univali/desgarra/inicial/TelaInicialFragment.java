package br.univali.desgarra.inicial;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import br.univali.desgarra.R;


public class TelaInicialFragment extends Fragment {

    public TelaInicialFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_tela_inicial, container, false);

        Button btnCadastro = v.findViewById(R.id.iniciarcadastro);
        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Usuario u = new Usuario();
                u.setId(0);
                u.setNome(" ");
                u.setSenha(" ");
                u.setEmail(" ");
                u.setData_nascimento(" ");
                u.setTelefone(" ");
                u.setEndereco(" ");
                u.setComplemento(" ");
                u.setNumero(0);
                u.setCep(" ");
                u.setRg(" ");
                u.setCpf(" ");
                u.setChave_pix(" ");

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new RegistroInicioFragment(u)).commit();
            }
        });

        Button btnLogin = v.findViewById(R.id.japossuicadastro);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new LoginFragment()).commit();
            }
        });

        //TODO Não sei fazer isso testa da um jeito akakak te amo <3
        Button btnSair = v.findViewById(R.id.sair);
        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finishAffinity();
            }
        });

        return v;
    }
}
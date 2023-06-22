package br.univali.desgarra.inicial;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;

import br.univali.desgarra.R;


public class RegistroInicioFragment extends Fragment {

    EditText etNome;
    EditText etEmail;
    EditText etSenha;
    EditText etDataNascimento;

    Usuario u;

    public RegistroInicioFragment(Usuario usu) {
        this.u = usu;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_registro_inicio, container, false);

        etNome     = v.findViewById(R.id.editTextNome);
        etEmail    = v.findViewById(R.id.editTextEmail);
        etSenha    = v.findViewById(R.id.editTextSenha);
        etDataNascimento    = v.findViewById(R.id.editTextDataNascimento);


        if (!u.getNome().equals(" ")){
            etNome.setText(u.getNome());
        }

        if (!u.getEmail().equals(" ")){
            etEmail.setText(u.getEmail());
        }

        if (!u.getSenha().equals(" ")){
            etSenha.setText(u.getSenha());
        }

        if (!u.getData_nascimento().equals(" ")){
            etDataNascimento.setText(u.getData_nascimento());
        }

        AppCompatImageButton btnSair = v.findViewById(R.id.voltar);
        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new TelaInicialFragment()).commit();
            }
        });

        Button btnContinuar = v.findViewById(R.id.continuar);
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etNome.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Por favor, preencha seu nome!", Toast.LENGTH_LONG).show();
                } else if (etEmail.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Por favor, preencha o e-mail!", Toast.LENGTH_LONG).show();
                }else if (etSenha.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Por favor, preencha a senha!", Toast.LENGTH_LONG).show();
                }else if (etDataNascimento.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Por favor, preencha a sua data de Nascimento!", Toast.LENGTH_LONG).show();
                }else{

                    u.setNome(etNome.getText().toString());
                    u.setSenha(etSenha.getText().toString());
                    u.setEmail(etEmail.getText().toString());
                    u.setData_nascimento(etDataNascimento.getText().toString());

                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new RegistroFimFragment(u)).commit();
                }

            }
        });
        return v;
    }
}
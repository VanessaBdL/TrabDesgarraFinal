package br.univali.desgarra.inicial;

import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;

import br.univali.desgarra.R;
import br.univali.desgarra.database.DatabaseHelper;
import br.univali.desgarra.menu.FeedFragment;


public class RegistroFimFragment extends Fragment {

    Usuario u;
    DatabaseHelper databaseHelper;
    EditText etTelefone;
    EditText etCPF;
    public RegistroFimFragment(Usuario u) {
        this.u = u;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_registro_fim, container, false);
        databaseHelper = new DatabaseHelper(getActivity());

        etTelefone  = v.findViewById(R.id.editTextTelefone);
        etCPF      = v.findViewById(R.id.editTextCPF);

        if (!u.getTelefone().equals(" ")){
            etTelefone.setText(u.getTelefone());
        }

        if (!u.getCpf().equals(" ")){
            etCPF.setText(u.getCpf());
        }

        Button btnContinuar = v.findViewById(R.id.continuar);
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etTelefone.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Por favor, preencha seu telefone!", Toast.LENGTH_LONG).show();
                }else{
                    boolean podelogar = false;
                    u.setTelefone(etTelefone.getText().toString());
                    u.setCpf(etCPF.getText().toString());

                    databaseHelper.createUsuario(u);


                    Cursor dataUsuario = databaseHelper.getAllUsuario();
                    while (dataUsuario.moveToNext()) {
                        int ColumnIndex = dataUsuario.getColumnIndex("nome");
                        String nome = dataUsuario.getString(ColumnIndex);

                        ColumnIndex = dataUsuario.getColumnIndex("email");
                        String email = dataUsuario.getString(ColumnIndex);

                        ColumnIndex = dataUsuario.getColumnIndex("senha");
                        String senha = dataUsuario.getString(ColumnIndex);

                        if (u.getEmail().equals(email) && u.getNome().equals(nome) && u.getSenha().equals(senha)){
                            ColumnIndex = dataUsuario.getColumnIndex("_id");
                            int id = Integer.parseInt(dataUsuario.getString(ColumnIndex));
                            u.setId(id);
                            podelogar = true;
                            break;
                        }
                    }
                    if (podelogar){
                        Toast.makeText(getActivity(), "Bem vindo ao Desgarra!", Toast.LENGTH_LONG).show();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new FeedFragment(u)).commit();
                    }else{
                        Toast.makeText(getActivity(), "Algum erro aconteceu, tente logar pelo nosso menu novamente!", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });

        etTelefone.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                if (etTelefone.getText().toString().equals("")) {
                    u.setTelefone(" ");
                }else{
                    u.setTelefone(etTelefone.getText().toString());
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });

        etCPF.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                if (etCPF.getText().toString().equals("")) {
                    u.setCpf("");
                }else{
                    u.setCpf(etCPF.getText().toString());
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });

        Button btnEndereco = v.findViewById(R.id.preencherendereco);
        btnEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new EnderecoFragment(u)).commit();
            }
        });

        AppCompatImageButton btnSair = v.findViewById(R.id.voltar);
        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new RegistroInicioFragment(u)).commit();
            }
        });


        return v;
    }
}
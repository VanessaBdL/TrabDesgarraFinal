package br.univali.desgarra.menu;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.univali.desgarra.R;
import br.univali.desgarra.database.DatabaseHelper;
import br.univali.desgarra.inicial.RegistroFimFragment;
import br.univali.desgarra.inicial.TelaInicialFragment;
import br.univali.desgarra.inicial.Usuario;

public class EditarUsuarioFragment extends Fragment {

    Usuario u;
    EditText etNome;
    EditText etEmail;
    EditText etSenha;
    EditText etDataNascimento;
    DatabaseHelper databaseHelper;
    EditText etTelefone;
    EditText etCPF;
    EditText etEndereco;
    EditText etNumero;
    EditText etComplemento;
    EditText etCEP;

    EditText etRG;
    EditText etPIX;

    public EditarUsuarioFragment(Usuario usuario) {
        this.u = usuario;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_editar_usuario, container, false);

        etNome     = v.findViewById(R.id.editTextNome);
        etEmail    = v.findViewById(R.id.editTextEmail);
        etSenha    = v.findViewById(R.id.editTextSenha);
        etDataNascimento    = v.findViewById(R.id.editTextDataNascimento);
        etTelefone  = v.findViewById(R.id.editTextTelefone);
        etCPF      = v.findViewById(R.id.editTextCPF);
        etEndereco  = v.findViewById(R.id.editTextEndereco);
        etNumero  = v.findViewById(R.id.editTextNumero);
        etComplemento  = v.findViewById(R.id.editTextComplemento);
        etCEP  = v.findViewById(R.id.editTextCEP);
        etPIX = v.findViewById(R.id.editTextPIX);
        etRG = v.findViewById(R.id.editTextRG);

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

        if (!u.getTelefone().equals(" ")){
            etTelefone.setText(u.getTelefone());
        }

        if (!u.getCpf().equals(" ")){
            etCPF.setText(u.getCpf());
        }
        if (!u.getEndereco().equals(" ")){
            etEndereco.setText(u.getEndereco());
        }

        if (u.getNumero() != 0){
            etNumero.setText(u.getEmail());
        }

        if (!u.getComplemento().equals(" ")){
            etComplemento.setText(u.getComplemento());
        }

        if (!u.getCep().equals(" ")){
            etCEP.setText(u.getCep());
        }

        if (!u.getChave_pix().equals(" ")){
            etPIX.setText(u.getChave_pix());
        }

        if (!u.getRg().equals(" ")){
            etRG.setText(u.getRg());
        }

        AppCompatImageButton btnSair = v.findViewById(R.id.voltar);
        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new PerfilFragment(u)).commit();
            }
        });

        Button btnSalvar = v.findViewById(R.id.salvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
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
                }else if (etTelefone.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Por favor, preencha seu telefone!", Toast.LENGTH_LONG).show();
                }else{

                    if (!etNome.getText().toString().equals("")) {
                        u.setNome(etNome.getText().toString());
                    }

                    if (!etPIX.getText().toString().equals("")) {
                        u.setChave_pix(etPIX.getText().toString());
                    }

                    if (!etRG.getText().toString().equals("")) {
                        u.setRg(etRG.getText().toString());
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

                    if (!u.getTelefone().equals(" ")){
                        etTelefone.setText(u.getTelefone());
                    }

                    if (!u.getCpf().equals(" ")){
                        etCPF.setText(u.getCpf());
                    }
                    if (!u.getEndereco().equals(" ")){
                        etEndereco.setText(u.getEndereco());
                    }

                    if (u.getNumero() != 0){
                        etNumero.setText(u.getEmail());
                    }

                    if (!u.getComplemento().equals(" ")){
                        etComplemento.setText(u.getComplemento());
                    }

                    if (!u.getCep().equals(" ")){
                        etCEP.setText(u.getCep());
                    }
                    databaseHelper = new DatabaseHelper(getActivity());
                    databaseHelper.updateUsuario(u);

                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new FeedFragment(u)).commit();
                }

            }
        });
        return v;
    }
}
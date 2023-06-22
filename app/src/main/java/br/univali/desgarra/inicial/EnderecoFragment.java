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


public class EnderecoFragment extends Fragment {

    Usuario u;
    public EnderecoFragment(Usuario u) {
        this.u = u;
    }

    EditText etEndereco;
    EditText etNumero;
    EditText etComplemento;
    EditText etCEP;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_endereco, container, false);

        etEndereco  = v.findViewById(R.id.editTextEndereco);
        etNumero  = v.findViewById(R.id.editTextNumero);
        etComplemento  = v.findViewById(R.id.editTextComplemento);
        etCEP  = v.findViewById(R.id.editTextCEP);

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

        AppCompatImageButton btnSair = v.findViewById(R.id.voltar);
        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new RegistroFimFragment(u)).commit();
            }
        });

        Button btnContinuar = v.findViewById(R.id.continuar);
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etEndereco.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Por favor, preencha seu endereço!", Toast.LENGTH_LONG).show();
                } else if (etNumero.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Por favor, preencha o número!", Toast.LENGTH_LONG).show();
                }else if (etComplemento.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Por favor, preencha um complemento!", Toast.LENGTH_LONG).show();
                }else if (etCEP.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Por favor, preencha seu CEP!", Toast.LENGTH_LONG).show();
                }else{

                    u.setEndereco(etEndereco.getText().toString());
                    u.setNumero(Integer.parseInt(etNumero.getText().toString()));
                    u.setComplemento(etComplemento.getText().toString());
                    u.setCep(etCEP.getText().toString());

                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new RegistroFimFragment(u)).commit();
                }

            }
        });
        return v;
    }
}
package br.univali.desgarra.menu;

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
import br.univali.desgarra.inicial.RegistroFimFragment;
import br.univali.desgarra.inicial.Usuario;


public class AdicionarInicioPeluciaFragment extends Fragment {

    Usuario u;
    Pelucia p;
    EditText etNome;
    EditText etDescricao;
    EditText etPreco;
    EditText etCor;
    public AdicionarInicioPeluciaFragment(Usuario usu, Pelucia pel) {
        this.u = usu;
        this.p = pel;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_adicionar_inicio_pelucia, container, false);

        etNome      = v.findViewById(R.id.editTextNome);
        etDescricao = v.findViewById(R.id.editTextDescricao);
        etPreco     = v.findViewById(R.id.editTextPreco);
        etCor       = v.findViewById(R.id.editTextCor);

        if (!p.getNome().equals(" ")){
            etNome.setText(p.getNome());
        }

        if (!p.getDescricao().equals(" ")){
            etDescricao.setText(p.getDescricao());
        }

        if (p.getPreco() != 0.00){
            etPreco.setText(String.valueOf(p.getPreco()));
        }

        if (!p.getCor().equals(" ")){
            etCor.setText(p.getCor());
        }

        AppCompatImageButton btnSair = v.findViewById(R.id.voltar);
        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new FeedFragment(u)).commit();
            }
        });

        Button btnContinuar = v.findViewById(R.id.continuar);
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etNome.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Por favor, preencha o nome da pelúcia!", Toast.LENGTH_LONG).show();
                } else if (etDescricao.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Por favor, preencha sua descrição!", Toast.LENGTH_LONG).show();
                }else if (etPreco.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Por favor, preencha seu preço!", Toast.LENGTH_LONG).show();
                }else if (etCor.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Por favor, preencha a sua cor!", Toast.LENGTH_LONG).show();
                }else{

                    p.setNome(etNome.getText().toString());
                    p.setDescricao(etDescricao.getText().toString());
                    p.setPreco(Float.parseFloat(etPreco.getText().toString()));
                    p.setCor(etCor.getText().toString());

                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new AdicionarFimPeluciaFragment(u,p)).commit();
                }


            }
        });

        return v;
    }
}
package br.univali.desgarra.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;

import br.univali.desgarra.R;
import br.univali.desgarra.database.DatabaseHelper;
import br.univali.desgarra.inicial.Usuario;


public class AdicionarFimPeluciaFragment extends Fragment {

    Usuario u;

    Pelucia p;
    DatabaseHelper databaseHelper;
    EditText etTamanho;
    EditText etDataAquisicao;
    CheckBox trocar;
    CheckBox vender;
    CheckBox doar;

    public AdicionarFimPeluciaFragment(Usuario usu, Pelucia pel) {
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

        View v = inflater.inflate(R.layout.fragment_adicionar_fim_pelucia, container, false);
        databaseHelper = new DatabaseHelper(getActivity());

        etTamanho  = v.findViewById(R.id.editTextTamanho);
        etDataAquisicao  = v.findViewById(R.id.editTextData);
        trocar  = v.findViewById(R.id.checkBoxTrocar);
        vender  = v.findViewById(R.id.checkBoxVender);
        doar  = v.findViewById(R.id.checkBoxDoar);

        if (!p.getData_aquisicao().equals(" ")){
            etDataAquisicao.setText(p.getData_aquisicao());
        }

        if (p.getTamanho() != 0.00){
            etTamanho.setText(String.valueOf(p.getTamanho()));
        }

        if (!p.getVender().equals(" ")){
            vender.setChecked(true);
        }

        if (!p.getDoar().equals(" ")){
            doar.setChecked(true);
        }

        if (!p.getTrocar().equals(" ")){
            trocar.setChecked(true);
        }

        AppCompatImageButton btnSair = v.findViewById(R.id.voltar);
        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new AdicionarInicioPeluciaFragment(u,p)).commit();
            }
        });

        Button btnContinuar = v.findViewById(R.id.continuar);
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etDataAquisicao.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Por favor, preencha a data de aquisição da pelúcia!", Toast.LENGTH_LONG).show();
                } else if (etTamanho.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Por favor, preencha seu tamanho!", Toast.LENGTH_LONG).show();
                }else if (!trocar.isChecked() && !doar.isChecked() && !vender.isChecked()) {
                    Toast.makeText(getActivity(), "Por favor, preencha pelo menos uma opção entre troca, venda ou doação!", Toast.LENGTH_LONG).show();
                }else{

                    p.setData_aquisicao(etDataAquisicao.getText().toString());
                    p.setTamanho(Float.parseFloat(etTamanho.getText().toString()));

                    if (trocar.isChecked()){
                        p.setTrocar("1");
                    }

                    if (doar.isChecked()){
                        p.setDoar("1");
                    }

                    if (vender.isChecked()){
                        p.setVender("1");
                    }
                    //Toast.makeText(getActivity(), p.getId_usuario() + " / " + u.getId(), Toast.LENGTH_LONG).show();
                    databaseHelper.createPelucia(p);
                    Toast.makeText(getActivity(), "Pelúcia inserida com sucesso", Toast.LENGTH_LONG).show();

                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new FeedFragment(u)).commit();
                }
            }
        });

        Button btnImagem = v.findViewById(R.id.anexar);
        btnImagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "FINGE QUE INSERIU UMA IMAGEM AQUI c:", Toast.LENGTH_LONG).show();
            }
        });

        return v;
    }
}
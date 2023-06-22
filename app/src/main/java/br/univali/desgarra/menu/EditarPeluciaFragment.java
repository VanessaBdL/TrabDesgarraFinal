package br.univali.desgarra.menu;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import br.univali.desgarra.R;
import br.univali.desgarra.database.DatabaseHelper;
import br.univali.desgarra.inicial.RegistroInicioFragment;
import br.univali.desgarra.inicial.Usuario;

public class EditarPeluciaFragment extends Fragment {

    Usuario u;
    int id_pelucia;
    Pelucia p;
    EditText etNome;
    EditText etDescricao;
    EditText etPreco;
    EditText etCor;
    DatabaseHelper databaseHelper;
    EditText etTamanho;
    EditText etDataAquisicao;
    CheckBox trocar;
    CheckBox vender;
    CheckBox doar;

    public EditarPeluciaFragment(Usuario usu, int pel) {
        this.u = usu;
        this.id_pelucia = pel;
    }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_editar_pelucia, container, false);

            etNome      = v.findViewById(R.id.editTextNome);
            etDescricao = v.findViewById(R.id.editTextDescricao);
            etPreco     = v.findViewById(R.id.editTextPreco);
            etCor       = v.findViewById(R.id.editTextCor);
            etTamanho  = v.findViewById(R.id.editTextTamanho);
            etDataAquisicao  = v.findViewById(R.id.editTextData);
            trocar  = v.findViewById(R.id.checkBoxTrocar);
            vender  = v.findViewById(R.id.checkBoxVender);
            doar  = v.findViewById(R.id.checkBoxDoar);

            DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());

            p = databaseHelper.getByIdPelucia(id_pelucia);

            AppCompatImageButton btnSair = v.findViewById(R.id.voltar);
            btnSair.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new PerfilFragment(u)).commit();
                    databaseHelper.closeDBConnection();
                }
            });

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
                    }else if (etDataAquisicao.getText().toString().equals("")) {
                        Toast.makeText(getActivity(), "Por favor, preencha a data de aquisição da pelúcia!", Toast.LENGTH_LONG).show();
                    } else if (etTamanho.getText().toString().equals("")) {
                        Toast.makeText(getActivity(), "Por favor, preencha seu tamanho!", Toast.LENGTH_LONG).show();
                    }else if (!trocar.isChecked() && !doar.isChecked() && !vender.isChecked()) {
                        Toast.makeText(getActivity(), "Por favor, preencha pelo menos uma opção entre troca, venda ou doação!", Toast.LENGTH_LONG).show();
                    }else{

                        p.setNome(etNome.getText().toString());
                        p.setDescricao(etDescricao.getText().toString());
                        p.setPreco(Float.parseFloat(etPreco.getText().toString()));
                        p.setCor(etCor.getText().toString());
                        p.setData_aquisicao(etDataAquisicao.getText().toString());
                        p.setTamanho(Float.parseFloat(etTamanho.getText().toString()));

                        if (trocar.isChecked()){
                            p.setTrocar("1");
                        }else{
                            p.setTrocar(" ");
                        }

                        if (doar.isChecked()){
                            p.setDoar("1");
                        }else{
                            p.setDoar(" ");
                        }

                        if (vender.isChecked()){
                            p.setVender("1");
                        }else{
                            p.setVender(" ");
                        }
                        //Toast.makeText(getActivity(), p.getId_usuario() + " / " + u.getId(), Toast.LENGTH_LONG).show();
                        databaseHelper.updatePelucia(p);
                        Toast.makeText(getActivity(), "Pelúcia editada com sucesso", Toast.LENGTH_LONG).show();
                        databaseHelper.closeDBConnection();

                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new PerfilFragment(u)).commit();
                    }


                }
            });

            AppCompatImageButton btnExcluir = v.findViewById(R.id.voltar);
            btnExcluir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    databaseHelper.deletePelucia(p);
                    Toast.makeText(getActivity(), "Pelúcia excluída com sucesso", Toast.LENGTH_LONG).show();
                    databaseHelper.closeDBConnection();

                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new PerfilFragment(u)).commit();
                }
            });

            return v;
        }
    }

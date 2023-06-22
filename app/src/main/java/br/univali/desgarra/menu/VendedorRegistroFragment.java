package br.univali.desgarra.menu;

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

import br.univali.desgarra.R;
import br.univali.desgarra.database.DatabaseHelper;
import br.univali.desgarra.inicial.RegistroFimFragment;
import br.univali.desgarra.inicial.Usuario;


public class VendedorRegistroFragment extends Fragment {

    Usuario u;

    DatabaseHelper databaseHelper;
    EditText etRG;
    EditText etCPF;
    EditText etChavePix;
    public VendedorRegistroFragment(Usuario usu) {
        this.u = usu;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_vendedor_registro, container, false);

        databaseHelper = new DatabaseHelper(getActivity());

        etRG     = v.findViewById(R.id.editTextRG);
        etCPF    = v.findViewById(R.id.editTextCPF);
        etChavePix     = v.findViewById(R.id.editTextPIX);

        if (!u.getRg().equals(" ")){
            etRG.setText(u.getRg());
        }

        if (!u.getCpf().equals(" ")){
            etCPF.setText(u.getCpf());
        }

        if (!u.getChave_pix().equals(" ")){
            etChavePix.setText(u.getChave_pix());
        }

        AppCompatImageButton btnVoltar = v.findViewById(R.id.voltar);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new FeedFragment(u)).commit();
            }
        });

        Button btnContinuar = v.findViewById(R.id.continuar);
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etRG.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Por favor, preencha o seu RG!", Toast.LENGTH_LONG).show();
                } else if (etCPF.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Por favor, preencha o seu CPF!", Toast.LENGTH_LONG).show();
                }else if (etChavePix.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Por favor, preencha a sua chave pix!", Toast.LENGTH_LONG).show();
                }else{


                    boolean IsValid = true;
                    Cursor dataUsuario = databaseHelper.getAllUsuario();
                    while (dataUsuario.moveToNext()) {
                        int ColumnIndex = dataUsuario.getColumnIndex("rg");
                        String rg = dataUsuario.getString(ColumnIndex);

                        ColumnIndex = dataUsuario.getColumnIndex("cpf");
                        String cpf = dataUsuario.getString(ColumnIndex);

                        ColumnIndex = dataUsuario.getColumnIndex("chave_pix");
                        String chave_pix = dataUsuario.getString(ColumnIndex);

                        ColumnIndex = dataUsuario.getColumnIndex("_id");
                        int id_usuario = dataUsuario.getInt(ColumnIndex);

                        if (etRG.getText().toString().equals(rg) || (etCPF.getText().toString().equals(cpf) && id_usuario !=(u.getId())) || etChavePix.getText().toString().equals(chave_pix)){
                            IsValid = false;
                            break;
                        }
                    }
                    if (IsValid){
                        u.setRg(etRG.getText().toString());
                        u.setCpf(etCPF.getText().toString());
                        u.setChave_pix(etChavePix.getText().toString());
                        databaseHelper.updateUsuario(u);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new FeedFragment(u)).commit();
                    }else{
                        Toast.makeText(getActivity(), "CPF, RG ou chave pix já cadastrada no banco!", Toast.LENGTH_LONG).show();
                    }

                }

            }
        });

        return v;
    }
}
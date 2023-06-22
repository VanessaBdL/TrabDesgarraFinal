package br.univali.desgarra.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

import br.univali.desgarra.R;
import br.univali.desgarra.database.DatabaseHelper;
import br.univali.desgarra.inicial.Usuario;

public class TransacaoPeluciaFragment extends Fragment {

    Usuario u;
    Usuario Vendedor;
    int id_pelucia;
    Pelucia p;
    TextView etNome;
    TextView etDescricao;
    TextView etPreco;
    TextView etCor;
    TextView etTamanho;
    TextView etDataAquisicao;

    TextView etNomeVendedor;
    CheckBox trocar;
    CheckBox vender;
    CheckBox doar;

    public TransacaoPeluciaFragment(Usuario usu, int pel) {
        this.u = usu;
        this.id_pelucia = pel;
    }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_transacao_pelucia, container, false);

            etNome      = v.findViewById(R.id.editTextNome);
            etDescricao = v.findViewById(R.id.editTextDescricao);
            etPreco     = v.findViewById(R.id.editTextPreco);
            etCor       = v.findViewById(R.id.editTextCor);
            etTamanho  = v.findViewById(R.id.editTextTamanho);
            etDataAquisicao  = v.findViewById(R.id.editTextData);
            trocar  = v.findViewById(R.id.checkBoxTrocar);
            vender  = v.findViewById(R.id.checkBoxVender);
            doar  = v.findViewById(R.id.checkBoxDoar);
            etNomeVendedor = v.findViewById(R.id.editTextVendedor);

            DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());




            p = databaseHelper.getByIdPelucia(id_pelucia);

            Usuario vendedor =  databaseHelper.getByIdUsuario(p.getId_usuario());
            etNomeVendedor.setText("Vendedor: "+ (vendedor.getNome()));

            AppCompatImageButton btnSair = v.findViewById(R.id.voltar);
            btnSair.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new PerfilFragment(u)).commit();
                    databaseHelper.closeDBConnection();
                }
            });

            if (!p.getNome().equals(" ")){
                etNome.setText("Nome: "+ p.getNome());
            }

            if (!p.getDescricao().equals(" ")){
                etDescricao.setText("Descrição: "+ p.getDescricao());
            }

            if (p.getPreco() != 0.00){
                etPreco.setText("Preço: "+ (String.valueOf(p.getPreco())));
            }

            if (!p.getCor().equals(" ")){
                etCor.setText("Cor: "+ p.getCor());
            }

            if (!p.getData_aquisicao().equals(" ")){
                etDataAquisicao.setText("Data de aquisição: "+ p.getData_aquisicao());
            }

            if (p.getTamanho() != 0.00){
                etTamanho.setText(String.valueOf("Tamanho: "+ p.getTamanho()));
            }

           // etNomeVendedor.setText();

            Button btnComprar = v.findViewById(R.id.Comprar);
            btnComprar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "Comprar", Toast.LENGTH_LONG).show();

                    Transacao t = new Transacao();
                    t.setId_usuario_vendedor(vendedor.getId());
                    t.setId_pelucia(p.getId());
                    t.setId_usuario_recebedor(u.getId());
                    t.setTipo_transacao("Vendida");

                    databaseHelper.createTransacao(t);
                    databaseHelper.closeDBConnection();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new FeedFragment(u)).commit();
                    //databaseHelper.closeDBConnection();
                }
            });

            Button btnDoar = v.findViewById(R.id.Doacao);
            btnDoar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "Doar", Toast.LENGTH_LONG).show();
                    Transacao t = new Transacao();
                    t.setId_usuario_vendedor(vendedor.getId());
                    t.setId_pelucia(p.getId());
                    t.setId_usuario_recebedor(u.getId());
                    t.setTipo_transacao("Doada");

                    databaseHelper.createTransacao(t);
                    databaseHelper.closeDBConnection();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new FeedFragment(u)).commit();
                }
            });

            Button btnTrocar = v.findViewById(R.id.Trocar);
            btnTrocar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "Por Falta de Verba no Aplicativo, a opção de troca não está funcionando. Financie a gente porque somos pobres trabalhadores que se estressam com programação, obrigada!", Toast.LENGTH_LONG).show();
                }
            });

            if (!p.getVender().equals(" ")){
                vender.setChecked(true);
                btnComprar.setVisibility(View.VISIBLE);
            }else{
                btnComprar.setVisibility(View.GONE);
            }

            if (!p.getDoar().equals(" ")){
                doar.setChecked(true);
                btnDoar.setVisibility(View.VISIBLE);
            }else{
                btnDoar.setVisibility(View.GONE);
            }

            if (!p.getTrocar().equals(" ")){
                trocar.setChecked(true);
                btnTrocar.setVisibility(View.VISIBLE);
            }else{
                btnTrocar.setVisibility(View.GONE);
            }


            return v;
        }
    }

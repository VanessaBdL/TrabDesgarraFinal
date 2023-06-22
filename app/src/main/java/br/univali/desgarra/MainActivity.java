package br.univali.desgarra;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import br.univali.desgarra.inicial.TelaInicialFragment;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new TelaInicialFragment()).commit();
        }
    }
}
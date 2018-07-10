package com.example.root.simple_list;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import static android.net.Uri.*;

public class MainActivity extends AppCompatActivity {
    //widgets
    private FloatingActionButton salvar;
    private ImageView foto;
    private EditText nome, rua, numero, bairro, cidade, telefone, detalhes;
    private SQLiteDatabase dados;
    private static final int IMAGEM_INTERNA = 1;

    public MainActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //cash dos widgets
        salvar = (FloatingActionButton)findViewById(R.id.salvar);
        nome = (EditText)findViewById(R.id.nome);
        rua = (EditText)findViewById(R.id.rua);
        numero = (EditText)findViewById(R.id.numero);
        bairro = (EditText)findViewById(R.id.bairro);
        cidade = (EditText)findViewById(R.id.cidade);
        telefone = (EditText)findViewById(R.id.telefone);
        detalhes = (EditText)findViewById(R.id.detalhes);
        foto = (ImageView)findViewById(R.id.foto);

        //abrir base de dados

        dados = openOrCreateDatabase("cliente.db",MODE_PRIVATE,null);
        dados.execSQL("CREATE TABLE IF NOT EXISTS cliente (id INTEGER PRIMARY KEY AUTOINCREMENT,foto VARCHAR,nome VARCHAR, rua VARCHAR,numero VARCHAR,bairro VARCHAR,cidade VARCHAR,telefone VACHAR,detalhes VARCHAR);");

        //cursor

        String aux = null;
        Cursor cursor = dados.rawQuery("SELECT foto FROM cliente  WHERE id  = 1", null);
        cursor.moveToFirst();
        foto.setImageURI(Uri.parse(aux = cursor.getString(cursor.getColumnIndexOrThrow("foto"))));
        cursor = dados.rawQuery("SELECT nome FROM cliente WHERE id = 1", null);
        cursor.moveToFirst();
        nome.setText(cursor.getString(cursor.getColumnIndexOrThrow("nome")));


        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,IMAGEM_INTERNA);
            }
        });

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome_pegado = nome.getText().toString();
                String rua_pegada = rua.getText().toString();
                String numero_pegado = numero.getText().toString();
                String bairro_pegado = bairro.getText().toString();
                String cidade_pegada = cidade.getText().toString();
                String telefone_pegado = telefone.getText().toString();
                String detalhes_pegados = detalhes.getText().toString();
                dados.execSQL(String.format("INSERT INTO cliente (nome) VALUES('%s');", nome_pegado));
                dados.execSQL(String.format("INSERT INTO cliente (rua) VALUES('%s');", rua_pegada));
                dados.execSQL(String.format("INSERT INTO cliente (numero) VALUES('%s');", numero_pegado));
                dados.execSQL(String.format("INSERT INTO cliente (bairro) VALUES('%s');", bairro_pegado));
                dados.execSQL(String.format("INSERT INTO cliente (cidade) VALUES('%s');", cidade_pegada));
                dados.execSQL(String.format("INSERT INTO cliente (telefone) VALUES('%s');", telefone_pegado));
                dados.execSQL(String.format("INSERT INTO cliente (detalhes) VALUES('%s');", detalhes_pegados));


            }
        });


    }

    protected  void onActivityResult (int requestCode, int resultCode, Intent intent){
        if(requestCode == IMAGEM_INTERNA) if (resultCode == RESULT_OK) {

            Uri imagemSelecionada = intent.getData();
            String imagem = imagemSelecionada.toString();
            dados.execSQL(String.format("INSERT INTO cliente (foto) VALUES('%s');", imagem));
            if (!imagem.isEmpty()){
                foto.setImageURI(imagemSelecionada);
            }




        }
    }
}

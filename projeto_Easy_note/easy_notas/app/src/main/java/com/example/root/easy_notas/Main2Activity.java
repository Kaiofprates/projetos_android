package com.example.root.easy_notas;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static java.lang.String.format;

public class Main2Activity extends MainActivity {

    private FloatingActionButton botao;
    private EditText texto;
    private SQLiteDatabase banco;
    private Button limpar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);



        Bundle info = getIntent().getExtras();
        final String nota = info.getString("nota");

        //Toast.makeText(Main2Activity.this, "essa é a nota = "+nota, Toast.LENGTH_SHORT).show();

        // cash
        limpar = (Button) findViewById(R.id.botao);
        limpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                texto.setText("");
            }
        });

        botao = (FloatingActionButton) findViewById(R.id.floatingActionButton2);
        texto = (EditText) findViewById(R.id.editText);

        // banco de dados

        banco = openOrCreateDatabase("apptarefas1", MODE_PRIVATE, null);
        banco.execSQL("CREATE TABLE IF NOT EXISTS tarefas(id INTEGER PRIMARY KEY AUTOINCREMENT, tarefa VARCHAR, nota VARCHAR ) ");

        try{
            Cursor cursor = banco.rawQuery("SELECT nota FROM tarefas WHERE tarefa = '" + nota + "'",null);
            cursor.moveToFirst();
            texto.setText(cursor.getString(cursor.getColumnIndexOrThrow("nota")));
        }catch (Exception e){
            Toast.makeText(Main2Activity.this, "não deu", Toast.LENGTH_SHORT).show();


        }

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String conteudo = texto.getText().toString();
                    banco.execSQL("UPDATE tarefas SET nota = '"+conteudo +"' WHERE tarefa = '"+ nota +"'");
                    Toast.makeText(Main2Activity.this, nota + " - Salvo com sucesso", Toast.LENGTH_SHORT).show();

                }catch (Exception e){
                    Toast.makeText(Main2Activity.this, "não deu", Toast.LENGTH_SHORT).show();

                }
            }
        });



    }
}

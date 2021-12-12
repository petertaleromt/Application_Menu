package com.example.myapplication_menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class activity_Cumple extends AppCompatActivity {

    private Button btn_cumple;
    private EditText edit_name;
    private EditText edit_email;
    private EditText edit_tel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cumple);

        //Agregar icono en el action Bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        //Asigando valores
        btn_cumple = (Button) findViewById(R.id.btn1_cumple);
        edit_name = (EditText) findViewById(R.id.edittext_namecumple);
        edit_email = (EditText) findViewById(R.id.edittext_emailcumple);
        edit_tel = (EditText) findViewById(R.id.edittext_telcumpe);

        btn_cumple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Enviar datos putExtra - name
                String datos = null;
                datos = edit_name.getText().toString() + " " + edit_email.getText().toString() + " " + edit_tel.getText().toString();
                Intent enviarDatos = new Intent(activity_Cumple.this, activity_Datos.class);
                enviarDatos.putExtra("datos_v1", String.valueOf(datos));
                startActivity(enviarDatos);

            }
        });


    }
}
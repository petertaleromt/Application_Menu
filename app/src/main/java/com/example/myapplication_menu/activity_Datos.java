package com.example.myapplication_menu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class activity_Datos extends AppCompatActivity {

    Bundle datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos);

        //Agregar icono en el action Bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);


        //Recibir datos utilizando - putExtra
        datos = getIntent().getExtras();
        String datosObtenidos = datos.getString("datos_v1");
        TextView mostrarDatos = (TextView) findViewById(R.id.name_text);
        mostrarDatos.setText(datosObtenidos);
    }
}
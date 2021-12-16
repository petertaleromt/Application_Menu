package com.example.myapplication_menu.ui.Servicios;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication_menu.FormActivity;
import com.example.myapplication_menu.R;
import com.example.myapplication_menu.databinding.FragmentServiciosBinding;
import com.example.myapplication_menu.ui.Adaptadores.ServicioAdapter;
import com.example.myapplication_menu.ui.Casos_Uso.CasoUsoServicios;
import com.example.myapplication_menu.ui.Datos.DBHelper;
import com.example.myapplication_menu.ui.Modelos.Servicio;

import java.util.ArrayList;

public class ServiciosFragment extends Fragment {
    private String TABLE_NAME = "SERVICIOS";
    private CasoUsoServicios casoUsoServicio;
    private GridView gridView;
    private DBHelper dbHelper;
    private ArrayList<Servicio> servicios;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_servicios, container, false);
        try{
            casoUsoServicio = new CasoUsoServicios();
            dbHelper = new DBHelper(getContext());
            Cursor cursor = dbHelper.getData(TABLE_NAME);
            servicios = casoUsoServicio.llenarListaServicios(cursor);
            gridView = (GridView) root.findViewById(R.id.gridServicios);
            ServicioAdapter servicioAdapter = new ServicioAdapter(root.getContext(), servicios);
            gridView.setAdapter(servicioAdapter);
        }catch (Exception e){
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.agregar:
                Intent intent = new Intent(getContext(), FormActivity.class);
                intent.putExtra("name","SERVICIOS");
                getActivity().startActivity(intent);
                //Toast.makeText(getContext(), "Hola Servicios", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
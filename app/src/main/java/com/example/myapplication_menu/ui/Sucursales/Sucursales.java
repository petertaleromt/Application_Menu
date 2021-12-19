package com.example.myapplication_menu.ui.Sucursales;

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
import com.example.myapplication_menu.databinding.FragmentSucursalesBinding;
import com.example.myapplication_menu.ui.Adaptadores.ServicioAdapter;
import com.example.myapplication_menu.ui.Adaptadores.SucursalAdapter;
import com.example.myapplication_menu.ui.Casos_Uso.CasoUsoServicios;
import com.example.myapplication_menu.ui.Casos_Uso.CasoUsoSucursales;
import com.example.myapplication_menu.ui.Datos.DBHelper;
import com.example.myapplication_menu.ui.Modelos.Servicio;
import com.example.myapplication_menu.ui.Modelos.Sucursal;
import com.example.myapplication_menu.ui.SucursalForm;

import java.util.ArrayList;

public class Sucursales extends Fragment {

    private GridView gridView;
    private DBHelper dbHelper;
    private CasoUsoSucursales casoUsoSucursal;
    private ArrayList<Sucursal> sucursales;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_sucursales, container, false);
        try{
            dbHelper = new DBHelper(getContext());
            casoUsoSucursal = new CasoUsoSucursales();
            Cursor cursor = dbHelper.getSucursales();
            sucursales = casoUsoSucursal.llenarListaSucursales(cursor);
            gridView = (GridView) root.findViewById(R.id.gridViewSucursales);
            SucursalAdapter sucursalAdapter = new SucursalAdapter(root.getContext(), sucursales);
            gridView.setAdapter(sucursalAdapter);

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
            case R.id.agregarsc:
                Intent intent = new Intent(getContext(), SucursalForm.class);
                intent.putExtra("name","SUCURSALES");
                getActivity().startActivity(intent);
                //Toast.makeText(getContext(), "Hola Sucursales", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
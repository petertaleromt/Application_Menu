package com.example.myapplication_menu.ui.Casos_Uso;

import android.database.Cursor;


import com.example.myapplication_menu.ui.Modelos.Sucursal;

import java.util.ArrayList;

public class CasoUsoSucursales {
    public ArrayList<Sucursal> llenarListaSucursales(Cursor cursor){
        ArrayList<Sucursal> list = new ArrayList<>();
        if(cursor.getCount() == 0){
            return list;
        }else{
            while (cursor.moveToNext()){
                Sucursal sucursal = new Sucursal(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getBlob(3)
                );
                list.add(sucursal);
            }
            return list;
        }
    }
}

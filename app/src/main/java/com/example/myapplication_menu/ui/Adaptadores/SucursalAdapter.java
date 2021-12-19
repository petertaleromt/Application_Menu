package com.example.myapplication_menu.ui.Adaptadores;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication_menu.R;
import com.example.myapplication_menu.ui.Modelos.Servicio;
import com.example.myapplication_menu.ui.Modelos.Sucursal;


import java.util.ArrayList;

public class SucursalAdapter extends BaseAdapter {
    Context context;
    ArrayList<Sucursal> sucursales;
    LayoutInflater inflater;

    public SucursalAdapter(Context context, ArrayList<Sucursal> sucursales) {
        this.context = context;
        this.sucursales = sucursales;
    }

    @Override
    public int getCount() {
        return sucursales.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView == null){
            convertView = inflater.inflate(R.layout.sucursal_item, null);
        }

        ImageView imageViewSC = convertView.findViewById(R.id.imgItemSC);
        TextView campoIdSC = convertView.findViewById(R.id.tvIdSC);
        TextView nameSC = convertView.findViewById(R.id.tvNameSC);
        TextView localizationSC = convertView.findViewById(R.id.tvLocalizationSC);

        Sucursal sucursal = sucursales.get(position);
        byte[] image = sucursal.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);

        imageViewSC.setImageBitmap(bitmap);
        campoIdSC.setText("ID :"+ sucursal.getId());
        nameSC.setText(sucursal.getName());
        localizationSC.setText(sucursal.getLocalization());

        return convertView;
    }
}

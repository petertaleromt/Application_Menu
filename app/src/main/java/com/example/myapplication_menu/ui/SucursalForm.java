package com.example.myapplication_menu.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.VolumeShaper;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication_menu.BuildConfig;
import com.example.myapplication_menu.FormActivity;
import com.example.myapplication_menu.R;
import com.example.myapplication_menu.ui.Datos.DBHelper;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.Projection;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class SucursalForm extends AppCompatActivity {

    private final int REQUEST_CODE_GALLERY = 999;
    private TextView tvlocalization;
    private EditText edtname;
    private Button btnChoose, btnInsertar;
    private ImageView imgSelected;

    private DBHelper dbHelper;
    private String name;
    private String localization;
    byte[] image;

    private MapView mapView;
    private MapController mapController;


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        int actionType = ev.getAction();
        switch (actionType){
            case MotionEvent.ACTION_UP:
                Projection pro = mapView.getProjection();
                GeoPoint loc = (GeoPoint) pro.fromPixels((int)ev.getX(), (int)ev.getY());
                String longitude = Double.toString(((double) loc.getLongitudeE6())/1000000);
                String latitude = Double.toString(((double) loc.getLatitudeE6())/1000000);

                tvlocalization.setText(latitude+","+longitude);
                Toast toast = Toast.makeText(getApplicationContext(), "Longitude: "+ longitude + "Latitude: "+latitude, Toast.LENGTH_SHORT);
                toast.show();
        }
        return super.dispatchTouchEvent(ev);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sucursal_form);

        btnInsertar = (Button) findViewById(R.id.btnInsertarSC);
        btnChoose = (Button) findViewById(R.id.btnChooseSC);
        edtname = (EditText) findViewById(R.id.editCampo1SC);
        tvlocalization = (TextView) findViewById(R.id.tvLocalization);
        imgSelected = (ImageView)  findViewById(R.id.imgSelectedSC);
        dbHelper = new DBHelper(getApplicationContext());

        mapView = (MapView) findViewById(R.id.openMap);
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);
        GeoPoint bogota = new GeoPoint(4.611250, -74.072480);
        GeoPoint bogota_norte = new GeoPoint(4.725640, -74.039695);
        GeoPoint medellin = new GeoPoint(6.241319, -75.567935);
        GeoPoint cartagena = new GeoPoint(10.443924, -75.521765);
        GeoPoint santa_marta = new GeoPoint(11.242006, -74.213039);

        mapView.setBuiltInZoomControls(true);

        mapController = (MapController) mapView.getController();
        mapController.setCenter(bogota);
        mapController.setZoom(6);

        mapView.setMultiTouchControls(true);

        final MyLocationNewOverlay myLocationNewOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(getApplicationContext()), mapView);
        mapView.getOverlays().add(myLocationNewOverlay);
        myLocationNewOverlay.enableMyLocation();

        myLocationNewOverlay.runOnFirstFix(new Runnable() {
            @Override
            public void run() {
                mapController.animateTo(myLocationNewOverlay.getMyLocation());

            }
        });

        ArrayList<OverlayItem> puntos = new ArrayList<OverlayItem>();
        puntos.add(new OverlayItem("Bogota", "Ciudad Capital (Centro)", bogota));
        puntos.add(new OverlayItem("Bogota", "Ciudad Capital (Cedritos)", bogota_norte));
        puntos.add(new OverlayItem("Medellin", "Las Palmas Medellin", medellin));
        puntos.add(new OverlayItem("Cartagena", "Cartagena Carrera 2da", cartagena));
        puntos.add(new OverlayItem("Santa Marta", "Parque de los novios", santa_marta));

        ItemizedIconOverlay.OnItemGestureListener<OverlayItem> tap = new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
            @Override
            public boolean onItemSingleTapUp(int index, OverlayItem item) {
                return false;
            }

            @Override
            public boolean onItemLongPress(int index, OverlayItem item) {
                return false;
            }
        };

        ItemizedOverlayWithFocus<OverlayItem> capa = new ItemizedOverlayWithFocus<OverlayItem>(getApplicationContext(), puntos, tap);
        capa.setFocusItemsOnTap(true);
        mapView.getOverlays().add(capa);



        btnInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llenarCampos();
                dbHelper.insertSucursal(name, localization, image);
                Toast.makeText(getApplicationContext(), "Insertar", Toast.LENGTH_SHORT).show();
                limpiarCampos();
            }
        });

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        SucursalForm.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });
    }

    public byte[] imageViewToByte(ImageView imageView){
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public void llenarCampos(){
        name = edtname.getText().toString().trim();
        localization = tvlocalization.getText().toString().trim();
        image = imageViewToByte(imgSelected);
    }

    public void limpiarCampos(){
        edtname.setText("");
        tvlocalization.setText("");
        imgSelected.setImageResource(R.mipmap.ic_launcher);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }else{
                Toast.makeText(getApplicationContext(), "Sin Permisos", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgSelected.setImageBitmap(bitmap);
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
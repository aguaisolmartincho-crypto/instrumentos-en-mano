package com.instrumentosenmano.app;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.*;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends Activity {

    LinearLayout layout;
    SharedPreferences datos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        datos = getSharedPreferences("instrumentos", MODE_PRIVATE);

        mostrarInicio();
    }

    private void mostrarInicio() {
        layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(32, 48, 32, 32);
        layout.setGravity(Gravity.CENTER_HORIZONTAL);

        TextView title = new TextView(this);
        title.setText("Instrumentos en Mano");
        title.setTextSize(28);
        title.setTextColor(Color.DKGRAY);
        title.setGravity(Gravity.CENTER);
        layout.addView(title);

        TextView subtitle = new TextView(this);
        subtitle.setText("Compra y venta de instrumentos musicales");
        subtitle.setTextSize(16);
        subtitle.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams sp =
                new LinearLayout.LayoutParams(-1, -2);
        sp.setMargins(0, 20, 0, 40);
        layout.addView(subtitle, sp);

        Button publicar = new Button(this);
        publicar.setText("Publicar instrumento");
        layout.addView(publicar);

        Button ver = new Button(this);
        ver.setText("Ver instrumentos");
        layout.addView(ver);

        publicar.setOnClickListener(v -> mostrarPublicar());
        ver.setOnClickListener(v -> mostrarInstrumentos());

        setContentView(layout);
    }

    private void mostrarInstrumentos() {
        layout.removeAllViews();

        TextView titulo = new TextView(this);
        titulo.setText("Instrumentos publicados");
        titulo.setTextSize(24);
        titulo.setGravity(Gravity.CENTER);
        layout.addView(titulo);

        try {
            String guardados = datos.getString("lista", "[]");
            JSONArray lista = new JSONArray(guardados);

            if (lista.length() == 0) {

                TextView vacio = new TextView(this);
                vacio.setText("Todavía no hay instrumentos publicados.");
                vacio.setTextSize(18);
                vacio.setGravity(Gravity.CENTER);
                vacio.setPadding(0, 40, 0, 40);
                layout.addView(vacio);

            } else {

                for (int i = 0; i < lista.length(); i++) {

                    JSONObject instrumento = lista.getJSONObject(i);

                    TextView item = new TextView(this);

                    String texto =
                            "Instrumento: " +
                            instrumento.getString("nombre") +
                            "\nPrecio: $" +
                            instrumento.getString("precio") +
                            "\nDescripción: " +
                            instrumento.getString("descripcion");

                    item.setText(texto);
                    item.setTextSize(18);
                    item.setPadding(20, 20, 20, 20);

                    layout.addView(item);
                }
            }

        } catch (Exception e) {
            Toast.makeText(
                    this,
                    "Error al cargar los instrumentos",
                    Toast.LENGTH_LONG
            ).show();
        }

        Button volver = new Button(this);
        volver.setText("Volver");
        layout.addView(volver);

        volver.setOnClickListener(v -> mostrarInicio());
    }

    private void mostrarPublicar() {

        layout.removeAllViews();

        TextView titulo = new TextView(this);
        titulo.setText("Publicar instrumento");
        titulo.setTextSize(24);
        titulo.setGravity(Gravity.CENTER);
        layout.addView(titulo);

        EditText nombre = new EditText(this);
        nombre.setHint("Nombre del instrumento");
        layout.addView(nombre);

        EditText precio = new EditText(this);
        precio.setHint("Precio");
        precio.setInputType(2);
        layout.addView(precio);

        EditText descripcion = new EditText(this);
        descripcion.setHint("Descripción");
        layout.addView(descripcion);

        Button publicar = new Button(this);
        publicar.setText("Publicar");
        layout.addView(publicar);

        Button volver = new Button(this);
        volver.setText("Volver");
        layout.addView(volver);

        publicar.setOnClickListener(v -> {

            String nombreTexto = nombre.getText().toString().trim();
            String precioTexto = precio.getText().toString().trim();
            String descripcionTexto = descripcion.getText().toString().trim();

            if (nombreTexto.isEmpty() ||
                precioTexto.isEmpty() ||
                descripcionTexto.isEmpty()) {

                Toast.makeText(
                        this,
                        "Completa todos los campos",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            try {

                String guardados =
                        datos.getString("lista", "[]");

                JSONArray lista =
                        new JSONArray(guardados);

                JSONObject nuevo =
                        new JSONObject();

                nuevo.put("nombre", nombreTexto);
                nuevo.put("precio", precioTexto);
                nuevo.put("descripcion", descripcionTexto);

                lista.put(nuevo);

                datos.edit()
                        .putString("lista", lista.toString())
                        .apply();

                Toast.makeText(
                        this,
                        "Instrumento guardado correctamente",
                        Toast.LENGTH_LONG
                ).show();

                mostrarInicio();

            } catch (Exception e) {

                Toast.makeText(
                        this,
                        "No se pudo guardar el instrumento",
                        Toast.LENGTH_LONG
                ).show();
            }
        });

        volver.setOnClickListener(v -> mostrarInicio());
    }
}


        
        

        

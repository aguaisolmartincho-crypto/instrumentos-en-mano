package com.instrumentosenmano.app;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.*;

public class MainActivity extends Activity {

    LinearLayout layout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        titulo.setText("Instrumentos disponibles");
        titulo.setTextSize(24);
        titulo.setGravity(Gravity.CENTER);
        layout.addView(titulo);

        TextView mensaje = new TextView(this);
        mensaje.setText("Todavía no hay instrumentos publicados.");
        mensaje.setTextSize(18);
        mensaje.setGravity(Gravity.CENTER);
        mensaje.setPadding(0, 40, 0, 40);
        layout.addView(mensaje);

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
            Toast.makeText(
                    this,
                    "Instrumento publicado",
                    Toast.LENGTH_LONG
            ).show();
        });

        volver.setOnClickListener(v -> mostrarInicio());
    }
}
        
        

        

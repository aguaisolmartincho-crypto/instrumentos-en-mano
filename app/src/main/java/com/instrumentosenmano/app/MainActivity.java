package com.instrumentosenmano.app;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.*;

public class MainActivity extends Activity {
    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(32, 48, 32, 32);
        layout.setGravity(Gravity.CENTER_HORIZONTAL);

        TextView title = new TextView(this);
        title.setText("Instrumentos en Mano");
        title.setTextSize(28);
        title.setTextColor(Color.DKGRAY);
        title.setGravity(Gravity.CENTER);
        layout.addView(title, new LinearLayout.LayoutParams(-1, -2));

        TextView subtitle = new TextView(this);
        subtitle.setText("Compra y venta de instrumentos musicales");
        subtitle.setTextSize(16);
        subtitle.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams sp = new LinearLayout.LayoutParams(-1, -2);
        sp.setMargins(0, 20, 0, 40);
        layout.addView(subtitle, sp);

        Button publish = new Button(this);
        publish.setText("Publicar instrumento");
        layout.addView(publish, new LinearLayout.LayoutParams(-1, -2));

        Button browse = new Button(this);
        browse.setText("Ver instrumentos");
        layout.addView(browse, new LinearLayout.LayoutParams(-1, -2));

        setContentView(layout);
    }
}

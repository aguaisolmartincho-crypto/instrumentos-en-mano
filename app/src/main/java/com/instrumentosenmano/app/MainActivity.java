lopackage com.instrumentosenmano.app;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.*;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends Activity {

    private static final int PICK_IMAGE = 1001;

    private SharedPreferences prefs;
    private Uri fotoSeleccionada = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = getSharedPreferences(
                "InstrumentosEnMano",
                MODE_PRIVATE
        );

        mostrarInicio();
    }

    private TextView titulo(String texto, float tamaño) {
        TextView t = new TextView(this);

        t.setText(texto);
        t.setTextSize(tamaño);
        t.setTextColor(Color.DKGRAY);
        t.setGravity(Gravity.CENTER);
        t.setPadding(0, 10, 0, 20);

        return t;
    }

    private LinearLayout base() {
        LinearLayout layout = new LinearLayout(this);

        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(32, 35, 32, 35);
        layout.setGravity(Gravity.CENTER_HORIZONTAL);

        return layout;
    }

    private Button boton(String texto) {
        Button b = new Button(this);

        b.setText(texto);
        b.setTextSize(16);
        b.setAllCaps(false);

        b.setLayoutParams(
                new LinearLayout.LayoutParams(-1, -2)
        );

        return b;
    }

    // =========================================================
    // INICIO
    // =========================================================

    private void mostrarInicio() {

        LinearLayout layout = base();

        layout.addView(
                titulo("Instrumentos en Mano", 28)
        );

        TextView subtitulo = new TextView(this);

        subtitulo.setText(
                "Compra y venta de instrumentos musicales"
        );

        subtitulo.setTextSize(16);
        subtitulo.setGravity(Gravity.CENTER);

        layout.addView(subtitulo);

        Space espacio = new Space(this);

        layout.addView(
                espacio,
                new LinearLayout.LayoutParams(1, 30)
        );

        Button publicar =
                boton("🎸 Publicar instrumento");

        Button ver =
                boton("🔎 Ver instrumentos");

        Button categorias =
                boton("🏷️ Categorías");

        layout.addView(publicar);
        layout.addView(ver);
        layout.addView(categorias);

        publicar.setOnClickListener(
                v -> mostrarPublicar()
        );

        ver.setOnClickListener(
                v -> mostrarInstrumentos("")
        );

        categorias.setOnClickListener(
                v -> mostrarInstrumentos("")
        );

        setContentView(layout);
    }

    // =========================================================
    // PUBLICAR
    // =========================================================

    private void mostrarPublicar() {

        LinearLayout layout = base();

        layout.addView(
                titulo("Publicar instrumento", 25)
        );

        EditText nombre = new EditText(this);

        nombre.setHint("Nombre del instrumento");

        layout.addView(nombre);

        // CATEGORÍA

        Spinner categoria = new Spinner(this);

        String[] categorias = {

                "Seleccionar categoría",

                "Guitarras",

                "Bajos",

                "Baterías",

                "Teclados",

                "Amplificadores",

                "Pedales y efectos",

                "Micrófonos",

                "Instrumentos de viento",

                "Instrumentos de cuerda",

                "Accesorios",

                "Otros"
        };

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_dropdown_item,
                        categorias
                );

        categoria.setAdapter(adapter);

        layout.addView(categoria);

        // PRECIO

        EditText precio = new EditText(this);

        precio.setHint("Precio");

        precio.setInputType(2);

        layout.addView(precio);

        // UBICACIÓN

        EditText ubicacion = new EditText(this);

        ubicacion.setHint(
                "Ubicación (ciudad/provincia)"
        );

        layout.addView(ubicacion);

        // CONTACTO

        EditText contacto = new EditText(this);

        contacto.setHint(
                "Teléfono o WhatsApp de contacto"
        );

        contacto.setInputType(2);

        layout.addView(contacto);

        // DESCRIPCIÓN

        EditText descripcion = new EditText(this);

        descripcion.setHint(
                "Descripción del instrumento"
        );

        descripcion.setMinLines(4);

        descripcion.setGravity(Gravity.TOP);

        layout.addView(descripcion);

        // FOTO

        Button foto =
                boton("📷 Agregar foto");

        TextView fotoNombre =
                new TextView(this);

        fotoNombre.setText(
                "No se seleccionó ninguna foto"
        );

        fotoNombre.setGravity(Gravity.CENTER);

        layout.addView(foto);

        layout.addView(fotoNombre);

        foto.setOnClickListener(v -> {

            Intent intent =
                    new Intent(Intent.ACTION_OPEN_DOCUMENT);

            intent.setType("image/*");

            intent.addCategory(
                    Intent.CATEGORY_OPENABLE
            );

            startActivityForResult(
                    intent,
                    PICK_IMAGE
            );
        });

        // PUBLICAR

        Button guardar =
                boton("✅ Publicar");

        Button volver =
                boton("⬅️ Volver");

        layout.addView(guardar);
        layout.addView(volver);

        guardar.setOnClickListener(v -> {

            String n =
                    nombre.getText()
                            .toString()
                            .trim();

            String p =
                    precio.getText()
                            .toString()
                            .trim();

            String c =
                    contacto.getText()
                            .toString()
                            .trim();

            if (n.isEmpty()) {

                nombre.setError(
                        "Escribe el nombre del instrumento"
                );

                return;
            }

            if (p.isEmpty()) {

                precio.setError(
                        "Escribe el precio"
                );

                return;
            }

            if (c.isEmpty()) {

                contacto.setError(
                        "Escribe un teléfono o WhatsApp"
                );

                return;
            }

            String cat =
                    categoria
                            .getSelectedItem()
                            .toString();

            if (cat.equals(
                    "Seleccionar categoría"
            )) {

                Toast.makeText(
                        this,
                        "Selecciona una categoría",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            try {

                JSONArray lista =
                        cargarLista();

                JSONObject instrumento =
                        new JSONObject();

                instrumento.put(
                        "nombre",
                        n
                );

                instrumento.put(
                        "categoria",
                        cat
                );

                instrumento.put(
                        "precio",
                        p
                );

                instrumento.put(
                        "ubicacion",
                        ubicacion
                                .getText()
                                .toString()
                                .trim()
                );

                instrumento.put(
                        "contacto",
                        c
                );

                instrumento.put(
                        "descripcion",
                        descripcion
                                .getText()
                                .toString()
                                .trim()
                );

                instrumento.put(
                        "foto",
                        fotoSeleccionada == null
                                ? ""
                                : fotoSeleccionada.toString()
                );

                lista.put(instrumento);

                guardarLista(lista);

                Toast.makeText(
                        this,
                        "Instrumento publicado",
                        Toast.LENGTH_SHORT
                ).show();

                fotoSeleccionada = null;

                mostrarInicio();

            } catch (Exception e) {

                Toast.makeText(
                        this,
                        "No se pudo guardar la publicación",
                        Toast.LENGTH_LONG
                ).show();
            }
        });

        volver.setOnClickListener(
                v -> mostrarInicio()
        );

        setContentView(layout);
    }

    // =========================================================
    // VER INSTRUMENTOS
    // =========================================================

    private void mostrarInstrumentos(
            String filtroInicial
    ) {

        LinearLayout layout = base();

        layout.addView(
                titulo(
                        "Instrumentos publicados",
                        25
                )
        );

        // BUSCADOR

        EditText buscar = new EditText(this);

        buscar.setHint(
                "🔎 Buscar por nombre, categoría o ubicación"
        );

        layout.addView(buscar);

        // FILTRO DE CATEGORÍA

        Spinner filtroCategoria =
                new Spinner(this);

        String[] categorias = {

                "Todas las categorías",

                "Guitarras",

                "Bajos",

                "Baterías",

                "Teclados",

                "Amplificadores",

                "Pedales y efectos",

                "Micrófonos",

                "Instrumentos de viento",

                "Instrumentos de cuerda",

                "Accesorios",

                "Otros"
        };

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_dropdown_item,
                        categorias
                );

        filtroCategoria.setAdapter(adapter);

        layout.addView(filtroCategoria);

        // LISTA

        LinearLayout listaVista =
                new LinearLayout(this);

        listaVista.setOrientation(
                LinearLayout.VERTICAL
        );

        layout.addView(
                listaVista,
                new LinearLayout.LayoutParams(
                        -1,
                        -2
                )
        );

        Button publicar =
                boton("➕ Publicar instrumento");

        Button volver =
                boton("⬅️ Volver");

        layout.addView(publicar);
        layout.addView(volver);

        Runnable actualizar = () -> {

            listaVista.removeAllViews();

            String texto =
                    buscar.getText()
                            .toString()
                            .trim()
                            .toLowerCase();

            String cat =
                    filtroCategoria
                            .getSelectedItem()
                            .toString();

            try {

                JSONArray lista =
                        cargarLista();

                int encontrados = 0;

                for (
                        int i = 0;
                        i < lista.length();
                        i++
                ) {

                    JSONObject obj =
                            lista.getJSONObject(i);

                    String nombre =
                            obj.optString(
                                    "nombre"
                            );

                    String categoria =
                            obj.optString(
                                    "categoria"
                            );

                    String precio =
                            obj.optString(
                                    "precio"
                            );

                    String ubicacion =
                            obj.optString(
                                    "ubicacion"
                            );

                    String contacto =
                            obj.optString(
                                    "contacto"
                            );

                    String descripcion =
                            obj.optString(
                                    "descripcion"
                            );

                    String foto =
                            obj.optString(
                                    "foto"
                            );

                    String todo =
                            (
                                    nombre +
                                    " " +
                                    categoria +
                                    " " +
                                    ubicacion +
                                    " " +
                                    descripcion
                            ).toLowerCase();

                    boolean coincideTexto =
                            texto.isEmpty()
                            ||
                            todo.contains(texto);

                    boolean coincideCategoria =
                            cat.equals(
                                    "Todas las categorías"
                            )
                            ||
                            categoria.equals(cat);

                    if (
                            !coincideTexto
                            ||
                            !coincideCategoria
                    ) {

                        continue;
                    }

                    encontrados++;

                    // TARJETA

                    LinearLayout tarjeta =
                            new LinearLayout(this);

                    tarjeta.setOrientation(
                            LinearLayout.VERTICAL
                    );

                    tarjeta.setPadding(
                            20,
                            20,
                            20,
                            20
                    );

                    TextView info =
                            new TextView(this);

                    info.setText(

                            "🎸 " +
                            nombre +

                            "\n🏷️ Categoría: " +
                            categoria +

                            "\n💰 Precio: $" +
                            precio +

                            "\n📍 Ubicación: " +
                            ubicacion +

                            "\n📝 " +
                            descripcion
                    );

                    info.setTextSize(17);

                    tarjeta.addView(info);

                    // FOTO

                    if (!foto.isEmpty()) {

                        try {

                            ImageView imagen =
                                    new ImageView(this);

                            imagen.setImageURI(
                                    Uri.parse(foto)
                            );

                            imagen.setAdjustViewBounds(
                                    true
                            );

                            imagen.setLayoutParams(
                                    new LinearLayout.LayoutParams(
                                            -1,
                                            400
                                    )
                            );

                            tarjeta.addView(imagen);

                        } catch (Exception ignored) {
                        }
                    }

                    // CONTACTAR

                    Button contactar =
                            boton(
                                    "💬 Contactar vendedor"
                            );

                    tarjeta.addView(contactar);

                    contactar.setOnClickListener(
                            v -> {

                                try {

                                    Intent intent =
                                            new Intent(
                                                    Intent.ACTION_SENDTO
                                            );

                                    intent.setData(
                                            Uri.parse(
                                                    "smsto:" +
                                                    contacto
                                            )
                                    );

                                    intent.putExtra(
                                            "sms_body",
                                            "Hola, vi tu publicación en Instrumentos en Mano: "
                                                    + nombre
                                    );

                                    startActivity(intent);

                                } catch (Exception e) {

                                    Toast.makeText(
                                            this,
                                            "Contacto: " +
                                                    contacto,
                                            Toast.LENGTH_LONG
                                    ).show();
                                }
                            }
                    );

                    listaVista.addView(tarjeta);

                    // SEPARADOR

                    View separador =
                            new View(this);

                    separador.setBackgroundColor(
                            Color.LTGRAY
                    );

                    listaVista.addView(
                            separador,
                            new LinearLayout.LayoutParams(
                                    -1,
                                    2
                            )
                    );
                }

                if (encontrados == 0) {

                    TextView vacio =
                            new TextView(this);

                    vacio.setText(
                            "No hay instrumentos que coincidan con la búsqueda."
                    );

                    vacio.setTextSize(17);

                    vacio.setGravity(
                            Gravity.CENTER
                    );

                    vacio.setPadding(
                            10,
                            30,
                            10,
                            30
                    );

                    listaVista.addView(vacio);
                }

            } catch (Exception e) {

                TextView error =
                        new TextView(this);

                error.setText(
                        "No se pudieron cargar las publicaciones."
                );

                listaVista.addView(error);
            }
              }
    }
}
}

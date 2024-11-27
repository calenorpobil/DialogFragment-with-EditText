package com.merlita.listadelacompra_13;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, FragmentoPersonalizado.MensajeItem,
        Fragmento2Botones.BorrarItem, Fragmento2Botones.ModificarItem {

    String[] elementosIniciales ={"Pan", "Lechuga", "Arroz",
            "Vino", "Uvas", "Leche", "Harina", "Azúcar", "Coca cola",
            "Infusiones", "Platos"};
    //Parejas de cada número con un valor "activado" o "desactivado":
    HashMap<Integer, Boolean> elementosAsignados = new HashMap<>();
    //Nombres de los elementos:
    ArrayList<String> elementos = new ArrayList<>(); int numElementos=0;
    //Grilla para almacenar la lista de textos:
    GridLayout gridLayout;
    LinearLayout linearLayout;

    TextView titulo;
    Button nuevoItem;
    Point p;
    int anchoPantalla, altoPantalla;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        p = new Point();
        Display pantallaDisplay = getWindowManager().getDefaultDisplay();
        pantallaDisplay.getSize(p);
        anchoPantalla = p.x;
        altoPantalla = p.y;

        titulo = findViewById(R.id.tvTitulo);
        nuevoItem = findViewById(R.id.button);
        linearLayout = findViewById(R.id.linear_en_scroll);

        titulo.setMaxWidth(anchoPantalla);

        hacerListaInicial(elementosIniciales);


        anyadirBotones();



    }

    private void hacerListaInicial(String[] elementosIniciales) {
        for(int i=0;i<elementosIniciales.length;i++){
            elementosAsignados.put(i, false);
            elementos.add(elementosIniciales[i]);
            numElementos++;
        }
    }


    private void anyadirBotones() {
        int tamanyo = elementos.size();
        numElementos=1;
        limpiarBotones();
        for (int i = 0; i< tamanyo; i++)
        {
            //Cada elemento tiene su textview:
            TextView b = new TextView(this);
            ViewGroup.LayoutParams lpView = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            ViewGroup.LayoutParams lp =
                    new ViewGroup.LayoutParams(
                            anchoPantalla, altoPantalla/17);
            //zb.setLayoutParams(lp);
            b.setText(elementos.get(i));
            if(Boolean.TRUE.equals(elementosAsignados.get(i))){
                b.setTextColor(Color.parseColor("Red"));
            }else{
                b.setTextColor(Color.parseColor("Green"));
            }
            b.setTextSize(40);

            b.setId(i);
            b.setOnClickListener(this);


            b.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View v){
                    Fragmento2Botones f2b = new Fragmento2Botones();
                    Bundle bundle = new Bundle();
                    bundle.putString("nombre", b.getText().toString());
                    bundle.putInt("id", b.getId());
                    f2b.setArguments(bundle);
                    f2b.show(getSupportFragmentManager(),"yyy");
                    return false;

                }
            });


            //gridLayout.setColumnCount(1);
            //gridLayout.addView(b);
            linearLayout.addView(b, i, lpView);
            numElementos++;
        }
    }

    private void limpiarBotones(){
        //gridLayout.removeAllViewsInLayout();
        linearLayout.removeAllViewsInLayout();
    }


    @Override
    public void onClick(View v) {
        TextView b = (TextView)v;
        int num = v.getId();
        if(Boolean.TRUE.equals(elementosAsignados.get(num))){
            elementosAsignados.put(num, false);
            b.setTextColor(Color.parseColor("green"));
        } else {
            elementosAsignados.put(num, true);
            b.setTextColor(Color.parseColor("red"));
        }

    }

    public void clickNuevoItem(View view) {
        FragmentoPersonalizado f2b = new FragmentoPersonalizado();
        Bundle bundle = new Bundle();
        bundle.putString("mensajeInput", "Introduce el nombre de tu nuevo item: ");
        f2b.show(getSupportFragmentManager(),"xxx");
    }

    @Override
    public void mensajeItem(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
        elementos.add(mensaje);
        elementosAsignados.put(numElementos, false);
        anyadirBotones();
    }
    @Override
    public void borrarItem(int i) {
        String nombre = elementos.get(i);
        Toast.makeText(this, nombre+" ha sido borrado",
                Toast.LENGTH_SHORT).show();
        elementos.remove(i);
        elementosAsignados.remove(i);
        anyadirBotones();
    }

    @Override
    public void modificarItem(int id, String nombre) {
        String mensajeInput = "Escribe aqui el nuevo nombre para "+nombre;
        FragmentoPersonalizado f2b = new FragmentoPersonalizado();
        Bundle bundle = new Bundle();
        bundle.putString("nombre", nombre);
        bundle.putString("mensajeInput", mensajeInput);
        bundle.putInt("id", id);
        f2b.setArguments(bundle);
        f2b.show(getSupportFragmentManager(),"yyy");

    }
}
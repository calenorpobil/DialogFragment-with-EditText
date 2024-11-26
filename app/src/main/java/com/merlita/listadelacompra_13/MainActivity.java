package com.merlita.listadelacompra_13;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, Fragmento2Botones.MensajeItem{

    String[] elementosIniciales ={"Pan", "Lechuga", "Arroz",
            "Vino", "Uvas", "Leche"};
    //Parejas de cada número con un valor "activado" o "desactivado":
    HashMap<Integer, Boolean> elementosAsignados = new HashMap<>();
    //Nombres de los elementos:
    ArrayList<String> elementos = new ArrayList<>(); int numElementos=0;
    //Grilla para almacenar la lista de textos:
    GridLayout gridLayout;

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
        gridLayout = findViewById(R.id.gridLayout);
        nuevoItem = findViewById(R.id.button);

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
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            ViewGroup.LayoutParams lp =
                    new ViewGroup.LayoutParams(
                            anchoPantalla, altoPantalla/17);
            b.setLayoutParams(lp);
            b.setText(elementos.get(i));
            if(Boolean.TRUE.equals(elementosAsignados.get(i))){
                b.setTextColor(Color.parseColor("Red"));
            }else{
                b.setTextColor(Color.parseColor("Green"));
            }
            b.setTextSize(20);

            b.setId(i);
            b.setOnClickListener(this);

            gridLayout.setColumnCount(1);
            gridLayout.addView(b);
            numElementos++;
        }
    }

    private void limpiarBotones(){
        gridLayout.removeAllViewsInLayout();
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

    public void clickDialogoMensaje(View view) {
        Fragmento2Botones f2b = new Fragmento2Botones();
        f2b.show(getSupportFragmentManager(),"xxx");
    }

    @Override
    public void mensajeItem(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
        elementos.add(mensaje);
        elementosAsignados.put(numElementos, false);
        anyadirBotones();
    }
}
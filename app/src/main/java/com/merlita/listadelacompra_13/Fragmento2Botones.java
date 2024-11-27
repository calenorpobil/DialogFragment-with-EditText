package com.merlita.listadelacompra_13;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

public class Fragmento2Botones extends DialogFragment {

    BorrarItem borrarItem;
    ModificarItem modificarItem;
    Button btBorrar, btModificar;

    @Override
    //Envia los datos a la principal
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        borrarItem = (BorrarItem) getActivity();
        modificarItem = (ModificarItem) getActivity();
    }

    @NonNull
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder ventana = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialogo_2_botones,null);
        dialogView.setBackground(new ColorDrawable(Color.TRANSPARENT));
        btBorrar = dialogView.findViewById(R.id.botonBorrar);
        btModificar = dialogView.findViewById(R.id.botonModificar);

        Bundle bundle = getArguments();
        assert bundle != null;
        int id = bundle.getInt("id");

        String nombreLista = bundle.getString("nombre");
        String txtBotones="Borrar "+nombreLista;
        btBorrar.setText(txtBotones);
        txtBotones = "Modificar "+nombreLista;
        btModificar.setText(txtBotones);

        ventana.setView(dialogView);

        btBorrar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                borrarItem.borrarItem(id);
                Objects.requireNonNull(Fragmento2Botones.this.getDialog()).cancel();

            }
        });
        btModificar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                modificarItem.modificarItem(id, nombreLista);
            }
        });

        return  ventana.create();
    }

    //ENVIAR AL MAINACTIVITY:
    public interface BorrarItem
    {
        void borrarItem(int i);
    }
    //ENVIAR AL MAINACTIVITY:
    public interface ModificarItem
    {
        void modificarItem(int id, String nombre);
    }
}

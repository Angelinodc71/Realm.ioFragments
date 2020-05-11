package com.example.realmiofragments.view;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.realmiofragments.R;
import com.example.realmiofragments.model.Producto;
import com.example.realmiofragments.viewmodel.ProductViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateProductFragment extends Fragment {
    private ProductViewModel productoViewModel;
    private NavController navController;

    private EditText tiendaEditText, productoEditText;
    private Button crearProducto;
    private TextView tituloEditText;
    private Producto productoSelecionado;


    public CreateProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_product, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        productoViewModel = ViewModelProviders.of(requireActivity()).get(ProductViewModel.class);


        tiendaEditText = view.findViewById(R.id.tiendaEditText);
        productoEditText = view.findViewById(R.id.productoEditText);
        tituloEditText = view.findViewById(R.id.crearProductoTextView);
        crearProducto = view.findViewById(R.id.btn_crear);

        if (productoViewModel.edit) {
            productoSelecionado = productoViewModel.getIdProducto(productoViewModel.userToEditId);
            tiendaEditText.setText(productoSelecionado.getTienda());
            productoEditText.setText(productoSelecionado.getNombre());
            crearProducto.setText("GUARDAR");
            tituloEditText.setText("EDITAR PRODUCTO");
        } else {
            crearProducto.setText("CREAR PRODUCTO");
            tituloEditText.setText("CREAR PRODUCTO");
        }

        crearProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tiendaEditText.getText().toString().isEmpty()){
                    tiendaEditText.setError("Introduzca una Tienda");
                    return;
                }
                if(productoEditText.getText().toString().isEmpty()){
                    productoEditText.setError("Introduzca un Producto");
                    return;
                }
                if (productoViewModel.edit) {
                    Producto producto = new Producto();

                    producto.setTienda(tiendaEditText.getText().toString());
                    producto.setNombre(productoEditText.getText().toString());

                    productoViewModel.updateProduct(productoViewModel.userToEditId, producto);
                    productoSelecionado = null;
                    productoViewModel.edit = false;
                    productoViewModel.userToEditId = 0;
                }

                else productoViewModel.insertProduct(new Producto(tiendaEditText.getText().toString(), productoEditText.getText().toString()));
                navController.popBackStack();
            }
        });
    }
}

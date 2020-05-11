package com.example.realmiofragments.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realmiofragments.R;
import com.example.realmiofragments.model.Producto;
import com.example.realmiofragments.viewmodel.ProductViewModel;

import io.realm.RealmResults;

public class HomeFragment extends Fragment {
    private NavController navController;
    ProductViewModel productoViewModel;
    private ProductosAdapter productosAdapter;
    private SearchView searchView;
    private RecyclerView recyclerView;
    Toolbar toolbar;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        navController = Navigation.findNavController(view);
        productoViewModel = ViewModelProviders.of(requireActivity()).get(ProductViewModel.class);


        view.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.createProductFragment);
            }
        });

        recyclerView = view.findViewById(R.id.recycler_listaProductos);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        productosAdapter = new ProductosAdapter();

        productosAdapter.productList(productoViewModel.getProductDetail());
        recyclerView.setAdapter(productosAdapter);

        searchView = view.findViewById(R.id.search_view);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                RealmResults<Producto> notice = query.equals("") ? productoViewModel.getProductDetail() : productoViewModel.getShop(query);
                productosAdapter.productList(notice);
                recyclerView.setAdapter(productosAdapter);
                return true;
            }
        });
    }

    class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.ProductoViewHolder> {

        RealmResults<Producto> productoDetalle;
        @NonNull
        @Override
        public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ProductoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_product, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final ProductoViewHolder holder, int position) {
            final Producto producto = productoDetalle.get(position);
            Log.i("Logger", String.valueOf(producto.getId()));
            holder.tiendaTextView.setText(producto.getTienda());
            holder.productoTextView.setText(producto.getNombre());
            holder.checkBox.setChecked(producto.isMake());
            holder.checkBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    holder.checkBox.setChecked(isChecked);
                    productoViewModel.deleteProduct(producto.getId());
                    navController.navigate(R.id.nav_home);
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    productoViewModel.editarTarea(producto.getId());
                    navController.navigate(R.id.createProductFragment);
                }
            });
        }

        @Override
        public int getItemCount() {
            return productoDetalle != null ? productoDetalle.size() : 0;
        }

        void productList(RealmResults<Producto> list) {
            productoDetalle = list;
            notifyDataSetChanged();
        }

        class ProductoViewHolder extends RecyclerView.ViewHolder {
            TextView tiendaTextView, productoTextView;
            CheckBox checkBox;
            ProductoViewHolder(@NonNull View itemView) {
                super(itemView);
                tiendaTextView = itemView.findViewById(R.id.textViewTienda);
                productoTextView = itemView.findViewById(R.id.textViewProducto);
                checkBox = itemView.findViewById(R.id.checkBox);
            }
        }
    }
}
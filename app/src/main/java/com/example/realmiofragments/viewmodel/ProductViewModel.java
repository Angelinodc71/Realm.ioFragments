package com.example.realmiofragments.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.realmiofragments.model.Producto;

import io.realm.Realm;
import io.realm.RealmResults;

public class ProductViewModel extends AndroidViewModel {


    private Realm realm;
    public boolean edit;
    public int userToEditId;

    public ProductViewModel(@NonNull Application application) {
        super(application);
        realm = Realm.getDefaultInstance();
    }

    public RealmResults<Producto> getProductDetail(){
        return realm.where(Producto.class).findAll();
    }

    public RealmResults<Producto> getShop(String busqueda){
        return realm.where(Producto.class).contains("tienda", busqueda).findAll();
    }

    public Producto getIdProducto(int id){
        return realm.where(Producto.class).equalTo("id", id).findAll().first();
    }

    public void insertProduct(final Producto producto){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Number maxId = getProductDetail().max("id");

                int nextId = (maxId == null) ? 1 : maxId.intValue() + 1;
                Producto noticeTmp = realm.createObject(Producto.class, nextId);

                noticeTmp.setTienda(producto.getTienda());
                noticeTmp.setNombre(producto.getNombre());
                realm.insertOrUpdate(noticeTmp);
            }
        });
    }

    public void updateProduct(final int id, final Producto producto){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Producto tareaAEditar = getIdProducto(id);

                tareaAEditar.setTienda(producto.getTienda());
                tareaAEditar.setNombre(producto.getNombre());
            }
        });
    }

    public void deleteProduct(final int id){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Producto> result = realm.where(Producto.class).equalTo("id",id).findAll();
                result.deleteAllFromRealm();

            }
        });
    }

    public void editarTarea(int editId) {
        edit = true;
        this.userToEditId = editId;
    }
}

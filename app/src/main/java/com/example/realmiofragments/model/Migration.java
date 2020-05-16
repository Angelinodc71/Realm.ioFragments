package com.example.realmiofragments.model;

import android.util.Log;

import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.FieldAttribute;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

public class Migration implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        RealmSchema schema = realm.getSchema();
        if (oldVersion == 0) {
            RealmObjectSchema personSchema = schema.get("Producto");

            // Combine 'firstName' and 'lastName' in a new field called 'fullName'
            personSchema
                    .addField("all", String.class, FieldAttribute.REQUIRED)
                    .transform(new RealmObjectSchema.Function() {
                        @Override
                        public void apply(DynamicRealmObject obj) {
                            obj.set("all", obj.getString("tienda") + " " + obj.getString("nombre"));
                        }
                    })
                    .removeField("tienda")
                    .removeField("nombre");
            oldVersion++;
        }
    }
}

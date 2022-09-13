package com.douglasluz.listadecompras.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class ProductsRepository(context: Context) : ManagedSQLiteOpenHelper(ctx = context, name = "ProductList.db", version = 1) {
    companion object {
        private var instance: ProductsRepository? = null

        @Synchronized
        fun getInstance(ctx: Context): ProductsRepository {
            if (instance == null) {
                instance = ProductsRepository(ctx.applicationContext)
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable("products", true,
            "id" to INTEGER + PRIMARY_KEY + UNIQUE,
                "name" to TEXT,
                "quantity" to INTEGER,
                "price" to REAL,
                "image" to BLOB
            )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}

val Context.database: ProductsRepository get() = ProductsRepository.getInstance(applicationContext)
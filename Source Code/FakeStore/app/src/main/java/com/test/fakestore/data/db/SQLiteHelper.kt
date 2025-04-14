package com.fakestore.data.local.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.fakestore.data.local.model.CartItem

class SQLiteHelper(context: Context) :
    SQLiteOpenHelper(context, "cart.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        val query = """
            CREATE TABLE cart (
                id INTEGER PRIMARY KEY,
                title TEXT,
                price REAL,
                image TEXT,
                quantity INTEGER
            )
        """.trimIndent()
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS cart")
        onCreate(db)
    }

    fun insertItem(item: CartItem) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("id", item.id)
            put("title", item.title)
            put("price", item.price)
            put("image", item.image)
            put("quantity", item.quantity)
        }
        db.insert("cart", null, values)
    }

    fun getAllItems(): List<CartItem> {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM cart", null)
        val items = mutableListOf<CartItem>()

        if (cursor.moveToFirst()) {
            do {
                val item = CartItem(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    title = cursor.getString(cursor.getColumnIndexOrThrow("title")),
                    price = cursor.getDouble(cursor.getColumnIndexOrThrow("price")),
                    image = cursor.getString(cursor.getColumnIndexOrThrow("image")),
                    quantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"))
                )
                items.add(item)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return items
    }

    fun updateItemQuantity(id: Int, quantity: Int) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("quantity", quantity)
        }
        db.update("cart", values, "id = ?", arrayOf(id.toString()))
    }

    fun deleteItem(id: Int) {
        val db = writableDatabase
        db.delete("cart", "id = ?", arrayOf(id.toString()))
    }

    fun clearCart() {
        val db = writableDatabase
        db.delete("cart", null, null)
    }

    fun getTotalPrice(): Double {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT SUM(price * quantity) FROM cart", null)
        var total = 0.0
        if (cursor.moveToFirst()) {
            total = cursor.getDouble(0)
        }
        cursor.close()
        return total
    }
}

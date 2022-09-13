package com.douglasluz.listadecompras

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import com.douglasluz.listadecompras.models.Product
import com.douglasluz.listadecompras.repository.database
import com.douglasluz.listadecompras.repository.productsRepository
import com.douglasluz.listadecompras.util.ProductAdapter
import com.douglasluz.listadecompras.util.toBitmap
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.parseList
import org.jetbrains.anko.db.rowParser
import org.jetbrains.anko.db.select
import java.text.NumberFormat
import java.util.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val productsAdapter = ProductAdapter(this)
        setContentView(R.layout.activity_main)


        productsAdapter.addAll(productsRepository)
        items_list.adapter = productsAdapter

        items_list.setOnItemLongClickListener { parent, view, position, id ->
            val item = productsAdapter.getItem(position)
            productsAdapter.remove(item)
            if (item != null) {
                deleteProduct(item.id)
            }
            toast("Item successfully removed ")
            true
        }

        add_button.setOnClickListener {
            startActivity<ProductRegisterActivity>()
        }
    }

    override fun onResume() {
        super.onResume()
        val productsAdapter = items_list.adapter as ProductAdapter
        database.use {
            select("products").exec {
                val parser = rowParser {
                    id: Int, name: String, quantity: Int, price: Double, photo: ByteArray? ->
                    Product(id, name, quantity, price, photo?.toBitmap())
                }
                var productList = parseList(parser)
                productsAdapter.clear()
                productsAdapter.addAll(productList)
                items_list.adapter = productsAdapter

                val sum = productList.sumByDouble { it.price * it.quantity }
                val format = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
                total_text.text = "TOTAL: ${ format.format(sum) }"
            }
        }
    }
    private fun deleteProduct(productId: Int) {
        database.use {
            delete("products", "id = $productId")
        }
    }
}
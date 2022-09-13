package com.douglasluz.listadecompras.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.douglasluz.listadecompras.R
import com.douglasluz.listadecompras.models.Product
import java.text.NumberFormat
import java.util.*

class ProductAdapter(context: Context): ArrayAdapter<Product>(context, 0) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_view_item, parent, false)
        val numberFormat = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
        val item = getItem(position)

        val productName = view.findViewById<TextView>(R.id.text_item_name)
        val productQuantity = view.findViewById<TextView>(R.id.text_item_quantity)
        val productPrice = view.findViewById<TextView>(R.id.text_item_value)
        val productPhoto = view.findViewById<ImageView>(R.id.list_product_image)

        productName.text = item?.name
        if (item != null) {
            productQuantity.text = item.quantity.toString()
        }
        productPrice.text = numberFormat.format(item?.price)
        productPhoto.setImageBitmap(item?.image)

        return view
    }
}
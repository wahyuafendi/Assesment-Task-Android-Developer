package com.test.fakestore.ui.detail

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.fakestore.data.local.db.SQLiteHelper
import com.fakestore.data.local.model.CartItem
import com.fakestore.data.local.repository.CartRepository
import com.test.fakestore.R
import com.test.fakestore.data.model.Product
import com.test.fakestore.databinding.ActivityProductDetailBinding
import com.test.fakestore.ui.cart.CartViewModel
import com.test.fakestore.ui.cart.CartViewModelFactory

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailBinding
    private lateinit var cartViewModel: CartViewModel
    private var product: Product? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        product = intent.getSerializableExtra("product") as? Product
        product?.let { showProductDetails(it) }

        val db = SQLiteHelper(this)
        val repository = CartRepository(db)
        cartViewModel = ViewModelProvider(this, CartViewModelFactory(repository))[CartViewModel::class.java]

        binding.btnAddToCart.setOnClickListener {
            val item = CartItem(
                id = product?.id ?: 0,
                title = product?.title ?: "",
                price = product?.price ?: 0.0,
                image = product?.image ?: "",
                quantity = 1
            )
            cartViewModel.addItem(item)
            Toast.makeText(this, "Berhasil ditambahkan ke keranjang!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showProductDetails(product: Product) {
        binding.tvProductTitle.text = product.title
        binding.tvProductPrice.text = "$${product.price}"
        binding.tvProductDescription.text = product.description

        Glide.with(this)
            .load(product.image)
            .placeholder(R.drawable.iconlock)
            .into(binding.ivProductImage)
    }
}

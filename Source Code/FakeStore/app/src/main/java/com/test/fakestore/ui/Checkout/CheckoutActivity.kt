package com.test.fakestore.ui.checkout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.test.fakestore.databinding.ActivityCheckoutBinding
import com.fakestore.data.local.model.CartItem

class CheckoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val items = intent.getSerializableExtra("cart_items") as? ArrayList<CartItem>
        val total = intent.getDoubleExtra("total_price", 0.0)

        if (items != null) {
            val itemSummary = items.joinToString("\n") {
                "- ${it.title} (${it.quantity}x)"
            }

            binding.txtOrderSummary.text = itemSummary
            binding.txtOrderTotal.text = "Total Harga: Rp $total"
        }

        binding.btnFinish.setOnClickListener {
            setResult(RESULT_OK)
            finish()
        }
    }
}

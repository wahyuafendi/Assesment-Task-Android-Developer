package com.test.fakestore.ui.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fakestore.data.local.db.SQLiteHelper
import com.fakestore.data.local.model.CartItem
import com.fakestore.data.local.repository.CartRepository
import com.test.fakestore.databinding.FragmentCartBinding
import com.test.fakestore.ui.checkout.CheckoutActivity

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var cartAdapter: CartAdapter
    private lateinit var cartViewModel: CartViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val db = SQLiteHelper(requireContext())
        val repository = CartRepository(db)
        cartViewModel = ViewModelProvider(this, CartViewModelFactory(repository))[CartViewModel::class.java]

        cartAdapter = CartAdapter(
            listOf(),
            onDeleteClick = { item -> cartViewModel.deleteItem(item.id) },
            onQuantityChange = { item, qty -> cartViewModel.updateQuantity(item.id, qty) }
        )

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cartAdapter
        }

        cartViewModel.cartItems.observe(viewLifecycleOwner) {
            cartAdapter.updateData(it)
        }

        cartViewModel.totalPrice.observe(viewLifecycleOwner) {
            binding.txtTotal.text = "Total: Rp $it"
        }

        cartViewModel.loadCartItems()

        binding.btnClear.setOnClickListener {
            cartViewModel.clearCart()
        }

        binding.btnCheckout.setOnClickListener {
            val items = cartViewModel.cartItems.value.orEmpty()
            if (items.isEmpty()) {
                Toast.makeText(requireContext(), "Keranjang kosong!", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(requireContext(), CheckoutActivity::class.java)
                intent.putExtra("cart_items", ArrayList(items))
                intent.putExtra("total_price", cartViewModel.totalPrice.value ?: 0.0)
                startActivity(intent)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

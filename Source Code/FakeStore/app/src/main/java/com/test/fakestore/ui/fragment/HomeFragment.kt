package com.test.fakestore.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.fakestore.databinding.FragmentHomeBinding
import com.test.fakestore.ui.adapter.CategoryAdapter
import com.test.fakestore.ui.adapter.ProductAdapter
import com.test.fakestore.ui.detail.ProductDetailActivity
import com.test.fakestore.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DashboardViewModel by viewModels()
    private lateinit var productAdapter: ProductAdapter
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerViews()
        observeViewModel()

        // Show loading animation
        showLoading(true)

        // Delay to simulate loading effect
        Handler(Looper.getMainLooper()).postDelayed({
            showLoading(false)
        }, 2000)

        // ❌ Jangan panggil getAllProducts()
        // ✅ Langsung ambil kategori, lalu auto-load produk dari kategori pertama
        viewModel.getAllCategories()
    }

    private fun setupRecyclerViews() {
        productAdapter = ProductAdapter(requireContext(), emptyList()) { product ->
            val intent = Intent(requireContext(), ProductDetailActivity::class.java)
            intent.putExtra("product", product)
            startActivity(intent)
        }

        categoryAdapter = CategoryAdapter { category ->
            showLoading(true)
            viewModel.getProductsByCategory(category)
        }

        binding.rvProducts.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = productAdapter
        }

        binding.rvCategories.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.products.observe(viewLifecycleOwner) { products ->
            productAdapter.updateData(products)
            showLoading(false)
        }

        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            categoryAdapter.updateCategories(categories)

            // ✅ Panggil kategori pertama langsung setelah categories selesai di-load
            if (categories.isNotEmpty()) {
                val firstCategory = categories[0]
                viewModel.getProductsByCategory(firstCategory)
            }
        }
    }

    private fun showLoading(show: Boolean) {
        binding.lottieLoading.visibility = if (show) View.VISIBLE else View.GONE
        binding.rvProducts.visibility = if (show) View.GONE else View.VISIBLE
        binding.rvCategories.visibility = if (show) View.GONE else View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

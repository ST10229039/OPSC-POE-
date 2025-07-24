package com.example.thabelop3.UI

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thabelop3.Activities.AddExpenseActivity
import com.example.thabelop3.Activities.ExpenseListActivity
import com.example.thabelop3.Activities.HomeActivity
import com.example.thabelop3.Activities.LoginActivity
import com.example.thabelop3.Activities.ProfileActivity
import com.example.thabelop3.Activities.ReportActivity
import com.example.thabelop3.Adapter.CategoryAdapter
import com.example.thabelop3.MainActivity
import com.example.thabelop3.Models.CategoryViewModel
import com.example.thabelop3.R
import com.example.thabelop3.RoomDb.Category
import com.example.thabelop3.databinding.FragmentCategoriesBinding

/**
 * Fragment for managing expense categories
 * Allows adding, viewing and deleting categories
 */
class CategoriesFragment : Fragment() {
    // View binding variables
    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!
    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var adapter: CategoryAdapter
    private var userId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up toolbar navigation
        binding.toolbar.setNavigationOnClickListener {
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }

        // Set up navigation drawer
        setupNavigation()

        userId = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            .getInt("user_id", 0)

        categoryViewModel = ViewModelProvider(this)[CategoryViewModel::class.java]

        adapter = CategoryAdapter { category ->
            showDeleteDialog(category)
        }

        binding.rvCategories.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCategories.adapter = adapter

        binding.btnAddCategory.setOnClickListener {
            showAddCategoryDialog()
        }

        binding.fabAddCategory.setOnClickListener {
            showAddCategoryDialog()
        }

        loadCategories()
    }

    private fun setupNavigation() {
        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(requireContext(), HomeActivity::class.java))
                }
                R.id.nav_expenses -> {
                    startActivity(Intent(requireContext(), AddExpenseActivity::class.java))
                }
                R.id.nav_list -> {
                    startActivity(Intent(requireContext(), ExpenseListActivity::class.java))
                }
                R.id.nav_categories -> {
                    // Already in categories fragment
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.nav_budget -> {
                    startActivity(Intent(requireContext(), MainActivity::class.java).apply {
                        putExtra("open_fragment", "budget")
                    })
                }
                R.id.nav_graph -> {
                    startActivity(Intent(requireContext(), MainActivity::class.java).apply {
                        putExtra("open_fragment", "graph")
                    })
                }
                R.id.nav_achievements -> {
                    startActivity(Intent(requireContext(), MainActivity::class.java).apply {
                        putExtra("open_fragment", "achievements")
                    })
                }
                R.id.nav_report -> {
                    startActivity(Intent(requireContext(), ReportActivity::class.java))
                }
                R.id.nav_logout -> {
                    requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE).edit().clear().apply()
                    startActivity(Intent(requireContext(), LoginActivity::class.java))
                    requireActivity().finish()
                }
                R.id.nav_profile -> {
                    startActivity(Intent(requireContext(), ProfileActivity::class.java))
                }
            }
            true
        }
    }


    // Load categories
    private fun loadCategories() {
        categoryViewModel.getCategoriesByUser(userId).observe(viewLifecycleOwner) { categories ->
            adapter.submitList(categories)
        }
    }
    /**
     * Loads categories for the current user
     */
    private fun showAddCategoryDialog() {
        val editText = EditText(requireContext())
        editText.hint = "Category name"

        android.app.AlertDialog.Builder(requireContext())
            .setTitle("Add New Category")
            .setView(editText)
            .setPositiveButton("Add") { _, _ ->
                val name = editText.text.toString()
                if (name.isNotEmpty()) {
                    val category = Category(name = name, userId = userId)
                    categoryViewModel.insert(category)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    /**
     * Shows confirmation dialog before deleting a category
     * @param category The category to be deleted
     */
    private fun showDeleteDialog(category: Category) {
        android.app.AlertDialog.Builder(requireContext())
            .setTitle("Delete Category")
            .setMessage("Are you sure you want to delete ${category.name}?")
            .setPositiveButton("Delete") { _, _ ->
                categoryViewModel.delete(category)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
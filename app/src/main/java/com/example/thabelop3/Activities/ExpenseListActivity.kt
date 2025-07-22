package com.example.thabelop3.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thabelop3.Adapter.ExpenseAdapter
import com.example.thabelop3.MainActivity
import com.example.thabelop3.Models.ExpenseViewModel
import com.example.thabelop3.R
import com.example.thabelop3.databinding.ActivityExpenseListBinding
import com.google.android.material.navigation.NavigationView

/**
 * Displays list of user expenses
 * - Shows expense items in RecyclerView
 * - Observes expense data changes
 * - Supports potential item click actions
 * - Debug logging for expense loading
 */
class ExpenseListActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityExpenseListBinding
    private lateinit var expenseViewModel: ExpenseViewModel
    private lateinit var adapter: ExpenseAdapter
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExpenseListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        // Set up navigation drawer
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_expenses, R.id.nav_list), // Top-level destinations
            binding.drawerLayout
        )

        val navView: NavigationView = binding.navView
        navView.setNavigationItemSelectedListener(this)

        // Set up navigation controller if using fragments
        val navController = findNavController(R.id.nav_list)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        expenseViewModel = ViewModelProvider(this)[ExpenseViewModel::class.java]

        setupRecyclerView()
        loadExpenses()
    }

    private fun setupRecyclerView() {
        adapter = ExpenseAdapter { expense ->
            // Handle expense item click
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@ExpenseListActivity)
            adapter = this@ExpenseListActivity.adapter
            setHasFixedSize(false)
        }
    }

    private fun loadExpenses() {
        val userId = 1
        expenseViewModel.getExpensesWithCategoryByUser(userId).observe(this) { expenses ->
            Log.d("ExpenseList", "Loaded expenses: ${expenses.size}")
            if (expenses.size > 1) {
                Log.d("ExpenseList", "First expense: ${expenses[0]}")
                Log.d("ExpenseList", "Second expense: ${expenses[1]}")
            }
            adapter.submitList(expenses.toList())
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_expenses -> {
                startActivity(Intent(this, AddExpenseActivity::class.java))
            }
            R.id.nav_list -> {
                // Already in ExpenseListActivity
            }
            R.id.nav_categories -> {
                startActivity(Intent(this, MainActivity::class.java).apply {
                    putExtra("open_fragment", "categories")
                })
            }
            R.id.nav_budget -> {
                startActivity(Intent(this, MainActivity::class.java).apply {
                    putExtra("open_fragment", "budget")
                })
            }
            R.id.nav_graph -> {
                startActivity(Intent(this, MainActivity::class.java).apply {
                    putExtra("open_fragment", "graph")
                })
            }
            R.id.nav_achievements -> {
                startActivity(Intent(this, MainActivity::class.java).apply {
                    putExtra("open_fragment", "achievements")
                })
            }
            R.id.nav_report -> {
                startActivity(Intent(this, ReportActivity::class.java))
            }
            R.id.nav_logout -> {
                getSharedPreferences("user_prefs", MODE_PRIVATE).edit().clear().apply()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        return if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        } else {
            binding.drawerLayout.openDrawer(GravityCompat.START)
            true
        }
    }
}
package com.example.thabelop3.Activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thabelop3.Adapter.ExpenseAdapter
import com.example.thabelop3.MainActivity
import com.example.thabelop3.Models.BudgetGoalRepository
import com.example.thabelop3.Models.BudgetGoalViewModel
import com.example.thabelop3.Models.ExpenseRepository
import com.example.thabelop3.Models.ExpenseViewModel
import com.example.thabelop3.R
import com.example.thabelop3.RoomDb.AppDatabase
import com.example.thabelop3.databinding.ActivityHomeBinding
import com.google.android.material.navigation.NavigationView
import java.util.Calendar
import kotlin.text.toDouble
import kotlin.text.toFloat

/**
 * Main dashboard activity with navigation buttons
 * Features:
 * - Launch point for key features
 * - Buttons for adding expenses, managing categories, and setting budgets
 * - Intent-based navigation to other activities
 */
class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: ExpenseViewModel
    private lateinit var adapter: ExpenseAdapter
    private lateinit var navView: NavigationView
    private lateinit var budgetGoalViewModel: BudgetGoalViewModel

    private val ocrLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            result.data?.let { data ->
                Intent(this, AddExpenseActivity::class.java).apply {
                    putExtra("amount", data.getStringExtra("amount"))
                    putExtra("date", data.getStringExtra("date"))
                    startActivity(this)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Navigation Drawer
        navView = findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener(this)

        // Set up toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu_white)

        // Set navigation listener
        navView.setNavigationItemSelectedListener(this)

        // Initialize ViewModels
        val dao = AppDatabase.getDatabase(application).expenseDao()
        ExpenseRepository(dao)
        viewModel = ViewModelProvider(this)[ExpenseViewModel::class.java]

        val budgetGoalDao = AppDatabase.getDatabase(application).budgetGoalDao()
        BudgetGoalRepository(budgetGoalDao)
        budgetGoalViewModel = ViewModelProvider(this)[BudgetGoalViewModel::class.java]

        // Setup RecyclerView
        adapter = ExpenseAdapter { expense ->
            // Handle item click if needed
        }

        binding.rvRecentTransactions.apply {
            layoutManager = LinearLayoutManager(this@HomeActivity)
            adapter = this@HomeActivity.adapter
            setHasFixedSize(true)
        }

        // Get current user ID
        val userId = getSharedPreferences("user_prefs", MODE_PRIVATE)
            .getInt("user_id", 0)

        refreshData()

        // Observe expenses and budget goals
        viewModel.getExpensesWithCategoryByUser(userId).observe(this) { expenses ->
            val monthlySpent = expenses.sumOf { it.expense.amount.toDouble() }.toFloat()
            binding.tvMonthlySpent.text = "R${"%.2f".format(monthlySpent)}"

            val calendar = Calendar.getInstance()
            val currentMonth = calendar.get(Calendar.MONTH) + 1
            val currentYear = calendar.get(Calendar.YEAR)

            budgetGoalViewModel.getBudgetGoal(userId, currentMonth, currentYear)
                .observe(this) { budgetGoal ->
                    val monthlyBudget = if (budgetGoal != null) {
                        budgetGoal.maxAmount.toFloat()
                    } else {
                        viewModel.getMonthlyBudget(userId)
                    }

                    binding.tvMonthlyBudget.text = "R${"%.2f".format(monthlyBudget)}"
                    val remainingBudget = monthlyBudget - monthlySpent
                    binding.tvMonthlySpent.text = "R${"%.2f".format(remainingBudget)} remaining"
                    binding.progressBudget.progress = if (monthlyBudget > 0) {
                        (monthlySpent / monthlyBudget * 100).toInt()
                    } else {
                        0
                    }
                }

            val recentExpenses = expenses.take(8)
            adapter.submitList(recentExpenses)

            if (recentExpenses.isEmpty()) {
                binding.rvRecentTransactions.visibility = View.GONE
                findViewById<TextView>(R.id.textRecentTransactionsLabel).visibility = View.GONE
            } else {
                binding.rvRecentTransactions.visibility = View.VISIBLE
                findViewById<TextView>(R.id.textRecentTransactionsLabel).visibility = View.VISIBLE
            }
        }


        // Load data
        refreshData()
        observeData()
    }

    private fun observeData() {
        val userId = getSharedPreferences("user_prefs", MODE_PRIVATE).getInt("user_id", 0)

        viewModel.getExpensesWithCategoryByUser(userId).observe(this) { expenses ->
            val monthlySpent = expenses.sumOf { it.expense.amount.toDouble() }.toFloat()
            binding.tvMonthlySpent.text = "R${"%.2f".format(monthlySpent)}"

            val monthlyBudget = viewModel.getMonthlyBudget(userId)
            binding.tvMonthlyBudget.text = "R${"%.2f".format(monthlyBudget)}"

            binding.progressBudget.progress =
                if (monthlyBudget > 0) (monthlySpent / monthlyBudget * 100).toInt() else 0

            adapter.submitList(expenses.take(5))
            toggleTransactionVisibility(expenses.isNotEmpty())
        }
    }

     override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                startActivity(Intent(this, HomeActivity::class.java))
            }
            R.id.nav_expenses -> {
                startActivity(Intent(this, AddExpenseActivity::class.java))
            }
            R.id.nav_list -> {
                startActivity(Intent(this, ExpenseListActivity::class.java))
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
            R.id.nav_profile -> {
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)

            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
        return true
    }

    @Deprecated("This method has been deprecated in favor of using the\n      {@link OnBackPressedDispatcher} via {@link #getOnBackPressedDispatcher()}.\n      The OnBackPressedDispatcher controls how back button events are dispatched\n      to one or more {@link OnBackPressedCallback} objects.")
    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun toggleTransactionVisibility(hasTransactions: Boolean) {
        binding.rvRecentTransactions.visibility = if (hasTransactions) View.VISIBLE else View.GONE
        findViewById<TextView>(R.id.textRecentTransactionsLabel).visibility =
            if (hasTransactions) View.VISIBLE else View.GONE
    }

    private fun refreshData() {
        val userId = getSharedPreferences("user_prefs", MODE_PRIVATE).getInt("user_id", 0)
        viewModel.getExpensesByUser(userId)
    }

    override fun onResume() {
        super.onResume()
        refreshData()
    }

    companion object {
        private const val OCR_REQUEST_CODE = 1001
    }
}
package com.example.thabelop3.Activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.thabelop3.MainActivity
import com.example.thabelop3.R
import com.example.thabelop3.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu_white)
        supportActionBar?.title = "Profile"

        // Set up navigation drawer
        binding.navView.setNavigationItemSelectedListener { menuItem ->
            handleNavigationItemSelected(menuItem)
            true
        }

        // Get user data from SharedPreferences
        val sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val username = sharedPref.getString("username", "")
        val email = sharedPref.getString("email", "")

        // Display user data
        binding.tvUsername.text = "Username: $username"
        binding.tvEmail.text = "Email: $email"

        binding.btnLogout.setOnClickListener {
            // Clear SharedPreferences
            with(sharedPref.edit()) {
                clear()
                apply()
            }

            // Navigate to LoginActivity and clear back stack
            startActivity(Intent(this, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            })
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
        return true
    }

    private fun handleNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
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
                // Already in profile activity
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }
        }
        return true
    }
}
package com.example.quicknews.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quicknews.R
import com.example.quicknews.data.model.Post
import com.example.quicknews.databinding.ActivityMainBinding
import com.example.quicknews.ui.detail.DetailActivity
import com.example.quicknews.ui.login.LoginActivity
import com.example.quicknews.utils.PreferencesManager
import com.example.quicknews.utils.ThemeManager

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var prefsManager: PreferencesManager
    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        prefsManager = PreferencesManager(this)
        ThemeManager.applyTheme(this, prefsManager.theme)

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        setupRecyclerView()
        setupSearchView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        adapter = PostAdapter(
            onItemClick = { post ->
                val intent = Intent(this, DetailActivity::class.java)
                intent.putExtra("POST", post)
                startActivity(intent)
            },
            onEdit = { post ->
                showEditDialog(post)
            },
            onDelete = { post ->
                showDeleteDialog(post)
            }
        )

        binding.recyclerView.apply {
            this.adapter = this@MainActivity.adapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun setupSearchView() {
        binding.etSearch.doOnTextChanged { text, _, _, _ ->
            viewModel.searchPosts(text.toString())
        }
    }

    private fun observeViewModel() {
        viewModel.posts.observe(this) { posts ->
            adapter.submitList(posts)
            binding.tvEmpty.visibility = if (posts.isEmpty()) View.VISIBLE else View.GONE
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(this) { error ->
            error?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun showEditDialog(post: Post) {
        val input = android.widget.EditText(this)
        input.setText(post.title)

        AlertDialog.Builder(this)
            .setTitle("Edit Title")
            .setView(input)
            .setPositiveButton("Save") { _, _ ->
                val newTitle = input.text.toString()
                if (newTitle.isNotEmpty()) {
                    viewModel.updatePost(post.copy(title = newTitle))
                    Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showDeleteDialog(post: Post) {
        AlertDialog.Builder(this)
            .setTitle("Delete Post")
            .setMessage("Are you sure you want to delete this post?")
            .setPositiveButton("Delete") { _, _ ->
                viewModel.deletePost(post)
                Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_refresh -> {
                viewModel.fetchPosts()
                true
            }
            R.id.theme_light -> {
                changeTheme(PreferencesManager.THEME_LIGHT)
                true
            }
            R.id.theme_dark -> {
                changeTheme(PreferencesManager.THEME_DARK)
                true
            }
            R.id.theme_custom -> {
                changeTheme(PreferencesManager.THEME_CUSTOM)
                true
            }
            R.id.action_logout -> {
                logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun changeTheme(theme: String) {
        prefsManager.theme = theme
        recreate()
    }

    private fun logout() {
        AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Yes") { _, _ ->
                prefsManager.logout()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
            .setNegativeButton("No", null)
            .show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("search_query", binding.etSearch.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.getString("search_query")?.let {
            binding.etSearch.setText(it)
        }
    }
}
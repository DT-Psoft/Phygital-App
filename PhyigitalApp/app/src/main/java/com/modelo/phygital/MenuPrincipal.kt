package com.modelo.phygital

import android.content.Intent
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.*
import androidx.core.view.isVisible
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.modelo.phygital.ui.casos_clinicos.CasosClinicosFragment


class MenuPrincipal : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_principal)
        val toolbar: Toolbar = findViewById(R.id.toolbar)


        val fabPaciente: FloatingActionButton = findViewById(R.id.fabP)
        val fabClinico: FloatingActionButton = findViewById(R.id.fabCS)

        fabPaciente.isVisible = true
        fabPaciente.isClickable = false
        fabClinico.isVisible = true
        fabClinico.isClickable = false

        setSupportActionBar(toolbar)

        fabPaciente.setOnClickListener {
            val intent = Intent(this, AddPacientes::class.java)
            startActivity(intent)
            finish()
        }

        fabClinico.setOnClickListener {
            val intent = Intent(this, AddCasosClinicos::class.java)
            startActivity(intent)
            finish()
        }


        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_pacientes, R.id.nav_casoClinico
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        p0.isChecked = true
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val fabp: FloatingActionButton = findViewById(R.id.fabP)
        val fabcs: FloatingActionButton = findViewById(R.id.fabCS)
     //   drawerLayout.closeDrawers()

        fabp.isVisible = true
        fabp.isClickable = true
        fabcs.isVisible = true
        fabcs.isClickable = true

        when (p0.itemId) {

            R.id.nav_pacientes -> {
                fabcs.isVisible = false
                fabcs.isClickable = false
                val intent = Intent(this, AddPacientes::class.java)
                startActivity(intent)
                finish()

            }

            R.id.nav_casoClinico -> {
                fabp.isVisible = false
                fabp.isClickable = false
                val intent = Intent(this, AddCasosClinicos::class.java)
                startActivity(intent)

                finish()
            }
        }

        return true
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_principal, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}

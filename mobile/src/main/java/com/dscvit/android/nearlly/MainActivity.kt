package com.dscvit.android.nearlly

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import com.dscvit.android.nearlly.R
import com.dscvit.android.nearlly.ui.fragment.ChatFragment

class MainActivity : AppCompatActivity(), ChatFragment.OnFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onSupportNavigateUp(): Boolean = Navigation.findNavController(this, R.id.fragment_nav_host).navigateUp()

    override fun onFragmentInteraction(string: String) {

    }
}

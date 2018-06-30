package com.dscvit.android.nearlly

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import androidx.navigation.Navigation
import com.dscvit.android.nearlly.R
import com.dscvit.android.nearlly.ui.fragment.ChatFragment
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MainActivity : AppCompatActivity(),
        ChatFragment.OnFragmentInteractionListener,
        HasSupportFragmentInjector {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onSupportNavigateUp(): Boolean = Navigation.findNavController(this, R.id.fragment_nav_host).navigateUp()

    override fun onFragmentInteraction(string: String) {

    }

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector() = dispatchingAndroidInjector
}

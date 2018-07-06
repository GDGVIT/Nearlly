package com.dscvit.android.nearlly.ui.fragment

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.dscvit.android.nearlly.MainActivity
import com.dscvit.android.nearlly.R
import com.dscvit.android.nearlly.di.Injectable
import com.dscvit.android.nearlly.ui.viewmodel.ChatViewModel
import com.dscvit.android.nearlly.utils.Constants
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.bundleOf
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.textColor
import petrov.kristiyan.colorpicker.ColorPicker
import javax.inject.Inject

class ProfileFragment : Fragment(), Injectable {
    private val TAG = MainActivity::class.java.simpleName

    private var listener: OnFragmentInteractionListener? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var preferences: SharedPreferences

    private lateinit var chatViewModel: ChatViewModel

    private lateinit var navController: NavController

    private var userNameInput = ""
    private var colorInput = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        listener?.onFragmentInteraction("some message")
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        chatViewModel = ViewModelProviders.of(this, viewModelFactory).get(ChatViewModel::class.java)
        navController = findNavController()

        attachClickListeners()

    }

    private fun attachClickListeners() {

        card_color_picker.setOnClickListener {
            ColorPicker(activity)
                    .setOnChooseColorListener(object : ColorPicker.OnChooseColorListener {
                        override fun onChooseColor(position: Int, color: Int) {
                            colorInput = color
                        }

                        override fun onCancel() {

                        }
                    })
                    .setRoundColorButton(true)
                    .setDismissOnButtonListenerClick(true)
                    .show()
        }

        fab_profile_done.setOnClickListener {
            userNameInput = textInputLayout.editText?.text.toString()
            Log.i(TAG, "USER NAME : ${userNameInput}")
            launch(UI) {
//                chatViewModel.saveColor(colorInput)
//                chatViewModel.saveUserName(userNameInput)
//
//                val bundle = bundleOf(
//                        Constants.PREF_USERNAME_KEY to userNameInput,
//                        Constants.PREF_COLOR_KEY to colorInput
//                )

                preferences.edit()
                        .putString(Constants.PREF_USERNAME_KEY, userNameInput)
                        .putInt(Constants.PREF_COLOR_KEY, colorInput)
                        .commit()

                preferences.edit().putBoolean(Constants.PREF_FIRST_TIME_KEY, false).commit()

                findNavController().popBackStack()
            }
        }
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(string: String)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}
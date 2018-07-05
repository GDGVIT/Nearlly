package com.dscvit.android.nearlly.ui.fragment

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
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
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.textColor
import petrov.kristiyan.colorpicker.ColorPicker
import javax.inject.Inject

class ProfileFragment : Fragment(), Injectable {
    private val TAG = MainActivity::class.java.simpleName

    private var listener: OnFragmentInteractionListener? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var chatViewModel: ChatViewModel

    private lateinit var navController: NavController

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
                            chatViewModel.saveColor(color)
                        }

                        override fun onCancel() {

                        }
                    })
                    .setRoundColorButton(true)
                    .setDismissOnButtonListenerClick(true)
                    .show()
        }

        fab_profile_done.setOnClickListener {
            launch(UI) {
                chatViewModel.saveUserName(textInputLayout.editText?.text.toString())
                findNavController().popBackStack(R.id.action_profileFragment_to_chatFragment5, false)
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
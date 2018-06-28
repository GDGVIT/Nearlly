package com.dscvit.android.nearlly.ui.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dscvit.android.nearlly.R
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.messages.Message
import com.google.android.gms.nearby.messages.MessageListener
import com.google.gson.Gson
import org.jetbrains.anko.support.v4.act
import android.view.animation.Animation
import android.view.animation.RotateAnimation



class ChatFragment : Fragment() {

    private val TAG = (activity as AppCompatActivity)::class.java.simpleName
    private val REQUEST_RESOLVE_ERROR = 1001

    private var listener: OnFragmentInteractionListener? = null

    private lateinit var mGoogleApiClient: GoogleApiClient
    private lateinit var mMessageListener: MessageListener
    private lateinit var mMessage: Message

    private val mRotateAnimation = RotateAnimation(0.0f, 360.0f,
            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
            0.5f)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        listener?.onFragmentInteraction("some message")
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mMessageListener = object : MessageListener() {
            override fun onFound(p0: Message?) {

            }

            override fun onLost(p0: Message?) {

            }
        }

        mMessage = Message("Hello, World again!!".toByteArray())
    }

    override fun onStart() {
        super.onStart()
        activity?.let {
            with(Nearby.getMessagesClient(it)) {
                publish(mMessage)
                subscribe(mMessageListener)
            }
        }
    }

    override fun onStop() {
        activity?.let {
            with(Nearby.getMessagesClient(it)) {
                unpublish(mMessage)
                unsubscribe(mMessageListener)
            }
        }
        super.onStop()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        listener = null
        super.onDetach()
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(string: String)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ChatFragment()
    }

}
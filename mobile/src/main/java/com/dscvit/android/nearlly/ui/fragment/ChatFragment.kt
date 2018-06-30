package com.dscvit.android.nearlly.ui.fragment

import android.app.Activity.RESULT_OK
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
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
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.dscvit.android.nearlly.MainActivity
import com.dscvit.android.nearlly.adapter.ChatAdapter
import com.dscvit.android.nearlly.di.Injectable
import com.dscvit.android.nearlly.model.ChatMessage
import com.dscvit.android.nearlly.ui.viewmodel.ChatViewModel
import com.google.android.gms.common.ConnectionResult
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.experimental.android.UI
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject


class ChatFragment : Fragment(), GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, Injectable {

    private val TAG = MainActivity::class.java.simpleName
    private val REQUEST_RESOLVE_ERROR = 1001

    private var listener: OnFragmentInteractionListener? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var adapter: ChatAdapter
    private lateinit var chatViewModel: ChatViewModel

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

        chatViewModel = ViewModelProviders.of(this, viewModelFactory).get(ChatViewModel::class.java)

        setUpRecyclerViews()
        setUpMessageSendViews()
        buildGoogleApiClient()
        buildMessageListener()
    }

    private fun buildMessageListener() {
        mMessageListener = object : MessageListener() {
            override fun onFound(message: Message?) {
                message?.let {
                    val messageString = String(message.content).trim()
                    val chatMessage = Gson().fromJson(messageString, ChatMessage::class.java)
                    chatViewModel.addMessage(chatMessage)
                }
            }
        }
    }

    private fun buildGoogleApiClient() {
        mGoogleApiClient = GoogleApiClient.Builder(context!!)
                .addApi(Nearby.MESSAGES_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build()
    }

    private fun setUpMessageSendViews() {
        with(mRotateAnimation) {
            interpolator = LinearInterpolator()
            duration = 500
            repeatCount = Animation.INFINITE
        }

        fab_send.setOnClickListener {
            val messageText = text_input_message.text.toString()
            if(messageText.isNotEmpty() && messageText.isNotBlank()) {
                fab_send.setImageDrawable(ContextCompat.getDrawable(context!!, R.drawable.ic_loop_white_24dp))
                fab_send.startAnimation(mRotateAnimation)
                publishMessage(ChatMessage(
                        chatViewModel.userName ?: "Username",
                        messageText
                ))
                hideSoftKeyboard(activity, it)
                text_input_message.clearFocus()
            }
        }
    }

    private fun hideSoftKeyboard(activity: FragmentActivity?, view: View?) {
        val inputMethodManager: InputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.applicationWindowToken, 0)
    }

    private fun publishMessage(chatMessage: ChatMessage) {
        val messageString = Gson().toJson(chatMessage)
        mMessage = Message(messageString.toByteArray())
        Nearby.Messages.publish(mGoogleApiClient, mMessage)
                .setResultCallback { status ->
                    if(status.isSuccess) {
                        chatViewModel.addMessage(chatMessage)
                    } else {
                        Toast.makeText(context, "Message not sent", Toast.LENGTH_SHORT).show()
                    }
                    resetMessageSendAnimation()
                    fab_send.setImageDrawable(ContextCompat.getDrawable(context!!, R.drawable.ic_chevron_right_white_24dp))
                }
    }

    private fun resetMessageSendAnimation() {
        mRotateAnimation.cancel()
        mRotateAnimation.reset()
    }

    private fun setUpRecyclerViews() {
        adapter = ChatAdapter {
            Toast.makeText(context, "Item clicked", Toast.LENGTH_SHORT).show()
        }
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        chat_recyclerview.layoutManager = linearLayoutManager
        chat_recyclerview.adapter = adapter
        populateRecyclerView()
    }

    private fun populateRecyclerView() {
        activity?.let {
            chatViewModel.chatMessages.observe(it, Observer {
                launch(UI) {
                    adapter.submitList(it)
                }
            })
        }
    }

    override fun onStart() {
        super.onStart()
        if(!mGoogleApiClient.isConnected) {
            mGoogleApiClient.connect()
        }
    }

    override fun onStop() {
        if(mGoogleApiClient.isConnected) {
            Nearby.Messages.unsubscribe(mGoogleApiClient, mMessageListener)
            mGoogleApiClient.disconnect()
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

    private fun subscribe() {
        Nearby.Messages.subscribe(mGoogleApiClient, mMessageListener)
                .setResultCallback { status ->
                    if (status.isSuccess) {
                        Toast.makeText(context, "Connected", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Not able to connect", Toast.LENGTH_SHORT).show()
                    }
                }
    }

    override fun onConnected(p0: Bundle?) {
        subscribe()
    }

    override fun onConnectionSuspended(p0: Int) {

    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(activity, REQUEST_RESOLVE_ERROR)
            } catch (e: IntentSender.SendIntentException) {
                e.printStackTrace()
            }
        } else {
            Log.e(TAG, "GoogleApiClient connection failed")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        when(requestCode) {
            REQUEST_RESOLVE_ERROR -> {
                if (requestCode == RESULT_OK) {
                    mGoogleApiClient.connect()
                } else {
                    Log.e(TAG, "GoogleApiClient connection failed. Unable to resolve.")
                }
            }
            else -> {
                super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

}
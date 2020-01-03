package com.victoweng.ciya2.ui.custom

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.button.MaterialButton
import com.victoweng.ciya2.R

/**
 * Toggles between the states of what a host or participant can do on the FullEventDetails screen
 * Gives the Host the option to delete the whole event... In the future maybe allow user to appoint new host.
 * Allows guests to request to join and to leave event.
 */
class JoinButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : MaterialButton(context, attrs, defStyleAttr) {

    enum class RequestState(val fieldText: String, val color : Int) {
        HOST("Delete Event", R.color.join_green),
        DELETE("Deleting...", R.color.leave_red),
        JOIN("Join", R.color.join_green),
        JOIN_REQUESTED("Requested to Join", R.color.leave_red),
        LEAVE("Leave", R.color.leave_red)
    }

    private var currentState = RequestState.JOIN

    fun setRequestState(state: RequestState) {
        currentState = state
        updateButton(currentState)
    }

    fun getCurrentState() = currentState

    fun updateButton(requestState: RequestState) {
        setBackgroundColor(requestState.color)
        setText(requestState.fieldText)
    }

    fun rotateState() {
        when(currentState) {
            RequestState.JOIN -> setRequestState(RequestState.JOIN_REQUESTED)
            RequestState.JOIN_REQUESTED -> setRequestState(RequestState.LEAVE)
            RequestState.LEAVE -> setRequestState(RequestState.JOIN)
            RequestState.HOST -> setRequestState(RequestState.DELETE)
        }
    }


}
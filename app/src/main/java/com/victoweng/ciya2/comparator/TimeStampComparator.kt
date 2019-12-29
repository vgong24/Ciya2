package com.victoweng.ciya2.comparator

import com.victoweng.ciya2.data.EventDetail

class TimeStampComparator() : Comparator<EventDetail> {

    override fun compare(p0: EventDetail, p1: EventDetail): Int {
        return p0.timestamp.compareTo(p1.timestamp)
    }

}
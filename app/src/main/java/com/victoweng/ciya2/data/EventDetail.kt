package com.victoweng.ciya2.data

data class EventDetail (val categoryType: CategoryType,
                        val location: Location,
                        val title: String,
                        val description: String,
                        val startDate: String,
                        val participants: Users?)
package model

import jdk.jfr.TransitionFrom
import java.time.LocalDate

data class WeakConEventFeedFilter(
    var dateFrom: LocalDate = LocalDate.now(),
    var dateTo: LocalDate = LocalDate.now(),
    var location: String = ""
)
package model

import java.time.LocalDate
data class WeakConCreatedEventFilter(
    var finished: Boolean = false,
    var inProgress: Boolean = false
)
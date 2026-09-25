package de.docutain.sdk.docutain_sdk_example_photopayment_kmp_compose.payment

import java.text.NumberFormat

internal actual fun formatAmount(value: Double): String =
    NumberFormat.getNumberInstance().apply {
        minimumFractionDigits = 2
        maximumFractionDigits = 2
    }.format(value)

package de.docutain.sdk.docutain_sdk_example_photopayment_kmp_compose.payment

import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSNumberFormatterDecimalStyle

internal actual fun formatAmount(value: Double): String {
    val formatter = NSNumberFormatter().apply {
        numberStyle = NSNumberFormatterDecimalStyle
        minimumFractionDigits = 2u
        maximumFractionDigits = 2u
    }
    return formatter.stringFromNumber(NSNumber(value)) ?: value.toString()
}

package de.docutain.sdk.docutain_sdk_example_photopayment_kmp_compose.payment

/**
 * Formats an amount the SDK returned as `1234.56` the way the device's locale writes numbers,
 * or `null` when [raw] is not such a number.
 *
 * The SDK returns no currency, so none is shown - an invoice is not necessarily in euro.
 */
fun localizedAmount(raw: String): String? {
    val value = raw.trim().toDoubleOrNull() ?: return null
    return formatAmount(value)
}

/** The amount with two decimals in the device's locale, e.g. `1.234,56` or `1,234.56`. */
internal expect fun formatAmount(value: Double): String

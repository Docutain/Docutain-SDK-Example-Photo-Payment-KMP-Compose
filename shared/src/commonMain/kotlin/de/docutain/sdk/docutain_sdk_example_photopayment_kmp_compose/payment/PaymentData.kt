package de.docutain.sdk.docutain_sdk_example_photopayment_kmp_compose.payment

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

/**
 * The payment data the SDK detected on an invoice.
 *
 * The SDK returns it as JSON, see [parse]. Only what a transfer form asks for is read, the
 * rest of the JSON (invoice id, date, address, payment state, ...) stays visible in the raw
 * result.
 */
@Serializable
data class PaymentData(
    @SerialName("Address") val address: Address = Address(),
    @SerialName("Amount") val amount: String = "",
    @SerialName("Reference") val reference: String = "",
    @SerialName("SEPACreditor") val sepaCreditor: String = "",
) {

    /**
     * Who receives the money. That is the SEPA creditor - the name on the invoice can be a
     * different company, e.g. `DB Fernverkehr AG` invoicing for `DB Vertrieb GmbH`. The photo
     * payment reads it by default and falls back to the name on the invoice when there is none.
     */
    val recipient: String
        get() = sepaCreditor.trim()

    /**
     * The IBAN of the first bank account, grouped in blocks of four to make it easier to read.
     *
     * An invoice can name several accounts. A transfer goes to one of them, so the form takes the
     * first; the others stay visible in the raw result.
     */
    val iban: String
        get() = (address.bankAccounts.firstOrNull()?.iban ?: address.ibans.firstOrNull()).orEmpty()
            .filterNot { it.isWhitespace() }
            .chunked(4)
            .joinToString(" ")

    /** The BIC of the first bank account, the one [iban] is taken from. */
    val bic: String
        get() = address.bankAccounts.firstOrNull()?.bic.orEmpty().trim()

    /**
     * The amount as the device's locale writes it, or `null` when the SDK found none. An amount
     * that is not a plain number is shown as the SDK returned it.
     */
    val displayAmount: String?
        get() = amount.trim()
            .takeIf { it.isNotEmpty() && it != EMPTY_AMOUNT }
            ?.let { localizedAmount(it) ?: it }

    companion object {

        /** An amount of `0.00` is how the SDK reports that it detected no amount at all. */
        private const val EMPTY_AMOUNT = "0.00"

        //the JSON holds more than a transfer needs, e.g. the postal address and the invoice id
        private val json = Json { ignoreUnknownKeys = true }

        /** Parses what the SDK returned, throws when it is not readable as payment data. */
        fun parse(raw: String): PaymentData = json.decodeFromString(serializer(), raw)
    }
}

/**
 * The bank details on the invoice.
 *
 * With `readBIC` the SDK lists the bank accounts under `Bank`, IBAN and BIC together. Without it
 * there is no `Bank`, only the IBANs as a list of strings under `IBAN`.
 */
@Serializable
data class Address(
    @SerialName("Bank") val bankAccounts: List<BankAccount> = emptyList(),
    @SerialName("IBAN") val ibans: List<String> = emptyList(),
)

/** One bank account named on an invoice. */
@Serializable
data class BankAccount(
    @SerialName("IBAN") val iban: String = "",
    @SerialName("BIC") val bic: String = "",
)

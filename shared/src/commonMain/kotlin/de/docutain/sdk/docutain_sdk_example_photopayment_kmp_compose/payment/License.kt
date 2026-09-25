package de.docutain.sdk.docutain_sdk_example_photopayment_kmp_compose.payment

import de.docutain.sdk.kmp.DocutainSdk

/**
 * The license key the sample runs with: a trial license for the application id
 * `de.docutain.sdk.example.photopayment.kmp.compose`, valid until 1 December 2026.
 *
 * For your own app, request a free trial license key for its application id at
 * https://sdk.docutain.com/TrialLicense?Product=photo-payment
 */
const val LICENSE_KEY = "XR+fKWLCluPLSRVEQHBKLm4R1JJdm7YzI3xn203S1vfqtZGAvlNQ4dSJX4VXzMdBJurVsg9orsQnZxsXT21jlZzMQdG+mw7NZqVmJenDzX+wzQyAPmiEpjAXiEozDRk1ZE2OMW1xUZ97HNns"

/**
 * Initializes the SDK with [LICENSE_KEY]. Call it once when the app starts, before anything
 * else uses the SDK.
 *
 * @return `false` when the SDK refused the license key, e.g. because it was issued for another
 * application id - then nothing of the SDK works.
 */
fun initDocutainSdk(): Boolean =
    try {
        //throws a DocutainException when the SDK refuses the license key
        DocutainSdk.initSdk(LICENSE_KEY)
        true
    } catch (error: Exception) {
        false
    }

package com.polo.net

import kotlinx.coroutines.delay

interface Retry {
    suspend fun <T> retry(block: suspend () -> T): T
}

class BackoffRetry(
    private val maxRetries: Int,
    private val initialDelay: Long = 1000,
    private val factor: Double = 2.0,
    private val retryableExceptions: Set<Class<out Exception>>
): Retry {
    override suspend fun <T> retry(block: suspend () -> T): T {
        var currentDelay = initialDelay
        var baseException: Exception? = null
        repeat(maxRetries -1) {
            try {
                return block()
            } catch(e: Exception) {
                if (baseException == null) {
                    baseException = e
                } else {
                    baseException?.addSuppressed(e)
                }
                if (retryableExceptions.any { it.isInstance(e)} ) {
                    delay(currentDelay)
                    currentDelay = (currentDelay * factor).toLong()
                } else {
                    throw baseException ?: e
                }
            }
        }
        return block() // Last attempt
    }
}

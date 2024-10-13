package com.polo.net.rest

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.github.fge.jsonpatch.JsonPatch
import com.github.fge.jsonpatch.JsonPatchException
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.JsonNode

object Patch {
    // Thread-safe singleton ObjectMapper
    private val mapper: ObjectMapper = ObjectMapper()
        .registerModule(KotlinModule.Builder().build())
        .registerModule(JavaTimeModule())
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    /**
     * Applies a JSON Patch to an object of type T.
     *
     * @param t The object to which the patch will be applied.
     * @param patch The JSON Patch containing the operations to apply.
     * @return The updated object of type T.
     * @throws RuntimeException if the patch application fails.
     */
    fun <T, P> apply(t: T, patch: P): T {
        val currentUserNode = mapper.valueToTree<ObjectNode>(t)

        return try {
            // Convert the patch to a JsonPatch
            val jsonPatch: JsonPatch = convertToJsonPatch(patch)

            // Apply the JSON Patch
            val updatedUserNode: JsonNode = jsonPatch.apply(currentUserNode)

            // Convert back to the desired type
            mapper.treeToValue(updatedUserNode, object : TypeReference<T>() {})
        } catch (e: JsonPatchException) {
            throw RuntimeException("Failed to apply JSON Patch", e)
        }
    }

    private fun <P> convertToJsonPatch(patch: P): JsonPatch {
        return mapper.readValue(mapper.writeValueAsBytes(patch), JsonPatch::class.java)
    }
}

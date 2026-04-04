package dev.saketanand.auth.domain

interface PatternValidator {
    fun matches(value: String): Boolean
}
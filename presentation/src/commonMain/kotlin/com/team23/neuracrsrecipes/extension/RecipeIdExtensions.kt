package com.team23.neuracrsrecipes.extension

fun String.toCleanRecipeId() = this.replace(SLASH, REPLACEMENT)
fun String.toUrlRecipeId() = this.replace(REPLACEMENT, SLASH)

private const val SLASH = '/'
private const val REPLACEMENT = '^'

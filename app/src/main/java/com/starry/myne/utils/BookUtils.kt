package com.starry.myne.utils

import com.starry.myne.api.models.Author
import java.util.*

object BookUtils {
    fun getAuthorsAsString(authors: List<Author>): String {
        return if (authors.isNotEmpty()) {
            var result: String
            if (authors.size > 1) {
                result = authors.first().name
                authors.slice(1 until authors.size).forEach { author ->
                    if (author.name != "N/A")
                        result += ", ${fixAuthorName(author.name)}"
                }
            } else {
                result = fixAuthorName(authors.first().name)
            }
            result
        } else {
            "Unknown Author"
        }
    }

    /**
     * For some weird reasons, gutenberg gives name of authors in
     * reversed, where first name and last are separated by a comma
     * Eg: "Fyodor Dostoyevsky" becomes "Dostoyevsky, Fyodor", This
     * function fixes that and returns name in correct format.
     */
    private fun fixAuthorName(name: String): String {
        val reversed = name.split(",").reversed()
        return reversed.joinToString(separator = " ") {
            return@joinToString it.trim()
        }
    }

    fun getLanguagesAsString(languages: List<String>): String {
        var result = ""
        languages.forEachIndexed { index, lang ->
            val loc = Locale(lang)
            if (index == 0) {
                result = loc.displayLanguage
            } else {
                result += ", ${loc.displayLanguage}"
            }
        }
        return result
    }

    fun getSubjectsAsString(subjects: List<String>, limit: Int): String {
        val allSubjects = ArrayList<String>()
        // strip "--" from subjects.
        subjects.forEach { subject ->
            if (subject.contains("--")) {
                allSubjects.addAll(subject.split("--"))
            } else {
                allSubjects.add(subject)
            }
        }
        val truncatedSubs: List<String> = if (allSubjects.size > limit) {
            allSubjects.toSet().toList().subList(0, limit)
        } else {
            allSubjects.toSet().toList()
        }
        return truncatedSubs.joinToString(separator = ", ") {
            return@joinToString it.trim()
        }
    }
}
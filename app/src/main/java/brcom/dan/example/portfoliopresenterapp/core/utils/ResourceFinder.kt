package brcom.dan.example.portfoliopresenterapp.core.utils

class ResourceFinder {

    companion object {

        fun formatProgrammingLanguageUri(language: String): String {

            val BASE_URL = "https://cdn.jsdelivr.net/npm/programming-languages-logos/src";
            val file_extension = ".png"

            val fmt_language = language.lowercase()

            return when(language.uppercase()){
                "C++" -> "$BASE_URL/cpp/cpp$file_extension"
                "C#" -> "$BASE_URL/csharp/csharp$file_extension"
                else -> "$BASE_URL/$fmt_language/$fmt_language$file_extension"
            }
        }
    }

}
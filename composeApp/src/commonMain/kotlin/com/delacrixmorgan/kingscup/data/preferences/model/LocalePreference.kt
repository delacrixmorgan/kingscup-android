package com.delacrixmorgan.kingscup.data.preferences.model

enum class LocalePreference(
    val code: String,
    val emoji: String,
    val localisedName: String,
    val contributorName: String? = null,
) {
    English(
        code = "en",
        emoji = "\uD83C\uDDEC\uD83C\uDDE7",
        localisedName = "English",
        contributorName = "Delacrix Morgan",
    ),
    BrazilianPortuguese(
        code = "pt-BR",
        emoji = "\uD83C\uDDE7\uD83C\uDDF7",
        localisedName = "Portuguese",
        contributorName = "Lays Correia",
    ),
    Chinese(
        code = "zh",
        emoji = "\uD83C\uDDE8\uD83C\uDDF3",
        localisedName = "Chinese",
        contributorName = "Wang Ruihao",
    ),
    Croatian(
        code = "hr",
        emoji = "\uD83C\uDDED\uD83C\uDDF7",
        localisedName = "Croatian",
    ),
    Czech(
        code = "cs",
        emoji = "\uD83C\uDDE8\uD83C\uDDFF",
        localisedName = "Czech",
        contributorName = "Michal Matlach",
    ),
    Danish(
        code = "da",
        emoji = "\uD83C\uDDE9\uD83C\uDDF0",
        localisedName = "Danish",
    ),
    Dutch(
        code = "nl",
        emoji = "\uD83C\uDDF3\uD83C\uDDF1",
        localisedName = "Dutch",
        contributorName = "Kasper Nooteboom",
    ),
    Filipino(
        code = "fil",
        emoji = "\uD83C\uDDF5\uD83C\uDDED",
        localisedName = "Filipino",
        contributorName = "Rexson Bernal",
    ),
    Finnish(
        code = "fi",
        emoji = "\uD83C\uDDEB\uD83C\uDDEE",
        localisedName = "Finnish",
        contributorName = "Karim Moubarik",
    ),
    French(
        code = "fr",
        emoji = "\uD83C\uDDEB\uD83C\uDDF7",
        localisedName = "French",
        contributorName = "David Chitchong Thingee",
    ),
    German(
        code = "de",
        emoji = "\uD83C\uDDE9\uD83C\uDDEA",
        localisedName = "German",
        contributorName = "Roland Stuhler",
    ),
    Greek(
        code = "el",
        emoji = "\uD83C\uDDEC\uD83C\uDDF7",
        localisedName = "Greek",
    ),
    Hebrew(
        code = "he",
        emoji = "\uD83C\uDDEE\uD83C\uDDF1",
        localisedName = "Hebrew",
    ),
    Hungarian(
        code = "hu",
        emoji = "\uD83C\uDDED\uD83C\uDDFA",
        localisedName = "Hungarian",
        contributorName = "Dávid Kardos",
    ),
    Indonesian(
        code = "id",
        emoji = "\uD83C\uDDEE\uD83C\uDDE9",
        localisedName = "Bahasa Indonesia",
        contributorName = "Julius Lukman",
    ),
    Italy(
        code = "it",
        emoji = "\uD83C\uDDEE\uD83C\uDDF9",
        localisedName = "Italian",
        contributorName = "Andrea Castaldi",
    ),
    Japanese(
        code = "ja",
        emoji = "\uD83C\uDDEF\uD83C\uDDF5",
        localisedName = "Japanese",
        contributorName = "Yukiko Kimura",
    ),
    Korean(
        code = "kr",
        emoji = "\uD83C\uDDF0\uD83C\uDDF7",
        localisedName = "Korean",
    ),
    Lao(
        code = "lo",
        emoji = "\uD83C\uDDF1\uD83C\uDDE6",
        localisedName = "Lao",
    ),
    Persian(
        code = "fa",
        emoji = "\uD83C\uDDEE\uD83C\uDDF7",
        localisedName = "Farsi",
    ),
    Polish(
        code = "pl",
        emoji = "\uD83C\uDDF5\uD83C\uDDF1",
        localisedName = "Polish",
        contributorName = "Kamil Kowalik",
    ),
    Romanian(
        code = "ro",
        emoji = "\uD83C\uDDF7\uD83C\uDDF4",
        localisedName = "Romanian",
    ),
    Russian(
        code = "ru",
        emoji = "\uD83C\uDDF7\uD83C\uDDFA",
        localisedName = "Russian",
        contributorName = "Alexander Bondarevskyi",
    ),
    Slovak(
        code = "sk",
        emoji = "\uD83C\uDDF8\uD83C\uDDF0",
        localisedName = "Slovak",
    ),
    Spanish(
        code = "es",
        emoji = "\uD83C\uDDEA\uD83C\uDDF8",
        localisedName = "Spanish",
        contributorName = "Santos Martinez and Gonzo Fernandez",
    ),
    Thai(
        code = "th",
        emoji = "\uD83C\uDDF9\uD83C\uDDED",
        localisedName = "Thai",
    ),
    Turkish(
        code = "tr",
        emoji = "\uD83C\uDDF9\uD83C\uDDF7",
        localisedName = "Turkish",
    ),
    Ukrainian(
        code = "uk",
        emoji = "\uD83C\uDDFA\uD83C\uDDE6",
        localisedName = "Ukrainian",
        contributorName = "Alexander Bondarevskyi",
    ),
    Vietnamese(
        code = "vi",
        emoji = "\uD83C\uDDFB\uD83C\uDDF3",
        localisedName = "Vietnamese",
    );

    companion object {
        val Default = English
    }
}
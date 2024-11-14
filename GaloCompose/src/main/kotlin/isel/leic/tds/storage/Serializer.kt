package isel.leic.tds.storage

/**
 * Serialization to use in a text base storage.
 * data == deserialize(serialize(data))
 */
interface Serializer<Data> {
    fun serialize(data: Data): String
    fun deserialize(text: String): Data
}
package isel.leic.tds.storage

import java.nio.file.NoSuchFileException
import kotlin.io.path.*

/**
 * Storage system based on text files.
 * Each entry corresponds to a text file.
 * The key is the file name with ".txt" extension.
 * The value is the contents of the file serialized to text.
 * @param baseFolderName the name of the folder where the files are stored.
 * @param serializer the serializer to convert data to text and vice-versa.
 */
class TextFileStorage<Key, Data: Any>(
    baseFolderName: String,
    private val serializer: Serializer<Data>
): Storage<Key, Data> {
    private val basePath = Path(baseFolderName)
    // Create base folder if it does not exist
    init {
        if (! basePath.exists()) basePath.createDirectory()
        else check(basePath.isDirectory()) { "$baseFolderName is not a directory" }
    }

    private fun getFile(key: Key) = basePath / "$key.txt"

    override fun create(key: Key, data: Data) {
        val file = getFile(key)
        check(! file.exists()) { "File $key.txt already exists" }
        file.writeText( serializer.serialize(data) )
    }

    override fun read(key: Key): Data? {
        val file = getFile(key)
        return try { serializer.deserialize(file.readText()) }
        catch (e: NoSuchFileException) { null }
    }

    override fun update(key: Key, data: Data) {
        val file = getFile(key)
        check(file.exists()) { "File $key.txt not found" }
        file.writeText( serializer.serialize(data) )
    }

    override fun delete(key: Key) {
        val file = getFile(key)
        check(file.deleteIfExists()) { "File $key.txt not found" }
    }
}
import isel.leic.tds.storage.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.AfterAll
import kotlin.io.path.*
import kotlin.test.*

class TextFileTests {
    companion object {
        private const val FOLDER_NAME = "test"
        val storage = TextFileStorage<String,String>(FOLDER_NAME,
            object : Serializer<String> {
                override fun serialize(data: String) = data
                override fun deserialize(text: String) = text
            }
        )
        @BeforeAll @JvmStatic fun setup() {
            assertTrue(Path(FOLDER_NAME).exists())
        }
        @AfterAll @JvmStatic fun cleanup() {
            @OptIn(ExperimentalPathApi::class)
            Path(FOLDER_NAME).deleteRecursively()
        }
    }

    @Test fun `Create and Read an entry`() {
        val key = "e1"
        storage.create(key,"content")
        assertEquals("content", storage.read(key))
    }
    @Test fun `Create and Update an entry`() {
        val key = "e2"
        storage.create(key,"old content")
        storage.update(key,"new content")
        assertEquals("new content", storage.read(key))
    }
    @Test fun `Create and Delete an entry`() {
        val key = "e3"
        storage.create(key,"content")
        storage.delete(key)
        assertNull(storage.read(key))
    }
    @Test fun `Create an entry that already exists`() {
        val key = "e4"
        storage.create(key,"content")
        assertFailsWith<IllegalStateException> {
            storage.create(key,"content")
        }
    }
    @Test fun `Read an entry that does not exist`() {
        assertNull(storage.read("e5"))
    }
    @Test fun `Update an entry that does not exist`() {
        assertFailsWith<IllegalStateException> {
            storage.update("e6","content")
        }
    }
    @Test fun `Delete an entry that does not exist`() {
        assertFailsWith<IllegalStateException> {
            storage.delete("e7")
        }
    }
}
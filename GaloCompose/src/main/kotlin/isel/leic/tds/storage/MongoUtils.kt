package isel.leic.tds.storage
import com.mongodb.ConnectionString
import com.mongodb.MongoClientException
import com.mongodb.MongoWriteException
import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.MongoClient
import com.mongodb.kotlin.client.MongoCollection
import com.mongodb.kotlin.client.MongoDatabase
import java.io.Closeable

/**
 * Name of the environment variable that contains the connection string in format:
 *   mongodb+srv://<username>:<password>@<host>/[<database>][?<options>]
 * Example: MONGO_CONNECTION=mongodb+srv://palex:tds123abc@cluster0.brsewd2.mongodb.net/reversi?retryWrites=true&w=majority
 */
private const val ENV_CONNECTION = "MONGO_CONNECTION"

/**
 * Represents the MongoDB driver. Must be closed at the end.
 * The connection string of the environment variable is used to connect to the remote database,
 * The database name is defined in the constructor parameter or in the connection string.
 * @param nameDb Database name (override database name in connection string)
 */
class MongoDriver(nameDb: String? =null) : Closeable {
    /**
     * Reference to database.
     * Receiver of database functions to call.
     */
    val db: MongoDatabase

    private val client: MongoClient
    init {
        val envConnection = System.getenv(ENV_CONNECTION)
            ?: throw MongoClientException("Connection string in environment variable $ENV_CONNECTION is required")
        val dbName = requireNotNull(
            nameDb ?: ConnectionString(envConnection).database
        ) { "Database name is required in constructor or in connection string" }
        client = MongoClient.create(envConnection)
        db = client.getDatabase(dbName)
    }

    /**
     * Close the drive.
     * Must be called at the end of use.
     */
    override fun close() = client.close()
}

/**
 * Gets one collection from the database.
 * @param id Collection identification.
 * @return The collection of documents of type T.
 */
inline fun <reified T : Any> MongoDriver.getCollection(id: String) =
    Collection(db.getCollection(id, T::class.java))

/**
 * Gets all collections from the database that contains documents of type T.
 * @return The list of document collections of type T.
 */
inline fun <reified T : Any> MongoDriver.getAllCollections(): Iterable<Collection<T>> =
    db.listCollectionNames().toList().map { getCollection<T>(it) }

/**
 * Represents a collection of documents of type T.
 */
class Collection<T: Any>(val collection: MongoCollection<T>)

/**
 * Get all documents of type T from the collection.
 * @return The list of all documents (of type T) in the collection.
 */
fun <T: Any> Collection<T>.getAllDocuments(): List<T> = collection.find().toList()

/**
 * Get a single document from the collection.
 * @param id Identification of document (assume _id property is of type K)
 * @return The document or null
 */
fun <T: Any, K> Collection<T>.getDocument(id: K): T? =
    collection.find(Filters.eq(id)).firstOrNull()

/**
 * Insert a new document (of type T) in the collection.
 * @param doc Document to insert.
 * @return true if writing was acknowledged.
 * @throws MongoWriteException if document already exists.
 */
fun <T: Any> Collection<T>.insertDocument(doc: T): Boolean =
    collection.insertOne(doc).insertedId!=null

/**
 * Updates the document with new content. Assumes the document has an _id property.
 * @param doc Document to update.
 * @return true if writing was acknowledged.
 */
fun <T: Any, K> Collection<T>.replaceDocument(id: K, doc: T): Boolean =
    collection.replaceOne(Filters.eq(id),doc).modifiedCount==1L

/**
 * Deletes the document with the identifier.
 * @param id Identification of document (_id property is of type K)
 * @return true if deleting was acknowledged.
 */
fun <T: Any,K> Collection<T>.deleteDocument(id: K): Boolean =
    collection.deleteOne(Filters.eq("_id",id)).deletedCount==1L

/**
 * Clear all documents from the collection.
 */
fun <T: Any> Collection<T>.deleteAllDocuments(): Boolean =
    collection.deleteMany(Filters.exists("_id")).wasAcknowledged()

import isel.leic.tds.storage.*
import org.bson.codecs.pojo.annotations.BsonProperty

data class Student(
    @BsonProperty("_id") val number: Int,
    val name: String,
    val grade: Double
)


fun main() {
    MongoDriver(nameDb = "test").use {
        val docs = it.getCollection<Student>("students")
        docs.apply {
            deleteAllDocuments()
            insertDocument(Student(51123, "Ana", 15.3))
            insertDocument(Student(51245, "Pedro", 18.5))
            getAllDocuments().forEach { println(it) }
            println(getDocument(51123))
            val s = Student(51123, "Ana", 16.1)
            replaceDocument(s.number, s)
            println(getDocument(51123))
        }
    }
}

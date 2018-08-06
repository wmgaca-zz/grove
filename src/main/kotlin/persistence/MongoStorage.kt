package grove.persistence

import com.mongodb.MongoCredential
import com.mongodb.ServerAddress
import com.mongodb.client.MongoDatabase
import org.litote.kmongo.*

class MongoStorage : Storage {

    private val db: MongoDatabase

    init {
        val host: String = System.getenv("MONGO_HOST") ?: "localhost"
        val port: Int = System.getenv("MONGO_PORT")?.toInt() ?: 27017
        val database: String = System.getenv("MONGO_DB_NAME") ?: "admin"
        val user: String = System.getenv("MONGO_USER") ?: "groveuser"
        val password: CharArray = System.getenv("MONGO_PASSWORD")?.toCharArray() ?: "grovepass".toCharArray()

        db = KMongo.createClient(
                ServerAddress(host, port),
                listOf(MongoCredential.createCredential(user, database, password)))
                .getDatabase(database)
    }

    override fun createCycle(name: String, financialYear: Int, quarter: Int) {
        val cycles = db.getCollection<Cycle>()

        cycles.insertOne(Cycle(
                name = name,
                financialYear = financialYear,
                quarter = quarter,
                objectives = listOf(Objective(
                    objective = "Make software great again.",
                    keyResults = listOf(KeyResult(keyResult = "Software be like 20% greater."))))))
    }

    override fun cycles() : List<Cycle> {
        val cycles = db.getCollection<Cycle>()

        return cycles.find().limit(20).toMutableList()
    }
}
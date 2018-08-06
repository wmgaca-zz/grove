package grove.persistence

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.newId

data class Cycle(
        @BsonId val key: Id<Cycle> = newId(),
        val name: String,
        val financialYear: Int,
        val quarter: Int,
        val objectives: List<Objective>)

data class Objective(val objective: String, val keyResults: List<KeyResult>)

data class KeyResult(val keyResult: String)
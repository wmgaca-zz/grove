package grove.controllers

import grove.persistence.Storage
import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.locations.*

@Location("/")
class Index

fun Route.index(storage: Storage) {

    get<Index> {
        val cycles = storage.cycles()

        call.respond(FreeMarkerContent("index.ftl", mapOf("cycles" to cycles)))
    }

}
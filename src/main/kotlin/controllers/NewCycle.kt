package grove.controllers

import grove.persistence.Storage
import grove.redirect
import io.ktor.application.*
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.request.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.response.respond
import io.ktor.routing.Route

@Location("/new/cycle")
class NewCycle

fun Route.newCycle(storage: Storage) {

    get<NewCycle> {
        call.respond(FreeMarkerContent("new-cycle.ftl", null))
    }

    post<NewCycle> {
        val post = call.receive<Parameters>()

        val name : String = post["name"] ?: return@post call.redirect(it)
        val financialYear : Int = post["financialYear"]?.toInt() ?: return@post call.redirect(it)
        val quarter : Int = post["quarter"]?.toInt() ?: return@post call.redirect(it)

        storage.createCycle(name, financialYear, quarter)

        call.redirect(Index())
    }

}
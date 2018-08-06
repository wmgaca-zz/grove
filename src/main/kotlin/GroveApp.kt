package grove

import com.fasterxml.jackson.databind.SerializationFeature
import freemarker.cache.ClassTemplateLoader
import grove.controllers.*
import grove.persistence.MongoStorage
import io.ktor.application.*
import io.ktor.content.resources
import io.ktor.content.static
import io.ktor.features.*
import io.ktor.freemarker.FreeMarker
import io.ktor.routing.*
import io.ktor.jackson.jackson
import io.ktor.locations.Locations
import io.ktor.locations.locations
import io.ktor.request.host
import io.ktor.request.port
import io.ktor.response.respondRedirect


suspend fun ApplicationCall.redirect(location: Any) {
    val host = request.host() ?: "localhost"
    val portSpec = request.port().let { if (it == 80) "" else ":$it" }
    val address = host + portSpec

    respondRedirect("http://$address${application.locations.href(location)}")
}

fun Application.configure(mongo: MongoStorage) {
    install(DefaultHeaders)
    install(CallLogging)
    install(ConditionalHeaders)
    install(Locations)
    install(PartialContent)
    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(Application::class.java.classLoader, "templates")
    }
    routing {
        static("/static") {
            resources("static")
        }

        index(mongo)
        newCycle(mongo)
    }
}

fun Application.main() {
    configure(MongoStorage())
}
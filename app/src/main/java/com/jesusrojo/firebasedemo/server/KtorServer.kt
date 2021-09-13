package com.jesusrojo.firebasedemo.server



import io.ktor.application.*
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.gson.*
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode

import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.Netty
import java.text.DateFormat
import java.util.concurrent.TimeUnit


class KtorServer {

    companion object {
        const val localhost8080 = "localhost:8080"
    }

    private val tokenDataBase = HashMap<String, String>()
    private lateinit var server: ApplicationEngine

    fun startServer() {
        println("startServer #")

//        val serviceAccount = FileInputStream("path/to/serviceAccountKey.json")//todo
//
//        val options: FirebaseOptions = FirebaseOptions.Builder()
//            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//            .setDatabaseUrl("https://fir-demo-a3232-default-rtdb.firebaseio.com")
//            .build()
//
//        FirebaseApp.initializeApp(options)


        server = embeddedServer(Netty, 8080) {

            install(StatusPages) {
                exception<Throwable> { e ->
                    call.respondText(e.localizedMessage, ContentType.Text.Plain, HttpStatusCode.InternalServerError)
                }
            }
            install(ContentNegotiation) {
                gson {
                    setDateFormat(DateFormat.LONG)
                    setPrettyPrinting()
                    disableHtmlEscaping()
                }
            }

            routing {
                post("/register/{userId}/{fcmToken}") {
                    val userId: String =  call.parameters["userId"]!!
                    val fcmToken: String =  call.parameters["fcmToken"]!!
                    tokenDataBase[userId] = fcmToken
                    println("User $userId, has new token $fcmToken")
                    call.respond(HttpStatusCode.OK)
                }

                get("/send_message/{userId}/{msgToSend}") {
                    val userId: String =  call.parameters["userId"]!!
                    val msgToSend: String =  call.parameters["msgToSend"]!!
                    val recipientToken: String? =  tokenDataBase[userId]
//todo
//                    val fcmMessage: Message = Message.builder()
//                        .putData("message", msgToSend)
//                        .setToken(recipientToken)
//                        .build()
//
//                    val response: String = FirebaseMessaging.getInstance().send(fcmMessage)
//
//                    if(response != null){
//                        call.respond(HttpStatusCode.OK)
//                    } else {
//                        call.respond(HttpStatusCode.BadRequest, "Failed sen")
//                    }
                }
            }
        }.start(wait = false) //IMPORTANT Android needs false
    }

    fun stopServer() {
        println("stopServer #")
	   //STOP THE SERVER  https://dev.to/viniciusccarvalho/graceful-shutdown-of-ktor-applications-1h53
       //https://stackoverflow.com/questions/67679674/start-and-stop-server-when-i-want
        Runtime.getRuntime().addShutdownHook(Thread {
            server?.stop(1, 1, TimeUnit.SECONDS)
        })
        Thread.currentThread().join()
    }
}
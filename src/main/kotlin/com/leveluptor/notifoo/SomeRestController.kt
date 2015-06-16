package com.leveluptor.notifoo

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectWriter
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import javax.ws.rs.GET
import javax.ws.rs.Path;
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

/**
 * TODO add description
 */

data class Message(val text:String)

@Path("foo")
public class SomeRestController {

    val mapper = jacksonObjectMapper().writer<ObjectWriter>().withDefaultPrettyPrinter();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public fun foo(): String {
        return mapper.writeValueAsString(Message("hello!"))
    }

}
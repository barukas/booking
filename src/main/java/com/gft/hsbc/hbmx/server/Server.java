package com.gft.hsbc.hbmx.server;

import io.muserver.Method;
import io.muserver.MuServer;
import io.muserver.MuServerBuilder;
import io.muserver.rest.RestHandlerBuilder;
import jakarta.ws.rs.Path;
import com.fasterxml.jackson.jakarta.rs.json.JacksonJsonProvider;

@Path("/test")
public class Server {

    public void createServer() {
        JacksonJsonProvider jacksonJsonProvider = new JacksonJsonProvider();
        MuServer server =  MuServerBuilder.httpServer()
                .withHttpPort(33333)
                .addHandler(RestHandlerBuilder.restHandler(new BookingController())
                        .addCustomWriter(jacksonJsonProvider)
                        .addCustomReader(jacksonJsonProvider)
                )
                .addHandler(Method.GET, "/", (request, response, pathParams) -> {
                    response.write("Hello, world");
                })
                .start();
        System.out.println("Started server at " + server.uri());

    }
}

package com.example.debeziumspostgres.camel.route;

import com.example.debeziumspostgres.camel.processor.PersonToIcebergProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class BackendDatabaseDebeziumDebeziumRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("debezium-postgres:dbz-postgres")
                .process(new PersonToIcebergProcessor())
                .log("body: ${body}");
    }
}

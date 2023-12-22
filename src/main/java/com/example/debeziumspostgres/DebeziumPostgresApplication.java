package com.example.debeziumspostgres;

import com.example.debeziumspostgres.person.Person;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.kafka.connect.data.Struct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class DebeziumPostgresApplication {

    public static void main(String[] args) {
        SpringApplication.run(DebeziumPostgresApplication.class, args);
    }

}

@Component
class DebeziumRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("debezium-postgres:dbz-postgres")
                .process(new PersonToIcebergProcessor())
                .log("body: ${body}");
    }
}

class PersonToIcebergProcessor implements Processor {

    Logger logger = LoggerFactory.getLogger(PersonToIcebergProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        String headers = exchange.getIn().getHeaders().toString();
        Struct body = exchange.getIn().getBody(Struct.class);
        Person person = new Person(
                body.getInt64("id"),
                body.getString("name"),
                body.getInt32("age"),
                body.getBoolean("is_active")
        );

        logger.info("headers: {}", headers);
        logger.info("person: " + person);
        exchange.getIn().setBody(person.toString());
    }
}
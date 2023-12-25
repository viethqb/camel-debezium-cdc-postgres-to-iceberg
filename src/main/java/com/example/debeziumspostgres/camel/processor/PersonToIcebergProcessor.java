package com.example.debeziumspostgres.camel.processor;

import com.example.debeziumspostgres.model.Person;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.kafka.connect.data.Struct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PersonToIcebergProcessor implements Processor {

    Logger logger = LoggerFactory.getLogger(PersonToIcebergProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        Map<String, ?> headers = exchange.getIn().getHeaders();
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

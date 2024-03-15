package io.zeebe;

import io.camunda.zeebe.exporter.api.Exporter;
import io.camunda.zeebe.exporter.api.context.Context;
import io.camunda.zeebe.exporter.api.context.Controller;
import io.camunda.zeebe.protocol.record.Record;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;


public class KafkaExporter implements Exporter {
  private Logger logger;

  private Controller controller;

 private KafkaProducer<String, String> producer;
  private ExporterConfiguration config;

  @Override
  public void configure(Context context) {
    logger = context.getLogger();
    config = context.getConfiguration().instantiate(ExporterConfiguration.class);

    logger.debug("Starting exporter with configuration: {}", config);

    final var filter = new RecordFilter(config);
    context.setFilter(filter);
  }

  @Override
  public void open(Controller controller) {
    this.controller = controller;
    producer = config.KafkaPublisher();
  }

  @Override
  public void export(Record<?> record) {
    if (producer != null) {



        ProducerRecord<String,String> msg= new ProducerRecord<>(config.getQueue(), Long.toString(record.getKey()), record.getValue().toString());

        producer.send(msg);
        logger.debug("Added a record to the kafka '{}' queue : {}", config.getQueue(), record.getValue());

    }

    controller.updateLastExportedRecordPosition(record.getPosition());
  }
}
package io.zeebe;


import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Optional;
import java.util.Properties;



public class ExporterConfiguration {

    private static final String ENV_PREFIX = "KAFKA_";





    public String getQueue() { return getEnv("QUEUE").orElse("zeebeRecords"); }

    public String getBootStrapServers() {return getEnv("BOOTSTRAP_SERVERS").orElse("");}
    public String getEnabledValueTypes() {
        return getEnv("ENABLED_VALUE_TYPES").orElse("");
    }
    public String getSecurityProtocol(){ return getEnv("SECURITY_PROTOCOL").orElse("PLAINTEXT");}

    public String getSASLMechanism(){return getEnv("SASL_MECHANISM").orElse("GSSAPI");}

    public String getSASL_JAS_CONFIG(){return getEnv("SASL_JAAS_CONFIG").orElse(null);}
    public String getEnabledRecordTypes() {
        return getEnv("ENABLED_RECORD_TYPES").orElse("");
    }

    private Optional<String> getEnv(String name) {
        return Optional.ofNullable(System.getenv(ENV_PREFIX + name));
    }




    public KafkaProducer<String, String> KafkaPublisher() {
            Properties props = new Properties();
            props.put("bootstrap.servers", getBootStrapServers());
            props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            //props.put("security.protocol",getSecurityProtocol());
            //props.put("sasl.mechanism",getSASLMechanism());
            //props.put("sasl.jaas.config",getSASL_JAS_CONFIG());
            KafkaProducer<String, String> producer= new KafkaProducer<>(props);
            return producer;
        }

    @Override
    public String toString() {
        return "[Queue="
                + getQueue()
                + ", enabledValueTypes="
                + getEnabledValueTypes()
                + ", enabledRecordTypes="
                + getEnabledRecordTypes()
                + "]";
    }


}

package com.razvanbaboiu.event_bucket.event_manager.configuration;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventProcessingMessageQueueConfiguration {
    public static final String TOPIC_EXCHANGE_NAME = "events";
    public static final String EVENT_ROUTING_KEY = "event.#";
    public static final String QUEUE_NAME = "event-processing";

    // Dead letter queue configuration
    public static final String DLQ_ROUTING_KEY = "deadletter.#";
    public static final String DLX = TOPIC_EXCHANGE_NAME + ".dlx";
    public static final String DLQ = QUEUE_NAME + ".dlq";

    @Bean
    Queue eventQueue() {
        return QueueBuilder.durable(QUEUE_NAME)
                .withArgument("x-dead-letter-exchange", DLX)
                .withArgument("x-dead-letter-routing-key", DLQ_ROUTING_KEY)
                .build();
    }

    @Bean
    TopicExchange eventExchange() {
        return new TopicExchange(TOPIC_EXCHANGE_NAME);
    }

    @Bean
    Binding eventBinding() {
        return BindingBuilder.bind(eventQueue()).to(eventExchange()).with(EVENT_ROUTING_KEY);
    }

    // Dead Letter Queue Configuration
    @Bean
    Queue eventDeadLetterQueue() {
        return QueueBuilder.durable(DLQ).build();
    }

    @Bean
    TopicExchange eventDeadLetterExchange() {
        return new TopicExchange(DLX);
    }

    @Bean
    Binding eventDeadLetterBinding() {
        return BindingBuilder
                .bind(eventDeadLetterQueue())
                .to(eventDeadLetterExchange())
                .with(DLQ_ROUTING_KEY);
    }
}

package com.razvanbaboiu.event_bucket.event_manager.configuration;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InsightProcessingMessageQueueConfiguration {
    public static final String TOPIC_EXCHANGE_NAME = "insights";
    public static final String EVENT_ROUTING_KEY = "insight.#";
    public static final String QUEUE_NAME = "insight-processing";

    // Dead letter queue configuration
    public static final String DLQ_ROUTING_KEY = "deadletter.#";
    public static final String DLX = TOPIC_EXCHANGE_NAME + ".dlx";
    public static final String DLQ = QUEUE_NAME + ".dlq";

    @Bean
    Queue insightQueue() {
        return QueueBuilder.durable(QUEUE_NAME)
                .withArgument("x-dead-letter-exchange", DLX)
                .withArgument("x-dead-letter-routing-key", DLQ_ROUTING_KEY)
                .build();
    }

    @Bean
    TopicExchange insightExchange() {
        return new TopicExchange(TOPIC_EXCHANGE_NAME);
    }

    @Bean
    Binding insightBinding() {
        return BindingBuilder.bind(insightQueue()).to(insightExchange()).with(EVENT_ROUTING_KEY);
    }

    // Dead Letter Queue Configuration
    @Bean
    Queue insightDeadLetterQueue() {
        return QueueBuilder.durable(DLQ).build();
    }

    @Bean
    TopicExchange insightDeadLetterExchange() {
        return new TopicExchange(DLX);
    }

    @Bean
    Binding insightDeadLetterBinding() {
        return BindingBuilder
                .bind(insightQueue())
                .to(insightExchange())
                .with(DLQ_ROUTING_KEY);
    }
}

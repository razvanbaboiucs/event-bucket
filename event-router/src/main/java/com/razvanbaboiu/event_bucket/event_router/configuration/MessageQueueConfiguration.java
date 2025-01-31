package com.razvanbaboiu.event_bucket.event_router.configuration;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageQueueConfiguration {
    public static final String EVENTS_TOPIC_EXCHANGE_NAME = "events";
    public static final String IDENTIFICATION_TOPIC_EXCHANGE_NAME = "identifications";
    public static final String INSIGHT_TOPIC_EXCHANGE_NAME = "insights";
    public static final String EVENT_ROUTING_KEY = "event.#";
    public static final String IDENTIFICATION_ROUTING_KEY = "identification.#";
    public static final String INSIGHT_ROUTING_KEY = "insight.#";

    @Bean
    TopicExchange eventsExchangeTopic() {
        return new TopicExchange(EVENTS_TOPIC_EXCHANGE_NAME);
    }

    @Bean
    TopicExchange identificationsExchangeTopic() {
        return new TopicExchange(IDENTIFICATION_TOPIC_EXCHANGE_NAME);
    }

    @Bean
    TopicExchange insightsExchangeTopic() {
        return new TopicExchange(INSIGHT_TOPIC_EXCHANGE_NAME);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }
}

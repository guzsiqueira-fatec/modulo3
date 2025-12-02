package br.gov.sp.fatec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class MessageConsumerFunction {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Bean
    public Consumer<Message> messageConsumer() {
        return message -> {
            logger.info("Received message: {}", message);
        };
    }
}

package com.example.demo.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;

import java.time.Instant;
import java.util.function.Function;

@Configuration
public class PojoConfiguration {

    @Bean
    Function<Flux<PojoIn>, Flux<PojoOut>> handler() {

        return flux -> flux.map(in -> {
            Assert.notNull(in.value, "Value shouldn't be null.");
            return PojoOut.from(in);
        });
    }

    @Data
    public static class PojoIn {

        private String value;

    }

    @Data
    public static class PojoOut {

        private String value;

        public static PojoOut from(PojoIn in) {
            val out = new PojoOut();
            out.setValue(in.getValue());
            return out;
        }

    }

}

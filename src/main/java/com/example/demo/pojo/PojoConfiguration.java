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

    /**
     * array( 'sku' => $product->get_sku(), 'subscription_id' => $subscription->get_id(),
     * 'user_id' => $user->ID, 'user_last_name' => $user->last_name, 'user_first_name' =>
     * $user->first_name, 'user_email' => $user->user_email, 'action' => [activate|suspend]
     * 'data' => array( 'status' => $subscription->get_status(), 'start_date' => strtotime(
     * $subscription->get_date( 'start' ) ) * 1000, 'end_date' => strtotime(
     * $subscription->get_date( 'next_payment' ) ) * 1000, 'renewals' => count( $renewals ) -
     * 1, 'revenues' => $revenues, ), 'package_type' => $package_type, )
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ProvisioningIn {

        private String sku;

        @JsonProperty("subscription_id")
        private Long subscriptionId;

        @JsonProperty("user_id")
        private Long userId;

        @JsonProperty("user_last_name")
        private String lastName;

        @JsonProperty("user_first_name")
        private String firstName;

        @JsonProperty("user_email")
        private String email;

        // activate|suspend
        private String action;

        @JsonProperty("package_type")
        private String packageType;

        private Data data;

        @lombok.Data
        public static class Data {

            private String status;

            @JsonProperty("start_date")
            private Instant startDate;

            @JsonProperty("end_date")
            private Instant endDate;

            private Integer renewals;

            private Double revenues;

        }

    }

}

package com.alexpoty.estatein.gateway.routes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import java.net.URI;

import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.setPath;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;

@Configuration
public class Routes {

    @Value("${property.service.url}")
    private String propertyServiceUrl;
    @Value("${booking.service.url}")
    private String bookingServiceUrl;
    @Value("${image.service.url}")
    private String imageServiceUrl;

    @Bean
    public RouterFunction<ServerResponse> propertyServiceRoute() {
        return route("property_service")
                .route(RequestPredicates.path("/api/property"), HandlerFunctions.http(propertyServiceUrl))
                .route(RequestPredicates.path("/api/property/{id}"), HandlerFunctions.http(propertyServiceUrl))
                .route(RequestPredicates.path("/api/property/page"), HandlerFunctions.http(propertyServiceUrl))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("propertyServiceCircuitBreaker",
                        URI.create("forward:/fallbackRoute")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> propertyServiceSwaggerRoute() {
        return GatewayRouterFunctions.route("property_service_swagger")
                .route(RequestPredicates.path("/aggregate/property-service/v3/api-docs"),
                        HandlerFunctions.http(propertyServiceUrl))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("propertyServiceSwaggerCircuitBreaker",
                        URI.create("forward:/fallbackRoute")))
                .filter(setPath("/api-docs"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> bookingServiceRoute() {
        return route("booking_service")
                .route(RequestPredicates.path("/api/booking"), HandlerFunctions.http(bookingServiceUrl))
                .route(RequestPredicates.path("/api/booking/{id}"), HandlerFunctions.http(bookingServiceUrl))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("bookingServiceCircuitBreaker",
                        URI.create("forward:/fallbackRoute")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> bookingServiceSwaggerRoute() {
        return GatewayRouterFunctions.route("booking_service_swagger")
                .route(RequestPredicates.path("/aggregate/booking-service/v3/api-docs"),
                        HandlerFunctions.http(bookingServiceUrl))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("bookingServiceSwaggerCircuitBreaker",
                        URI.create("forward:/fallbackRoute")))
                .filter(setPath("/api-docs"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> imageServiceRoute() {
        return route("image_service")
                .route(RequestPredicates.path("/api/image"), HandlerFunctions.http(imageServiceUrl))
                .route(RequestPredicates.path("/api/image/{id}"), HandlerFunctions.http(imageServiceUrl))
                .route(RequestPredicates.path("/api/image/upload"), HandlerFunctions.http(imageServiceUrl))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("imageServiceCircuitBreaker",
                        URI.create("forward:/fallbackRoute")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> imageServiceSwaggerRoute() {
        return route("image_service_swagger")
                .route(RequestPredicates.path("/aggregate/image-service/v3/api-docs"),
                        HandlerFunctions.http(imageServiceUrl))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("imageServiceSwaggerCircuitBreaker",
                        URI.create("forward:/fallbackRoute")))
                .filter(setPath("/api-docs"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> fallbackRoute() {
        return route("fallbackRoute")
                .GET("/fallbackRoute", request -> ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body("Sorry, this service is unavailable at the moment"))
                .POST("/fallbackRoute", request -> ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body("Sorry this service is unavailable at the moment"))
                .build();
    }
}

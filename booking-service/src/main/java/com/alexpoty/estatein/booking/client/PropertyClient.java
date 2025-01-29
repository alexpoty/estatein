package com.alexpoty.estatein.booking.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "property", url = "${property.service.url}")
public interface PropertyClient {
}

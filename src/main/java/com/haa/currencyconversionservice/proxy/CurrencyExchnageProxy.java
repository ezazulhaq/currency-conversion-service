package com.haa.currencyconversionservice.proxy;

import com.haa.currencyconversionservice.bean.CurrencyConversion;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name = "currency-exchange-service", url = "localhost:8001")
//@FeignClient(name = "currency-exchange-service")
@FeignClient(name = "currency-exchange-service", url = "${CURRENCY_EXCHANGE_SERVICE_HOST:http://localhost}:8001")
public interface CurrencyExchnageProxy {

    @GetMapping(path = "/currency-exchange/from/{from}/to/{to}")
    public CurrencyConversion fetchCurrencyExchange(@PathVariable String from, @PathVariable String to);
}

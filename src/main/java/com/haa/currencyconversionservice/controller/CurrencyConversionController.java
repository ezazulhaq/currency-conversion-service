package com.haa.currencyconversionservice.controller;

import java.math.BigDecimal;
import java.util.HashMap;

import com.haa.currencyconversionservice.bean.CurrencyConversion;
import com.haa.currencyconversionservice.proxy.CurrencyExchnageProxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConversionController {

        private Logger logger = LoggerFactory.getLogger(CurrencyConversionController.class);

        @Autowired
        private CurrencyExchnageProxy proxy;

        @GetMapping("currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
        public CurrencyConversion calculateCurrencyConversion(@PathVariable String from, @PathVariable String to,
                        @PathVariable BigDecimal quantity) {

                logger.info("Calculate currency conversion called from {} to {} with {}", from, to, quantity);

                HashMap<String, String> uriVariables = new HashMap<>();
                uriVariables.put("from", from);
                uriVariables.put("to", to);

                ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate().getForEntity(
                                "http://localhost:8001/currency-exchange/from/{from}/to/{to}", CurrencyConversion.class,
                                uriVariables);

                CurrencyConversion currencyConversion = responseEntity.getBody();

                return new CurrencyConversion(currencyConversion.getId(), from, to, quantity,
                                currencyConversion.getConversionMultiple(),
                                quantity.multiply(currencyConversion.getConversionMultiple()),
                                currencyConversion.getEnvironment());
        }

        @GetMapping("currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
        public CurrencyConversion calculateCurrencyConversionFeign(@PathVariable String from, @PathVariable String to,
                        @PathVariable BigDecimal quantity) {

                logger.info("Calculate currency conversion called from {} to {} with {}", from, to, quantity);

                CurrencyConversion currencyConversion = proxy.fetchCurrencyExchange(from, to);

                return new CurrencyConversion(currencyConversion.getId(), from, to, quantity,
                                currencyConversion.getConversionMultiple(),
                                quantity.multiply(currencyConversion.getConversionMultiple()),
                                currencyConversion.getEnvironment());
        }

}

package com.palczynski.rest;


import com.palczynski.dto.TradeDto;
import com.palczynski.util.ValidList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "trade")
public class TradeController {

    @Autowired
    protected LocalValidatorFactoryBean validator;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void validateTrades(@RequestBody @Valid ValidList<TradeDto> trades) {
    }

}

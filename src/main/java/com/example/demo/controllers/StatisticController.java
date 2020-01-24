package com.example.demo.controllers;

import com.example.demo.dto.StatisticDTO;
import com.example.demo.service.AgreementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by den on 24.01.20.
 */
@RestController
@RequestMapping("/rest")
public class StatisticController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final AgreementService agreementService;

    @Autowired
    public StatisticController(AgreementService agreementService) {
        this.agreementService = agreementService;
    }


    /**
     * Возвращает статистику по договорам
     * @param clientId  ИД клиента
     * @param productId  ИД продукта
     * @return список договоров
     */
    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getStatistics(@RequestParam(value = "clientId", required = false) Integer clientId,
                                        @RequestParam(value = "productId", required = false) Integer productId) {
        logger.info("GET ../rest/statistics");
        StatisticDTO result = agreementService.getStatistics(clientId, productId);
        return new ResponseEntity(result, HttpStatus.OK);
    }

}

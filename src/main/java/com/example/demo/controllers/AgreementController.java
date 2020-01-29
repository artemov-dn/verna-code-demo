package com.example.demo.controllers;

import com.example.demo.dto.AgreementDTO;
import com.example.demo.exception.AgreementIsEmptyException;
import com.example.demo.exception.AgreementNotFoundException;
import com.example.demo.exception.AgreementWithNegativeOrZeroAmount;
import com.example.demo.exception.AgreementWithNullProperty;
import com.example.demo.service.AgreementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by den on 21.01.20.
 */
@RestController
@RequestMapping("/rest")
public class AgreementController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final AgreementService agreementService;

    @Autowired
    public AgreementController(AgreementService agreementService) {
        this.agreementService = agreementService;
    }

    /**
     * Добавляет новый договор
     * @param newAgreementDTO новый договор
     * @return добавленный договор
     */
    @RequestMapping(value = "/agreements", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity addAgreement(@RequestBody AgreementDTO newAgreementDTO) {
        logger.info("POST ../rest/agreements");
        if (newAgreementDTO != null && newAgreementDTO.getClientId() != null && newAgreementDTO.getProductId() != null &&
                newAgreementDTO.getAmount() != null && newAgreementDTO.getStartDate() != null) {
            if (newAgreementDTO.getAmount().compareTo(new BigDecimal(0)) > 0) {
                AgreementDTO result = agreementService.addAgreement(newAgreementDTO);
                return new ResponseEntity(result, HttpStatus.CREATED);
            } else {
                throw new AgreementWithNegativeOrZeroAmount();
            }
        } else {
            throw new AgreementWithNullProperty();
        }
    }


    /**
     * Возвращает договор по заданному id
     * @param id ИД договора
     * @return список договоров
     */
    @RequestMapping(value = "/agreements/{id}", method = RequestMethod.GET)
    @ResponseBody
    public AgreementDTO getAgreement(@PathVariable("id") Integer id) {
        logger.info("GET ../rest/agreements/{}", id);
        return agreementService.getAgreement(id)
                .orElseThrow(() -> new AgreementNotFoundException());
    }


    /**
     * Удаляет договор по заданному id
     * @param id ИД договора
     * @return
     */
    @RequestMapping(value = "/agreements/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public AgreementDTO deleteAgreement(@PathVariable("id") Integer id) {
        logger.info("DELETE ../rest/agreements/{}", id);
        return agreementService.deleteAgreement(id)
                .orElseThrow(() -> new AgreementNotFoundException());
    }


    /**
     * Возвращает список договоров
     * @param clientId  ИД клиента
     * @param productId  ИД продукта
     * @return список договоров
     */
    @RequestMapping(value = "/agreements", method = RequestMethod.GET)
    @ResponseBody
    public List<AgreementDTO> getAgreements(@RequestParam(value = "clientId", required = false) Integer clientId,
                                        @RequestParam(value = "productId", required = false) Integer productId) {
        logger.info("GET ../rest/agreements");
        return agreementService.getAgreements(clientId, productId);
    }

}

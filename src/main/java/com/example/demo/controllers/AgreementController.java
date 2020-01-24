package com.example.demo.controllers;

import com.example.demo.dto.AgreementDTO;
import com.example.demo.dto.NewAgreementDTO;
import com.example.demo.service.AgreementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity addAgreements(@RequestBody NewAgreementDTO newAgreementDTO)  {
        logger.info("POST ../rest/agreements");
        AgreementDTO result = agreementService.addAgreement(newAgreementDTO);
        if (result != null) {
            return new ResponseEntity(result, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Возвращает договор по заданному id
     * @param id ИД договора
     * @return список договоров
     */
    @RequestMapping(value = "/agreements/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getAgreement(@PathVariable("id") Integer id) {
        logger.info("GET ../rest/agreements/{}", id);
        AgreementDTO result = agreementService.getAgreement(id);
        if (result != null) {
            return new ResponseEntity(result, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }


    /**
     * Удаляет договор по заданному id
     * @param id ИД договора
     * @return
     */
    @RequestMapping(value = "/agreements/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity deleteAgreement(@PathVariable("id") Integer id) {
        logger.info("DELETE ../rest/agreements/{}", id);
        AgreementDTO result = agreementService.deleteAgreement(id);
        if (result != null) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }


    /**
     * Возвращает список договоров
     * @param clientId  ИД клиента
     * @param productId  ИД продукта
     * @return список договоров
     */
    @RequestMapping(value = "/agreements", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getAgreements(@RequestParam(value = "clientId", required = false) Integer clientId,
                                        @RequestParam(value = "productId", required = false) Integer productId) {
        logger.info("GET ../rest/agreements");
        List<AgreementDTO> result = agreementService.getAgreements(clientId, productId);
        return new ResponseEntity(result, HttpStatus.OK);
    }

}

package com.example.demo.controllers;

import com.example.demo.dto.AgreementDTO;
import com.example.demo.exception.AgreementIsEmptyException;
import com.example.demo.exception.AgreementNotFoundException;
import com.example.demo.exception.AgreementWithNegativeOrZeroAmount;
import com.example.demo.exception.AgreementWithNullProperty;
import com.example.demo.service.AgreementService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(
            summary = "Добавить договор",
            description = "Добавляет новый договор",
            responses = {
                    @ApiResponse(
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AgreementDTO.class)),
                            responseCode = "201"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Некорректный запрос"
                    )
            }
    )

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
    @Operation(
            summary = "Найти договор по id",
            description = "Возвращает договор по заданному id",
            responses = {
                    @ApiResponse(
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AgreementDTO.class)),
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Договор с заданным id не существует"
                    )
            }
    )
    public AgreementDTO getAgreement(
            @Parameter(description = "Идентификатор договора", required = true) @PathVariable("id") Integer id) {
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
    @Operation(
            summary = "Удалить договор по id",
            description = "Удаляет договор по заданному id",
            responses = {
                    @ApiResponse(
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AgreementDTO.class)),
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Договор с заданным id не существует"
                    )
            }
    )
    public AgreementDTO deleteAgreement(
            @Parameter(description = "Идентификатор договора", required = true) @PathVariable("id") Integer id) {
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
    @Operation(
            summary = "Найти договора",
            description = "Возвращает список договоров с заданными clientId и productId. Если clientId и productId не заданы, возвращает все договора.",
            responses = {
                    @ApiResponse(
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = AgreementDTO.class))),
                            responseCode = "200"
                    )
            }
    )
    public List<AgreementDTO> getAgreements(
            @Parameter(description = "Идентификатор клиента")@RequestParam(value = "clientId", required = false) Integer clientId,
            @Parameter(description = "Идентификатор продукта")@RequestParam(value = "productId", required = false) Integer productId) {
        logger.info("GET ../rest/agreements");
        return agreementService.getAgreements(clientId, productId);
    }

}

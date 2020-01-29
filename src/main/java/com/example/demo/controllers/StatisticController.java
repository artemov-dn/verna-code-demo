package com.example.demo.controllers;

import com.example.demo.dto.AgreementDTO;
import com.example.demo.dto.StatisticDTO;
import com.example.demo.service.AgreementService;
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

import java.util.List;

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
    @Operation(
            summary = "Статистика по договорам",
            description = "Возвращает статистику по договорам с заданными clientId и productId. Если clientId и productId не заданы, возвращает статистику по всем договорам.",
            responses = {
                    @ApiResponse(
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = StatisticDTO.class))),
                            responseCode = "200"
                    )
            }
    )
    public StatisticDTO getStatistics(
                @Parameter(description = "Идентификатор клиента")@RequestParam(value = "clientId", required = false) Integer clientId,
                @Parameter(description = "Идентификатор продукта")@RequestParam(value = "productId", required = false) Integer productId) {
        logger.info("GET ../rest/statistics");
        return agreementService.getStatistics(clientId, productId);
    }

}

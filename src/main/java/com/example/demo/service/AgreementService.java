package com.example.demo.service;

import com.example.demo.dto.AgreementDTO;
import com.example.demo.dto.StatisticDTO;
import com.example.demo.entity.Agreement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Created by den on 21.01.20.
 */
@Service
public class AgreementService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private AtomicInteger LastAgreementId = new AtomicInteger(0);

    private Map<Integer, AgreementDTO> agreements = new ConcurrentHashMap<>();

    /**
     * Добавляет договор
     * @param agreement новый договор
     * @return добавленный договор
     */
    public AgreementDTO addAgreement(Agreement agreement){
        if (agreement != null && agreement.getClientId() != null && agreement.getProductId() != null &&
                agreement.getAmount() != null && agreement.getStartDate() != null &&
                agreement.getAmount().compareTo(new BigDecimal(0)) > 0) {
            AgreementDTO result = new AgreementDTO.Builder()
                    .agreementId(LastAgreementId.addAndGet(1))
                    .clientId(agreement.getClientId())
                    .productId(agreement.getProductId())
                    .amount(agreement.getAmount())
                    .startDate(agreement.getStartDate())
                    .timestamp(Calendar.getInstance().getTime())
                    .build();
            agreements.put(result.getAgreementId(), result);
            return result;
        } else {
            return null;
        }
    }


    /**
     * Возвращает список договоров
     * @param clientId  ИД клиента, по которому нужно отфильтровать договора
     * @param productId ИД продукта, по которому нужно отфильтровать договора
     * @return список договоров
     */
    public List<AgreementDTO> getAgreements(Integer clientId, Integer productId){
        if (clientId == null && productId == null) {
            return new ArrayList<AgreementDTO>(agreements.values());
        } else {
            return agreements.values().parallelStream()
                    .filter(dto -> (clientId == null || dto.getClientId() == clientId))
                    .filter(dto -> (productId == null || dto.getProductId() == productId))
                    .collect(Collectors.toList());
        }
    }


    /**
     * Возвращает договор по заданному id
     * @param id  ИД договора
     * @return договор
     */
    public AgreementDTO getAgreement(Integer id){
        if (id != null) {
            return agreements.get(id);
        } else {
            return null;
        }
    }


    /**
     * Удаляет договор по заданному id
     * @param id  ИД договора
     * @return договор
     */
    public AgreementDTO deleteAgreement(Integer id){
        if (id != null) {
            return agreements.remove(id);
        } else {
            return null;
        }
    }


    /**
     * Возвращает статистику
     * @param clientId  ИД клиента, по которому нужно отфильтровать договора
     * @param productId ИД продукта, по которому нужно отфильтровать договора
     * @return статистика
     */
    public StatisticDTO getStatistics(Integer clientId, Integer productId){
        Stream<AgreementDTO> stream = agreements.values().parallelStream();
        if (clientId != null) {
            stream = stream.filter(dto -> (clientId == null || dto.getClientId() == clientId));
        }
        if (productId == null) {
            stream = stream.filter(dto -> (productId == null || dto.getProductId() == productId));
        }
        return stream.map(dto ->
                        new StatisticDTO.Builder().count(1)
                                .minAmount(dto.getAmount())
                                .maxAmount(dto.getAmount())
                                .sum(dto.getAmount())
                                .build())
                .reduce((s1, s2) -> new StatisticDTO.Builder()
                        .count(s1.getCount()+s2.getCount())
                        .minAmount(s1.getMinAmount().compareTo(s2.getMinAmount()) < 0 ? s1.getMinAmount() : s2.getMinAmount())
                        .maxAmount(s1.getMaxAmount().compareTo(s2.getMaxAmount()) > 0 ? s1.getMaxAmount() : s2.getMaxAmount())
                        .sum(s1.getSum().add(s2.getSum()))
                        .build())
                .orElse(new StatisticDTO.Builder()
                        .count(0)
                        .minAmount(new BigDecimal("0"))
                        .maxAmount(new BigDecimal("0"))
                        .sum(new BigDecimal("0"))
                        .build());
    }

}

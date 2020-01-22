package com.example.demo.service;

import com.example.demo.dto.AgreementDTO;
import com.example.demo.dto.StatisticDTO;
import com.example.demo.entity.Agreement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class AgreementServiceTest {

    @Test
    void addNullAgreement() {
        AgreementService agreementService = new AgreementService();
        AgreementDTO actual = agreementService.addAgreement(null);
        assertNull(actual);
    }


    @Test
    void addAgreementWithNullProperty() {
        AgreementService agreementService = new AgreementService();
        Agreement agreement = new Agreement();
        agreement.setClientId(null);
        agreement.setProductId(12);
        agreement.setAmount(new BigDecimal("112.34"));
        agreement.setStartDate(new Date(1579564800L * 1000));// "2020-01-21"
        AgreementDTO actual = agreementService.addAgreement(agreement);
        assertNull(actual);
        agreement.setClientId(11);
        agreement.setProductId(null);
        actual = agreementService.addAgreement(agreement);
        assertNull(actual);
        agreement.setProductId(12);
        agreement.setAmount(null);
        actual = agreementService.addAgreement(agreement);
        assertNull(actual);
        agreement.setAmount(new BigDecimal("112.34"));
        agreement.setStartDate(null);// "2020-01-21"
        actual = agreementService.addAgreement(agreement);
        assertNull(actual);
    }


    @Test
    void addAgreementWithNegativeOrZeroAmount() {
        AgreementService agreementService = new AgreementService();
        Agreement agreement = new Agreement();
        agreement.setClientId(11);
        agreement.setProductId(12);
        agreement.setStartDate(new Date(1579564800L * 1000));// "2020-01-21"
        agreement.setAmount(new BigDecimal("0"));
        AgreementDTO actual = agreementService.addAgreement(agreement);
        assertNull(actual);
        agreement.setAmount(new BigDecimal("-100"));
        actual = agreementService.addAgreement(agreement);
        assertNull(actual);
    }


    @Test
    void addAgreement() {
        AgreementService agreementService = new AgreementService();
        Agreement agreement = new Agreement();
        agreement.setClientId(11);
        agreement.setProductId(12);
        agreement.setAmount(new BigDecimal("112.34"));
        agreement.setStartDate(new Date(1579564800L * 1000));// "2020-01-21"

        List<AgreementDTO> actualList = agreementService.getAgreements(null, null);
        assertNotNull(actualList);
        assertEquals(0, actualList.size());

        long expected_now = Calendar.getInstance().getTime().getTime();
        AgreementDTO actual = agreementService.addAgreement(agreement);

        assertEquals(1, actual.getAgreementId());
        assertEquals(11,actual.getClientId());
        assertEquals(12, actual.getProductId());
        assertEquals(new BigDecimal("112.34"), actual.getAmount());
        assertEquals(new Date(1579564800L * 1000), actual.getStartDate());
        assertEquals(expected_now, actual.getTimestamp().getTime(), 1000);// проверяем, что установилось текущее время с точностью до секунды


        actualList = agreementService.getAgreements(null, null);
        assertTrue(actualList.size() == 1);

        actual = actualList.get(0);
        assertEquals(1, actual.getAgreementId());
        assertEquals(11,actual.getClientId());
        assertEquals(12, actual.getProductId());
        assertEquals(new BigDecimal("112.34"), actual.getAmount());
        assertEquals(new Date(1579564800L * 1000), actual.getStartDate());
        assertEquals(expected_now, actual.getTimestamp().getTime(), 1000);// проверяем, что установилось текущее время с точностью до секунды
    }


    @Test
    void getAgreements() {
        AgreementService agreementService = new AgreementService();

        Agreement agreement = new Agreement();
        agreement.setClientId(1);
        agreement.setProductId(1);
        agreement.setAmount(new BigDecimal("202.55"));
        agreement.setStartDate(new Date(1579910400L * 1000));// "2020-01-21"
        agreementService.addAgreement(agreement);
        agreement.setClientId(2);
        agreementService.addAgreement(agreement);
        agreement.setProductId(3);
        agreementService.addAgreement(agreement);
        agreement.setClientId(3);
        agreementService.addAgreement(agreement);

        List<AgreementDTO> actualList = agreementService.getAgreements(null, null);
        assertEquals(4, actualList.size());

        actualList = agreementService.getAgreements(1, null);
        assertEquals(1, actualList.size());
        assertEquals(1, actualList.get(0).getClientId());

        actualList = agreementService.getAgreements(2, null);
        assertEquals(2, actualList.size());
        assertEquals(2, actualList.get(0).getClientId());
        assertEquals(2, actualList.get(1).getClientId());

        actualList = agreementService.getAgreements(null, 1);
        assertEquals(2, actualList.size());
        assertEquals(1, actualList.get(0).getProductId());
        assertEquals(1, actualList.get(1).getProductId());

        actualList = agreementService.getAgreements(2, 3);
        assertEquals(1, actualList.size());
        assertEquals(2, actualList.get(0).getClientId());
        assertEquals(3, actualList.get(0).getProductId());

        actualList = agreementService.getAgreements(1, 3);
        assertNotNull(actualList);
        assertEquals(0, actualList.size());

        actualList = agreementService.getAgreements(1, 6);
        assertNotNull(actualList);
        assertEquals(0, actualList.size());

        actualList = agreementService.getAgreements(10, 3);
        assertNotNull(actualList);
        assertEquals(0, actualList.size());

        actualList = agreementService.getAgreements(10, 6);
        assertNotNull(actualList);
        assertEquals(0, actualList.size());

        actualList = agreementService.getAgreements(-10, null);
        assertNotNull(actualList);
        assertEquals(0, actualList.size());

        actualList = agreementService.getAgreements(null, -6);
        assertNotNull(actualList);
        assertEquals(0, actualList.size());
    }


    @Test
    void getAgreement() {
        AgreementService agreementService = new AgreementService();

        Agreement agreement = new Agreement();
        agreement.setClientId(21);
        agreement.setProductId(22);
        agreement.setAmount(new BigDecimal("202.55"));
        agreement.setStartDate(new Date(1579910400L * 1000));// "2020-01-21"
        agreementService.addAgreement(agreement);
        agreement.setClientId(30);
        agreementService.addAgreement(agreement);
        agreement.setProductId(20);
        agreementService.addAgreement(agreement);

        AgreementDTO actual = agreementService.getAgreement(2);

        assertEquals(2, actual.getAgreementId());
        assertEquals(30,actual.getClientId());
        assertEquals(22, actual.getProductId());
        assertEquals(new BigDecimal("202.55"), actual.getAmount());
        assertEquals(new Date(1579910400L * 1000), actual.getStartDate());
    }


    @Test
    void getNullAgreement() {
        AgreementService agreementService = new AgreementService();
        AgreementDTO actual = agreementService.getAgreement(null);
        assertNull(actual);
    }


    @Test
    void getZeroAgreement() {
        AgreementService agreementService = new AgreementService();
        AgreementDTO actual = agreementService.getAgreement(0);
        assertNull(actual);
    }


    @Test
    void deleteAgreement() {
        AgreementService agreementService = new AgreementService();

        Agreement agreement = new Agreement();
        agreement.setClientId(21);
        agreement.setProductId(22);
        agreement.setAmount(new BigDecimal("202.55"));
        agreement.setStartDate(new Date(1579910400L * 1000));// "2020-01-21"
        agreementService.addAgreement(agreement);
        agreement.setClientId(10);
        agreementService.addAgreement(agreement);
        agreement.setClientId(15);
        agreementService.addAgreement(agreement);

        List<AgreementDTO> actualList = agreementService.getAgreements(null, null);
        assertEquals(3, actualList.size());
        AgreementDTO actual = agreementService.getAgreement(2);
        assertNotNull(actual);
        assertEquals(2, actual.getAgreementId());

        agreementService.deleteAgreement(2);
        actualList = agreementService.getAgreements(null, null);
        assertEquals(2, actualList.size());
        actual = agreementService.getAgreement(2);
        assertNull(actual);
    }


    @Test
    void deleteNullAgreement() {
        AgreementService agreementService = new AgreementService();
        AgreementDTO actual = agreementService.deleteAgreement(null);
        assertNull(actual);
    }


    @Test
    void deleteZeroAgreement() {
        AgreementService agreementService = new AgreementService();
        AgreementDTO actual = agreementService.deleteAgreement(0);
        assertNull(actual);
    }


    @Test
    void getStatNotThrowException()throws InterruptedException  {
        AgreementService agreementService = new AgreementService();
        ExecutorService executorService =
                Executors.newFixedThreadPool(4);
        Agreement agreement = new Agreement();
        agreement.setClientId(21);
        agreement.setProductId(22);
        agreement.setAmount(new BigDecimal("1"));
        agreement.setStartDate(new Date(1579910400L * 1000));// "2020-01-21"
        long startTime = System.nanoTime();
        for (int i = 0; i < 4; i++) {
            executorService.execute(() -> {
                for (int j = 0; j < 500__000; j++) {
                    agreement.setProductId(ThreadLocalRandom.current().nextInt(5));
                    agreementService.addAgreement(agreement);
                }
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);
        long addAvgRuntime = (System.nanoTime() - startTime) / 500__000;

        System.out.println("AddAvgRuntime = " + addAvgRuntime);

        executorService = Executors.newFixedThreadPool(3);

        Future addFuture = executorService.submit(() -> {
            for (int j = 0; j < 500__000; j++) {
                agreement.setProductId(ThreadLocalRandom.current().nextInt(5));
                agreementService.addAgreement(agreement);
            }
        });
         Future delFuture = executorService.submit(() -> {
             for (int j = 0; j < 500__000; j++) {
                 int key = ThreadLocalRandom.current().nextInt(2__500__000);
                 agreementService.deleteAgreement(key);
             }
         });
        Future statFuture = executorService.submit(() -> {
            for (int j = 0; j < 5; j++) {
                agreementService.getStatistics(null, null);
            }
        });
        assertDoesNotThrow(() -> addFuture.get());
        assertDoesNotThrow(() -> delFuture.get());
        assertDoesNotThrow(() -> statFuture.get());
    }

}
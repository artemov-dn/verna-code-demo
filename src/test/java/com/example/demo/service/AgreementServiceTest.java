package com.example.demo.service;

import com.example.demo.dto.AgreementDTO;
import com.example.demo.dto.StatisticDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class AgreementServiceTest {

    @Test
    void addNullAgreement() throws Exception {
        AgreementService agreementService = new AgreementService();
        assertThrows(Exception.class, () -> agreementService.addAgreement(null));
    }


    @Test
    void addAgreement() throws Exception {
        AgreementService agreementService = new AgreementService();
        AgreementDTO newAgreementDTO = new AgreementDTO.Builder()
                .clientId(11).productId(12)
                .amount(new BigDecimal("112.34")).startDate(new Date(1579564800L * 1000))  // "2020-01-21"
                .build();


        List<AgreementDTO> actualList = agreementService.getAgreements(null, null);
        assertNotNull(actualList);
        assertEquals(0, actualList.size());

        long expected_now = Calendar.getInstance().getTime().getTime();
        AgreementDTO actual = agreementService.addAgreement(newAgreementDTO);

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
    void getAgreements() throws Exception {
        AgreementService agreementService = new AgreementService();

        AgreementDTO newAgreementDTO = new AgreementDTO.Builder()
                .clientId(1).productId(1)
                .amount(new BigDecimal("202.55")).startDate(new Date(1579910400L * 1000))  // "2020-01-21"
                .build();
        agreementService.addAgreement(newAgreementDTO);
        newAgreementDTO = new AgreementDTO.Builder()
                .clientId(2).productId(1)
                .amount(new BigDecimal("202.55")).startDate(new Date(1579910400L * 1000))  // "2020-01-21"
                .build();        agreementService.addAgreement(newAgreementDTO);
        newAgreementDTO = new AgreementDTO.Builder()
                .clientId(2).productId(3)
                .amount(new BigDecimal("202.55")).startDate(new Date(1579910400L * 1000))  // "2020-01-21"
                .build();        agreementService.addAgreement(newAgreementDTO);
        newAgreementDTO = new AgreementDTO.Builder()
                .clientId(3).productId(3)
                .amount(new BigDecimal("202.55")).startDate(new Date(1579910400L * 1000))  // "2020-01-21"
                .build();        agreementService.addAgreement(newAgreementDTO);

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
    void getAgreement() throws Exception  {
        AgreementService agreementService = new AgreementService();

        AgreementDTO newAgreementDTO = new AgreementDTO.Builder()
                .clientId(21).productId(22)
                .amount(new BigDecimal("202.55")).startDate(new Date(1579910400L * 1000))  // "2020-01-21"
                .build();
        agreementService.addAgreement(newAgreementDTO);
        newAgreementDTO = new AgreementDTO.Builder()
                .clientId(30).productId(22)
                .amount(new BigDecimal("202.55")).startDate(new Date(1579910400L * 1000))  // "2020-01-21"
                .build();
        agreementService.addAgreement(newAgreementDTO);
        newAgreementDTO = new AgreementDTO.Builder()
                .clientId(20).productId(22)
                .amount(new BigDecimal("202.55")).startDate(new Date(1579910400L * 1000))  // "2020-01-21"
                .build();
        agreementService.addAgreement(newAgreementDTO);

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
    void deleteAgreement() throws Exception  {
        AgreementService agreementService = new AgreementService();
        AgreementDTO newAgreementDTO = new AgreementDTO.Builder()
                .clientId(21).productId(22)
                .amount(new BigDecimal("202.55")).startDate(new Date(1579910400L * 1000))  // "2020-01-21"
                .build();
        agreementService.addAgreement(newAgreementDTO);
        newAgreementDTO = new AgreementDTO.Builder()
                .clientId(10).productId(22)
                .amount(new BigDecimal("202.55")).startDate(new Date(1579910400L * 1000))  // "2020-01-21"
                .build();
        agreementService.addAgreement(newAgreementDTO);
        newAgreementDTO = new AgreementDTO.Builder()
                .clientId(15).productId(22)
                .amount(new BigDecimal("202.55")).startDate(new Date(1579910400L * 1000))  // "2020-01-21"
                .build();
        agreementService.addAgreement(newAgreementDTO);

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
    void getStatNotThrowException()throws InterruptedException, Exception   {
        AgreementService agreementService = new AgreementService();
        ExecutorService executorService =
                Executors.newFixedThreadPool(4);
        long startTime = System.nanoTime();
        for (int i = 0; i < 4; i++) {
            executorService.execute(() -> {
                for (int j = 0; j < 500__000; j++) {
                    AgreementDTO newAgreementDTO = new AgreementDTO.Builder()
                            .clientId(21).productId(ThreadLocalRandom.current().nextInt(5))
                            .amount(new BigDecimal("1")).startDate(new Date(1579910400L * 1000))  // "2020-01-21"
                            .build();
                    try {
                        agreementService.addAgreement(newAgreementDTO);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
                AgreementDTO newAgreementDTO = new AgreementDTO.Builder()
                        .clientId(21).productId(ThreadLocalRandom.current().nextInt(5))
                        .amount(new BigDecimal("1")).startDate(new Date(1579910400L * 1000))  // "2020-01-21"
                        .build();
                try {
                    agreementService.addAgreement(newAgreementDTO);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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

    @Test
    void getStatistics() throws Exception {
        AgreementService agreementService = new AgreementService();

        AgreementDTO newAgreementDTO = new AgreementDTO.Builder()
                .clientId(1).productId(1).amount(new BigDecimal("202.55")).startDate(new Date(1579910400L * 1000)) // "2020-01-21"
                .build();
        agreementService.addAgreement(newAgreementDTO);
        newAgreementDTO = new AgreementDTO.Builder()
                .clientId(2).productId(1).amount(new BigDecimal("102.05")).startDate(new Date(1579910400L * 1000)) // "2020-01-21"
                .build();
        agreementService.addAgreement(newAgreementDTO);
        newAgreementDTO = new AgreementDTO.Builder()
                .clientId(2).productId(3).amount(new BigDecimal("102.05")).startDate(new Date(1579910400L * 1000)) // "2020-01-21"
                .build();
        agreementService.addAgreement(newAgreementDTO);
        newAgreementDTO = new AgreementDTO.Builder()
                .clientId(3).productId(3).amount(new BigDecimal("310")).startDate(new Date(1579910400L * 1000)) // "2020-01-21"
                .build();
        agreementService.addAgreement(newAgreementDTO);

        StatisticDTO actual = agreementService.getStatistics(null, null);
        assertEquals(4, actual.getCount());
        assertEquals(new BigDecimal("102.05"), actual.getMinAmount());
        assertEquals(new BigDecimal("310"), actual.getMaxAmount());
        assertEquals(new BigDecimal("716.65"), actual.getSum());

        actual = agreementService.getStatistics(1, null);
        assertEquals(1, actual.getCount());
        assertEquals(new BigDecimal("202.55"), actual.getMinAmount());
        assertEquals(new BigDecimal("202.55"), actual.getMaxAmount());
        assertEquals(new BigDecimal("202.55"), actual.getSum());

        actual = agreementService.getStatistics(2, null);
        assertEquals(2, actual.getCount());
        assertEquals(new BigDecimal("102.05"), actual.getMinAmount());
        assertEquals(new BigDecimal("102.05"), actual.getMaxAmount());
        assertEquals(new BigDecimal("204.10"), actual.getSum());

        actual = agreementService.getStatistics(null, 1);
        assertEquals(2, actual.getCount());
        assertEquals(new BigDecimal("102.05"), actual.getMinAmount());
        assertEquals(new BigDecimal("202.55"), actual.getMaxAmount());
        assertEquals(new BigDecimal("304.60"), actual.getSum());

        actual = agreementService.getStatistics(2, 3);
        assertEquals(1, actual.getCount());
        assertEquals(new BigDecimal("102.05"), actual.getMinAmount());
        assertEquals(new BigDecimal("102.05"), actual.getMaxAmount());
        assertEquals(new BigDecimal("102.05"), actual.getSum());

        actual = agreementService.getStatistics(1, 3);
        assertNotNull(actual);
        assertEquals(0, actual.getCount());
        assertEquals(new BigDecimal("0"), actual.getMinAmount());
        assertEquals(new BigDecimal("0"), actual.getMaxAmount());
        assertEquals(new BigDecimal("0"), actual.getSum());

        actual = agreementService.getStatistics(1, 3);
        assertNotNull(actual);
        assertEquals(0, actual.getCount());

        actual = agreementService.getStatistics(1, 6);
        assertNotNull(actual);
        assertEquals(0, actual.getCount());

        actual = agreementService.getStatistics(10, 3);
        assertNotNull(actual);
        assertEquals(0, actual.getCount());

        actual = agreementService.getStatistics(10, 6);
        assertNotNull(actual);
        assertEquals(0, actual.getCount());

        actual = agreementService.getStatistics(-10, null);
        assertNotNull(actual);
        assertEquals(0, actual.getCount());

        actual = agreementService.getStatistics(null, -6);
        assertNotNull(actual);
        assertEquals(0, actual.getCount());
    }

}
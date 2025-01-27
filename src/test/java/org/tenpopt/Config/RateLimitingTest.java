//package org.tenpopt.Config;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@TestPropertySource(properties = {
//        "spring.datasource.url=jdbc:postgresql://localhost:5432/postgres",
//        "spring.datasource.username=postgres",
//        "spring.datasource.password=tenpopt",
//        "spring.main.allow-bean-definition-overriding=true"
//})
//@AutoConfigureMockMvc
//public class RateLimitingTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private RateLimitingFilter rateLimitingFilter;
//
//    @BeforeEach
//    void resetRateLimiter() {
//        rateLimitingFilter.resetRateLimiting(); // Limpiar los contadores
//    }
//
//    @Test
//    void shouldAllowUpToThreeRequests() throws Exception {
//        // Realizar 3 solicitudes exitosas
//        for (int i = 0; i < 3; i++) {
//            mockMvc.perform(get("/transaction"))
//                    .andExpect(status().isOk());
//        }
//    }
//
//    @Test
//    void shouldReturnTooManyRequestsAfterLimitExceeded() throws Exception {
//        // Realizar 3 solicitudes exitosas
//        for (int i = 0; i < 3; i++) {
//            mockMvc.perform(post("/transaction")
//                            .contentType("application/json")
//                            .content("{\"id\":1,\"trxMonto\":100.0,\"trxGiroComercio\":\"Retail\",\"tenpista\":\"John Doe\",\"trxFecha\":\"2023-01-01\",\"trxTipo\":\"Original\"}"))
//                    .andExpect(status().isOk());
//        }
//
//        // Realizar una 4ta solicitud que debe fallar con 429
//        mockMvc.perform(post("/transaction")
//                        .contentType("application/json")
//                        .content("{\"id\":1,\"trxMonto\":100.0,\"trxGiroComercio\":\"Retail\",\"tenpista\":\"John Doe\",\"trxFecha\":\"2023-01-01\",\"trxTipo\":\"Original\"}"))
//                .andExpect(status().isTooManyRequests());
//    }
//}
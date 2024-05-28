package com.sa1zer.simplebanking.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sa1zer.simplebanking.entity.BankAccount;
import com.sa1zer.simplebanking.entity.Customer;
import com.sa1zer.simplebanking.entity.Email;
import com.sa1zer.simplebanking.entity.Phone;
import com.sa1zer.simplebanking.payload.dto.CustomerDto;
import com.sa1zer.simplebanking.payload.request.AuthRequest;
import com.sa1zer.simplebanking.payload.request.TransferRequest;
import com.sa1zer.simplebanking.payload.response.AuthResponse;
import com.sa1zer.simplebanking.repo.BankAccountRepo;
import com.sa1zer.simplebanking.repo.CustomerRepo;
import com.sa1zer.simplebanking.repo.EmailRepo;
import com.sa1zer.simplebanking.repo.PhoneRepo;
import com.sa1zer.simplebanking.service.CustomerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class BalanceControllerTest {

    private static final String BALANCE_API = "/api/balance/";
    private static final String AUTH_API = "/api/auth/signin";
    private static final String MANAGER_API = "/api/manager/";
    private static final Map<String, String> AUTH_DATA = new HashMap<>();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmailRepo emailRepo;
    @Autowired
    private PhoneRepo phoneRepo;
    @Autowired
    private BankAccountRepo accountRepo;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @BeforeEach
    @Transactional
    public void init() throws Exception {
        customerRepo.deleteAll();
        accountRepo.deleteAll();
        phoneRepo.deleteAll();
        emailRepo.deleteAll();

        Customer customer1 = buildCustomer(
          "Ivan", "Иванов Иван Иваныч", LocalDate.of(1999, 3, 1),
          "awdawdadw@mail.ru", "79024333333", BigDecimal.valueOf(5000)
        );

        Customer customer2 = buildCustomer(
                "Pavel", "Павлов Павел Павлович", LocalDate.of(1999, 3, 1),
                "awdaddddwdadw@mail.ru", "79024555555", BigDecimal.valueOf(5000)
        );

        Customer customer3 = buildCustomer(
                "Alex", "Александров Александр Александрович", LocalDate.of(1999, 3, 1),
                "awdaawdwdadw@mail.ru", "79024777777", BigDecimal.valueOf(5000)
        );

        customerRepo.save(customer1);
        customerRepo.save(customer2);
        customerRepo.save(customer3);

        //auth
        AuthRequest authRequest1 = new AuthRequest("Ivan", "123456");
        AuthRequest authRequest2 = new AuthRequest("Pavel", "123456");
        AuthRequest authRequest3 = new AuthRequest("Alex", "123456");

        String response1 = mockMvc.perform(post(AUTH_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest1))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        String response2 = mockMvc.perform(post(AUTH_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest2))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        String response3 = mockMvc.perform(post(AUTH_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest3))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        AuthResponse authResponse1 = objectMapper.readValue(response1, AuthResponse.class);
        AuthResponse authResponse2 = objectMapper.readValue(response2, AuthResponse.class);
        AuthResponse authResponse3 = objectMapper.readValue(response3, AuthResponse.class);

        AUTH_DATA.put(authRequest1.login(), authResponse1.token());
        AUTH_DATA.put(authRequest2.login(), authResponse2.token());
        AUTH_DATA.put(authRequest3.login(), authResponse3.token());

        System.out.println("Token data: ");
        System.out.println(AUTH_DATA.keySet());
        System.out.println(AUTH_DATA.values());
    }

    @Test
    void transfer() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        List<Future<ResultActions>> futures = new ArrayList<>();

        TransferRequest transferRequest1 = new TransferRequest(BigDecimal.valueOf(10), "Alex");

        for (int i = 0; i < 300; i++) {
            Callable<ResultActions> callable1 = () -> {
                ResultActions perform = this.mockMvc.perform(post(BALANCE_API + "/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", AUTH_DATA.get("Ivan"))
                        .content(objectMapper.writeValueAsString(transferRequest1)));
                return perform.andDo(result -> {
                    System.out.println("Transfer result: ");
                    System.out.println(result.getResponse().getContentAsString());
                }).andExpect(status().isOk());
            };

            Future<ResultActions> future1 = executorService.submit(callable1);
            futures.add(future1);
        }

        for (Future<ResultActions> future : futures) {
            future.get();
        }

        String IvanResponse = this.mockMvc.perform(get(MANAGER_API + "/search?name=Иван")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        String alexResponse = this.mockMvc.perform(get(MANAGER_API + "/search?name=Алексан")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        CustomerDto[] ivanDtos = objectMapper.readValue(IvanResponse, CustomerDto[].class);
        CustomerDto[] alexDtos = objectMapper.readValue(alexResponse, CustomerDto[].class);

        Assertions.assertAll(() -> {
            Assertions.assertEquals(BigDecimal.valueOf(2000).doubleValue(), ivanDtos[0].account().balance().doubleValue());
            Assertions.assertEquals(BigDecimal.valueOf(8000.00).doubleValue(), alexDtos[0].account().balance().doubleValue());
        });
    }

    @AfterEach
    public void destroy() {
        customerRepo.deleteAll();
        accountRepo.deleteAll();
        phoneRepo.deleteAll();
        emailRepo.deleteAll();
    }

    private Customer buildCustomer(String login, String name, LocalDate date, String email, String phone, BigDecimal balance) {
        return Customer.builder()
                .login(login)
                .birthday(date)
                .password(encoder.encode("123456"))
                .name(name)
                .email(List.of(Email.builder()
                        .email(email)
                        .build()))
                .phones(List.of(Phone.builder()
                        .phone(phone)
                        .build()))
                .account(BankAccount.builder()
                        .firstDeposit(balance)
                        .balance(balance)
                        .build())
                .build();
    }
}
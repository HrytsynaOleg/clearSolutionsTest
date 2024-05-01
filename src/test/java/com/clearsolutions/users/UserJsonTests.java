package com.clearsolutions.users;

import com.clearsolutions.users.model.User;
import com.clearsolutions.users.utils.FileReader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class UserJsonTests {
    @Autowired
    private JacksonTester<User> jacksonTester;
    @Value("classpath:expected.json")
    private Resource resource;
    private final User user = User.builder()
            .email("mail@gmail.com")
            .firstName("FirstName")
            .lastName("LastName")
            .birthDate(LocalDate.of(1972, 11, 8))
            .address("UserAddress")
            .phoneNumber("0385556633")
            .build();

    @Test
    void UserSerializationTest() throws IOException {
        String expectedJson = FileReader.asString(resource);
        assertThat(jacksonTester.write(user)).isStrictlyEqualToJson(expectedJson);
        assertThat(jacksonTester.write(user)).hasJsonPathStringValue("@.firstName");
        assertThat(jacksonTester.write(user)).extractingJsonPathStringValue("@.firstName").isEqualTo("FirstName");
        assertThat(jacksonTester.write(user)).hasJsonPathStringValue("@.lastName");
        assertThat(jacksonTester.write(user)).extractingJsonPathStringValue("@.lastName").isEqualTo("LastName");
        assertThat(jacksonTester.write(user)).hasJsonPathStringValue("@.email");
        assertThat(jacksonTester.write(user)).extractingJsonPathStringValue("@.email").isEqualTo("mail@gmail.com");
        assertThat(jacksonTester.write(user)).hasJsonPathStringValue("@.birthDate");
        assertThat(jacksonTester.write(user)).extractingJsonPathStringValue("@.birthDate").isEqualTo("1972-11-08");
        assertThat(jacksonTester.write(user)).hasJsonPathStringValue("@.address");
        assertThat(jacksonTester.write(user)).extractingJsonPathStringValue("@.address").isEqualTo("UserAddress");
        assertThat(jacksonTester.write(user)).hasJsonPathStringValue("@.phoneNumber");
        assertThat(jacksonTester.write(user)).extractingJsonPathStringValue("@.phoneNumber").isEqualTo("0385556633");
    }

    @Test
    void UserDeserializationTest() throws IOException {
        String expectedJson = FileReader.asString(resource);
        assertThat(jacksonTester.parse(expectedJson)).isEqualTo(user);
    }
}

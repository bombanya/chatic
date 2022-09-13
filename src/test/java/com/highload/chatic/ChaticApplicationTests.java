
package com.highload.chatic;

import com.highload.chatic.initializer.Postgres;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(initializers = {
        Postgres.Initializer.class
})
@Transactional
//@Sql({"/sql/auth.sql"})
public abstract class ChaticApplicationTests {
    @BeforeAll
    static void init() {
        Postgres.container.start();
    }
}

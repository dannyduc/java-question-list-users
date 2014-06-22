package question.listusers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import java.util.List;

public class UserApiTest {

    SimpleDriverDataSource dataSource;
    UserApi userApi;

    @Before
    public void initApp() {
        dataSource = new SimpleDriverDataSource(new org.hsqldb.jdbcDriver(),
                "jdbc:hsqldb:mem:locktest",
                "sa",
                "");

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        createUserTable(jdbcTemplate);
        insertUserRecords(jdbcTemplate);

        // TODO: replace with pagging impl
        userApi = new UserApiAllInMemory(dataSource);
//        userApi = new UserApiPaging(dataSource);
    }

    @After
    public void cleanUp() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        dropUserTable(jdbcTemplate);
    }


    void createUserTable(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.execute("create table user (\n" +
                "  user_id integer,\n" +
                "  first_name varchar(255),\n" +
                "  last_name varchar(255)\n" +
        ")");
    }

    void dropUserTable(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.execute("drop table user");
    }

    void insertUserRecords(JdbcTemplate jdbcTemplate) {
        int max = 10;
        for (int i = 0; i < max; i++) {
            String fn = String.format("fn%d", i);
            String ln = String.format("ln%d", i);
            jdbcTemplate.update("insert into user (user_id, first_name, last_name) values (?, ?, ?)", i, fn, ln);
        }
    }

    @Test
    public void printAllUsersShouldNotRunOutOfMemory() {
        List<User> users = userApi.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }
    }
}

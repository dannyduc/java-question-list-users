package question.listusers;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserApiAllInMemory implements UserApi {

    private DataSource dataSource;

    public UserApiAllInMemory(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select user_id, first_name, last_name from user");
            while (rs.next()) {
                users.add(
                        new User(rs.getInt("user_id"), rs.getString("first_name"), rs.getString("last_name"))
                );
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return users;
    }
}

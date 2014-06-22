package question.listusers;

import javax.sql.DataSource;
import java.util.List;

public class UserApiPaging implements UserApi {

    private DataSource dataSource;

    public UserApiPaging(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<User> getAllUsers() {
        // TODO: implement
        return null;
    }
}

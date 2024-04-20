package networking.networking.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    public User getUserByUsername(@Param("userNameOrEmail") String userNameOrEmail);
    public User getUserByEmail(@Param("userNameOrEmail") String userNameOrEmail);

}
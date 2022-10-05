package bookstore.bookstore.repository;

import bookstore.bookstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends JpaRepository<User, Integer>{

    @Query("select user from User user where user.username=?1")
    public User findByUsername(String username);

    public User findByEmail(String email);
}

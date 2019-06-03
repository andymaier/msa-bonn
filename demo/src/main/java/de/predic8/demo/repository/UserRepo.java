package de.predic8.demo.repository;


import de.predic8.demo.domain.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer>{

  List<User> findAllByVornameContains(String vorname);

}

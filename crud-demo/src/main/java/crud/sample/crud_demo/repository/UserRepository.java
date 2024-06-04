package crud.sample.crud_demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import crud.sample.crud_demo.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}

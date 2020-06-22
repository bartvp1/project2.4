package MeetUpAPI.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import MeetUpAPI.dbModels.User;

public interface UserRepository extends JpaRepository<User, Integer> {

  boolean existsByUsername(String username);
  boolean existsByPhone(String phone);

  User findByUsername(String username);

  @Transactional
  void deleteByUsername(String username);

}

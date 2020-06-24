package MeetUpAPI.dbModels;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface HobbyRepository extends JpaRepository<Hobby, Integer> {

  List<Hobby> findAll();
}

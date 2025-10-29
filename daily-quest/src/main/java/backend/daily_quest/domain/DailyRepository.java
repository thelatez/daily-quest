package backend.daily_quest.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyRepository extends CrudRepository<Daily, Long> {
    List<Daily> findByCategory(String category);
}

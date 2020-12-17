package nd.ermakov.wcapp.repository;

import nd.ermakov.wcapp.model.WeatherRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WeatherRepository extends JpaRepository<WeatherRecord, Long> {
    List<WeatherRecord> findAllByLocationAndDateBetweenOrderByDateDesc(String location, LocalDate start, LocalDate end);
    boolean existsByLocationAndDate(String location, LocalDate date);
}

package nd.ermakov.wcapp.repository;

import nd.ermakov.wcapp.model.CurrencyRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CurrencyRepository extends JpaRepository<CurrencyRecord, Long> {
    List<CurrencyRecord> findAllByDateBetweenOrderByDateDesc(LocalDate start, LocalDate end);
    boolean existsByDate(LocalDate date);
}
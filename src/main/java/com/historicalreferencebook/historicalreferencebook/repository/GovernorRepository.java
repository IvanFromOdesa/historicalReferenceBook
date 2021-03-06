package com.historicalreferencebook.historicalreferencebook.repository;

import com.historicalreferencebook.historicalreferencebook.domain.Governor;
import com.historicalreferencebook.historicalreferencebook.domain.State;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public interface GovernorRepository extends CrudRepository<Governor, Integer> {

    Long countByIdGovernor(int id);

    @Query(value="SELECT COUNT (id_governor) FROM governor\n" +
            "WHERE date_of_intercession > ?1", nativeQuery = true)
    Integer findGovernorsByDateOfIntercessionAfter(java.sql.Date dateStart);

    @Query(value="SELECT * FROM governor\n" +
            "WHERE date_of_intercession > ?1", nativeQuery = true)
    Set<Governor> findAllByDateOfIntercessionAfter(java.sql.Date dateStart);

    @Query(value = "SELECT id_state, official_state_name FROM state\n" +
            "WHERE NOT EXISTS\n" +
            "\t(SELECT id_governor FROM governor\n" +
            "\tWHERE governor.id_state = state.id_state)", nativeQuery = true)
    Set<Object[]> findStatesWithNoGovernors();
}

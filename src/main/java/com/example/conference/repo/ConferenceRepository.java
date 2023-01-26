package com.example.conference.repo;

import com.example.conference.model.Conference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConferenceRepository extends JpaRepository<Conference, Long> {

    List<Conference> findByOwnerId(Long id);

    Optional<Conference> findByIdAndOwnerId(Long id, Long ownerId);
}

package de.vkb.kranken.example.adapter.persistence.jpa;

import de.vkb.kranken.example.adapter.persistence.jpa.model.PartnerDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartnerRepositoryJpa extends JpaRepository<PartnerDao, String>
{
    List<PartnerDao> findAllByLastname(String lastname);
}

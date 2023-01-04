package br.com.victor.realmeet.domain.repository;

import br.com.victor.realmeet.domain.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, String> {
}

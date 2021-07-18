package repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Contato;

public interface ContatoRepository extends JpaRepository<Contato, Long>{
    List<Contato> findByNomeContaining(String nome);
}

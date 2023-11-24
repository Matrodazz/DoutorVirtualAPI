package fiap.com.br.doutorvirtual.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import fiap.com.br.doutorvirtual.models.Ficha;

public interface FichaRepository extends JpaRepository<Ficha, Long> {
    Page<Ficha> findByNomeContaining(String busca, Pageable pageable);
}

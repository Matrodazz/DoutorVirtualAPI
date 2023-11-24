package fiap.com.br.doutorvirtual.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import fiap.com.br.doutorvirtual.models.Mensagem;

public interface MensagemRepository extends JpaRepository<Mensagem, Long> {
    Page<Mensagem> findByConteudoContaining(String busca, Pageable pageable);
}

package fiap.com.br.doutorvirtual.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fiap.com.br.doutorvirtual.models.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);
    
}
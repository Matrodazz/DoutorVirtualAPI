package fiap.com.br.doutorvirtual.models;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import fiap.com.br.doutorvirtual.controllers.EnderecoController;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Endereco {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull(message = "O logradouro não pode estar vazio")
    private String logradouro;

    @NotNull(message = "O número não pode estar vazio")
    private int numero;

    @NotNull(message = "O cep não pode estar vazio")
    private String cep;

    @NotNull(message = "O bairro não pode estar vazio")
    private String bairro;

    private String ponto_referencia;

    @NotNull(message = "A cidade não pode estar vazia")
    private String cidade;

    @NotNull(message = "O estado não pode estar vazio")
    private String estado;

    @NotNull(message = "A regiao não pode estar vazia")
    private String regiao;

    public EntityModel<Endereco> toEntityModel(){
        return EntityModel.of(
            this, 
            linkTo(methodOn(EnderecoController.class).show(id)).withSelfRel(),
            linkTo(methodOn(EnderecoController.class).destroy(id)).withRel("delete"),
            linkTo(methodOn(EnderecoController.class).index(null, Pageable.unpaged())).withRel("all")
        );

    }
}

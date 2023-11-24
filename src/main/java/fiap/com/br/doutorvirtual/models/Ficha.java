package fiap.com.br.doutorvirtual.models;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import fiap.com.br.doutorvirtual.controllers.FichaController;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Ficha {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank
    private String nome;

    @NotBlank
    private String email;

    @NotBlank
    private String telefone;

    @NotBlank
    private String cpf;

    @NotBlank
    private String data_nascimento;

    @NotBlank
    private String grupo_sanguineo;

    @NotBlank
    private String nome_contatoe;

    @NotBlank
    private String telefone_contatoe;


    @OneToOne
    private Endereco endereco;


    public EntityModel<Ficha> toEntityModel(){
        return EntityModel.of(
            this, 
            linkTo(methodOn(FichaController.class).show(id)).withSelfRel(),
            linkTo(methodOn(FichaController.class).destroy(id)).withRel("delete"),
            linkTo(methodOn(FichaController.class).index(null, Pageable.unpaged())).withRel("all")
        );

    }
}

package fiap.com.br.doutorvirtual.models;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import fiap.com.br.doutorvirtual.controllers.MensagemController;
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

public class Mensagem {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    private String data;

    @NotNull
    private String hora;

    @NotNull(message = "A mensagem não pode estar vazia")
    private String conteudo;

    private String retorno;



    public EntityModel<Mensagem> toEntityModel(){
        return EntityModel.of(
            this, 
            linkTo(methodOn(MensagemController.class).show(id)).withSelfRel(),
            linkTo(methodOn(MensagemController.class).destroy(id)).withRel("delete"),
            linkTo(methodOn(MensagemController.class).index(null, Pageable.unpaged())).withRel("all")
        );

    }
}

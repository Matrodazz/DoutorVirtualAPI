package fiap.com.br.doutorvirtual.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fiap.com.br.doutorvirtual.exceptions.RestNotFoundException;
import fiap.com.br.doutorvirtual.models.Endereco;
import fiap.com.br.doutorvirtual.repositories.EnderecoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;


@RestController
@Slf4j
@RequestMapping("/api/endereco")

public class EnderecoController {

    Logger log = LoggerFactory.getLogger(EnderecoController.class);

    @Autowired
    EnderecoRepository repository;

    @Autowired
    PagedResourcesAssembler<Object> assembler;
    @SecurityRequirement(name = "bearer-key")

    @GetMapping
    @Operation(summary = "Get de endereco",description = "Retorna a lista de todos os enderecos cadastrados")
    public PagedModel<EntityModel<Object>> index(@RequestParam(required = false) String busca, @PageableDefault(size = 5) Pageable pageable){
        Page<Endereco> enderecos = (busca == null)?
        
        repository.findAll(pageable):
        repository.findByCep(busca, pageable);
        return assembler.toModel(enderecos.map(Endereco::toEntityModel));
    }


    @PostMapping
    @Operation(summary = "Post de endereco", description = "Realiza o cadastro de um novo endereco")
    public ResponseEntity<Object> create(@RequestBody @Valid Endereco endereco) {
        log.info("Cadastrando endereco " + endereco);
        repository.save(endereco);
        return ResponseEntity
                .created(endereco.toEntityModel().getRequiredLink("self").toUri())
                .body(endereco.toEntityModel());
    }
    


    @GetMapping("{id}")
    @Operation(summary = "Get de endereco especifico", description = "Retorna detalhes de um endereco com Id especifico")
    public ResponseEntity<Endereco> show(@PathVariable Long id) {
        log.info("Detalhando endereco" + id);
        var endereco = repository.findById(id)
        .orElseThrow(() -> new RestNotFoundException("Endereco não encontrado"));

        return ResponseEntity.ok(endereco);
    }



    @DeleteMapping("{id}")
    @Operation(summary = "Delete de endereco",description = "Deleta o registro de um endereco especifico")
    public ResponseEntity<Endereco> destroy(@PathVariable Long id) {
        log.info("Apagando endereco" + id);
        var endereco = repository.findById(id)
        .orElseThrow(() -> new RestNotFoundException("Erro ao apagar, endereco não encontrado"));

        repository.delete(endereco);

        return ResponseEntity.noContent().build();
    }


    @PutMapping("{id}")
    @Operation(summary = "Put de endereco",description = "Realiza a atualizacao do cadastro de um endereco")
    public ResponseEntity<Endereco> update(@PathVariable Long id, @RequestBody @Valid Endereco endereco){
        log.info("Atualizando endereco" + id);
        repository.findById(id)
        .orElseThrow(() -> new RestNotFoundException("Erro ao atualizar, endereco não encontrado"));

        endereco.setId(id);
        repository.save(endereco);

        return ResponseEntity.ok(endereco);
        
    }
}

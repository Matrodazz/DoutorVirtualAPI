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
import fiap.com.br.doutorvirtual.models.Ficha;
import fiap.com.br.doutorvirtual.repositories.FichaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;


@RestController
@Slf4j
@RequestMapping("/api/ficha")

public class FichaController {

    Logger log = LoggerFactory.getLogger(FichaController.class);

    @Autowired
    FichaRepository repository;

    @Autowired
    PagedResourcesAssembler<Object> assembler;
    @SecurityRequirement(name = "bearer-key")

    @GetMapping
    @Operation(summary = "Get de ficha medica",description = "Retorna a lista de todas as fichas medicas cadastradas")
    public PagedModel<EntityModel<Object>> index(@RequestParam(required = false) String busca, @PageableDefault(size = 5) Pageable pageable){
        Page<Ficha> fichas = (busca == null)?
        
        repository.findAll(pageable):
        repository.findByNomeContaining(busca, pageable);
        return assembler.toModel(fichas.map(Ficha::toEntityModel));
    }


    @PostMapping
    @Operation(summary = "Post de ficha medica", description = "Realiza o cadastro de uma nova ficha medica")
    public ResponseEntity<Object> create(@RequestBody @Valid Ficha ficha) {
        log.info("Cadastrando ficha " + ficha);
        repository.save(ficha);
        return ResponseEntity
                .created(ficha.toEntityModel().getRequiredLink("self").toUri())
                .body(ficha.toEntityModel());
    }
    


    @GetMapping("{id}")
    @Operation(summary = "Get de ficha medica especifica", description = "Retorna detalhes de uma ficha medica com Id especifico")
    public ResponseEntity<Ficha> show(@PathVariable Long id) {
        log.info("Detalhando ficha" + id);
        var ficha = repository.findById(id)
        .orElseThrow(() -> new RestNotFoundException("ficha não encontrada"));

        return ResponseEntity.ok(ficha);
    }



    @DeleteMapping("{id}")
    @Operation(summary = "Delete de ficha medica",description = "Deleta o registro de um ficha medica especifica")
    public ResponseEntity<Ficha> destroy(@PathVariable Long id) {
        log.info("Apagando ficha" + id);
        var ficha = repository.findById(id)
        .orElseThrow(() -> new RestNotFoundException("Erro ao apagar, ficha não encontrada"));

        repository.delete(ficha);

        return ResponseEntity.noContent().build();
    }


    @PutMapping("{id}")
    @Operation(summary = "Put de ficha medica",description = "Realiza a atualizacao do cadastro de uma ficha medica")
    public ResponseEntity<Ficha> update(@PathVariable Long id, @RequestBody @Valid Ficha ficha){
        log.info("Atualizando ficha" + id);
        repository.findById(id)
        .orElseThrow(() -> new RestNotFoundException("Erro ao atualizar, ficha não encontrada"));

        ficha.setId(id);
        repository.save(ficha);

        return ResponseEntity.ok(ficha);
        
    }
}

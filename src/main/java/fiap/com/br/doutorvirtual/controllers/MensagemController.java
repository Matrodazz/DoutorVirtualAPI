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
import fiap.com.br.doutorvirtual.models.Mensagem;
import fiap.com.br.doutorvirtual.repositories.MensagemRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;


@RestController
@Slf4j
@RequestMapping("/api/mensagem")

public class MensagemController {

    Logger log = LoggerFactory.getLogger(MensagemController.class);

    @Autowired
    MensagemRepository repository;

    @Autowired
    PagedResourcesAssembler<Object> assembler;
    @SecurityRequirement(name = "bearer-key")

    @GetMapping
    @Operation(summary = "Get de mensagem",description = "Retorna a lista de todas as mensagems cadastradas")
    public PagedModel<EntityModel<Object>> index(@RequestParam(required = false) String busca, @PageableDefault(size = 5) Pageable pageable){
        Page<Mensagem> mensagens = (busca == null)?
        
        repository.findAll(pageable):
        repository.findByConteudoContaining(busca, pageable);
        return assembler.toModel(mensagens.map(Mensagem::toEntityModel));
    }


    @PostMapping
    @Operation(summary = "Post de mensagem", description = "Realiza o cadastro de uma nova mensagem")
    public ResponseEntity<Object> create(@RequestBody @Valid Mensagem mensagem) {
        log.info("Cadastrando endereco " + mensagem);
        repository.save(mensagem);
        return ResponseEntity
                .created(mensagem.toEntityModel().getRequiredLink("self").toUri())
                .body(mensagem.toEntityModel());
    }

    @GetMapping("{id}")
    @Operation(summary = "Get de mensagem especifica", description = "Retorna detalhes de uma mensagem com Id especifico")
    public ResponseEntity<Mensagem> show(@PathVariable Long id) {
        log.info("Detalhando mensagem" + id);
        var mensagem = repository.findById(id)
        .orElseThrow(() -> new RestNotFoundException("mensagem não encontrada"));

        return ResponseEntity.ok(mensagem);
    }



    @DeleteMapping("{id}")
    @Operation(summary = "Delete de mensagem",description = "Deleta o registro de um mensagem especifica")
    public ResponseEntity<Mensagem> destroy(@PathVariable Long id) {
        log.info("Apagando mensagem" + id);
        var mensagem = repository.findById(id)
        .orElseThrow(() -> new RestNotFoundException("Erro ao apagar, mensagem não encontrada"));

        repository.delete(mensagem);

        return ResponseEntity.noContent().build();
    }


    @PutMapping("{id}")
    @Operation(summary = "Put de mensagem",description = "Realiza a atualizacao do cadastro de uma mensagem medica")
    public ResponseEntity<Mensagem> update(@PathVariable Long id, @RequestBody @Valid Mensagem mensagem){
        log.info("Atualizando mensagem" + id);
        repository.findById(id)
        .orElseThrow(() -> new RestNotFoundException("Erro ao atualizar, mensagem não encontrada"));

        mensagem.setId(id);
        repository.save(mensagem);

        return ResponseEntity.ok(mensagem);
        
    }
}

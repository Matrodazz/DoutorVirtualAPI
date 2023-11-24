# Doutor Virtual
## Proposta
Nos últimos anos, temos testemunhado avanços extraordinários na área da saúde com a ajuda da tecnologia e inovação, e gostaríamos de apresentar a proposta do Doutor Virtual!
Atualmente, profissionais da saúde enfrentam o desafio de lidar com uma quantidade massiva de dados em registros médicos, relatórios e exames todos os dias. A nossa proposta visa otimizar esse processo por meio da aplicação de uma Inteligência Artificial, que recebe o texto sobre sintomas e comportamentos do paciente e descreve um diagnóstico automaticamente para o paciente durante a triagem, sendo utilizado como base pelos médicos de plantão na hora da consulta. Eventualmente, o sistema poderia até mesmo checar registros médicos dos pacientes para aumentar a base de conhecimento e oferecer diagnósticos ainda mais precisos e prevenções para complicações futuras.

Imagine um cenário em que um paciente em uma área remota pode receber um diagnóstico rápido e preciso, possibilitando tratamentos precoces e potencialmente salvando vidas. Essa é a premissa do Doutor Virtual.


## Arquitetura
![Diagram](https://i.imgur.com/dII7CLz.png)

## Integrantes
* João Victor Jales RM94673
* Vitor dos Santos Moreira RM93101

## Utilização

Clone o repositório

`git clone https://github.com/Matrodazz/DoutorVirtualAPI.git`

Ajuste as configurações de login, porta, e endereço do Banco de dados no application.properties.
A API cria as tabelas automaticamente no Oracle DataBase na primeira execução.

Inicie a aplicação

## Endpoints e Documentação
Documentação de apoio criada automaticamente usando Swagger na aplicação.

Inicie a API e acesse `http://localhost:8080/swagger-ui/index.html#/` para ver a documentação.

* Ficha Médica (/api/ficha)
    * Cadastrar Ficha
    * Atualizar Ficha
    * Excluir Ficha
    * Listar todas
    
* Mensagem (/api/mensagem)
    * Cadastrar Mensagem
    * Atualizar Mensagem
    * Excluir Mensagem
    * Listar todas
---


## JSONs Exemplos

Autenticação [POST]
`http://localhost:8080/api/registrar`

```
{
	"nome" : "Usuario",
	"email" : "usuario@autenticado.com",
	"senha" : "123456"
}
```

---

Login [POST]
`http://localhost:8080/api/login`

```
{
	"email" : "usuario@autenticado.com",
	"senha" : "123456"
}
```
O retorno será um Token, você deve utilizá-lo para todas as requisições usando o método de autenticação Bearer Token.

---

Listar Fichas Médicas [GET]
`http://localhost:8080/api/ficha`

---

Cadastrar Ficha Médica [POST]
`http://localhost:8080/api/ficha`

```
{
  "nome": "Larissa",
  "email": "Lari@fiap.com.br",
  "telefone": "(11) 346355344",
  "cpf": "543.229.877-53",
  "data_nascimento": "12/12/2001",
  "grupo_sanguineo": "A+",
  "nome_contatoe": "Sandra",
  "telefone_contatoe": "(11) 346355344",
  "endereco": {
    "logradouro": "Rua Silvio Silvio",
	"numero": 114,
	"cep": "09836-475",
	"bairro": "Silvestre",
	"ponto_referencia": "Proximo a rotatoria",
	"cidade": "São Paulo",
	"estado": "SP",
	"regiao": "Sudeste"
  }
}
```

---

Atualizar Ficha Médica [PUT]
`http://localhost:8080/api/ficha/{id}`

```
{
  "nome": "Larissa Dias",
  "email": "Larissadias@fiap.com.br",
  "telefone": "(11) 346355344",
  "cpf": "543.229.877-53",
  "data_nascimento": "12/12/2002",
  "grupo_sanguineo": "A+",
  "nome_contatoe": "Sandra",
  "telefone_contatoe": "(11) 346355344",
  "endereco": {
    "logradouro": "Rua Silvio Alvarenga",
	"numero": 114,
	"cep": "09836-475",
	"bairro": "Silvestre",
	"ponto_referencia": "Proximo a rotatoria",
	"cidade": "São Paulo",
	"estado": "SP",
	"regiao": "Sudeste"
  }
}
```

---

Deletar Ficha Médica [DELETE]
`http://localhost:8080/api/ficha/{id}`

---


Listar Mensagens [GET]
`http://localhost:8080/api/mensagem`

---

Cadastrar Mensagens [POST]
`http://localhost:8080/api/mensagem`

```
{
    "data": "12/12/2023",
    "hora": "13:40",
    "conteudo": "Estou com tosse frequente já tem mais de uma semana"
}
```

---

Atualizar Mensagem [PUT]
`http://localhost:8080/api/mensagem/{id}`

```
{
    "data": "12/12/2023",
    "hora": "13:55",
    "conteudo": "Estou com tosse frequente já tem mais de uma semana, mesmo tomando medicamentos, a situação não melhorou"
}
```

---

Deletar Mensagem [DELETE]
`http://localhost:8080/api/mensagem/{id}`

---



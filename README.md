## Sicredi-Digital-QA-api-master

Desafio de automação da API utilizando RestAssured.

#### Documentação e regras da API

As regras da API juntamente com sua implementação e instruções de execução encontram-se [aqui](https://reqres.in/). 

#### Requisitos

Para a execução do projeto é necessário possuir:
- Maven
- Java 1.8 ou +

##### Execução

Para execução do projeto navegue até a pasta raiz com um terminal de sua preferência e utilize o comando:

`$ mvn test`

O comando irá executar todos os testes contidos no projeto.

Para executar métodos de testes específicos: 

`$ mvn test -Dtest=Test1,Test2`

Para executar um grupo específico de testes:

`$ mvn test -DincludeGroups=TestGroup1,TestGroup2` 

Para excluir um grupo específico de conjunto de testes, utilize o comando: 

`$ mvn test -DexcludeGroups=TestGroup3,TestGroup4`

Para excluir um pacote específico contendo um conjunto de testes, utilize o comando: 

`$ mvn test -Dtest="test.java.com.service.map.**"`

##### End

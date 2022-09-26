# Avaliação Final Back-end II - Projeto Prático

O objetivo deste projeto é colocar em prática os conhecimentos adquridos ao longo do segundo bimestre da especialização
de Back-end II. Consiste na evolução da autenticação realizada na Avaliação Parcial.

## Enunciado
Dando continuidade à tarefa proposta no trabalho prático parcial, trabalharemos agora no sistema de faturamento.
Nesta etapa, vamos lidar com duas novas funcionalidades:
- Os diferentes fornecedores de faturas poderão registrar faturas.
- Os usuários poderão pesquisar as suas faturas.

### Diagrama da Arquitetura
![Arquitetura dos micro serviços](Trabalho%20Prático%20final.svg)

### Instruçoes para execução do projeto
 - Clonar o repositório

```
git clone https://github.com/ronilsonalves/AvFinalBackendII.git
cd AvFinalBackendII
```
 - Subir o container do Keycloak do arquivo [docker-compose.yaml](docker-compose.yaml)
```
docker-compose up -d --build
```
 - Efetue o login no admin console do Keycloack
```
http://localhost:8080
```
 - Importe o arquivo [realm-export.json](realm-export.json) para importar o reino, clientes, grupos e roles. 
   Cadastre usuários e mapeie pelo menos um para o grupo ``PROVIDERS``
 - Depois abra a pasta do projeto na IDE ou rode-os usando os comandos do maven.
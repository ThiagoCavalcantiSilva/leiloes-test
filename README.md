## Testes de um sistema de leilões
+ O projeto escolhido foi uma aplicação de leilões, com autenticação. Onde os usuários podem cadastrar itens e dar lances.
    - Motivo da escolha:
      - Projeto utiliza banco de dados H2, que é em memória, logo, não é necessário instalar ferramentas externas.
      - (Deveria ser um projeto com front-end para uso do selenium)
 
## Regras de negócio
+ Não é possível dar lances menores que o valor mínimo;
+ O usuário não poderá dar lances em seus itens, apenas editar as informações;
+ O mesmo usuário não poderá dar dois lances seguidos, terá que aguardar outro usuário dar um novo lance;
+ O usuário poderá dar apenas 5 lances por item.

## Para executar
+ É necessário utilizar Java 8 ou superior;
+ Basta importar o projeto, baixar as dependências do maven e inicializar o LeilaoApplication (Main)
    - O servidor estará ativo em localhost:8080
    - Para acessar, basta fazer login com o usuário "fulano" e senha "pass"
+ Para verificar os testes, rodar um "mvn test" ou executar pela IDE

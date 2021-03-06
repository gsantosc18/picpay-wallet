# Desafio técnico - Picpay
## Requisitos
- Java 11
- Docker
- Docker-compose

## Cenários
- **Cenário 1:** Manter cliente
    - Deve ser possível criar, atualizar e desativar clientes via endpoint.
        - Ao criar um novo cliente, deve-se informar os seguintes dados:
            - Cliente:
                - Nome
                - Sobrenome
                - Data de nascimento
                - Email
                - Documento
                - Tipo de documento
                - senha
        - Ao criar um novo cliente, deve ser criado tabém a carteira do mesmo.
- **Cenário 2:** Saque
    - Deve ser possivel realizar saque da conta de um cliente ativo
        - Ao realizar o saque de uma conta existente, deve ser informado os seguintes dados:
            - Número da carteira
            - Valor pretendido
        - Ao realizar o saque da conta de um cliente deve ser feita as seguintes validaçoes:
            - Se a conta existe e está ativa
            - Se possui saldo suficiente
        - Caso o cliente não tenha saldo suficiente para a operação, deve ser lançado uma exceção informando.
        - Caso o cliente tenha saldo, deve realizar a operação e registrar no histórico.
- **Cenário 3:** Transferência
    - Deve ser possivel realizar transferência entre contas de clientes
        - Ao realizar uma transferência entre contas, deve ser dada as seguintes informações
            - Carteira do cliente atual
            - Carteira do cliente destino
            - Valor a ser transferido
        - Ao realizar uma transferência, devem ser feitas as seguintes validações:
            - Se a conta do cliente remetente e destinatário existe e está ativa
                - Caso a conta não exista, deve ser lançada uma exceção informado
                - Caso o cliente tenha saldo, deve realizar a operação e registrar no histórico
- **Cenário 4:** Depósito
    - Deve ser possível realizar depósito em uma conta existente
        - Ao realizar o depósito em uma conta existente
        - Deve ser informado os seguintes dados para realizar o depósito:
            - Número da conta
            - Valor
        - Ao realizar um depósito, deve ser registrar um histórico de movimentação
- **Cenário 5:** Pagamento de conta
    - Deve ser possível realizar o pagamento de uma conta com o valor disponível da carteira
        - Ao realizar o pagamento de uma conta, deve ser informado os seguintes dados:
            - Número da carteira
            - Valor da conta
            - Valor a ser pago
        - Ao realizar o pagamento da conta, deve ser feita a validação se a conta existe e está ativa
        - Ao realizar o pagamento da conta, deve ser validado os valor da conta e a ser pago
        - Ao realizar o pagamento da conta, deve ser validado se o valor a ser pago é superior ao valor na carteira
        - Ao realizar o pagamento da conta, deve ser registrado um histórico de movimentação
- **Cenário 6:** Mensageria
    - A cada movimentação realizada deve ser enviada uma mensagem para uma fila

## Arquitetura
![Arquitetura do serviço](./img/arquitetura.jpg)    
## Acesso ao serviço
#### Subindo o serviço
Iniciar o serviço:

<code>
docker-compose up -d --build
</code>

Parar o serviço:

<code>
docker-compose down
</code>

#### Endpoint
[Swagger-ui](http://localhost:8091/swagger-ui.html)

[Criar cliente](http://localhost:8091/client)

[Criar carteira](http://localhost:8091/wallet)
### BE-JV-007-projeto-final
# API de Compra e Venda de Bitcoins

## Visão geral
A API permite que usuários realizem transações de compra e venda de Bitcoins a partir da moeda local (BRL) e acessem informações sobre seu portfólio. A API requer autenticação por usuário e senha para acessar todas as funcionalidades.

## Operações suportadas

- Login
- Registro
- Obter valor do portfólio em BRL ou BTC
- Apagar conta do usuário
- Comprar ou vender BTC
- Depositar ou sacar R$


## Endpoints

### - Login
Endpoint: `POST /login`

Este endpoint recebe um objeto DTO com o nome de usuário e a senha do usuário. Se as credenciais estiverem corretas, a API retornará um token de autenticação que deve ser usado em todas as outras chamadas de API.

Exemplo de requisição:
```
POST /login

Content-Type: application/json
{
    "username": "usuarioqualquer",
    "password": "s3nh4s3gur4"
}
```

Exemplo de resposta:
```
HTTP/1.1 200 OK

Content-Type: application/json
{
    "token": <token_de_autorizacao>
}
```

### - Registro
Endpoint: `POST /register`

Esse endpoint recebe um objeto DTO com o nome de usuário, senha e tipo de usuário. Se o registro for bem-sucedido, a API retornará um token de autenticação que deve ser usado em todas as outras chamadas de API.

Exemplo de requisição:
```
POST /register

Content-Type: application/json
{
    "username": "beltrano",
    "password": "p@ssw0rd",
    "userType": "regular"
}
```

Exemplo de resposta:
```
HTTP/1.1 201 Created

Content-Type: application/json
{
    "token": <token_de_autorizacao>
}
```

### - Obter valor do portfólio em BRL ou BTC
Endpoint: `GET /{id}?currency=`

Este endpoint recebe o ID do usuário e um parâmetro "currency" opcional, que pode ser "BRL" ou "BTC". Se o usuário estiver autenticado e for o mesmo que o ID fornecido ou for um admin, a API retornará o valor do portfólio correspondente em BRL ou BTC, de acordo com o valor do parâmetro "currency".

Exemplo de requisição:
```
GET /1?currency=BRL
Authorization: Bearer <token_de_autorizacao>
```

Exemplo de resposta:
```
HTTP/1.1 200 OK

Content-Type: application/json
{
    "value": 1000.0
}
```

### - Apagar conta do usuário
Endpoint: `DELETE /{id}`

Este endpoint apaga a conta do usuário correspondente ao id informado na URL. O acesso é restrito ao próprio usuário ou a um administrador.


Exemplo de requisição:
```
DELETE /root/{id}
Authorization: Bearer <token_de_autorizacao>
```

Exemplo de resposta:
```
Resposta de sucesso
Código: 204 No Content
Não há corpo de resposta nesse caso.
```

### - Comprar e vender BTC
Endpoint: `POST /{id}/btc`

Este endpoint permite que o usuário compre ou venda bitcoins. O número de bitcoins é informado no corpo da requisição. Um valor negativo indica que o usuário deseja vender bitcoins. O acesso é restrito ao próprio usuário ou a um administrador.

Exemplo de requisição:
```
POST /root/{id}/btc
Authorization: Bearer <token_de_autorizacao>

Content-Type: application/json
{
    "quantity": 0.5
}
```

Exemplo de resposta:
```
{
    "message": "A transação foi concluída com sucesso."
}
```

### - Depósitos e saques em BRL

Endpoint: `POST /{id}/brl`

Este endpoint permite que o usuário deposite ou saque reais da sua conta. O valor a ser depositado ou sacado é informado no corpo da requisição. Um valor negativo indica que o usuário deseja sacar reais. O acesso é restrito ao próprio usuário ou a um administrador.

Exemplo de requisição:
```
POST /root/{id}/brl
Authorization: Bearer <token_de_autorizacao>

Content-Type: application/json
{
    "value": 100.00
}
```

Exemplo de resposta:
```
{
    "message": "A transação foi concluída com sucesso."
}
```


## Consulta de cotação de Bitcoin

Algumas funcionalidades da API de compra e venda de Bitcoin dependem da cotação atual da criptomoeda em relação ao Real (BRL). Para obter essa informação, a API utiliza a Exchange Rates API fornecida pela Blockchain.com.

A consulta à Exchange Rates API é feita internamente pela API de compra e venda de Bitcoin, portanto, não é necessário que o cliente faça chamadas adicionais à API da Blockchain.com.

Exemplo de requisição:
```
GET https://blockchain.info/tobtc?currency=BRL&value=1000
```
Nesse exemplo, a cotação do Bitcoin em relação ao Real é consultada para o valor de R$ 1.000,00 (mil reais). O valor de retorno é um número decimal que representa a quantidade de Bitcoin equivalente ao valor informado em reais.

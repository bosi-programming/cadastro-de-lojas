# API de Cadastro de Loja por CPF

Esta api tem como objetivo fazer o gerenciamento de cadastro de lojas de acordo com com as faixas de CEP que cada loja cadastrada atende. Ela contém todo o serviço de CRUD para realizar este controle.

Neste momento, a documentação desta api se encontra neste README, mas o ideal será move-la, em momento futuro, para algum serviço/lib de documentação que fornece maior apoio a quem a consome, como o swagger. A api também se beneficiaria de uma documentação interna feita em JavaDocs para documentar a sua estrutura e classes.

## Inicio

Para iniciar esta api, você precisará ter instalado em sua máquina, online ou em um conteiner, uma instância do banco de dados MariaDB. Inicia a sua instância. Dentro dela, crie o database que irá usar na api. Depois desta etapa garantida, troque os dados do arquivo application.properties pelos dados da sua instância do banco de dados. Troque `spring.datasource.url` pela url da sua instância, `spring.datasource.username` pelo username que você configurou em seu banco de dados e `spring.datasource.password` pela sua senha.

### Rotas

#### Rota `/loja`

##### GET

Realizando um get sem nenhuma parâmetro na url fará com que a rota retorne uma lista com todas as lojas cadastradas no sistema. Todavia, a rota também aceita o parâmetro códigoLoja, onde a rota retornará todas as instâncias do banco de dados relacionadas com aquele códig; e o parâmetro cep, onde a rota retornará a loja que atende aquele cep ou retornará que não há lojas que atendem o cep em específico.

##### POST

Através do método POST na rota `/loja`, é possivel adicionar uma nova loja no sistema. Para isto, o corpo da requisição deve conter:

- codigoLoja: O código da loja que queremos adicionar;
- faixaInicio: O início da faixa de cep que aquela loja atende;
- faixaFim: O fim da faixa de cep que a loja atende.

Esta rota validará os dados para confirmar que os valores de cep contém 9 digítos, se o valor de faixaFim é maior que o de faixaInicio e se não há nenhuma outra loja que atende a mesma faixa de cep ou que intercala em algum momento com a faixa que queremos cadastrar.

Para evitar possíveis problemas que podem ocorrer com duas lojas com faixas de cep conflitantes sendo cadastradas ao mesmo tempo, essa rota, assim como a rota de PUT para `/loja/{id}` foram feitas como `synchronized`. Todavia, este processo pode tornar a api não escalável.

Caso o problema descrito acima seja considerado improvável, pode-se ser retirado o termo `synchronized` dos métodos. Contudo, caso o problema seja real, mas a escalabilidade seja importante, podemos adicionar um trigger no banco de dados para adicionar um constrain/validação da faixa de cep dentro dele, evitando assim tanto o problema de múltiplas instâncias/threads tentando adicionar dados conflitantes no banco.

#### Rota `/loja/{id}`

##### GET

Uma request do tipo get na rota `/loja/{id}` retornará a loja em específico que tem o id dado.

##### PUT

Através do método PUT na rota acima, podemos realizar um update numa loja. O mesmo precisa do mesmo corpo e sofre dos mesmos problemas descritos na [rota POST de `/loja`](#POST).

##### DELETE

Através do delete na rota citada, é possível deletar a loja relacionada ao id dado.

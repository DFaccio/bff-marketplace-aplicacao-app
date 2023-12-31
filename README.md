# Marketplace de Compra Coletiva de Café

O objetivo do projeto é automatizar o processo de compra coletiva de café, que atualmente é realizado por um grupo no WhatsApp.

O processo tomado como base é a compra coletiva do Diário de um [Coffe Lover](https://www.instagram.com/diariodeumcoffeelover/).

O projeto consiste em fornecer um template web para a criação de grupos de compra, com um portfólio de cafés do fornecedor selecionado pelo administrador do grupo, e possibilitar a criação de pedidos de compra de forma controlada e com o acompanhamento atualizado de estoque e compradores interessados, com histórico das compras realizadas e confirmação de pagamento via anexo de comprovantes de transferência via PIX para uma conta específica.

O projeto encontra-se em andamento. Neste primeiro momento, estaremos entregando MVP com algumas operações CRUD conforme relação abaixo:
* Pessoa (GET, POST, PUT)
* Fornecedor (GET, POST)
* Grupo de Compra (GET, POST, PUT)
* Chave Pix (POST, PUT)
* Portífolio (GET, POST, PUT, DELETE)
* Produto (GET, POST, PUT, DELETE)
* Usuário (POST, PUT, GET)

Para um maior entendimento sobre o projeto, sinta-se livre para acessar nossa documentação usando o Notion [aqui](https://www.notion.so/8d23441e4ddc4bb88ab78d7b952595e7?v=ea155403c7cd4b568b812d0d50aee463&pvs=4).

O projeto foi idealizado e está sendo construído ao longo do curso de Arquitetura e Desenvolvimento Java da FIAP.


Membros do grupo de desenvolvimento:

 - [Aydan Amorim](https://github.com/AydanAmorim/)
 - [Danilo Faccio](https://github.com/DFaccio/)
 - [Erick Ribeiro](https://github.com/erickmatheusribeiro)
 - [Isabela França](https://github.com/fysabelah)

### Itens a instalar

O projeto utiliza as seguintes tecnologias. Desta forma, será necessário instalações prévias.
- [Java versão 17](https://www.oracle.com/br/java/technologies/downloads/#java17);
- [Banco de dados PostgreSQL >= 14](https://www.postgresql.org/)


### Configurações
Realize as seguintes configurações no application.properties:
* spring.datasource.url:

        jdbc:postgresql://localhost:{porta da instalação do PostgreSQL}/{banco criado para executar a aplicação}
* spring.datasource.username: altere *admin* para o usuário que deseja utilizar
* spring.datasource.password: altere *coffeelover* pela senha do usuário adicionada na propriedade anterior

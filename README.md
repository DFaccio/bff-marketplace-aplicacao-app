# Marketplace de Compra Coletiva de Café

O objetivo do projeto é automatizar o processo de compra coletiva de café, que atualmente é realizado por um grupo no WhatsApp.

O processo tomado como base é a compra coletiva do Diário de um [Coffe Lover](https://www.instagram.com/diariodeumcoffeelover/).

O projeto consiste em fornecer um template web para a criação de grupos de compra, com um portfólio de cafés do fornecedor selecionado pelo administrador do grupo, e possibilitar a criação de pedidos de compra de forma controlada e com o acompanhamento atualizado de estoque e compradores interessados, com histórico das compras realizadas e confirmação de pagamento via anexo de comprovantes de transferência via PIX para uma conta específica.

O projeto encontra-se em andamento. Neste primeiro momento, estaremos entregando MVP referente aos cadastros das entidades.

Para um maior entendimento sobre o projeto, sinta-se livre para acessar nossa documentação [aqui](https://www.notion.so/8d23441e4ddc4bb88ab78d7b952595e7?v=ea155403c7cd4b568b812d0d50aee463&pvs=4).

O projeto foi idealizado e está sendo construído ao longo do curso de Arquitetura e Desenvolvimento Java da FIAP.

Membros do grupo de desenvolvimento
 - Aydan Amorim
 - Danilo Faccio
 - Erick Ribeiro
 - [Isabela França](https://github.com/fysabelah)
 - João Baptista

### Itens a instalar
O projeto utiliza Java 17 e banco de dados PostgreSQL (>=14). Desta forma, será necessário ter os dois instalados.

### Configurações
Realize as seguintes configurações no application.properties:
* spring.datasource.url:

        jdbc:postgresql://localhost:{porta da instalação do PostgreSQL}/{banco criado para executar a aplicação}
* spring.datasource.username: altere *admin* para o usuário que deseja utilizar
* spring.datasource.password: altere *coffeelover* pela senha do usuário adicionada na propriedade anterior
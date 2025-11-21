# Lojinha

1. Introdução
Este projeto é uma aplicação de console desenvolvida em Java para simular um sistema básico de Ponto de Venda (PDV) e gestão de estoque. O sistema permite o cadastro inicial de produtos, o registro de vendas, a emissão de notas fiscais e a manutenção administrativa do estoque.

------------------------------------------------------------------------------------------------------------------------------------------

2. Estrutura do Sistema
O sistema opera através de um menu de console, onde todas as funcionalidades são acessadas por opções numéricas.

------------------------------------------------------------------------------------------------------------------------------------------

2.1 Funcionalidades de Venda e Catálogo:

1   Inicializar Base: Carrega os dados iniciais do catálogo de produtos.
2	Exibir Catálogo: Lista os produtos disponíveis em estoque.
3	Adicionar Item: Inclui um produto e quantidade na venda atual (carrinho).
4	Resumo da Venda: Exibe os itens no carrinho e o subtotal.
5	Finalizar Venda: Conclui a transação, atualiza o estoque e imprime a nota fiscal.
6	Histórico de Vendas: Lista os IDs e valores totais dos pedidos concluídos.
7	Buscar Venda Específica: Reimprime a nota fiscal de um pedido pelo seu ID.

------------------------------------------------------------------------------------------------------------------------------------------

2.2. Funcionalidades Administrativas (Admin):

8	Repor Estoque: Permite adicionar unidades ao estoque de um produto existente. Requer autenticação de administrador.
9	Relatório de Estoque Baixo: Lista produtos cuja quantidade em estoque é inferior a 10. Requer autenticação de administrador.
0	Sair: Encerra a execução da aplicação.

------------------------------------------------------------------------------------------------------------------------------------------

3. Implementação Técnica

Linguagem: Java.

Estrutura de Dados: Utiliza arrays paralelos estáticos para o catálogo (ids, nomes, precos, estoques) e para o histórico de pedidos (historicoIdsPedidos, historicoValoresPedidos).

Controle de Tempo: O java.time é utilizado para registrar a data e hora da emissão da nota fiscal.


------------------------------------------------------------------------------------------------------------------------------------------

4. Execução
Compilar o arquivo Main.java utilizando um JDK.

Executar a classe compilada.

Iniciar o uso do sistema selecionando a opção 1 (Inicializar base) no menu principal.

------------------------------------------------------------------------------------------------------------------------------------------
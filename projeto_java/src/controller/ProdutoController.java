package controller;

import model.Livro;
import model.Produto;
import repository.ProdutoRepository;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ProdutoController {
    private ProdutoRepository produtoRepository;
    private Scanner scanner;

    public ProdutoController() {
        this.produtoRepository = new ProdutoRepository();
        this.scanner = new Scanner(System.in);
    }

    public void exibirMenu() {
        int opcao;
        do {
            System.out.println("----------- Menu -----------");
            System.out.println("1. Listar todos os produtos");
            System.out.println("2. Buscar produto por ID");
            System.out.println("3. Cadastrar novo produto");
            System.out.println("4. Atualizar produto");
            System.out.println("5. Deletar produto");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            try {
                opcao = scanner.nextInt();
                switch (opcao) {
                    case 1:
                        listarTodos();
                        break;
                    case 2:
                        buscarPorId();
                        break;
                    case 3:
                        cadastrarProduto();
                        break;
                    case 4:
                        atualizarProduto();
                        break;
                    case 5:
                        deletarProduto();
                        break;
                    case 0:
                        System.out.println("Saindo do sistema. Até logo!");
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Erro ao ler entrada. Certifique-se de inserir um número.");
                scanner.nextLine(); // Limpa o buffer do scanner para evitar loop infinito
                opcao = -1; // Reinicia o loop
            }
        } while (opcao != 0);
    }

    private void listarTodos() {
        List<Produto> produtos = produtoRepository.listarTodos();
        for (Produto produto : produtos) {
            System.out.println(produto.exibirDetalhes());
        }
    }

    private void buscarPorId() {
        System.out.print("Digite o ID do produto: ");
        try {
            int id = scanner.nextInt();
            Produto produto = produtoRepository.buscarPorId(id);

            if (produto != null) {
                System.out.println(produto.exibirDetalhes());
            } else {
                System.out.println("Produto não encontrado.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Erro ao ler entrada. Certifique-se de inserir um número.");
            scanner.nextLine(); // Limpa o buffer do scanner para evitar loop infinito
        }
    }

    private void cadastrarProduto() {
        try {
            System.out.print("Digite o nome do produto: ");
            String nome = scanner.next();
            System.out.print("Digite o preço do produto: ");
            double preco = scanner.nextDouble();

            // Aqui poderia ser escolhido entre cadastrar um Produto ou um Livro (herdado)
            System.out.print("O produto é um livro? (S/N): ");
            char resposta = scanner.next().charAt(0);

            if (resposta == 'S' || resposta == 's') {
                System.out.print("Digite o nome do autor do livro: ");
                String autor = scanner.next();
                Livro livro = new Livro(produtoRepository.listarTodos().size() + 1, nome, preco, autor);
                produtoRepository.cadastrar(livro);
            } else {
                Produto produto = new Produto(produtoRepository.listarTodos().size() + 1, nome, preco) {
                    @Override
                    public String exibirDetalhes() {
                        return "Produto: " + nome + " - Preço: " + preco;
                    }
                };
                produtoRepository.cadastrar(produto);
            }

            System.out.println("Produto cadastrado com sucesso!");
        } catch (InputMismatchException e) {
            System.out.println("Erro ao ler entrada. Certifique-se de inserir dados válidos.");
            scanner.nextLine(); // Limpa o buffer do scanner para evitar loop infinito
        }
    }

    private void atualizarProduto() {
        try {
            System.out.print("Digite o ID do produto que deseja atualizar: ");
            int id = scanner.nextInt();
            Produto produto = produtoRepository.buscarPorId(id);

            if (produto != null) {
                System.out.print("Digite o novo nome do produto: ");
                String novoNome = scanner.next();
                System.out.print("Digite o novo preço do produto: ");
                double novoPreco = scanner.nextDouble();

                produtoRepository.atualizar(new Produto(id, novoNome, novoPreco) {
                    @Override
                    public String exibirDetalhes() {
                        return "Produto: " + novoNome + " - Preço: " + novoPreco;
                    }
                });

                System.out.println("Produto atualizado com sucesso!");
            } else {
                System.out.println("Produto não encontrado.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Erro ao ler entrada. Certifique-se de inserir um número.");
            scanner.nextLine(); // Limpa o buffer do scanner para evitar loop infinito
        }
    }

    private void deletarProduto() {
        try {
            System.out.print("Digite o ID do produto que deseja deletar: ");
            int id = scanner.nextInt();
            Produto produto = produtoRepository.buscarPorId(id);

            if (produto != null) {
                produtoRepository.deletar(id);
                System.out.println("Produto deletado com sucesso!");
            } else {
                System.out.println("Produto não encontrado.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Erro ao ler entrada. Certifique-se de inserir um número.");
            scanner.nextLine(); // Limpa o buffer do scanner para evitar loop infinito
        }
    }

    public static void main(String[] args) {
        ProdutoController controller = new ProdutoController();
        controller.exibirMenu();
    }
}


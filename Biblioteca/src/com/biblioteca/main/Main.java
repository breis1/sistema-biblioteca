package com.biblioteca.main;

import com.biblioteca.models.Livro;
import com.biblioteca.services.LivroService;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        LivroService livroService = new LivroService();
        Scanner scanner = new Scanner(System.in);

        int opcao;
        do {
            System.out.println("---------------------------");
            System.out.println("1. Listar livros");
            System.out.println("2. Adicionar livro");
            System.out.println("3. Remover livro");
            System.out.println("4. Buscar livro por ISBN");
            System.out.println("0. Sair");
            System.out.println("---------------------------");
            System.out.println("Escolha uma opcao:");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer após ler o número

            switch (opcao) {
                case 1:
                    try {
                        List<Livro> livros = livroService.listarLivros();
                        for (Livro livro : livros) {
                            System.out.println(livro.getId() + ". " + livro.getTitulo() + " - Autor: " + livro.getAutor() +
                                    ", ISBN: " + livro.getIsbn() + ", Quantidade disponivel: " + livro.getQuantidade());
                        }
                    } catch (SQLException e) {
                        System.err.println("Erro ao listar livros: " + e.getMessage());
                    }
                    break;

                case 2:
                    System.out.println("Digite o titulo do livro:");
                    String titulo = scanner.nextLine();
                    System.out.println("Digite o autor do livro:");
                    String autor = scanner.nextLine();
                    System.out.println("Digite o ISBN do livro:");
                    String isbn = scanner.nextLine();
                    System.out.println("Digite a quantidade de exemplares:");
                    int quantidade = scanner.nextInt();
                    scanner.nextLine(); // Limpar o buffer após ler o número
                    Livro novoLivro = new Livro(titulo, autor, isbn, quantidade);
                    try {
                        livroService.adicionarLivro(novoLivro);
                        System.out.println("Livro adicionado com sucesso!");
                    } catch (SQLException e) {
                        System.err.println("Erro ao adicionar livro: " + e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("Digite o titulo do livro que deseja remover:");
                    String tituloRemover = scanner.nextLine();
                    System.out.println("Digite a quantidade de exemplares a remover:");
                    int quantidadeRemover = scanner.nextInt();
                    scanner.nextLine(); // Limpar o buffer após ler o número
                    try {
                        livroService.removerLivroPorTitulo(tituloRemover, quantidadeRemover);
                        System.out.println("Livro removido com sucesso!");
                    } catch (SQLException e) {
                        System.err.println("Erro ao remover livro: " + e.getMessage());
                    }
                    break;
                case 4:
                    System.out.println("Digite o ISBN do livro que deseja buscar:");
                    String isbnBuscar = scanner.nextLine();
                    try {
                        Livro livroEncontrado = livroService.buscarLivroPorISBN(isbnBuscar);
                        if (livroEncontrado != null) {
                            System.out.println("Livro encontrado:");
                            System.out.println("Titulo: " + livroEncontrado.getTitulo());
                            System.out.println("Autor: " + livroEncontrado.getAutor());
                            System.out.println("ISBN: " + livroEncontrado.getIsbn());
                            System.out.println("Quantidade disponível: " + livroEncontrado.getQuantidade());
                        } else {
                            System.out.println("Livro não encontrado com o ISBN: " + isbnBuscar);
                        }
                    } catch (SQLException e) {
                        System.err.println("Erro ao buscar livro por ISBN: " + e.getMessage());
                    }
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção invalida.");
            }
        } while (opcao != 0);

        scanner.close();
    }
}

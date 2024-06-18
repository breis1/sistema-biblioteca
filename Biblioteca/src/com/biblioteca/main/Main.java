package com.biblioteca.main;

import com.biblioteca.models.Emprestimo;
import com.biblioteca.models.Livro;
import com.biblioteca.models.Usuario;
import com.biblioteca.services.EmprestimoService;
import com.biblioteca.services.LivroService;
import com.biblioteca.services.UsuarioService;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final UsuarioService usuarioService = new UsuarioService();
    private static final EmprestimoService emprestimoService = new EmprestimoService();
    private static final LivroService livroService = new LivroService();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String[] args) {
        mostrarMenu();
    }

    private static void mostrarMenu() {
        int opcao = 0;
        do {
            System.out.println("\n### Biblioteca - Menu Principal ###");
            System.out.println("------------------------------------------");
            System.out.println("1. Listar Livros");
            System.out.println("2. Listar Empréstimos");
            System.out.println("3. Registrar Empréstimo");
            System.out.println("4. Devolver Livro");
            System.out.println("5. Listar Usuários");
            System.out.println("6. Adicionar Usuário");
            System.out.println("7. Sair");
            System.out.println("------------------------------------------");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
                switch (opcao) {
                    case 1:
                        listarLivros();
                        break;
                    case 2:
                        listarEmprestimos();
                        break;
                    case 3:
                        registrarEmprestimo();
                        break;
                    case 4:
                        devolverLivro();
                        break;
                    case 5:
                        listarUsuarios();
                        break;
                    case 6:
                        adicionarUsuario();
                        break;
                    case 7:
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número válido.");
            } catch (SQLException | ParseException e) {
                System.err.println("Erro: " + e.getMessage());
            }

        } while (opcao != 7);
    }

    private static void listarLivros() throws SQLException {
        List<Livro> livros = livroService.listarLivros();
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado.");
        } else {
            System.out.println("\n### Lista de Livros ###");
            livros.forEach(System.out::println);
        }
    }

    private static void listarEmprestimos() throws SQLException {
        List<Emprestimo> emprestimos = emprestimoService.listarEmprestimos();
        if (emprestimos.isEmpty()) {
            System.out.println("Nenhum empréstimo encontrado.");
        } else {
            System.out.println("\n### Lista de Empréstimos ###");
            emprestimos.forEach(System.out::println);
        }
    }

    private static void registrarEmprestimo() throws SQLException, ParseException {
        System.out.println("\n### Registrar Empréstimo ###");
        listarLivros();
        System.out.print("ID do livro: ");
        int livroId = Integer.parseInt(scanner.nextLine());
        System.out.print("Nome do usuário: ");
        String nomeUsuario = scanner.nextLine();
        System.out.print("Data de devolução (yyyy-MM-dd): ");
        Date dataDevolucao = dateFormat.parse(scanner.nextLine());

        emprestimoService.registrarEmprestimo(livroId, nomeUsuario, dataDevolucao);
        System.out.println("Empréstimo registrado com sucesso.");
    }

    private static void devolverLivro() throws SQLException {
        System.out.println("\n### Devolver Livro ###");
        listarEmprestimos();
        System.out.print("ID do empréstimo a ser devolvido: ");
        int emprestimoId = Integer.parseInt(scanner.nextLine());

        emprestimoService.devolverEmprestimo(emprestimoId);
        System.out.println("Livro devolvido com sucesso.");
    }

    private static void listarUsuarios() throws SQLException {
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário encontrado.");
        } else {
            System.out.println("\n### Lista de Usuários ###");
            usuarios.forEach(System.out::println);
        }
    }

    private static void adicionarUsuario() throws SQLException {
        System.out.println("\n### Adicionar Novo Usuário ###");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();

        Usuario usuario = new Usuario(nome, email);
        usuarioService.adicionarUsuario(usuario);
        System.out.println("Usuário adicionado com sucesso.");
    }
}

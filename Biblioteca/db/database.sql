-- Conectar ao banco de dados biblioteca
\c biblioteca

-- Tabela de livros
CREATE TABLE livros (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    autor VARCHAR(255) NOT NULL,
    isbn VARCHAR(20) NOT NULL,
    quantidade INT NOT NULL
);

-- Tabela de usuários
CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL
);

-- Tabela de empréstimos
CREATE TABLE emprestimos (
    id SERIAL PRIMARY KEY,
    livro_id INT NOT NULL REFERENCES livros(id),
    usuario_id INT NOT NULL REFERENCES usuarios(id),
    data_emprestimo DATE NOT NULL,
    data_devolucao DATE
);

-- Inserir dados de exemplo
INSERT INTO livros (titulo, autor, isbn, quantidade) VALUES 
('A Arte da Guerra', 'Sun Tzu', '9788576571224', 3),
('1984', 'George Orwell', '9780451524935', 5),
('Dom Quixote', 'Miguel de Cervantes', '9780060934347', 2);

INSERT INTO usuarios (nome, email) VALUES 
('João Silva', 'joao.silva@email.com'),
('Maria Oliveira', 'maria.oliveira@email.com'),
('Pedro Santos', 'pedro.santos@email.com');

INSERT INTO emprestimos (livro_id, usuario_id, data_emprestimo) VALUES 
(1, 1, '2024-06-01'),
(2, 2, '2024-06-02'),
(3, 3, '2024-06-03');

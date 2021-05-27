insert into categoria_skate (nome) values
('Street'), ('Long'), ('Cruiser');

insert into skate (categoria_id, conservacao_atual, nome, quebrado, tamanho) values
(1, 100, 'Skate Street 1', false, 5.3),
(3, 100, 'Skate Cruiser 1', false, 12.3),
(2, 100, 'Skate Long 1', false, 25.3),
(1, 100, 'Skate Street 2', true, 6.2);

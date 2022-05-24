insert into client (id, birthday,document,documenttype,email,lastname,name, created_at)
    values (1, '1995-12-12', '12345678911', 'CPF', 'fulano@email', 'Tal', 'Fulano', NOW());

insert into client (id, birthday,document,documenttype,email,lastname,name, created_at)
    values (2, '1995-12-12', '12345678922', 'CPF', 'fulano@email', 'Tal', 'Fulano', NOW());

ALTER SEQUENCE sq_client RESTART WITH 3;
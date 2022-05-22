insert into client (id, birthday,document,documenttype,email,lastname,name)
    values (1, '1995-12-12', '12345678911', 1, 'fulano@email', 'Tal', 'Fulano');

insert into client (id, birthday,document,documenttype,email,lastname,name)
    values (2, '1995-12-12', '12345678922', 1, 'fulano@email', 'Tal', 'Fulano');

ALTER SEQUENCE sq_client RESTART WITH 3;
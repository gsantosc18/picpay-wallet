insert into history (created_at, updated_at, history_action, wallet, id) values (now(), null, 'DEPOSIT', 1, 1);
ALTER SEQUENCE sq_history RESTART WITH 2;
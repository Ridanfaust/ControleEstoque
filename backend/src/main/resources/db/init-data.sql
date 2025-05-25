DO '
BEGIN
INSERT INTO estoque.situacao_venda (id, descricao)
VALUES (1, ''CONCLUÍDA''), (2, ''CANCELADA'')
ON CONFLICT (id) DO UPDATE SET descricao = EXCLUDED.descricao;
END;
'

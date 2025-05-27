DO '
BEGIN
-- Insere situação venda
INSERT INTO estoque.situacao_venda (id, descricao)
VALUES (1, ''CONCLUÍDA''), (2, ''CANCELADA'')
ON CONFLICT (id) DO UPDATE SET descricao = EXCLUDED.descricao;

-- Insere tipos de produto
INSERT INTO estoque.tipo_produto (id, descricao, data_cadastro, ativo)
VALUES
    (1, ''Eletrônicos'', CURRENT_DATE, true),
    (2, ''Móveis e Eletrodomésticos'', CURRENT_DATE, true),
    (3, ''Serviço'', CURRENT_DATE, true)
ON CONFLICT (id) DO UPDATE SET descricao = EXCLUDED.descricao, data_cadastro = EXCLUDED.data_cadastro, ativo = EXCLUDED.ativo;

-- Insere tipos de movimentação
INSERT INTO estoque.tipo_movimentacao (id, descricao, natureza_movimentacao, venda, data_cadastro, ativo)
VALUES
    -- Tipo ENTRADA
    (1, ''Compra de Fornecedor'', ''ENTRADA'', false, CURRENT_DATE, true),
    (2, ''Compra Direto da Fábrica'', ''ENTRADA'', false, CURRENT_DATE, true),

    -- Tipos SAIDA
    (3, ''Venda ao Cliente'', ''SAIDA'', true, CURRENT_DATE, true),
    (4, ''Venda para Revendedor'', ''SAIDA'', true, CURRENT_DATE, true),
    (5, ''Uso Interno'', ''SAIDA'', false, CURRENT_DATE, true)
ON CONFLICT (id) DO UPDATE SET descricao = EXCLUDED.descricao, natureza_movimentacao = EXCLUDED.natureza_movimentacao, venda = EXCLUDED.venda, data_cadastro = EXCLUDED.data_cadastro, ativo = EXCLUDED.ativo;

-- Atualiza as sequences para continuar após o maior ID inserido
PERFORM setval(''estoque.tipo_produto_seq'', (SELECT MAX(id) FROM estoque.tipo_produto), true);
PERFORM setval(''estoque.tipo_movimentacao_seq'', (SELECT MAX(id) FROM estoque.tipo_movimentacao), true);
END;
'

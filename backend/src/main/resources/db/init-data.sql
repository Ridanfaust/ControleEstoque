DO '
BEGIN
-- Insere situação venda
INSERT INTO estoque.situacao_venda (id, descricao)
VALUES (1, ''CONCLUÍDA''), (2, ''CANCELADA'')
ON CONFLICT (id) DO UPDATE SET descricao = EXCLUDED.descricao;

-- Insere tipos de produto
INSERT INTO estoque.tipo_produto (id, descricao, data_cadastro, ativo)
VALUES
    (nextval(''estoque.tipo_produto_seq''), ''Eletrônicos'', CURRENT_DATE, true),
    (nextval(''estoque.tipo_produto_seq''), ''Móveis e Eletrodomésticos'', CURRENT_DATE, true),
    (nextval(''estoque.tipo_produto_seq''), ''Serviço'', CURRENT_DATE, true)
ON CONFLICT (id) DO UPDATE SET descricao = EXCLUDED.descricao, data_cadastro = EXCLUDED.data_cadastro, ativo = EXCLUDED.ativo;

-- Insere tipos de movimentação
INSERT INTO estoque.tipo_movimentacao (id, descricao, natureza_movimentacao, venda, data_cadastro, ativo)
VALUES
    -- Tipo ENTRADA
    (nextval(''estoque.tipo_movimentacao_seq''), ''Compra de Fornecedor'', ''ENTRADA'', false, CURRENT_DATE, true),
    (nextval(''estoque.tipo_movimentacao_seq''), ''Compra Direto da Fábrica'', ''ENTRADA'', false, CURRENT_DATE, true),

    -- Tipos SAIDA
    (nextval(''estoque.tipo_movimentacao_seq''), ''Venda ao Cliente'', ''SAIDA'', true, CURRENT_DATE, true),
    (nextval(''estoque.tipo_movimentacao_seq''), ''Venda para Revendedor'', ''SAIDA'', true, CURRENT_DATE, true),
    (nextval(''estoque.tipo_movimentacao_seq''), ''Uso Interno'', ''SAIDA'', false, CURRENT_DATE, true)
ON CONFLICT (id) DO UPDATE SET descricao = EXCLUDED.descricao, natureza_movimentacao = EXCLUDED.natureza_movimentacao, venda = EXCLUDED.venda, data_cadastro = EXCLUDED.data_cadastro, ativo = EXCLUDED.ativo;
END;
'

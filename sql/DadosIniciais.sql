use frotacontrol;
START TRANSACTION;

-- =====================================================
-- 1. EMPRESA
-- =====================================================

INSERT INTO empresa
(
    data_atualizacao,
    data_cadastro,
    ativa,
    cnpj,
    contato,
    email,
    nome_fantasia,
    razao_social,
    telefone
)
SELECT
    NOW(),
    NOW(),
    b'1',
    '12.345.678/0001-99',
    'Waldyr Barbosa',
    'contato@cilazatta.com.br',
    'Cila Zatta Desenvolvimento',
    'CILA ZATTA DESENVOLVIMENTO LTDA',
    '(11)99999-9999'
FROM DUAL
WHERE NOT EXISTS (
    SELECT 1 FROM empresa WHERE cnpj = '12.345.678/0001-99'
);

-- =====================================================
-- 2. FUNCIONÁRIO
-- =====================================================

INSERT INTO funcionario
(
    data_atualizacao,
    data_cadastro,
    ativo,
    bairro,
    cep,
    cidade,
    email,
    logradouro,
    matricula,
    nome,
    numero,
    telefone,
    empresa_id,
    uf
)
SELECT
    NOW(),
    NOW(),
    b'1',
    'Centro',
    '01000-000',
    'Sao Paulo',
    'dev@cilazatta.com.br',
    'Rua Exemplo',
    'DEV001',
    'DESENVOLVEDOR PADRAO',
    '100',
    '(11)99999-9999',
    e.id,
    'SP'
FROM empresa e
WHERE e.cnpj = '12.345.678/0001-99'
AND NOT EXISTS (
    SELECT 1 FROM funcionario WHERE matricula = 'DEV001'
);

-- =====================================================
-- 3. USUÁRIO
-- =====================================================

INSERT INTO usuario
(
    data_atualizacao,
    data_cadastro,
    ativo,
    login,
    senha,
    empresa_id,
    funcionario_id,
    name
)
SELECT
    NOW(),
    NOW(),
    b'1',
    'admin',
    '$2a$10$zjcaleVAK3IBkyZVh9xdtOSb8jRwwqYzV55U9lz0cpbBV1ffF32wi',
    e.id,
    f.id,
    'Administrador do Sistema'
FROM empresa e
JOIN funcionario f ON f.matricula = 'DEV001'
WHERE e.cnpj = '12.345.678/0001-99'
AND NOT EXISTS (
    SELECT 1 FROM usuario WHERE login = 'admin'
);

-- =====================================================
-- 4. ROLE (usuario_roles)
-- =====================================================

INSERT INTO usuario_roles
(
    usuario_id,
    role
)
SELECT
    u.id,
    'ROLE_ADMIN'
FROM usuario u
WHERE u.login = 'admin'
AND NOT EXISTS (
    SELECT 1
    FROM usuario_roles ur
    WHERE ur.usuario_id = u.id
    AND ur.role = 'ROLE_ADMIN'
);

COMMIT;
-- Seed do catalogo de ativos B3. Idempotente: so insere tickers inexistentes.
-- Precos sao seeds aproximados; POST /assets/refresh-prices atualiza via brapi.

CREATE TEMP TABLE _asset_seed (
    ticker varchar(30),
    name varchar(100),
    asset_type varchar(30),
    industry_segment varchar(255),
    price numeric(13, 4)
);

INSERT INTO _asset_seed VALUES
    ('PETR4',  'Petrobras PN',                'STOCK', 'Petroleo e Gas',       38.50),
    ('PETR3',  'Petrobras ON',                'STOCK', 'Petroleo e Gas',       36.10),
    ('VALE3',  'Vale',                        'STOCK', 'Mineracao',            62.40),
    ('ITUB4',  'Itau Unibanco',               'STOCK', 'Bancos',               33.20),
    ('BBDC4',  'Bradesco PN',                 'STOCK', 'Bancos',               14.80),
    ('BBAS3',  'Banco do Brasil',             'STOCK', 'Bancos',               27.90),
    ('WEGE3',  'Weg',                         'STOCK', 'Industria',            41.30),
    ('ABEV3',  'Ambev',                       'STOCK', 'Bebidas',              12.60),
    ('SUZB3',  'Suzano',                      'STOCK', 'Papel e Celulose',     57.20),
    ('GGBR4',  'Gerdau PN',                   'STOCK', 'Siderurgia',           19.40),
    ('VIVT3',  'Telefonica Brasil',           'STOCK', 'Telecomunicacoes',     53.70),
    ('EGIE3',  'Engie Brasil',                'STOCK', 'Energia Eletrica',     42.80),
    ('TAEE11', 'Taesa',                       'STOCK', 'Energia Eletrica',     39.10),
    ('ITSA4',  'Itausa',                      'STOCK', 'Holdings',             10.90),
    ('LREN3',  'Lojas Renner',                'STOCK', 'Varejo',               18.30),
    ('MXRF11', 'Maxi Renda FII',              'REIT',  'Papel',                10.15),
    ('HGLG11', 'CSHG Logistica FII',          'REIT',  'Logistica',           161.00),
    ('KNRI11', 'Kinea Renda Imobiliaria FII', 'REIT',  'Hibrido',             138.50),
    ('XPML11', 'XP Malls FII',                'REIT',  'Shopping',            104.20),
    ('VISC11', 'Vinci Shopping Centers FII',  'REIT',  'Shopping',            112.30),
    ('HGRU11', 'CSHG Renda Urbana FII',       'REIT',  'Renda Urbana',        125.60),
    ('VILG11', 'Vinci Logistica FII',         'REIT',  'Logistica',            94.80),
    ('IVVB11', 'iShares S&P 500 ETF',         'ETF',   'Renda Variavel EUA',  285.00),
    ('BOVA11', 'iShares Ibovespa ETF',        'ETF',   'Indice Bovespa',      118.70),
    ('SMAL11', 'iShares Small Cap ETF',       'ETF',   'Small Caps Brasil',    89.40),
    ('HASH11', 'Hashdex Nasdaq Crypto ETF',   'ETF',   'Cripto Indice',        33.20);

INSERT INTO gd_asset (id, ticker, name, asset_type, industry_segment, price)
SELECT gen_random_uuid(), s.ticker, s.name, s.asset_type, s.industry_segment, s.price
FROM _asset_seed s
WHERE NOT EXISTS (
    SELECT 1 FROM gd_asset a WHERE a.ticker = s.ticker
);

DROP TABLE _asset_seed;

-- Database generated with pgModeler (PostgreSQL Database Modeler).
-- pgModeler  version: 0.9.2
-- PostgreSQL version: 12.0
-- Project Site: pgmodeler.io
-- Model Author: ---


-- Database creation must be done outside a multicommand file.
-- These commands were put in this file only as a convenience.
-- -- object: new_database | type: DATABASE --
-- -- DROP DATABASE IF EXISTS new_database;
-- CREATE DATABASE new_database;
-- -- ddl-end --
-- 

-- object: public.produto_seq | type: SEQUENCE --
-- DROP SEQUENCE IF EXISTS public.produto_seq CASCADE;
CREATE SEQUENCE public.produto_seq
	INCREMENT BY 1
	MINVALUE 0
	MAXVALUE 2147483647
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;
-- ddl-end --
-- ALTER SEQUENCE public.produto_seq OWNER TO postgres;
-- ddl-end --

-- object: public.produto | type: TABLE --
-- DROP TABLE IF EXISTS public.produto CASCADE;
CREATE TABLE public.produto (
	id integer NOT NULL DEFAULT nextval('public.produto_seq'::regclass),
	nome character varying(50) NOT NULL,
	preco double precision NOT NULL,
	estoque integer NOT NULL DEFAULT 0,
	descricao text NOT NULL,
	CONSTRAINT produto_pk PRIMARY KEY (id),
	CONSTRAINT preco_ck CHECK (preco >= 0),
	CONSTRAINT estoque_ck CHECK (estoque >= 0)

);
-- ddl-end --
-- ALTER TABLE public.produto OWNER TO postgres;
-- ddl-end --

-- object: public.cliente_seq | type: SEQUENCE --
-- DROP SEQUENCE IF EXISTS public.cliente_seq CASCADE;
CREATE SEQUENCE public.cliente_seq
	INCREMENT BY 1
	MINVALUE 0
	MAXVALUE 2147483647
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;
-- ddl-end --
-- ALTER SEQUENCE public.cliente_seq OWNER TO postgres;
-- ddl-end --

-- object: public.cliente | type: TABLE --
-- DROP TABLE IF EXISTS public.cliente CASCADE;
CREATE TABLE public.cliente (
	id integer NOT NULL DEFAULT nextval('public.cliente_seq'::regclass),
	nome character varying(100) NOT NULL,
	cpf character varying(11) NOT NULL,
	descricao text NOT NULL,
	endereco text NOT NULL,
	data_cadastro timestamp NOT NULL DEFAULT now(),
	telefone character varying(20),
	email character varying(100),
	CONSTRAINT cliente_pk PRIMARY KEY (id),
	CONSTRAINT cpf_un UNIQUE (cpf)

);
-- ddl-end --
-- ALTER TABLE public.cliente OWNER TO postgres;
-- ddl-end --

-- object: public.fornecedor_seq | type: SEQUENCE --
-- DROP SEQUENCE IF EXISTS public.fornecedor_seq CASCADE;
CREATE SEQUENCE public.fornecedor_seq
	INCREMENT BY 1
	MINVALUE 0
	MAXVALUE 2147483647
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;
-- ddl-end --
-- ALTER SEQUENCE public.fornecedor_seq OWNER TO postgres;
-- ddl-end --

-- object: public.fornecedor | type: TABLE --
-- DROP TABLE IF EXISTS public.fornecedor CASCADE;
CREATE TABLE public.fornecedor (
	id integer NOT NULL DEFAULT nextval('public.fornecedor_seq'::regclass),
	nome character varying(100) NOT NULL,
	cnpj character varying(20) NOT NULL,
	descricao text NOT NULL,
	endereco text NOT NULL,
	data_cadastro timestamp NOT NULL DEFAULT now(),
	telefone character varying(20),
	email character varying(100),
	CONSTRAINT fornecedor_pk PRIMARY KEY (id),
	CONSTRAINT cnpj_un UNIQUE (cnpj)

);
-- ddl-end --
-- ALTER TABLE public.fornecedor OWNER TO postgres;
-- ddl-end --

-- object: public.produtofornecido_seq | type: SEQUENCE --
-- DROP SEQUENCE IF EXISTS public.produtofornecido_seq CASCADE;
CREATE SEQUENCE public.produtofornecido_seq
	INCREMENT BY 1
	MINVALUE 0
	MAXVALUE 2147483647
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;
-- ddl-end --
-- ALTER SEQUENCE public.produtofornecido_seq OWNER TO postgres;
-- ddl-end --

-- object: public.produtofornecido | type: TABLE --
-- DROP TABLE IF EXISTS public.produtofornecido CASCADE;
CREATE TABLE public.produtofornecido (
	id integer NOT NULL DEFAULT nextval('public.produtofornecido_seq'::regclass),
	id_fornecedor integer NOT NULL,
	id_produto integer NOT NULL,
	CONSTRAINT produtofornecido_pk PRIMARY KEY (id)

);
-- ddl-end --
-- ALTER TABLE public.produtofornecido OWNER TO postgres;
-- ddl-end --

-- object: fornecedor_fk | type: CONSTRAINT --
-- ALTER TABLE public.produtofornecido DROP CONSTRAINT IF EXISTS fornecedor_fk CASCADE;
ALTER TABLE public.produtofornecido ADD CONSTRAINT fornecedor_fk FOREIGN KEY (id_fornecedor)
REFERENCES public.fornecedor (id) MATCH FULL
ON DELETE CASCADE ON UPDATE CASCADE;
-- ddl-end --

-- object: produto_fk | type: CONSTRAINT --
-- ALTER TABLE public.produtofornecido DROP CONSTRAINT IF EXISTS produto_fk CASCADE;
ALTER TABLE public.produtofornecido ADD CONSTRAINT produto_fk FOREIGN KEY (id_produto)
REFERENCES public.produto (id) MATCH FULL
ON DELETE CASCADE ON UPDATE CASCADE;
-- ddl-end --

-- object: public.operacao_seq | type: SEQUENCE --
-- DROP SEQUENCE IF EXISTS public.operacao_seq CASCADE;
CREATE SEQUENCE public.operacao_seq
	INCREMENT BY 1
	MINVALUE 0
	MAXVALUE 2147483647
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;
-- ddl-end --
-- ALTER SEQUENCE public.operacao_seq OWNER TO postgres;
-- ddl-end --

-- object: public.operacao | type: TABLE --
-- DROP TABLE IF EXISTS public.operacao CASCADE;
CREATE TABLE public.operacao (
	id integer NOT NULL DEFAULT nextval('public.operacao_seq'::regclass),
	data date NOT NULL DEFAULT now(),
	tipo character varying(10) NOT NULL,
	valor_final double precision NOT NULL,
	forma_pagamento character varying(10) NOT NULL,
	id_cliente integer,
	id_fornecedor integer,
	CONSTRAINT venda_pk PRIMARY KEY (id),
	CONSTRAINT tipo_ck CHECK (tipo in ('VENDA', 'COMPRA')),
	CONSTRAINT forma_pagamento_ck CHECK (forma_pagamento in ('DINHEIRO', 'PRAZO')),
	CONSTRAINT valor_final_ck CHECK (valor_final >= 0)

);
-- ddl-end --
-- ALTER TABLE public.operacao OWNER TO postgres;
-- ddl-end --

-- object: cliente_fk | type: CONSTRAINT --
-- ALTER TABLE public.operacao DROP CONSTRAINT IF EXISTS cliente_fk CASCADE;
ALTER TABLE public.operacao ADD CONSTRAINT cliente_fk FOREIGN KEY (id_cliente)
REFERENCES public.cliente (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: public.itemoperacao | type: TABLE --
-- DROP TABLE IF EXISTS public.itemoperacao CASCADE;
CREATE TABLE public.itemoperacao (
	id integer NOT NULL DEFAULT nextval('public.operacao_seq'::regclass),
	quantidade integer NOT NULL,
	id_produto integer NOT NULL,
	id_operacao integer NOT NULL,
	CONSTRAINT itemvenda_pk PRIMARY KEY (id),
	CONSTRAINT quantidade_ck CHECK (quantidade > 0)

);
-- ddl-end --
-- ALTER TABLE public.itemoperacao OWNER TO postgres;
-- ddl-end --

-- object: produto_fk | type: CONSTRAINT --
-- ALTER TABLE public.itemoperacao DROP CONSTRAINT IF EXISTS produto_fk CASCADE;
ALTER TABLE public.itemoperacao ADD CONSTRAINT produto_fk FOREIGN KEY (id_produto)
REFERENCES public.produto (id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --

-- object: operacao_fk | type: CONSTRAINT --
-- ALTER TABLE public.itemoperacao DROP CONSTRAINT IF EXISTS operacao_fk CASCADE;
ALTER TABLE public.itemoperacao ADD CONSTRAINT operacao_fk FOREIGN KEY (id_operacao)
REFERENCES public.operacao (id) MATCH FULL
ON DELETE CASCADE ON UPDATE CASCADE;
-- ddl-end --

-- object: public.compra_seq | type: SEQUENCE --
-- DROP SEQUENCE IF EXISTS public.compra_seq CASCADE;
CREATE SEQUENCE public.compra_seq
	INCREMENT BY 1
	MINVALUE 0
	MAXVALUE 2147483647
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;
-- ddl-end --
-- ALTER SEQUENCE public.compra_seq OWNER TO postgres;
-- ddl-end --

-- object: public.itemcompra_seq | type: SEQUENCE --
-- DROP SEQUENCE IF EXISTS public.itemcompra_seq CASCADE;
CREATE SEQUENCE public.itemcompra_seq
	INCREMENT BY 1
	MINVALUE 0
	MAXVALUE 2147483647
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;
-- ddl-end --
-- ALTER SEQUENCE public.itemcompra_seq OWNER TO postgres;
-- ddl-end --

-- object: fornecedor_fk | type: CONSTRAINT --
-- ALTER TABLE public.operacao DROP CONSTRAINT IF EXISTS fornecedor_fk CASCADE;
ALTER TABLE public.operacao ADD CONSTRAINT fornecedor_fk FOREIGN KEY (id_fornecedor)
REFERENCES public.fornecedor (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: public.debito_seq | type: SEQUENCE --
-- DROP SEQUENCE IF EXISTS public.debito_seq CASCADE;
CREATE SEQUENCE public.debito_seq
	INCREMENT BY 1
	MINVALUE 0
	MAXVALUE 2147483647
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;
-- ddl-end --
-- ALTER SEQUENCE public.debito_seq OWNER TO postgres;
-- ddl-end --

-- object: public.debito | type: TABLE --
-- DROP TABLE IF EXISTS public.debito CASCADE;
CREATE TABLE public.debito (
	id integer NOT NULL DEFAULT nextval('public.debito_seq'::regclass),
	sequencial smallint NOT NULL,
	valor double precision NOT NULL,
	pago boolean NOT NULL DEFAULT false,
	vencimento date NOT NULL,
	id_operacao integer NOT NULL,
	CONSTRAINT debito_pk PRIMARY KEY (id),
	CONSTRAINT valor_ck CHECK (valor > 0)

);
-- ddl-end --
-- ALTER TABLE public.debito OWNER TO postgres;
-- ddl-end --

-- object: operacao_fk | type: CONSTRAINT --
-- ALTER TABLE public.debito DROP CONSTRAINT IF EXISTS operacao_fk CASCADE;
ALTER TABLE public.debito ADD CONSTRAINT operacao_fk FOREIGN KEY (id_operacao)
REFERENCES public.operacao (id) MATCH FULL
ON DELETE CASCADE ON UPDATE CASCADE;
-- ddl-end --

-- object: fornecedor_produto_un | type: CONSTRAINT --
-- ALTER TABLE public.produtofornecido DROP CONSTRAINT IF EXISTS fornecedor_produto_un CASCADE;
ALTER TABLE public.produtofornecido ADD CONSTRAINT fornecedor_produto_un UNIQUE (id_fornecedor,id_produto);
-- ddl-end --

-- object: sequencial_operacao_un | type: CONSTRAINT --
-- ALTER TABLE public.debito DROP CONSTRAINT IF EXISTS sequencial_operacao_un CASCADE;
ALTER TABLE public.debito ADD CONSTRAINT sequencial_operacao_un UNIQUE (id_operacao,sequencial);
-- ddl-end --

-- object: produto_operacao_un | type: CONSTRAINT --
-- ALTER TABLE public.itemoperacao DROP CONSTRAINT IF EXISTS produto_operacao_un CASCADE;
ALTER TABLE public.itemoperacao ADD CONSTRAINT produto_operacao_un UNIQUE (id_produto,id_operacao);
-- ddl-end --



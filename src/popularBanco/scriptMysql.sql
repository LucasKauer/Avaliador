CREATE TABLE Apresentacao
  (
    Id_Apresentacao INT NOT NULL AUTO_INCREMENT,
    Titulo          VARCHAR (50) NOT NULL ,
    Resumo          VARCHAR (4000) NOT NULL ,
    Categoria       VARCHAR (50) NOT NULL ,
    Data            DATETIME NOT NULL ,
    Situacao        VARCHAR (50) NOT NULL,
    CONSTRAINT Apresentacao_PK PRIMARY KEY ( Id_Apresentacao )
  );

CREATE TABLE Autor
  (
    Id_Autor INT NOT NULL AUTO_INCREMENT,
    Nome     VARCHAR (50) NOT NULL ,
    Genero   CHAR (1) NOT NULL,
    CONSTRAINT Autor_PK PRIMARY KEY ( Id_Autor )
  ) ;

CREATE TABLE Autor_Apresentacao
  (
    Id_Autor_Apresentacao INT NOT NULL AUTO_INCREMENT,
    Apresentacao_Id       INT NOT NULL ,
    Autor_Id              INT NOT NULL,
    CONSTRAINT Autor_Apresentacao_PK PRIMARY KEY ( Id_Autor_Apresentacao )
  ) ;

CREATE TABLE Avaliacao
  (
    Id_Avaliacao      INT NOT NULL AUTO_INCREMENT,
    Comentario_Geral  VARCHAR (4000) NOT NULL ,
    Critica_Tecnica   VARCHAR (4000) NOT NULL ,
    Nota_Conteudo     INT NOT NULL ,
    Nota_Inovacao     INT NOT NULL ,
    Nota_Apresentacao INT NOT NULL ,
    Restricao         CHAR (1) NOT NULL ,
    Avaliador_Id      INT NOT NULL ,
    Apresentacao_Id   INT NOT NULL,
	CONSTRAINT Avaliacao_PK PRIMARY KEY ( Id_Avaliacao )
  ) ;

CREATE TABLE Avaliador
  (
    Id_Avaliador INT NOT NULL AUTO_INCREMENT,
    Instituicao  VARCHAR (50) NOT NULL ,
    Nome         VARCHAR (50) NOT NULL ,
    Idade        INT NOT NULL ,
    Genero       CHAR (1) NOT NULL ,
    Usuario_Id   INT NOT NULL,
    CONSTRAINT Avaliador_PK PRIMARY KEY ( Id_Avaliador )
  ) ;
CREATE UNIQUE INDEX Avaliador__IDX ON Avaliador
  (
    Usuario_Id ASC
  );

CREATE TABLE Usuario
  (
    Id_Usuario    INT NOT NULL AUTO_INCREMENT,
    Login         VARCHAR (50) NOT NULL ,
    Senha         VARCHAR (100) NOT NULL ,
    Nivel_Usuario VARCHAR (100) NOT NULL,
	CONSTRAINT Usuario_PK PRIMARY KEY ( Id_Usuario )
  ) ;

ALTER TABLE Autor_Apresentacao ADD CONSTRAINT Apresentacao_FK FOREIGN KEY ( Apresentacao_Id ) REFERENCES Apresentacao ( Id_Apresentacao ) ;

ALTER TABLE Autor_Apresentacao ADD CONSTRAINT Autor_FK FOREIGN KEY ( Autor_Id ) REFERENCES Autor ( Id_Autor ) ;

ALTER TABLE Avaliacao ADD CONSTRAINT Avaliacao_Apresentacao_FK FOREIGN KEY ( Apresentacao_Id ) REFERENCES Apresentacao ( Id_Apresentacao ) ;

ALTER TABLE Avaliacao ADD CONSTRAINT Avaliacao_Avaliador_FK FOREIGN KEY ( Avaliador_Id ) REFERENCES Avaliador ( Id_Avaliador ) ;

ALTER TABLE Avaliador ADD CONSTRAINT Avaliador_Usuario_FK FOREIGN KEY ( Usuario_Id ) REFERENCES Usuario ( Id_Usuario ) ;

ALTER TABLE Autor ADD COLUMN Email VARCHAR (100) NOT NULL;

ALTER TABLE Avaliacao DROP COLUMN Restricao;
ALTER TABLE Avaliacao  ADD COLUMN Restricao BOOL DEFAULT FALSE;
ALTER TABLE Autor DROP COLUMN Genero;

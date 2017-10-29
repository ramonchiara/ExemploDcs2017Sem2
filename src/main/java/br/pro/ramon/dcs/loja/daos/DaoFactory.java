package br.pro.ramon.dcs.loja.daos;

import br.pro.ramon.dcs.loja.categorias.CategoriaDao;
import br.pro.ramon.dcs.loja.categorias.CategoriaDaoJdbc;

public class DaoFactory {

    private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String URL = "jdbc:sqlserver://localhost;database=db";
    private static final String USER = "user";
    private static final String PASS = "pass";

    public static CategoriaDao getCategoriaDao() {
        return new CategoriaDaoJdbc(DRIVER, URL, USER, PASS);
    }

}

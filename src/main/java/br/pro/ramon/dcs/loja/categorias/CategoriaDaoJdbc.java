package br.pro.ramon.dcs.loja.categorias;

import br.pro.ramon.dcs.loja.daos.DaoException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDaoJdbc implements CategoriaDao {

    @Override
    public List<Categoria> findAll() throws DaoException {
        List<Categoria> categorias = new ArrayList<>();

        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url = "jdbc:sqlserver://localhost;database=db";
        String user = "user";
        String pass = "pass";

        try {
            Class.forName(driver);
            try (Connection conn = DriverManager.getConnection(url, user, pass);
                    PreparedStatement stmt = conn.prepareStatement("select * from Categoria order by nomeCategoria");
                    ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Long id = rs.getLong("idCategoria");
                    String nome = rs.getString("nomeCategoria");
                    String descricao = rs.getString("descCategoria");

                    Categoria categoria = new Categoria(id, nome, descricao);
                    categorias.add(categoria);
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            throw new DaoException(ex);
        }

        return categorias;
    }

    @Override
    public void create(Categoria categoria) throws DaoException {
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url = "jdbc:sqlserver://localhost;database=db";
        String user = "user";
        String pass = "pass";

        try {
            if (!categoria.isValid()) {
                throw new IllegalArgumentException("categoria");
            }
            Class.forName(driver);
            try (Connection conn = DriverManager.getConnection(url, user, pass);
                    PreparedStatement stmt = conn.prepareStatement("insert into Categoria (nomeCategoria, descCategoria) values (?, ?)", Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, categoria.getNome());
                stmt.setString(2, categoria.getDescricao());
                int n = stmt.executeUpdate();
                if (n == 0) {
                    throw new SQLException("Nenhum registro inserido!");
                } else {
                    try (ResultSet rs = stmt.getGeneratedKeys()) {
                        if (rs.next()) {
                            Long id = rs.getLong(1);
                            categoria.setId(id);
                        } else {
                            throw new SQLException("Id não foi criado!");
                        }
                    }
                }
            }
        } catch (IllegalArgumentException | ClassNotFoundException | SQLException ex) {
            throw new DaoException(ex);
        }
    }

}

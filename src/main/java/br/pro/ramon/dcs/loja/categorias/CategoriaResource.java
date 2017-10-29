package br.pro.ramon.dcs.loja.categorias;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/categorias")
public class CategoriaResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategorias() {
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url = "jdbc:sqlserver://localhost;database=db";
        String user = "user";
        String pass = "pass";

        try {
            Class.forName(driver);
            try (Connection conn = DriverManager.getConnection(url, user, pass);
                    PreparedStatement stmt = conn.prepareStatement("select * from Categoria order by nomeCategoria");
                    ResultSet rs = stmt.executeQuery()) {
                List<Categoria> categorias = new ArrayList<>();
                while (rs.next()) {
                    Long id = rs.getLong("idCategoria");
                    String nome = rs.getString("nomeCategoria");
                    String descricao = rs.getString("descCategoria");

                    Categoria categoria = new Categoria(id, nome, descricao);
                    categorias.add(categoria);
                }
                return Response.ok(new Categorias(categorias)).build();
            }
        } catch (ClassNotFoundException | SQLException ex) {
            return Response.serverError().entity(ex.getMessage()).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCategoria(Categoria categoria) {
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
                            return Response.created(URI.create("categorias/" + id)).build();
                        } else {
                            throw new SQLException("Id não foi criado!");
                        }
                    }
                }
            }
        } catch (IllegalArgumentException | ClassNotFoundException | SQLException ex) {
            return Response.serverError().entity(ex.getMessage()).build();
        }
    }

}

package br.pro.ramon.dcs.loja.categorias;

import br.pro.ramon.dcs.loja.daos.DaoException;
import java.net.URI;
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
        try {
            CategoriaDao dao = new CategoriaDaoJdbc();
            List<Categoria> categorias = dao.findAll();
            return Response.ok(new Categorias(categorias)).build();
        } catch (DaoException ex) {
            return Response.serverError().entity(ex.getMessage()).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCategoria(Categoria categoria) {
        try {
            CategoriaDao dao = new CategoriaDaoJdbc();
            dao.create(categoria);
            return Response.created(URI.create("categorias/" + categoria.getId())).build();
        } catch (DaoException ex) {
            return Response.serverError().entity(ex.getMessage()).build();
        }
    }

}

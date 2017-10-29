package br.pro.ramon.dcs.loja.categorias;

import br.pro.ramon.dcs.loja.daos.DaoException;
import java.util.List;

public interface CategoriaDao {

    List<Categoria> findAll() throws DaoException;

    void create(Categoria categoria) throws DaoException;

}

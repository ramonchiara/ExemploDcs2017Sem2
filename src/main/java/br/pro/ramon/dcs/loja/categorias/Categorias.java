package br.pro.ramon.dcs.loja.categorias;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Categorias implements Serializable {

    private List<Categoria> categorias;

    protected Categorias() {
    }

    public Categorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    @XmlElement
    public List<Categoria> getCategorias() {
        return categorias;
    }

}

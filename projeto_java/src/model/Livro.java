package model;

public class Livro extends Produto {
    private String autor;

    public Livro(int id, String nome, double preco, String autor) {
        super(id, nome, preco);
        this.autor = autor;
    }

    // Getter e Setter para autor

    @Override
    public String exibirDetalhes() {
        return "Livro: " + nome + " - Autor: " + autor + " - Preço: " + preco;
    }
}

package repository;

import model.Produto;

import java.util.ArrayList;
import java.util.List;

public class ProdutoRepository {
    private List<Produto> produtos;

    public ProdutoRepository() {
        this.produtos = new ArrayList<>();
    }

    public List<Produto> listarTodos() {
        return new ArrayList<>(produtos);
    }

    public Produto buscarPorId(int id) {
        for (Produto produto : produtos) {
            if (produto.getId() == id) {
                return produto;
            }
        }
        return null;
    }

    public void cadastrar(Produto produto) {
        produtos.add(produto);
    }

    public void atualizar(Produto produtoAtualizado) {
        int index = -1;
        for (int i = 0; i < produtos.size(); i++) {
            if (produtos.get(i).getId() == produtoAtualizado.getId()) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            produtos.set(index, produtoAtualizado);
        } else {
            throw new IllegalArgumentException("Produto não encontrado para atualização.");
        }
    }

    public void deletar(int id) {
        Produto produto = buscarPorId(id);
        if (produto != null) {
            produtos.remove(produto);
        } else {
            throw new IllegalArgumentException("Produto não encontrado para exclusão.");
        }
    }
}


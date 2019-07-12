package model;

public class Servico {
    private int id;
    private String nome;
    private float preco;

    public Servico(String nome, float preco) {
        this.nome = nome;
        this.preco = preco;
    }
    
    public Servico(String nome, String preco) {
        this.nome = nome;
        try {
            this.preco = Float.valueOf(preco);
        } catch (NumberFormatException | NullPointerException ex) {
            this.preco = (float) -0.01;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public void setPreco(String preco) {
        try {
            this.preco = Float.valueOf(preco);
        } catch (NumberFormatException | NullPointerException ex) {
            this.preco = (float) -0.01;
        }
    }
}
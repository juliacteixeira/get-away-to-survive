public class Item {
  private String nome;
  private String descricao;
  private int peso;

  public Item(String nome, String descricao, int peso) {
    this.nome = nome;
    this.descricao = descricao;
    this.peso = peso;
  }

  public String getDescricao() {
    return descricao;
  }

  public int getPeso() {
    return peso;
  }

  public String getNome() {
    return nome;
  }

  public String getFullItemData() {
    return "Item: " + nome + " " + "\n" + descricao + "\npeso: " + peso;
  }

  public void setItemAsCollected(){
    nome = null;
    descricao = null;
    peso = 0;
  }
}

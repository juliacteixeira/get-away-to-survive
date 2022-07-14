import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Estudante {
  private String nome;
  private ArrayList<Item> robBall = new ArrayList<Item>();

  public Estudante(String nome) {
    this.nome = nome;
  }

  public String getStudantName() {
    return nome;
  }

  public List<Item> getRobBall() {
    return robBall;
  }

  public ArrayList<Item> setRobBall(Item item) {
    robBall.add(new Item(item.getNome(), item.getDescricao(), item.getPeso()));
    return robBall;
  }

  public boolean deleteItem(String itemNome) {
    for (int i = 0; i < robBall.size(); i++) {
      if (robBall.get(i).getNome().equals(itemNome)) {
        robBall.remove(i);
        return true;
      }
    }
    System.out.println("Item não encontrado");
    return false;
  }

  public List<Item> getAllRobBall() {
    return Collections.unmodifiableList(robBall);
  }

}


class Ingredient{

  String name;
  Double cost;

  public Ingredient(String name, Double cost, String priceUnit, String recipeUnit) {
      this.cost = cost;
      this.name = name;
      System.out.println("My name is: " + name);
  }

  public void printMe(){
      System.out.println("My name is: " + this.name);
  }
}


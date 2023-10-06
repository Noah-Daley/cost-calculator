import java.io.*;

class Main{
  
  
  public static int addTwoNumbers(int x, int y){
    return x + y;
  }
  
  public int addThreeNumbers(int x, int y){
    return x + y;
  }
  
  public static int findIngredient(Ingredient[] ingredients, String name){
    for(int i=0;i<ingredients.length; i++){
      if(ingredients[i].name == name)
        return i;
    }
    return -1;
  }
    static Ingredient[] allIngredients = {
      new Ingredient("milk", 2.58, "gallon", "cup"),
      new Ingredient("flour", 0.80, "lb", "cup"),
      
    };
  
  public static void main(String args[])
  throws IOException
  {

    

    int index = Main.findIngredient(allIngredients, "flour");
    if(index != -1){
      allIngredients[index].printMe();
    }


  
    
    System.out.println(addTwoNumbers(4,5));
    Main test = new Main();
    System.out.println(test.addThreeNumbers(8,5));
    
    System.out.print("\n");
    
    
    // Creating BufferedReader Object
    // InputStreamReader converts bytes to
    // stream of character
    BufferedReader bfn = new BufferedReader(new InputStreamReader(System.in));
    
    System.out.println("Enter the name of a new recipe:");
    String name = bfn.readLine();
    Recipe rec = new Recipe(name);

    for(int i=0;i<allIngredients.length; i++){
      System.out.println(allIngredients[i].name);
    }

    System.out.println("Enter the name of the first ingredient:");
    String str = bfn.readLine();
    System.out.println("Enter the quantity of " + str);
    double quantity = Float.parseFloat(bfn.readLine());
    
    rec.addIngredient(str, quantity);

    rec.printMe();

    /*      
    // Integer reading internally
    int it = Integer.parseInt(bfn.readLine());
    
    // Printing String
    System.out.println("Entered String : " + str);
    
    // Printing Integer
    System.out.println("Entered Integer : " + it);
    */
  }
}


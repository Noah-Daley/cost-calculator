import java.util.ArrayList; // import the ArrayList class



class Recipe {

    String name;
    ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>(); // Create an ArrayList object


    public Recipe(String name) {
    System.out.println("new recipe = " + name);
    this.name = name;
        
    

    }   

    public void addIngredient(String ing, Double quant) {
        //int index = Main.findIngredient(Main.allIngredients, ing);
        //if(index != -1){
        //ingredients.add(Main.allIngredients[0]);//index]);
        System.out.println();
    }
        
    
    

    public void printMe() {
        System.out.println("Name: " + name);
        System.out.println("Ingredients:");
        for(int i=0; i < 1/*ingredients.size()*/; i++) {
            
        }
    }

}
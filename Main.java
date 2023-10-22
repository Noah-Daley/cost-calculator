import java.io.*;
import java.util.Scanner;

class Main{
  
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

    BufferedReader bfn = new BufferedReader(new InputStreamReader(System.in));
    File f = new File("/home/noah/dev/cost-calculator/files");
    if (f.mkdir() == true) {
      System.out.println("done");
    } else {
      System.out.println("faill");
    }
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

    
  }


  public void writeFile(String text, String fileName) {
    File file = new File("/home/noah/dev/w3schools_tutorial/src/files/" + fileName);
    try {
      FileWriter myWriter = new FileWriter(file, true);
      myWriter.write("\n" + text);
      myWriter.close();
      System.out.println("Successfully wrote to the file.");
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }     
  }
  
  public void readFile(String fileName) {
    File file = new File("/home/noah/dev/w3schools_tutorial/src/files/" + fileName);
    try {
      Scanner myReader = new Scanner(file);
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        System.out.println(data);
      }
      myReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    } 
  }

  public void printFileInfo(String fileName) {
    File file = new File("/home/noah/dev/w3schools_tutorial/src/files/" + fileName);
    if (file.exists()) {
      System.out.println("File name: " + file.getName());
      System.out.println("Absolute path: " + file.getAbsolutePath());
      System.out.println("Writeable: " + file.canWrite());
      System.out.println("Readable: " + file.canRead());
      System.out.println("File size in bytes " + file.length());
    } else {
      System.out.println("The file does not exist.");
    }
  }

  public void printAllFiles(String folderPath) {
    File[] listOfFiles = new File(folderPath).listFiles();
    
    System.out.println("All files in " + folderPath + ":");
    for (File file : listOfFiles) {
      if(file.isFile()) {
      System.out.println(file.getName());
      }
    }
  }


}


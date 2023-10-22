import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
//import java.util.Scanner;

class Main{
    
  /*
    static Ingredient[] allIngredients = {
      new Ingredient(arrofStr[0], Double.parseDouble(arrofStr[1]), arrofStr[2], arrofStr[3]),
      new Ingredient("flour", 0.80, "lb", "cup"),

    };
  */
  public static void main(String args[])
  throws IOException
  {

    
/*
    int index = Main.findIngredient(allIngredients, "flour");
    if(index != -1){
      allIngredients[index].printMe();
    }

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

    //rec.printMe();
*/
    
    String text;
    String command;
    String fileName = "false";

    BufferedReader bfn = new BufferedReader(new InputStreamReader(System.in));
    
    text = bfn.readLine();
    String[] arrofStr = text.split(" ", 2);
    command = arrofStr[0];
    if (arrofStr.length > 1) {
      fileName = arrofStr[1] + ".txt";
    }
    File file = new File("/home/noah/dev/cost-calculator/recipes/" + fileName);
        
    switch(command) {
      case "new":
          if (file.createNewFile()) {
          System.out.println("Created: " + file.getName());
        } else {
          System.out.println("File already exists.");
        }
        break;
      case "read":
        //int line = Integer.parseInt(bfn.readLine());
        System.out.println(readFile(fileName, -1));
        break;
      case "rm":
        if (file.delete()) { 
          System.out.println("Deleted: " + file.getName());
        } else {
          System.out.println("Failed to delete the file.");
        }
        break;
      case "info":
        printFileInfo(fileName);
        break;
      case "write":
        System.out.println("Writing to " + fileName + ":");
        while (true) {
          text = bfn.readLine();
          if(text.equals("")) {
            break;
          } else {
            writeFile(text, fileName, " ");
          }
          text = bfn.readLine();
          if(text.equals("")) {
            break;
          } else {
            writeFile(text, fileName, "\n");
          }
        }
        System.out.println("Successfully wrote to the file.");
        break;
      case "ls":
        printAllFiles("/home/noah/dev/cost-calculator/recipes/");
        break;
      case "calc":
        System.out.println(calcRecipe(fileName));
        break;
      case "find":
        text = bfn.readLine();
        System.out.println(searchFile(fileName, text));
        break;
      default:
        System.out.println("unknown command: " + command);
    }

    
  }




/*********************************************************************************************/

  public static double calcRecipe(String fileName) throws IOException{
    double totalPrice = 0;
    Path path = Paths.get("/home/noah/dev/cost-calculator/recipes/" + fileName);
    long numOfLines = 0;
    double ingPrice = 0;

    try {
      numOfLines = Files.lines(path).count();
    } catch (IOException e) {
      e.printStackTrace();
    }


    for(int i=0; i<numOfLines ; i++) {
      String text = readFile(fileName, i);
      String[] arrofRec = text.split(" ", -1);
      String allIngredient = searchFile("../allIngredients.txt", arrofRec[0]);
      String[] arrOfIngredient = allIngredient.split(" ", -1);
      ingPrice = (Double.parseDouble(arrOfIngredient[1]) / Double.parseDouble(arrOfIngredient[4])) * Double.parseDouble(arrofRec[1]); 
      System.out.println(ingPrice);
      totalPrice += ingPrice;
    }
    return totalPrice;
  }

  public static String searchFile(String fileName, String inputSearch) throws IOException {
    BufferedReader bfn = new BufferedReader(new FileReader("/home/noah/dev/cost-calculator/recipes/" + fileName));
    String line;
    int countLine = -1;
    boolean done = false;
    String paddedInput = " " + inputSearch + " ";
    String paddedInputStart = inputSearch + " ";
    String paddedInputEnd = " " +inputSearch ;

    while((line = bfn.readLine()) != null) {
      countLine++;

      if(line.equals(inputSearch) ||
        line.startsWith(paddedInputStart) ||
        line.endsWith(paddedInputEnd) ||
        line.contains(paddedInput)) {
        done = true;
        break;
      }
    }
    bfn.close();
    if(done) {
      return readFile(fileName, countLine);
    } else {
      return "done";
    }
  }

  public static int findIngredient(Ingredient[] ingredients, String name){
    for(int i=0;i<ingredients.length; i++){
      if(ingredients[i].name == name)
        return i;
    }
    return -1;
  }

  public static void writeFile(String text, String fileName, String newLine) {
    File file = new File("/home/noah/dev/cost-calculator/recipes/" + fileName);
    try {
      FileWriter myWriter = new FileWriter(file, true);
      myWriter.write(text + newLine);
      myWriter.close();
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }     
  }
  
  public static String readFile(String fileName, int lineNum) {
    String returnValue = "";
    //System.out.println("Contents of " + fileName + ":");
    if(lineNum == -1) {
      Path path = Paths.get("/home/noah/dev/cost-calculator/recipes/" + fileName);
      long numOfLines = 0;
      
      try {
      numOfLines = Files.lines(path).count();
      } catch (IOException e) {
      e.printStackTrace();
      }

      for(int i = 0; i < numOfLines; i++) {
        try{
          String line = Files.readAllLines(Paths.get("/home/noah/dev/cost-calculator/recipes/" + fileName)).get(i);
          returnValue = returnValue + "\n" + line;
        } 
        catch(IOException e){
          System.out.println(e);
        }
      }
    } else {
      try{
          String line = Files.readAllLines(Paths.get("/home/noah/dev/cost-calculator/recipes/" + fileName)).get(lineNum);
          returnValue = line;
        } 
        catch(IOException e){
          System.out.println(e);
        }
    }
    return returnValue;
  }

  public static void printFileInfo(String fileName) {
    File file = new File("/home/noah/dev/cost-calculator/recipes/" + fileName);
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

  public static void printAllFiles(String folderPath) {
    File[] listOfFiles = new File(folderPath).listFiles();
    
    System.out.println("All files in " + folderPath + ":");
    for (File file : listOfFiles) {
      if(file.isFile()) {
      System.out.println(file.getName());
      }
    }
  }


}


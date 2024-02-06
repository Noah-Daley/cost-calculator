import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
//import java.util.Scanner;

class Main{
  
  public static void main(String args[])
  throws IOException
  {
    
    String text;
    String command;
    String fileName = "false";
    
    
    BufferedReader bfn = new BufferedReader(new InputStreamReader(System.in));
    

    while(true) {
      System.out.print("~>");
      text = bfn.readLine();
      if(text.equals("e") || text.equals("x") || text.equals("exit")) {
        break;
      }

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
              arrofStr = text.split(" ", -1);
              writeFile(arrofStr[0] + " " + convertToGrams(text), fileName);
            }
          }
          System.out.println("Successfully wrote to the file.");
          break;
        case "ls":
          printAllFiles("/home/noah/dev/cost-calculator/recipes/");
          break;
        case "calc":
          double x = Math.round(calcRecipe(fileName) * 100);
          System.out.println(x / 100);
          break;
        case "find":
          text = bfn.readLine();
          System.out.println(searchFile(fileName, text));
          break;
        case "con":
          text = bfn.readLine();
          System.out.println(convertToGrams(text));
          break;
        case "help":
          System.out.println(
              " new <recipe-name>: new recipe \n" +
              " read <recipe-name>: read recipe \n" +
              " rm <recipe-name>: remove recipe\n" +
              " info <recipe-name>: get file stats\n" +
              " write \\n <name quantity unit>: add ingredient to recipe\n" +
              " ls: list all recipes\n" +
              " calc <recipe-name>: calculate recipe cost\n" +
              " find <file-name> \\n <text-for-search>: find a line in file\n" +
              " con \\n <ingredient-name quantity unit>: convert to grams\n" +
              " help: show command list\n");
          break;
        default:
          System.out.println("unknown command: " + command);
      }
    }
    
    
  }
  
  
  
  
  /******************************************************************************************************************/
  
  public static double convertToGrams(String text) throws IOException { //e.g. input: "flour 4 c" and output: "480"
    String[] arrofStr = text.split(" ", 3);
    String name = arrofStr[0];
    double num = Integer.parseInt(arrofStr[1]);
    String unit = arrofStr[2];
    double output = -1;
    if(unit.equals("g")) {return num;} //if already grams: return
    if(unit.equals("oz")) {num = num * 28.3495; return num;} //convert oz to grams and return
    if(unit.equals("pounds") || unit.equals("lb")) {num = num * 453.592; return num;} //convert pounds to grams and return
    if(unit.equals("c")) {num = num * 48; unit = "t";} //convert cups to teaspoons
    if(unit.equals("T")) {num = num * 3; unit = "t";} //convert Tablespoons to teaspoons
    String ingredient = searchFile("../allIngredients.txt", name); //search for ingredient, returns: e.g. flour 0.80 lb t 2.5
    arrofStr = ingredient.split(" ", -1);
    
    if(unit.equals(arrofStr[2])) {output = num * Double.parseDouble(arrofStr[3]); //if ing-unit = unit: multiply by ing-grams
    } else {
      System.out.println("Could not parse: " + unit);
    }
    
    if(output == -1) {System.out.println("Could not parse: " + text);}
    return output;
  }

  public static double calcRecipe(String fileName) throws IOException{
    double totalPrice = 0;
    Path path = Paths.get("/home/noah/dev/cost-calculator/recipes/" + fileName);
    long numOfLines = 0;
    double ingPrice = 0;
    
    numOfLines = Files.lines(path).count();
    
    for(int i=0; i<numOfLines ; i++) {
      String text = readFile(fileName, i);
      String[] arrofRec = text.split(" ", -1);
      String allIngredient = searchFile("../allIngredients.txt", arrofRec[0]);
      String[] arrOfIngredient = allIngredient.split(" ", -1);
      ingPrice = Double.parseDouble(arrOfIngredient[1]) * Double.parseDouble(arrofRec[1]); 
      //System.out.println(ingPrice);
      totalPrice += ingPrice;
    }
    return totalPrice;
  }
  
  public static String searchFile(String fileName, String inputSearch) throws IOException { //returns whole line in which it found inputSearch
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
      System.out.println("!!!searchFile: could not find: " + inputSearch + "!!!");
      return "fail";
    }
  }
  
  public static void writeFile(String text, String fileName) {
    File file = new File("/home/noah/dev/cost-calculator/recipes/" + fileName);
    try {
      FileWriter myWriter = new FileWriter(file, true);
      myWriter.write(text + "\n");
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


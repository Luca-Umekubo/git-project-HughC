import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;

public class gitTester {
    public static void main(String[] args) throws IOException {
        checkAndDelete();
        Git repo = new Git();
        File testFile = new File("testfile.txt");
        repo.makeBlob(testFile);
    }

    //checks if all correct files exist in their propper locations and then deletes them after
    public static void checkAndDelete() throws IOException{
        File gitDir = new File("git");

        File objectsDir = new File("git/objects");

        File indexFile = new File("git/index");

        if (gitDir.exists() && objectsDir.exists() && indexFile.exists()){
            System.out.println("Git repository exists");
            
            recursiveDelete(gitDir);
            System.out.println("successfully deleted git directory and all contents");
        }
        else{
            if (!objectsDir.exists()){
                System.out.println("objects directory doesnt exist");  
            }
            if (!indexFile.exists()){
                System.out.println("index doesnt exist"); 
            }
            if (!gitDir.exists()){
                System.out.println("git directory doesnt exist");  
            }
        }
    }
    
    public static void recursiveDelete(File file) throws FileNotFoundException{
        if (file.isDirectory()){
            for (File c : file.listFiles())
            recursiveDelete(c);
        }
        if (!file.delete())
        throw new FileNotFoundException("Failed to delete file: " + file);
    }
}

import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class gitTester {
    public static void main(String[] args) throws IOException {
        //resets git repo:
        checkAndDelete();

        //makes new git repo
        Git repo = new Git();

        repo.setCompression(true);

        //tests file using a file in the git project folder
        File testFile = new File("testfile.txt");
        repo.makeBlob(testFile);

        //check blob
        checkBlobCreation(repo, testFile);
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

    public static void checkBlobCreation(Git repo, File testFile) throws IOException{
        System.out.println("checking if blob was created correctly:");
        String testfilehash = repo.generateFileName(testFile);

        File copiedFile = new File("git/objects/" + testfilehash);
        if(copiedFile.exists()){
            System.out.println("    successfully copied file to objects folder");
        }
        
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("git/index")));
        String line;
        while ((line = br.readLine()) != null)
        {
            if (line.contains(testfilehash))
            {
                if (line.contains(testFile.getName())) {
                    System.out.println("    successfully created blob and index entry");
                }
            }
        }
        br.close();
    }
}

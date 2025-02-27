import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class FileNode{
    String name;
    boolean isDirectory;
    List <FileNode> children;

    FileNode (String name, boolean isDirectory){
        this.name = name;
        this.isDirectory = isDirectory;
        this.children = new ArrayList<>();
    }

    void addChild(FileNode child){
        if (this.isDirectory){
            this.children.add(child);
        }
    }
}

interface FileFoundListener{
    void onFileFound(String filePath);
}

class RecursiveFileSearcher{
    private FileFoundListener listener;
    
    public void setFileFoundListener(FileFoundListener listener){
        this.listener = listener;
    }

    public void searchFiles(FileNode directory, String extension, String path){
        if (!directory.isDirectory) return;

        for (FileNode child: directory.children){
            String newPath = path + "/" + child.name;

            if (child.isDirectory){
                searchFiles(child, extension, newPath);
            } else if (child.name.endsWith(extension)){
                if (listener != null)
                listener.onFileFound(newPath);
                saveResult(newPath);
                }
            }
        }

private void saveResult(String filePath){
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("searc_results.txt", true))){
        writer.write(filePath);
        writer.newLine();
        } catch (IOException e){
                e.printStackTrace();
                }
            }
        }

public class RecursiveSearchApp{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        FileNode root = new FileNode("Root", true);
        FileNode subFolder = new FileNode("Documents", true);
        FileNode file1 = new FileNode("notes.txt", false);
        FileNode file2 = new FileNode("report.pdf", false);
        FileNode file3 = new FileNode("todo.txt", false);

        subFolder.addChild(file1);
        subFolder.addChild(file2);
        root.addChild(subFolder);
        root.addChild(file3);

        System.out.println("Enter file extension to search for: ");
        String extension = sc.nextLine();

        RecursiveFileSearcher searcher = new RecursiveFileSearcher();
        searcher.setFileFoundListener(filePath -> System.out.println("File found: " + filepath));

        System.out.println("\nSearching...\n");
        searcher.searchFiles(root, extension, "Root");
        System.out.println("\nSearch completed. Results saved to search_results.txt.");
    }
}
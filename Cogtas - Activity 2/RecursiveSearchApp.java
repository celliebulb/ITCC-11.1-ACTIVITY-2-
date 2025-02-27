import java.util.Scanner;

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
        searcher.setFileFoundListener(filePath -> System.out.println("File found: " + filePath));

        System.out.println("\nSearching...\n");
        searcher.searchFiles(root, extension, "Root");
        System.out.println("\nSearch completed. Results saved to search_results.txt.");
    }
}
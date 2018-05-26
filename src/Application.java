import me.ibrahimyilmaz.sorting.FileSorter;

public class Application {

    public static void main(String[] args) {
        String inputFile = "/Users/ibrahimyilmaz/IdeaProjects/SortFile/dir/unsorted.txt";
        String outputFile = "/Users/ibrahimyilmaz/IdeaProjects/SortFile/dir/sorted.txt";
        FileSorter.sort(inputFile, outputFile);
    }

}

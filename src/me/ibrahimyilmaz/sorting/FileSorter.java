package me.ibrahimyilmaz.sorting;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileSorter {
    private FileSorter() {

    }


    /***
     * Sort given input in the input file
     * @param inputFile file directory contains unsorted input
     * @param outputFile file directory to have sorted input
     */
    public static void sort(String inputFile, String outputFile) {
        File tempDir = FileManager.createTempDir();
        try {

            SortingLogger.i("Temp Directory Created");
            FileManager.splitItemsToChunks(inputFile, tempDir);
            SortingLogger.i("File splitted into chunks");
            String[] list = tempDir.list();
            int chunkSize = list.length;

            //sort n listed list in file by getting each one
            while (true) {
                List<Integer> elements = new ArrayList<>();
                for (int i = 0; i < chunkSize; i++) {
                    Integer item = FileManager.getFirstLineAndRemove(tempDir.getAbsolutePath() + "/" + list[i]);
                    if (item != null)
                        elements.add(item);
                }

                if (elements.isEmpty())
                    break;

                Collections.sort(elements);
                while (!elements.isEmpty()) {
                    Integer min = elements.get(0);
                    elements.remove(min);
                    FileManager.appendResult(outputFile, min);
                }
            }

            SortingLogger.i("output contains sorted inputs");
            tempDir.deleteOnExit();
        } catch (SortingException ex) {
            SortingLogger.e(ex.getMessage());
        }
    }
}

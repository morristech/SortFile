package me.ibrahimyilmaz.sorting;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    public static final int BLOCKSIZE = 50;
    private static final int TEMP_DIR_ATTEMPTS = 10000;

    public static File createTempDir() {
        File baseDir = new File(System.getProperty("java.io.tmpdir"));
        String baseName = System.currentTimeMillis() + "-";

        for (int counter = 0; counter < TEMP_DIR_ATTEMPTS; counter++) {
            File tempDir = new File(baseDir, baseName + counter);
            if (tempDir.mkdir()) {
                return tempDir;
            }
        }
        throw new IllegalStateException("Failed to create directory within "
                + TEMP_DIR_ATTEMPTS + " attempts (tried "
                + baseName + "0 to " + baseName + (TEMP_DIR_ATTEMPTS - 1) + ')');
    }

    /***
     * Writes given integer list to given file
     * @param directory temp dirs
     * @param fileName temp file name
     * @param list input List
     * @throws SortingException
     */
    private static void writeToFile(File directory, String fileName, List<Integer> list) throws SortingException {
        File file = null;
        try {
            file = File.createTempFile(fileName, ".tmp", directory);
        } catch (IOException e) {
            throw SortingException.newException(ErrorCode.TEMP_FILE_NOT_CREATED);
        }

        try {
            FileWriter fileWriter = new FileWriter(file);

            for (Integer val : list) {
                fileWriter.write(Integer.toString(val));
                fileWriter.write("\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            throw SortingException.newUnknownException(e.getMessage());
        }
    }


    /***
     * Split given input into same-sized sub input files...
     * @param fileURL
     * @param tempDirectory
     * @throws SortingException
     */
    public static void splitItemsToChunks(String fileURL, File tempDirectory) throws SortingException {

        File file = new File(fileURL);
        if (!file.exists() || file.isDirectory()) {
            throw SortingException.newException(ErrorCode.FILE_NOT_FOUND);
        }


        try {
            FileReader fileReader = new FileReader(file);

            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;

            ArrayList<Integer> buffer = new ArrayList<>();
            while ((line = bufferedReader.readLine()) != null) {
                buffer.add(Integer.valueOf(line));

                if (buffer.size() == BLOCKSIZE) {
                    saveChunkToTempDirectory(tempDirectory, SortedChunk.from(buffer));
                    buffer.clear();
                }
            }
            if (buffer.size() > 0) {
                saveChunkToTempDirectory(tempDirectory, SortedChunk.from(buffer));
                buffer.clear();
            }

            fileReader.close();

        } catch (FileNotFoundException e) {
            throw SortingException.newException(ErrorCode.FILE_NOT_FOUND);
        } catch (IOException e) {
            throw SortingException.newUnknownException(e.getMessage());
        }
    }

    /***
     * Save sorted chucks to file check:splitItemsToChunks method...
     * @param directory
     * @param chunk
     * @throws SortingException
     */
    public static void saveChunkToTempDirectory(File directory, SortedChunk chunk) throws SortingException {
        writeToFile(directory, chunk.getId(), chunk.getItems());
    }

    /**
     * Read first line of file and remove it...
     *
     * @param fileDir
     * @return
     * @throws SortingException
     */
    public static Integer getFirstLineAndRemove(String fileDir) throws SortingException {
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(fileDir, "rw");
        } catch (FileNotFoundException e) {
            throw SortingException.newException(ErrorCode.FILE_NOT_FOUND);
        }
        //Initial write position
        long writePosition = 0;
        try {
            writePosition = raf.getFilePointer();
            String firstLine = raf.readLine();
            // Shift the next lines upwards.
            long readPosition = raf.getFilePointer();

            byte[] buff = new byte[1024];
            int n;
            while (-1 != (n = raf.read(buff))) {
                raf.seek(writePosition);
                raf.write(buff, 0, n);
                readPosition += n;
                writePosition += n;
                raf.seek(readPosition);
            }
            raf.setLength(writePosition);
            raf.close();

            if (firstLine == null || firstLine.isEmpty())
                return null;

            return Integer.valueOf(firstLine);
        } catch (IOException e) {
            throw SortingException.newUnknownException(e.getMessage());
        }
    }

    /***
     * Append value to output file...
     * @param outputFile
     * @param value
     * @throws SortingException
     */
    public static void appendResult(String outputFile, Integer value) throws SortingException {
        try {
            FileWriter writer = new FileWriter(outputFile, true);
            writer.append(Integer.toString(value));
            writer.write("\n");
            writer.close();
        } catch (IOException e) {
            throw SortingException.newUnknownException(e.getMessage());
        }
    }
}

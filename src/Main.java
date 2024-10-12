import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

class FileCopyExample {

    public static void main(String[] args) {
        String sourceFile1 = "sourceFile1.txt";
        String destinationFile1 = "destinationFile1.txt";
        String sourceFile2 = "sourceFile2.txt";
        String destinationFile2 = "destinationFile2.txt";

        // Последовательное копирование
        long sequentialStartTime = System.currentTimeMillis();
        copyFileSequentially(sourceFile1, destinationFile1);
        copyFileSequentially(sourceFile2, destinationFile2);
        long sequentialEndTime = System.currentTimeMillis();
        System.out.println("Sequential copy time: " + (sequentialEndTime - sequentialStartTime) + " ms");

        // Параллельное копирование
        long parallelStartTime = System.currentTimeMillis();
        Thread thread1 = new Thread(() -> copyFile(sourceFile1, destinationFile1));
        Thread thread2 = new Thread(() -> copyFile(sourceFile2, destinationFile2));

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long parallelEndTime = System.currentTimeMillis();
        System.out.println("Parallel copy time: " + (parallelEndTime - parallelStartTime) + " ms");
    }

    private static void copyFile(String sourcePath, String destinationPath) {
        try {
            Files.copy(Path.of(sourcePath), Path.of(destinationPath), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Copied " + sourcePath + " to " + destinationPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void copyFileSequentially(String sourcePath, String destinationPath) {
        copyFile(sourcePath, destinationPath);
    }
}
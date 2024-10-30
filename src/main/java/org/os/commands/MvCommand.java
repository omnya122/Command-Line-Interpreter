
package org.os.commands;
import java.nio.file.*;
import java.io.IOException;

public class MvCommand {

    public void move(String sourcePath, String destinationPath) throws IOException {
        Path source = Paths.get(sourcePath);
        Path destination = Paths.get(destinationPath);

        // Debugging output
        System.out.println("Trying to move from: " + source.toAbsolutePath());
        System.out.println("To: " + destination.toAbsolutePath());

        // Ensure the source file or directory exists
        if (!Files.exists(source)) {
            throw new IOException("Source path does not exist: " + sourcePath);
        }

        // Ensure the destination is a directory when moving a directory
        if (Files.isDirectory(source) && Files.exists(destination) && !Files.isDirectory(destination)) {
            throw new IOException("Destination is not a directory: " + destinationPath);
        }

        // Rename or move the file/directory
        if (source.getParent().equals(destination.getParent()) && !Files.isDirectory(source)) {
            Files.move(source, destination, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Renamed " + source.getFileName() + " to " + destination.getFileName());
        } else {
            Files.move(source, destination, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Moved " + source.getFileName() + " to " + destination);
        }
    }

}

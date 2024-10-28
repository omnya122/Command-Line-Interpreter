
package org.os.commands;
import java.nio.file.*;
import java.io.IOException;

public class MvCommand {

    public void move(String sourcePath, String destinationPath) throws IOException {
        Path source = Paths.get(sourcePath);
        Path destination = Paths.get(destinationPath);

        // Ensure the source file or directory exists
        if (!Files.exists(source)) {
            throw new IOException("Source path does not exist: " + sourcePath);
        }

        // Ensure the destination is a directory when moving a directory
        if (Files.isDirectory(source) && Files.exists(destination) && !Files.isDirectory(destination)) {
            throw new IOException("Destination is not a directory: " + destinationPath);
        }
        if (source.getParent().equals(destination.getParent()) && !Files.isDirectory(source)) {
            Files.move(source, destination, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Renamed " + source.getFileName() + " to " + destination.getFileName());
        }
        // Case 2: Move to different directory (possibly rename as well)
        else {
            Files.move(source, destination, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Moved " + source.getFileName() + " to " + destination);
        }
    }
}

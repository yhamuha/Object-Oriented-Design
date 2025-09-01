package file_search.filesystem;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class File {
    private final boolean isDirectory;
    private final int size;
    private final String owner;
    private final String filename;
    private final Set<File> entries = new HashSet<>();

    public File(final boolean isDirectory, final int size, final String owner, final String filename) {
        this.isDirectory = isDirectory;
        this.size = size;
        this.owner = owner;
        this.filename = filename;
    }

    public Object extract(final FileAttribute attributeName) {
        switch (attributeName) {
            case SIZE -> {
                return size;
            }
            case OWNER -> {
                return owner;
            }
            case IS_DIRECTORY -> {
                return isDirectory;
            }
            case FILENAME -> {
                return filename;
            }
        }
        throw new IllegalArgumentException("invalid filter criteria type");
    }

    public void addEntry(final File entry) {
        entries.add(entry);
    }

    public Set<File> getEntries() {
        return Collections.unmodifiableSet(entries);
    }

    public String getFilename() {
        return filename;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public int getSize() {
        return size;
    }

    public String getOwner() {
        return owner;
    }
}

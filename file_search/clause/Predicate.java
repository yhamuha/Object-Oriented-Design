package file_search.clause;

import file_search.filesystem.File;

// Base interface for all file search predicates
public interface Predicate {
    // Checks if the given file matches the search condition
    boolean isMatch(final File inputFile);
}

package gitlet;

import java.io.Serializable;
import java.util.*;

import static gitlet.Utils.*;

/**
 * The staging area representation.
 * */
public class StagingArea implements Serializable {

    /** The map to store blobs for addition.
     * Filename as the key, SHA1 id of the blob as the value.
     */
    private Map<String, String> addition = new HashMap<>();

    /** The set to store filenames for removal.
     * */
    private Set<String> removal = new HashSet<>();

    /**
     * Remove the specific given blob from the addition staging area.
     *
     * @param fileName Filename of the blob
     * @return the SHA1 id of the removed blob. Return null if there was no mapping for the key.
     * */
    public String rmFileInAddition(String fileName) {
        return addition.remove(fileName);
    }

    /**
     * Add the file to the addition area.
     *
     * @param fileName Name of the file.
     * */
    public void addAddition(String fileName, String blobID) {
        // Get the path to the real file in CWD and verify if it exists
        addition.put(fileName, blobID);
    }

    /**
     * Stage the file for removal.
     *
     * @param fileName Name of the file.
     * */
    public void addRemoval(String fileName) {
        removal.add(fileName);
    }

    /**
     * Get the value of specific fileName.
     *
     * @param fileName Name of the file
     * @return SHA1 id of the blob
     * */
    public String getFile(String fileName) {
        return addition.get(fileName);
    }

    /**
     * Clear the staging area.
     * */
    public void clear() {
        addition.clear();
        removal.clear();
    }

    /**
     * Serialize the staging area to a file.*/
    public void save() {
        writeObject(Repository.STAGE, this);
    }

    /** Create a set view of the ADDITION hash map.
     *
     * @return A set of 'Map.Entry' objects, which contain both the key and the value
     * for each entry in the map.
     * */
    public Set<Map.Entry<String, String>> getAddSet() {
        return addition.entrySet();
    }

    public Set<String> getRemovalSet() {
        return removal;
    }

    /**
     * Check if staging area is empty.
     * */
    public boolean isEmpty() {
        return addition.isEmpty() && removal.isEmpty();
    }





}

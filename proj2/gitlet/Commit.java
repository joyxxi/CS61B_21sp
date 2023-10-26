package gitlet;

// TODO: any imports you need here

import java.io.File;
import java.io.Serializable;
import java.util.*;

import static gitlet.Utils.*;
import static gitlet.MyUtils.*;
import static gitlet.Utils.join;

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Commit implements Serializable, Cloneable {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /**
     * The message of this Commit.
     */
    private String message;

    /**
     * The time when this Commit was created.
     */
    private String timestamp;

    /**
     * The parent Commit.
     */
    private String parentID;

    /**
     * The Hashmap for storing file names and UID of blobs
     */
    private HashMap<String, String> file;

    /**
     * The SHA1 id.
     */
    private String id;
    // Something that keeps track of what files
    // this commit is tracking
    // Some other stuff?

    /**
     * Constructor for the initial commit.*/
    public Commit(String message) {
        this.message = message;
        this.parentID = null;
        this.timestamp = generateCurrentTime();
        file = new HashMap<>();
        this.id = getUID();
    }

    public Commit(String message, Commit parent) {
        this.message = message;
        this.parentID = parent.getUID();
        this.timestamp = generateCurrentTime();
        file = new HashMap<>();
        this.id = getUID();
    }

    /**
     * Constructor for cloning a new commit.
     * */
    private Commit(String message, HashMap<String, String> file, String parentID) {
        this.message = message;
        this.parentID = parentID;
        this.timestamp = generateCurrentTime();
        this.file = file;
        this.id = getUID();
    }


    /**
     * Generate the timestamp for this Commit.
     */
    private String generateCurrentTime() {
        /*Time for the initial Commit*/
        if (this.parentID == null) {
            return DateTimeFormatting.epochDateTime();
        }
        return DateTimeFormatting.currentDateTime();
    }

    /**
     * Add a new tracking file or update the file with a new blob,
     * and update the SHA1 ID of this commit.
     */
    public void updateFile(String fileName, String blob_UID) {
        file.put(fileName, blob_UID);
        id = getUID();
    }

    /**
     * Remove a tracked file in the FILE map,
     * and update the SHA1 ID of this commit.
     * */
    public void rmFile(String fileName) {
        file.remove(fileName);
        id = getUID();
    }

    /**
     * Get the Blob UID of the corresponding FILENAME.
     * If no such file exists, return null.
     *
     * @param fileName Name of the file
     * @return SHA1 id of the blob
     */
    public String getFile(String fileName) {
        return file.get(fileName);
    }

    public Boolean fileExists(String fileName) {
        if (!file.containsKey(fileName)) {
            return false;
        }
        Blob fileBlob = new Blob(fileName);
        return fileBlob.getID().equals(file.get(fileName));
    }

    /**
     * Serialize a commit to a file with UID in COMMITS_DIR
     */
    public void saveCommit() {
        File file = getCommitFile(id);
        saveObject(file, this);
    }

    /**
     * Deserialize a commit from a file with UID in COMMITS_DIR.
     */
    public static Commit commitFromFile(String commitUID) {
        File commitFile = getCommitFile(commitUID);
        return readObject(commitFile, Commit.class);
    }

    /**
     * Takes the version of the file as it exists in the commit,
     * and puts it in the working directory,
     * overwriting the version of the file that’s already there if there is one.
     * @param filename
     */
    public void writeFile(String filename) {
        String blobID = getFile(filename);
        if (blobID == null) {
            GitletException.exit("File does not exist in that commit.");
        }
        Blob fileBlob = Blob.blobFromFile(blobID);
        byte[] fileContent = fileBlob.getFileContent();
        File file = Utils.join(Repository.CWD, filename);
        writeContents(file, fileContent);
    }

    /**
     * Takes the version of all files as it exists in the commit, and puts them in the working directory.
     * Overwriting the version of the file that's already there.
     */
    public void writeAllFiles() {
        if (!file.isEmpty()) {
            for (Map.Entry<String, String> entry : file.entrySet()) {
                String fileName = entry.getKey();
                String blobID = entry.getValue();
                Blob fileBlob = Blob.blobFromFile(blobID);
                byte[] fileContent = fileBlob.getFileContent();
                File file = join(Repository.CWD, fileName);
                writeContents(file, fileContent);
            }
        }
    }

    /**
     * Clone a child commit with the same file map,
     * but with its own message, timestamp, and parentID.
     * Its parentID has been automatically set to the id of current commit.
     *
     * @param message commit message
     * @return new child commit*/
    public Commit clone(String message) {
        return new Commit(message, file, id);
    }



    /**
     * Get the UID of this commit.
     */
    public String getUID() {
        if (parentID == null) {
            return sha1(message, timestamp, file.toString());
        }
        return sha1(message, parentID, timestamp, file.toString());
        // message, parent, timestamp may have problem.
        // e.g. the parent of initial commit is null, would that cause the problem?
    }

    public String getMessage() {
        return this.message;
    }

    public String getParentID() {
        return this.parentID;
    }


    public String getTimestamp() {
        return this.timestamp;
    }
}


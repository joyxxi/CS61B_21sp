package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import static gitlet.Utils.*;

public class MyUtils {
//    /**
//     * Get directory name from SHA1 id.
//     *
//     * @param uid SHA1 id
//     * @return Name of the directory
//     */
//    public static String getObjectDirName(String uid) {
//        return uid.substring(0, 2);
//    }
//
//    /**
//     * Get the file name from SHA1 id.
//     *
//     * @param uid SHA1 id
//     * @return Name of the file
//     */
//    public static String getObjectFileName(String uid) {
//        return uid.substring(2);
//    }

    /**
     * Get a file instance of the commit with the path generated from SHA1 id in the COMMITS_DIR folder.
     *
     * @param id SHA1 id
     * @return File instance
     */
    public static File getCommitFile(String id) {
//        String dirName = getObjectDirName(id);
//        String fileName = getObjectFileName(id);
        return join(Repository.COMMITS_DIR, id);
    }

    /**
     * Get a commit instance with the specific SHA1 id
     * @param id commit id
     * @return Commit instance
     */
    public static Commit getCommit(String id) {
        File commitFile = getCommitFile(id);
        if (!commitFile.exists()) {
            GitletException.exit("No commit with that id exists.");
        }
        return readObject(commitFile, Commit.class);
    }

    /**
     * Get a file instance of the blob with the path generated from SHA1 id in the BLOBS_DIR folder.
     *
     * @param id SHA1 id
     * @return File instance
     * */
    public static File getBlobFile(String id) {
//        String dirName = getObjectDirName(id);
//        String fileName = getObjectFileName(id);
        return join(Repository.BLOBS_DIR, id);
    }

    public static StagingArea getStagingArea() {
        if (Repository.STAGE.exists()) {
            return readObject(Repository.STAGE, StagingArea.class);
        }
        return new StagingArea();
    }


    /**
     * Write a serializable OBJ to File. Make a new directory if it doesn't exist.
     *
     * @param file File instance
     * @param obj Serializable object
     */
    public static void saveObject(File file, Serializable obj) {
        File parDir = file.getParentFile();

        if (!parDir.exists()) {
            parDir.mkdir();
        }

        writeObject(file, obj);
    }

    /**
     * Clear current working directory, delete all the files.
     */
    public static void clearCWD() {
        List<String> fileList = plainFilenamesIn(Repository.CWD);
        if (fileList == null) {
            return;
        }
        for (String fileName : fileList) {
            join(Repository.CWD, fileName).delete();
        }
    }


}

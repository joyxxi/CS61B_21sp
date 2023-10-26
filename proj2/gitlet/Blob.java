package gitlet;

import java.io.File;
import java.io.Serializable;

import static gitlet.Utils.*;
import static gitlet.MyUtils.*;

public class Blob implements Serializable {
    /** The file name of the blob. */
    public String fileName;

    /** The path to the file. */
    private final File file;

    /** Contents of the file in bytes. */
    private final byte[] contentsInBytes;

    /** SHA1 id. */
    private String id;

    Blob(String fileName) {
        this.fileName = fileName;
        this.file = join(Repository.CWD, fileName);
        this.contentsInBytes = readContents(file);
        this.id = getUID();
    }

    /**
     * Get the SHA1 id of this blob.
     *
     * @return SHA1 id
     * */
    private String getUID() {
//        if (contentsInBytes == null) {
//            return sha1(fileName);
//        }
        return sha1(fileName, contentsInBytes);
    }

    /**
     * Get the SHA1 id of this blob.
     *
     * @return SHA1 id
     * */
    public String getID() {
        return id;
    }

    /**
     * Serialize a blob to a file with UID in BLOBS_DIR. */
    public void saveBlob() {
        File blobFIle = getBlobFile(id);
        saveObject(blobFIle, this);
    }

    /**
     * Deserialize a blob from file BLOBS_DIR with UID.
     *
     * @return blob instance with blobUID
     * */
    public static Blob blobFromFile(String blobUID) {
        File blobFile = getBlobFile(blobUID);
        return readObject(blobFile, Blob.class);
    }

    /**
     * Get the contents in byte
     * */
    public byte[] getFileContent() {
        return contentsInBytes;
    }
}

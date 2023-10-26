package gitlet;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static gitlet.Utils.*;
import static gitlet.MyUtils.*;

// TODO: any imports you need here

/** Represents a gitlet repository.
 * The sructure of a Repository is as follows:
 *
 * .gitlet/ -- top level folder for all persistent data in current folder
 * |-- Stage/ -- folder of staging area
 *      |-- addition/ -- folder for all persistent data for addition
 *      |-- removal/ -- folder for all persistent data for removal
 * |-- Commits/ -- folder for all persistent data for commits
 * |-- Blobs/ -- folder for all persistent data for blobs
 * |-- HEAD/ -- folder for storing pointers
 *      |-- Master/ -- folder for storing the master pointer to each branch.
 *          |-- default master branch
 *      |-- HEAD/ -- file for storing the current HEAD pointer
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Repository {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));

    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");

    /** The Commits directory inside .gitlet folder. */
    public static final File COMMITS_DIR = join(GITLET_DIR, "Commits");

    /** The Blobs directory inside .gitlet folder. */
    public static final File BLOBS_DIR = join(GITLET_DIR, "Blobs");

    /** The Stage Area inside .gitlet folder. */
    public static final File STAGE = join(GITLET_DIR, "Stage");

    /** The HEAD directory inside .gitlet folder. */
    public static final File HEAD_DIR = join(GITLET_DIR, "HEAD");

    /** The Master directory inside HEAD folder. */
    public static final File MASTER_DIR = join(HEAD_DIR, "Master");

    /** The HEAD file inside the HEAD folder. */
    public static final File HEAD_FILE = join(HEAD_DIR, "HEAD.txt");

    /** The name of the default master branch. */
    public static final String DEFAULT_MASTER_BRANCH = "master";

//    /** An instance of staging area. */
//    private StagingArea s = new StagingArea();

    /** Initialize the repository structure. */
    public static void initialization() {
        /* Setup the .gitlet folder. */
        GITLET_DIR.mkdir();
        COMMITS_DIR.mkdir();
        BLOBS_DIR.mkdir();
        HEAD_DIR.mkdir();
        MASTER_DIR.mkdir();
    }


    /**
     * Initialize a repository at the current working directory.
     */
    public static void init() {
        // Abort the action when there is already a
        // gitlet version-control system in the current directory
        // TODO: Should I just print message or throw a new gitletexception with message?
        if (GITLET_DIR.exists()) {
            throw error("A Gitlet version-control system already exists in the current directory.");
        }

        // Initialize the structure of the system
        initialization();

        //Create a new commit
        Commit initial = new Commit("initial commit");
        initial.saveCommit();

        //Update the HEAD and branch pointer
        Head.setCurrentHead(initial);
        Head.setBranchHead(initial, DEFAULT_MASTER_BRANCH);
    }


    /**
     *  Staging a file for addition.
     *  */
    public void add(String fileName) {
        StagingArea s = getStagingArea();
        //Check if the file exists in the CWD
        if (!join(CWD, fileName).exists()) {
            GitletException.exit("File doesn't exist.");
        }
        // Create a new Blob according to the content of the file
        Blob fileBlob = new Blob(fileName);
        // Verify if the file is identical to the version in the current commit
        // -- get current commit
        Commit currentCommit = Head.getHeadCommit();
        // -- Use Hashmap get(fileName) to find the UID of the corresponding blob
        String commitBlobID = currentCommit.getFile(fileName);
        // -- not identical(includes null)-- add to the staging area
        if (commitBlobID == null || !commitBlobID.equals(fileBlob.getID())) {
            fileBlob.saveBlob();
            s.addAddition(fileName, fileBlob.getID());
            s.save();
        } else {
            // -- identical -- do not stage and remove it from the staging area
            s.rmFileInAddition(fileName);
            s.save();
        }

    }

    /**
     * Save a snapshot of tracked files in the current commit and staging area.
     * */
    public void commit(String message) {
        // Clone the parent commit
        Commit parentCommit = Head.getHeadCommit();
        Commit commit = parentCommit.clone(message);

        // Update the contents of the file
        StagingArea s = getStagingArea();
        if (s.isEmpty()) {
            GitletException.exit("No changes added to the commit.");
        }
        stageFileUpdate(commit, s);

        // Update the head pointer
        Head.setCurrentHead(commit);
        Head.setBranchHead(commit, DEFAULT_MASTER_BRANCH);
        commit.saveCommit();
    }

    /**
     * Stage the file for removal or unstage it if it is currently staged for addition.
     * */
    public void rm(String fileName) {
        StagingArea s = getStagingArea();
        Commit c = Head.getHeadCommit();
        // File is staged for addition
        if (s.getFile(fileName) != null) {
            s.rmFileInAddition(fileName);
        }
        // File is tracked in the current commit
        else if (c.getFile(fileName) != null) {
            s.addRemoval(fileName);
            join(CWD, fileName).delete();
        }
        else {
            throw new GitletException("No reason to remove the file.");
        }
        s.save();
    }

    /**
     * Display from the head commit to the initial commit along the commit tree.
     * */
    public void log() {
        Commit commit = Head.getHeadCommit();
        while (commit != null) {
            logFormat(commit);
            String parentID = commit.getParentID();
            if (parentID == null) {
                commit = null;
            } else {
                commit = Commit.commitFromFile(parentID);
            }
        }
        // TODO: Consider the merge commit(with two parents). Add a line below the first consist of the first seven digits of the first and second parents' commit ids.
    }

    /**
     * Displays information about all commits ever made. The order of commits doesn't matter.
     * */
    public void globalLog() {
        List<String> commitsID = plainFilenamesIn(COMMITS_DIR);
        if (commitsID != null) {
            for (String commitID: commitsID) {
                Commit commit = Commit.commitFromFile(commitID);
                logFormat(commit);
            }
        }
    }

    /**
     * Takes the version of the file as it exists in the head commit and puts it in the working directory,
     * overwriting the version of the file that’s already there if there is one.
     * The new version of the file is not staged.
     * */
    public void checkoutFilename(String filename) {
        Commit headCommit = Head.getHeadCommit();
        headCommit.writeFile(filename);
    }

    /**
     * Takes the version of the file as it exists in the commit with the given id,
     * and puts it in the working directory, overwriting the version of the file 
     * that’s already there if there is one. 
     * The new version of the file is not staged.*/
    public void checkoutID(String commitID, String fileName) {
        Commit commit = getCommit(commitID);
        commit.writeFile(fileName);
    }

    /**
     * Takes all files in the commit at the head of the given branch, and puts them in the working directory,
     * overwriting the versions of the files that are already there if they exist.
     * Also, at the end of this command, the given branch will now be considered the current branch (HEAD).
     * Any files that are tracked in the current branch but are not present in the checked-out branch are deleted.
     * The staging area is cleared, unless the checked-out branch is the current branch (see Failure cases below).
     */
    public void checkoutBranch(String branchName) {
        Commit currentHeadCommit = Head.getHeadCommit();
        // if a working file is untracked in the current branch and would be overwritten by the checkout, error
        List<String> cwdFileList = plainFilenamesIn(CWD);
        if (cwdFileList != null) {
            for (String fileName : cwdFileList) {
                if (!currentHeadCommit.fileExists(fileName)) {
                    GitletException.exit("There is an untracked file in the way; " +
                            "delete it, or add and commit it first.");
                }
            }
        }
        Commit checkoutHeadCommit = Head.getHeadCommitOfBranch(branchName);

        // If that branch is the current branch, print No need to checkout the current branch.
        if (checkoutHeadCommit.equals(currentHeadCommit)) {
            GitletException.exit("No need to checkout the current branch.");
        }

        // Any files that are tracked in the current branch but are not present in the checked-out branch are deleted
        clearCWD();
        checkoutHeadCommit.writeAllFiles();

        // The staging area is cleared
        StagingArea s = getStagingArea();
        s.clear();
        s.save();

        // set the given branch current branch(HEAD)
        Head.setCurrentHead(checkoutHeadCommit);
    }
    /**
     * Print the commitID, date, and message of a commit with determined format.
     *
     * @param commit a commit instance
     * */
    private void logFormat(Commit commit) {
        String id = commit.getUID();
        String message = commit.getMessage();
        String timestamp = commit.getTimestamp();
        timestamp = DateTimeFormatting.logTimestampConversion(timestamp);
        System.out.println("===");
        System.out.println("commit " + id);
        System.out.println("Date: " + timestamp);
        System.out.println(message);
        System.out.println();
    }

    /**
     * Update the contents of files that have been staged for addition or removal,
     * clear the staging area after updating.
     * */
    private static void stageFileUpdate(Commit commit, StagingArea s) {
        // Update files that have been staged for addition
        Set<Map.Entry<String, String>> addSet = s.getAddSet();
        for (Map.Entry<String, String> entry : addSet) {
            String fileName = entry.getKey();
            String blobID = entry.getValue();
            commit.updateFile(fileName, blobID);
        }

        // Remove files that have been staged for removal
        Set<String> rmSet = s.getRemovalSet();
        for (String fileName: rmSet) {
            commit.rmFile(fileName);
        }

        // Clear the staging area
        s.clear();
        s.save();
    }

    /**
     * Check if current working directory is initialized.
     * */
    public static void checkCWD() {
        if (!GITLET_DIR.exists()) {
            throw error("Not in an initialized Gitlet directory.");
        }
    }

}

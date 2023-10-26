package gitlet;

import java.io.File;

import static gitlet.MyUtils.*;
import static gitlet.Utils.*;

public class Head {
    /**
     * Get current HEAD Commit.
     *
     * @return HEAD commit
     * */
    public static Commit getHeadCommit() {
        String currentCommitID = readContentsAsString(Repository.HEAD_FILE);
        return readObject(getCommitFile(currentCommitID), Commit.class);
    }

    /**
     * Set the current HEAD.
     *
     * @param headCommit head commit
     */
    public static void setCurrentHead(Commit headCommit) {
        writeContents(Repository.HEAD_FILE, headCommit.getUID());
    }

    /**
     * Set the head commit of the branch.
     *
     * @param headCommit head commit
     * @param branchName Name of the branch
     */
    public static void setBranchHead(Commit headCommit, String branchName) {
        File branch = Utils.join(Repository.MASTER_DIR, branchName);
        writeContents(branch, headCommit.getUID());
    }

    public static Commit getHeadCommitOfBranch(String branchName) {
        File branch = join(Repository.MASTER_DIR, branchName);
        if (!branch.exists()) {
            GitletException.exit("No commit with that id exists.");
        }
        String headCommitID = readContentsAsString(branch);
        return getCommit(headCommitID);
    }
}


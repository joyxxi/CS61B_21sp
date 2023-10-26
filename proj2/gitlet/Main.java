package gitlet;

import java.io.File;
import java.util.concurrent.TransferQueue;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author TODO
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ...
     *  java gitlet.Main add hello.txt
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            GitletException.exit("Incorrect operands.");
        }
        String firstArg = args[0];
        switch(firstArg) {
            case "init":
                verifyArgNumber(args, 1);
                Repository.init();
                break;
            case "add":
                Repository.checkCWD();
                verifyArgNumber(args, 2);
                new Repository().add(args[1]);
                break;
            case "commit":
                Repository.checkCWD();
                verifyArgNumber(args, 2);
                String message = args[1];
                if (message.isEmpty()) {
                    GitletException.exit("Please enter a commit message.");
                }
                new Repository().commit(message);
                break;
            case "rm":
                Repository.checkCWD();
                verifyArgNumber(args, 2);
                new Repository().rm(args[1]);
                break;
            case "log":
                Repository.checkCWD();
                verifyArgNumber(args, 1);
                new Repository().log();
                break;
            case "global-log":
                Repository.checkCWD();
                verifyArgNumber(args, 1);
                new Repository().globalLog();
                break;
            case "checkout":
                Repository.checkCWD();
                // checkout filename
                if (args.length == 3) {
                    String fileName = args[2];
                    new Repository().checkoutFilename(fileName);
                }
                // checkout commit id and filename
                else if (args.length == 4) {
                    String commitID = args[1];
                    String fileName = args[3];
                    new Repository().checkoutID(commitID, fileName);
                }
                // checkout branch name
                else if (args.length == 2) {
                    String branchName = args[1];
                    new Repository().checkoutBranch(branchName);
                }
                else {
                    GitletException.exit("Incorrect operands.");
                }
        }
    }

    private static void verifyArgNumber(String[] args, int num) {
        if (args.length != num) {
            GitletException.exit("Incorrect operands.");
        }
    }



    public void commit() {
        //Read from my computer the head commit object and the staging area

        // Clone the HEAD commit
        // Modify its message and timestamp according to user input
        // Use the staging area in order to modify the files tracked by the new commit

        //Write back any new object made or any modified objects read earlier
    }
}

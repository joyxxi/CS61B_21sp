package gitlet;

import static gitlet.Utils.message;

/** General exception indicating a Gitlet error.  For fatal errors, the
 *  result of .getMessage() is the error message to be printed.
 *  @author P. N. Hilfinger
 */
class GitletException extends RuntimeException {


    /** A GitletException with no message. */
    GitletException() {
        super();
    }

    /** A GitletException MSG as its message. */
    GitletException(String msg) {
        super(msg);
    }

    /**
     * Print a message and exit with status code 0.
     *
     * @param msg error message
     * @param args arguments related to the error
     * */
    public static void exit(String msg, Object... args) {
        message(msg, args);
        System.exit(0);
    }

}

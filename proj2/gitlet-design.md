# Gitlet Design Document

**Name**: Zhxi Xu

## Classes and Data Structures

### Main
This is the entry point to our program. It takes in arguments from the command
line and based on the command line calls the corresponding command in `Repository`
which will execute the main logic of the command. It also validates the arguments based
on the command to ensure that enough and correct arguments are passd in.
#### Instance Variables
This class has no instance variables. It simply validates arguments and defers the 
execution to the `Repository` class.
#### Methods
1. `private static void verifyArgNumber(String[] args, int num)` The helper function 
to validate the number of arguments.
# _____________________________________
### Commit

#### Instance Variables
1. message - Contains the message of a commit.
2. timestamp - Time at which a commit was created. Assigned by the constructor.
3. parent - The parent commit of a commit object.
4. files - The Hashmap for storing file names and UID of blobs.

#### Methods
1. generateCurrentTime - Generate the timestamp for this commit.
2. updateFile - Add a new tracking file or update the file with a new blob.
3. getMessage - Get the message information.
4. getParent - Get the UID of parent commit.
5. getTimestamp - Get the timestamp.


### DateTimeFormatting

#### Methods
1. currentDateTime - Get the current formatted timestamp at the current moment. 
Used to generate the timestamp for commits other than the initial commit.
2. epochDateTime - Get the epoch formatted timestamp for the initial commit.
3. getFormatter - Generate the formatter for formatting the date and time.
# _____________________________________
### Repository
This is where the main logic of our program will live. This class will handle all
of the actual commands within gitlet.

#### Instance Variables
1. `CWD`
2. `GITLET_DIR`
3. `COMMITS_DIR`
4. BLOBS_DIR
5. INDEX_DIR
6. HEAD_DIR
7. MASTER_DIR
8. HEAD_FILE

#### Methods
1. initialization - Initialize the repository structure


## Algorithms

## Persistence


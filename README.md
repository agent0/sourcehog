# Sourcehog

A simple tool to index and search your source directories (not repositories!)

## How it works

Sourcehog scans through a ist of directories and builds a simple index of the contents of the filetypes
it knows about. Currently, these are `*.java` , `*.js` , `*.ts` and `*.css` files. In the UI, 
you can then search this index using case-insensitive string matching.

Clicking on an entry of the result list will open Notepad++ on the line of the match.

## Indexing algorithm

The indexing algorithm of sourcehog is a masterpiece of algorithmic beauty: basically,
the indexer reads a line, then checks if this line should be indexed (for java files, for example, we do not index the `import` statements),
the it replaces all non-alphanumeric characters with spaces, splits the line on spaces and for every word of more than
three characters, it writes a line containing the keyword and the file location to the database. It is a very simple algorithm 
that, up until now, produces quite satisfying results.

## User settings

The currently available settings are stored as standard Java properties and save in the directory that is returned by calling
`System.getProperty("user.home")`. Obviously, it is plattform dependant what location this actually is.

## Running Sourcehog

```
gradle build
```

```
java -jar build/libs/sourcehog.jar
```

# Todo

Currently, the configuration options are very limited. When running the index command from the main menu via
`Run` > `Index`, the user can choose the location where the database file should be stored and a colon-separated list
of directories that are recursively indexed. The next steps will be to allow a better fine-tuning especially 
of the file and directory selection process. Also, a hard-coded list of excludes is defined within the 
source code.
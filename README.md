#Sourcehog

A simple tool to index and search your source directories (not repositories!)

##How it works

Sourcehog scans through a ist of directories and build a simple index of the filetypes
it knows about. Currently, these are `*.java` , `*.js` , `*.ts` and `*.css` files. In the UI, 
you can then search this index using case-insensitive string matching.

Clicking on an entry of the result list will open Notepad++ on the line of the match.

##Running Sourcehog

```
gradle build
```

```
java -jar build/libs/sourcehog.jar
```

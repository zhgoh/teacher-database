# Student database program

Simple java program (with database) to display student and subjects information.

## Structure

- GUI
- CLI

### GUI

Uses java swing to display a table and allow to add columns (subject names) and add rows (student informations)

#### Usage

```bash
cd src
javac .\guide\gui\Character.java
javac .\guide\gui\Database.java
javac .\guide\gui\forms\LabelText.java
javac .\guide\gui\forms\MainForm.java
javac .\guide\gui\forms\RowView.java
java java guide.gui.forms.MainForm
```

### CLI

Does not use java swing, able to prompt for input and add items to the database.

#### Usage

```bash
java .\src\guide\cli\Program.java
```

## TODO:

- Might want to create an adaptor to combine both GUI/CLI (extract out similar codes for GUI,CLI)
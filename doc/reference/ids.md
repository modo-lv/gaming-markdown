# Identifiers

Every todo item needs to have a project-unique identifier in order for any kind of state persistence
(saving which items have been checked and which haven't) to work.
IDs are also required for internal links.

IDs are case-insensitive (converted to lowercase) by default, 
you can enable case sensitivity/preservation with a setting.

IDs must be unique within the project.
If a duplicate ID is detected, the project build process will abort with an error.

## Implicit (automatic) IDs

By default, these IDs are generated automatically, based on the todo's location in the project hierarchy
(these are not meant for linking to).
The hierarchy starts with the file, which is given an ID based on its location in the project 
(relative to the content folder), and continues down the tree of containers.

For example, a page file `early/start.md`:
```markdown
- Location
++ Item
++ Item
++ Item 2
```
would generate the following IDs for the items 
(special separator symbols are used to avoid potential naming conflicts between auto-generated and user-defined IDs):
```
early/start␝location␝item␞1
early/start␝location␝item␞2
early/start␝location␝item⸱2␞1
```


## Explicit IDs (`#...`)

You can assign an explicit ID to any element by using `#...` syntax within the GMD metadata.
```markdown
+ Item {#first}
```
This has several benefits:
* Allows referring to this item by its ID, for example, to link to it from elsewhere in the pages.
* Ensures that the item retains the same ID even if it is moved around (even to a different page).

It is actually recommended to set an explicit ID for each page, 
instead of relying on the auto-generated ID that depends on file path.
Given a page `early/start.md`, if the first line is:
```markdown
{#begin}
```
The ID of the page will be explicitly set to `begin`,
making all auto-generated IDs in the page start with that instead of `early/start`, 
and will remain so even if the file is moved or renamed.


### State IDs (`##...`)

A "secondary" ID for referencing an item's state, rather than the item (which can have an explicit ID set as well).

This allows multiple items to be tracked as one in terms of done/not done status.

Example:
```markdown
First attempt:
+ Item X {##x}

Second attempt:
+ Item X {##x}
```
Marking either of these as done/not done will also mark the other one. 
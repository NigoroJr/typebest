Design
======

This is a scratch work of how to implement the software.

How the database will be structured.  

Note that [Apache Derby](http://db.apache.org/derby/) is needed for development. See [my website](http://nigorojr.com/tips/index.php?id=30) for installation of plug-in.

Basic Ideas
-----------
The type area consists of lines of words. There is (will be) a "Word" class,
which is a collection of letters that make up a single word, and there is also
a "Line" class that is a collection of words. It is not decided yet whether
the lines will always disappear when the user finishes typing the line, or
only when the words go over the boundry. When the words go over the boundry,
i.e. not all the words are visible, the finished line will be "popped"
(animation is a todo). This will allow modes such as "infinite typing" to be
implemented fairly easily.

Records
-------
What | key | type
---|---|---
user ID | USER_ID | int
record ID | RECORD_ID | int
Username (is it necessary?) | USERNAME | varchar(100)
record (in nanosec) | RECORD | int
date | DATE | timestamp
miss-type counts | MISS | int
keyboard layout | LAYOUT | varchar(50)

User Pref
---------
What | key | type
---|---|---
user ID | USER_ID | int
Username | USERNAME | varchar(100)
default layout (add current?) | LAYOUT | varchar(50)
font family | FONT_FAMILY | varchar(50)
font color | FONT_COLOR | int or varchar(50)
font size | FONT_SIZE | int
last mode used | LAST_MODE | int or varchar(50)

System pref
-----------
What | key | type
---|---|---
last user (might be better to use file I/O | LAST_USER | varchar(100)
dictionary file location | DIC_LOC | varchar(150)
file name (file I/O?) | DIC_NAME | varchar(150)

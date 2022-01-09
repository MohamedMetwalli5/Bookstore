![GUI](https://img.shields.io/badge/GUI-Java%20-orange.svg)
![DBMS](https://img.shields.io/badge/Database%20Management%20System-MySQL%20-violet.svg)
![ReportingTool](https://img.shields.io/badge/Reporting%20Tool-JasperReports%20-green.svg)
![License](https://img.shields.io/badge/License-GPL&ndash;3.0%20-blue.svg)

# The Database Project
This is Analysis, design, and implementation of a database system to support the operations of a simplified online bookstore. And the creation of the necessary forms to perform these operations.

## The Main Operations
* Adding new books
To add a new book, the user enters the properties of the new book along with a threshold (the minimum
quantity in stock to be maintained for that book).
* Modifying existing books
For updating an existing book, the user first searches for the book then he does the required update. For a given
book, the user can update the quantity in stock when a copy or more of the book is sold. The user cannot update
the quantity of a book if this update will cause the quantity of a book in stock to be negative.
* Placing orders on books
An order with constant quantity is placed only when the quantity of a book drops from above a given threshold
(the minimum quantity in stock) to below the given threshold.
* Confirming orders
The user can confirm an order when receiving the ordered quantity from the book’s publisher; the quantity of
the book in store automatically increases with the quantity specified in the order. Assume that deleting the order
means that the order is received from publisher.
* Searching for books
The user can search for a book by ISBN, and title. The user can search for books of a specific Category, author
or publisher.

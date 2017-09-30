package com.junyeong.yu;

import com.junyeong.yu.handler.DataHandlerFileExtXml;
import com.junyeong.yu.models.DataContext;
import com.junyeong.yu.models.DataContextDao;
import com.junyeong.yu.handler.DataHandlerFileExtCsv;
import com.junyeong.yu.handler.DataHandlerFileImpl;
import com.junyeong.yu.models.GroceryStore;
import com.junyeong.yu.core.ApplicationContext;
import com.junyeong.yu.core.annotations.Bean;

/**
 * Author : Junyeong Yu
 * Class Code : COMP1030
 * Entrance of program
 */
@Bean
public class GroceryStoreMain {
    public static void main(String[] args) {
        // 1. automatic dependency injection - using ApplicationContext.java (For CSV file)
        System.out.println("\n===== Using CSV Data File =====");
        ApplicationContext context = new ApplicationContext("com.junyeong.yu");
        GroceryStore groceryStore = context.getBean(GroceryStore.class);
        groceryStore.runGroceryStore();

        // 2. manual dependency injection (For Xml file)
        System.out.println("\n===== Using XML Data File =====");
        GroceryStore groceryStoreManunal = new GroceryStore();
        groceryStoreManunal.setDataContext(new DataContext());
        groceryStoreManunal.setDataContextDao(new DataContextDao());
        groceryStoreManunal.setDataHandlerFileExt(new DataHandlerFileExtXml()); // It is xml process dependency. (DI)
        groceryStoreManunal.setDataHandlerFile(new DataHandlerFileImpl());
        groceryStoreManunal.runGroceryStore();
    }
}

/**
 * Major Project
 Your Cash Register application idea has been purchased by ABC Groceries Corporation and have given you 50% up front.  They have asked for the following enhancements to be made to receive the remaining 50%:

 (O)There is too much code, use Inheritance to substitute these properties in Products, Invoice and InvoiceItem object:
 (O)   Id (includes ProductId, InvoiceId, InvoiceItemId)
 (O)   CreateDate
 (O)   Add EditDate - similar to CreateDate but changes when the object is edited and is set initially to the same value as CreateDate in the default constructor
 (O)Change all LocalDate references to LocalDateTime
 (O)Add an input interface
 (O)   The menu structure looks like this:
 (O)      1. Daily Summary - Displays a summary of the daily sales (for Today) - Requires LocalDateTime as a reference to today's date
 (O)      2. Invoice Recall - Asks for an invoice number then displays the Invoice Detail
 (O)      3. Add new purchase
 (O)          1. Enter a Product (choose # - #)
 (O)          2. Enter Quantity
 (O)          3. Complete - Print Receipt
 (O)Bonus Marks! Use Polymorphism and Reflection to build a generic Save and Read function:
 (O)      Send the data List to the function as a parameter (i.e. Products, Invoices, InvoiceItems)
 (O)      Determine the Class of the first item in the List
 (O)    Use the Class name as a String to generate a file name (i.e. Models.Invoices.csv). Be mindful of the plural "s"
 (O)    Save gets the "FileOutput" of each item in the List as a String from the object and saves it to the csv file
 (O)    Read gets the FileInput from each line as a String from the csv file

 (X)Submission Details
 (O)    Submit only the project files related to the application in 1 ZIP file
 (O)      Your Name (Chris Dyck) and Class Code (COMP1030) at the top of each .java file in a /* Block * comment
 (O)    Include simple user instructions
 */
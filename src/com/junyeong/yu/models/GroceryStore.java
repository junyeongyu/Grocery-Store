package com.junyeong.yu.models;

import com.junyeong.yu.core.annotations.Bean;
import com.junyeong.yu.core.annotations.Inject;
import com.junyeong.yu.handler.DataHandlerFile;
import com.junyeong.yu.handler.DataHandlerFileExt;

import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author : Junyeong Yu
 * Class Code : COMP1030
 * Treatments of input interface
 */
@Bean
public class GroceryStore {

    @Inject
    public DataContext dataContext;
    public void setDataContext(DataContext dataContext) {
        this.dataContext = dataContext;
    }
    public void setDataContextDao(DataContextDao dataContextDao) {
        this.dataContext.setDataContextDao(dataContextDao);
    }
    public void setDataHandlerFileExt(DataHandlerFileExt dataHandlerFileExt) {
        this.dataContext.setDataHandlerFileExt(dataHandlerFileExt);
    }
    public void setDataHandlerFile(DataHandlerFile dataHandlerFile) {
        this.dataContext.setDataContextDaoHandler(dataHandlerFile);
    }

    public void runGroceryStore() {
        dataContext.initialize(); // data initialization

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nMain Menu\n");
            System.out.println("1. Daily Summary - Displays a summary of the daily sales (for Today)");
            System.out.println("2. Invoice Recall - Asks for an invoice number then displays the Invoice Detail");
            System.out.println("3. Add new purchase");
            System.out.println("4. Exit\n");
            System.out.print("Please enter your number : ");
            String response = scanner.nextLine();

            if ("1".equals(response)) { // Daily Summary
                printDailySummary(); // Make sure that data are read from file system.
            } else if ("2".equals(response)) { // Invoice Recall
                printInvoiceDetail();
            } else if ("3".equals(response)) { // Add new purchase
                addNewPurchase();
            } else if ("4".equals(response)) { // Exit
                break;
            } else { // wrong case
                printWrongNumber();
            }
        }
    }
    private void printDailySummary() {
        System.out.println("\r\n" + dataContext.toSummaryString(LocalDateTime.now()));
    }
    private void printInvoiceDetail() {
        System.out.print("Please Enter Invoice Id : ");
        Scanner scanner = new Scanner(System.in);
        String response = scanner.nextLine();
        if (isNumber(response) == false) {
            printWrongNumber();
            return;
        }

        int invoiceId = Integer.parseInt(response);
        System.out.println();
        for (Invoice invoice : dataContext.getInvoiceList()) {
            if (invoice.getId() == invoiceId) {
                System.out.println(invoice.toDetailString());
                break;
            }
        }
    }
    private void addNewPurchase() { // Add new product for same invoice.
        Scanner scanner = new Scanner(System.in);
        Invoice invoice = null;
        while (true) {
            InvoiceItem invoiceItem = new InvoiceItem();
            System.out.print("1. Enter a Product (choose 1 - 4) : ");
            String response = scanner.nextLine();
            if (isNumber(response) == false) {
                printWrongNumber();
                continue;
            }

            int productId = Integer.parseInt(response);
            if (productId < 1 || productId > 4) {
                printWrongNumber();
                continue;
            }
            invoiceItem.setProduct(dataContext.getProduct(productId));

            System.out.print("2. Enter Quantity : ");
            response = scanner.nextLine();
            if (isNumber(response) == false) {
                printWrongNumber();
                continue;
            }
            int quantity = Integer.parseInt(response);
            if (quantity < 1 || quantity > Integer.MAX_VALUE) {
                printWrongNumber();
                continue;
            }
            invoiceItem.setQuantity(quantity);

            System.out.println("3. Complete - Print Receipt\n");
            invoice = dataContext.allocateInvoiceItem(invoice, invoiceItem);
            printWithReceipt(invoiceItem);

            System.out.print("Will you continue to add product? (y/n) : ");
            response = scanner.nextLine();

            if ("y".equals(response)) {
                continue;
            }

            dataContext.addInvoice(invoice);

            break;
        }
    }
    private void printWithReceipt(InvoiceItem invoiceItem) {
        System.out.println("=====  Receipt for Registered Product ====");
        System.out.println("Invoice     Id      : " + invoiceItem.getInvoiceId());
        System.out.println("InvoiceItem Id      : " + invoiceItem.getId());
        System.out.println("Product     Id      : " + invoiceItem.getProductId());
        System.out.println("Product     Quantity: " + invoiceItem.getQuantity());
        System.out.println("==========================================");
    }
    private void printWrongNumber() {
        System.out.println("\nYour choice is wrong. Please input again.");
    }
    private boolean isNumber(String line) {
        Pattern p = Pattern.compile("-?\\d+");
        Matcher m = p.matcher(line);
        return m.matches();
    }
}
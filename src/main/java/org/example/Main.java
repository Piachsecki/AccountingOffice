    package org.example;

import org.example.adapter.in.PrintingService;

public class Main {
    private static final PrintingService printingService = new PrintingService();
    private static final String MENU =   "GOOD MORNING ACCOUNTANT!" +
                            "Here is the list of the operations available" +
                            "0. Turn off the program" +
                            "1. Add customer to your clients." +
                            "2. Add an invoice for the customer" +
                            "3. Delete a specified customer" +
                            "4. List all invoices for a specified customer";

//    public static void main(String[] args) {
//        PrintingService.printStartingMessage(MENU);
//
//        boolean flag = true;
//        while (flag){
//            int action = PrintingService.askForANumber(
//                    "Please enter the number of the operation you would like to perform: "
//            );
//            switch (action){
//                case 0: {
//                    flag = false;
//                }break;
//                case 1: {
//                    PrintingService.addClientMessage();
//                }break;
//                case 2: {
//
//                }break;
//                case 3: {
//
//                }break;
//                case 4: {
//
//                }break;
//                case 5: {
//
//                }break;
//                default:{
//
//                }
//            }
//
//        }
//
//    }

    public static void main(String[] args) {

    }
}
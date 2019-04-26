/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bookstorermi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ghadeer
 */
public class BookStoreServer extends UnicastRemoteObject implements BookFacade{

    
    private List<Book> bookList;
    
    public BookStoreServer(List<Book> bookList) throws RemoteException {
        super();
        this.bookList = bookList;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Registry reg = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
            
            BookStoreServer store = new BookStoreServer(init());
            
            reg.rebind("rmi://localhost/service", store);
            System.out.println("Server Running...");
        } catch (RemoteException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public Book findBook(Book b) throws RemoteException {
        Predicate<Book> predicate = x -> x.getCode().equals(b.getCode());
        Book response = bookList.stream().filter(predicate).findFirst().get();
        return response;
    }

    @Override
    public List<Book> getAllBooks() throws RemoteException {
        return bookList;
    }
    
    private static List<Book> init() {
        List<Book> list = new ArrayList<>();
        list.add(new Book("Head First Java, 2nd Edition", "A1", 31.41));
        list.add(new Book("Java In A Nutshell", "A2", 10.90));
        list.add(new Book("Java: The Complete Reference", "B1", 40.18));
        list.add(new Book("Head First Servlets and JSP", "B2", 35.41));
        list.add(new Book("Java Puzzlers: Traps, Pitfalls, and Corner Cases", "C1", 39.99));
        return list;
    }
}

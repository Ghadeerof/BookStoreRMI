/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bookstorermi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Ghadeer
 */
public interface BookFacade extends Remote {
    Book findBook(Book b) throws RemoteException;
    
    List<Book> getAllBooks() throws RemoteException;
}

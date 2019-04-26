/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bookstorermi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.NoSuchElementException;
import javax.swing.JOptionPane;

/**
 *
 * @author Ghadeer
 */
public class Client {
    
    public static void main(String[] args){
        try{
            Registry reg = LocateRegistry.getRegistry("localhost",1099);
            BookFacade server = (BookFacade) reg.lookup("rmi://localhost/service");
            
            boolean findMore;
            do{
                String[] options = { "Show All", "Find a book", "Exit"};
                
                int choice = JOptionPane.showOptionDialog(null, "Choose an action", "Option dialog",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.INFORMATION_MESSAGE,
                            null, options, options[0]);
                
                switch(choice){
                    case 0:{
                        List<Book> list = server.getAllBooks();
                        
                        StringBuilder message = new StringBuilder();
                        list.forEach( x -> {
                            message.append(x.toString() + "\n");
                        });
                        JOptionPane.showMessageDialog(null, new String(message));
                        break;
                    }
                    case 1: {
                        String code = JOptionPane.showInputDialog("Type the code of the book you want to find");
                        try {
                            Book response = server.findBook(new Book(code));

                            JOptionPane.showMessageDialog(null, "Title :"
                                    + response.getTitle() + "\n" + "Cost : $"
                                    + response.getPrice(),
                                    response.getCode(), JOptionPane.INFORMATION_MESSAGE);
                            
                        } catch (NoSuchElementException ex) {
                            JOptionPane.showMessageDialog(null, "Not found");
                        }
                        break;
                    }// end case 1:
                    
                    default:
                        System.exit(0);
                        break;
                }
                findMore = (JOptionPane.showConfirmDialog(null, "Do you want to exit?","Exit",
                            JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION);
                
            }while(findMore);
        
        }catch(RemoteException | NotBoundException e){
            System.out.println(e.getMessage());
        }
    }
    
}

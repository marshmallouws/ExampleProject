/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package errorhandling;

/**
 *
 * @author Annika
 */
public class NotUniqueException extends Exception {
    public NotUniqueException(String msg) {
        super(msg);
    }
}
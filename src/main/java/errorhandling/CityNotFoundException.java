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
public class CityNotFoundException extends Exception {
    public CityNotFoundException(String msg) {
        super(msg);
    }
}

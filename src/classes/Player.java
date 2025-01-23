/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

/**
 *
 * @author user
 */
public class Player {
    String name;
    String password;
    String email;
    int score;

    public Player(String name, String email, int score) {
        this.name = name;
        this.email = email;
        this.score = score;
    }
    
}

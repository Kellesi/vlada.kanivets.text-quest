package ua.javarush.textquest.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Account {
    private final int id;
    private String name;
    private  String password;
    private int collectedEndings;
    private int deathCount;
    private String savePoint;

    public Account(int id, String name, String password) {
        this.id=id;
        this.name = name;
        this.password = password;
    }
}

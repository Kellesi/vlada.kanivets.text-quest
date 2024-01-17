package ua.javarush.textquest.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Account {
    private static int counter;
    private final int id;
    private String name;
    private String password;
    private String savePoint;
    private int collectedEndings;
    private int deathCount;

    public Account(String name, String password) {
        this.id = counter++;
        this.name = name;
        this.password = password;
    }
}

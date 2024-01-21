package ua.javarush.textquest.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Stage {
    private StepChoice[] stepChoices;
    private int id;
    private String description;
    private String img;
    private boolean isDeadPoint;
    private boolean isEndPoint;
}

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
    private String stageDescription;
    private int stageID;
    private StepChoice[] stepChoices;
    private String stageImg;
    private boolean isDeadPoint;
    private boolean isEndPoint;
}

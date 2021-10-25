package com.vgorash.lab2.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@XStreamAlias("event")
public class Event {

    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    private String name; //Поле не может быть null, Строка не может быть пустой

    private java.time.LocalDateTime date; //Поле может быть null

    private EventType eventType; //Поле может быть null
}
package com.vgorash.lab2.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@XStreamAlias("ticket")
public class Ticket {

    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    private String name; //Поле не может быть null, Строка не может быть пустой

    private Coordinates coordinates; //Поле не может быть null

    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    private Integer price; //Поле может быть null, Значение поля должно быть больше 0

    private String comment; //Длина строки не должна быть больше 333, Поле может быть null

    private TicketType type; //Поле может быть null

    private Event event; //Поле может быть null

}

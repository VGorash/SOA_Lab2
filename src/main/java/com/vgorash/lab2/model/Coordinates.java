package com.vgorash.lab2.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@XStreamAlias("coordinates")
public class Coordinates {

    private Float x; //Максимальное значение поля: 916, Поле не может быть null

    private int y;

}
package com.vgorash.lab2.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.ConversionException;
import com.vgorash.lab2.model.Coordinates;
import com.vgorash.lab2.model.Event;
import com.vgorash.lab2.model.Ticket;


public class XStreamUtil {

    private static final XStream xStream = createXStream();

    private static XStream createXStream(){
        XStream result = new XStream();
        result.processAnnotations(Ticket.class);
        result.processAnnotations(Event.class);
        result.processAnnotations(TicketListWrap.class);
        Class<?>[] classes = new Class[] {Ticket.class, Event.class, Coordinates.class};
        result.allowTypes(classes);
        result.setMode(XStream.NO_REFERENCES);
        return result;
    }

    public static String toXML(Ticket ticket){
        return xStream.toXML(ticket);
    }

    public static Ticket fromXML(String s){
        return (Ticket) xStream.fromXML(s);
    }
}

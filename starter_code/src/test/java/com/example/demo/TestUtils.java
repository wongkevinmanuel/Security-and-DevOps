package com.example.demo;

import java.lang.reflect.Field;
import java.util.Objects;

//Nos ayuda a inyectar el objeto
public class TestUtils {

    public static void InyectarObjeto(Object blancoObjetivo, String nombreCampo, Object aInyectar) {
        boolean privado = false;
        try{
            //Para que ??
            Field field = blancoObjetivo.getClass().getDeclaredField(nombreCampo);
            //Vereficar si el accesible para ??
            if(!field.isAccessible()){
                field.setAccessible(true);
                privado = true;
            }

            //Establecer un objeto con el objeto que quiero inyectar.
            field.set(blancoObjetivo, aInyectar);

            if(privado)
                field.setAccessible(false);

        }catch (NoSuchFieldException e){

        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}

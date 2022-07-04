package com.example.demo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SareetaApplicationTests {

	//Darle nombre significativos a la pruebas que demuestren lo que hacen
	@Test
	public void contextLoads() {
		String mensaje = (String) null;

	}

	//@Before Se ejecuta antes de cada metodo
	//@BeforeClass se ejecuta antes de cada clase
	//@AfterClass se ejecuta solo una vez, que es al final de cada clase.
	//@After se ejecuta despues de cada metodo
	//@Ignore omite un caso de prueba en particular si no desea q se ejecute

	//Pruebas parametrizadas se utilizan cuando quiere pasar cierto valor de datos
	//para ejecutar en su metodo de prueba, pero no dessea crear valores de datos
	//dentro de los metodos.
	//Se usa de la siguiente manera:
	//Creas una clase de prueba como HelperParametrizedTest
	//Para que no se ejecute como las demas clase se debe agregar a HelperParametrizedTest
	//la anotacion @RunWith(Parametrized.class)
	//Esta clase de prueba se considera una clase de prueba parametrizada
	//Se debe agregar un metodo que de vuelva los datoa
	//Ejemplo:@Parametrized.Parameters public static Collection initData(){ return new ArrasList();}

	//% de covertura de codigo
	//La cantidad de codigo que prueba su prueba se conoce como cobertura de codigo.
	//Ejemplo supongamos que tiene 20 lineas de codigo que ha escrito en
	//En una clase particu lar y tiene pruebas escritas que solo tocan cinco lineas,
	//
}

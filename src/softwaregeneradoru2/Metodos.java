/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwaregeneradoru2;

import java.text.NumberFormat;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JTextField;
import org.apache.commons.math3.distribution.ChiSquaredDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;

/**
 *
 * @author Gener
 */
public class Metodos {
    
    public void promedio(JFrame fr, JTable tb, JTextField conf, JTextField jtf, JTextField jli, JTextField jls, JTextField res)
    {
        //Calculamos el promedio
        double suma=0;
        int i = 0;
        for(i=0; i<tb.getRowCount(); i++)
        {
            suma = suma + Double.parseDouble(tb.getValueAt(i,0).toString());
        }
        double promedio = suma/i;
        jtf.setText(String.valueOf(promedio));
        
        
        //Obtenemos el valor de z
        double confianza= (Double.parseDouble(conf.getText()))/100;
        double alfa = 1-confianza;
        double z;
        //Para obtener el valor z (sin consultar tablas) se utiliza la siguiente fórmula.
        NormalDistribution nd = new NormalDistribution();
        z= nd.inverseCumulativeProbability(1 - alfa/2 );
        
        NumberFormat numberFormat = NumberFormat.getInstance();
        z = Double.parseDouble(numberFormat.format(z));
        
        //Calculamos limite inferior
        Double li = 0.5 - z * (1/Math.sqrt(12*i));
        jli.setText(String.valueOf(li));
        
        //Calculamos limite superior
        Double ls = 0.5 + z * (1/Math.sqrt(12*i));
        jls.setText(String.valueOf(ls));
        //Damos el resultado final
        if(promedio > li & promedio < ls)
        {
            res.setText("¡Prueba Superada!");
        } else
        {
            res.setText("Prueba NO Superada");
        }
        
    }
    
    
    public void varianza (JFrame fr, JTable tb, JTextField conf, JTextField jtf, JTextField jli, JTextField jls, JTextField res)
    {   
        double promedio = 0;
        double sumaV=0;
        int i = 0;
        double varianza = 0;
        double sumaP=0;
        int iP = 0;
        
        //Primero calculamos el promedio
        for(iP=0; iP<tb.getRowCount(); iP++)
        {
            sumaP = sumaP + Double.parseDouble(tb.getValueAt(iP,0).toString());
        }
        promedio = sumaP/iP;

        
        //Calculamos la varianza
        for(i=0; i<tb.getRowCount(); i++)
        {
            sumaV = sumaV + Math.pow((Double.parseDouble(tb.getValueAt(i,0).toString())-promedio), 2);
        }
        
        varianza = (sumaV)/(i-1);
        jtf.setText(String.valueOf(varianza));
        

        int grados_libertad = i-1;
        
        
        //Obtenemos el valor de z
        double confianza= (Double.parseDouble(conf.getText()))/100;
        double alfa = 1-confianza;
        double z;
        //Para obtener el valor z (sin consultar tablas) se utiliza la siguiente fórmula.
        NormalDistribution nd = new NormalDistribution();
        z= nd.inverseCumulativeProbability(1 - alfa/2 );
        
        NumberFormat numberFormat = NumberFormat.getInstance();
        z = Double.parseDouble(numberFormat.format(z));
        
        ChiSquaredDistribution chi = new ChiSquaredDistribution(grados_libertad);
        
        //Calculamos el limite inferior
        Double li = chi.inverseCumulativeProbability(alfa/2)/(12*grados_libertad);
        jli.setText(String.valueOf(li));
        //Calculamos el limite superior
        Double ls = chi.inverseCumulativeProbability(1-alfa/2)/(12*grados_libertad);
        jls.setText(String.valueOf(ls));
        //Damos el resultado final
        
        if(varianza > li & varianza < ls)
        {
            res.setText("¡Prueba Superada!");
        } else
        {
            res.setText("Prueba NO Superada");
        }
    }
    
    public void corridas(JFrame fr, JTable tb, JTextField conf, JTextField cadena, JTextField jnc, JTextField jm, JTextField jv, JTextField jz, JTextField res)
    {
        double confianza= (Double.parseDouble(conf.getText()))/100;
        double alfa = 1-confianza;
        double suma=0;
        int i = 0;
        for(i=0; i<tb.getRowCount(); i++)
        {
            suma = suma + Double.parseDouble(tb.getValueAt(i,0).toString());
        }
        
        Double[] datos = new Double[i];
        
        for (int ip = 0; ip < i; ip++)
            {
                datos[ip] = Double.parseDouble(tb.getValueAt(ip,0).toString());
            }
        
        //Creamos una lista para guardar los ceros y unos.
        ArrayList<Integer> bits = new ArrayList<>();
        int i3, corridas, dato;
        double  media, varianza, z;
        //Revisa si cada dato actual es menor al dato anterior. 
        //Si es así, se guarda un 0, de lo contrario, se guarda un 1
        for (i3=1; i3<datos.length; i3++)
        {
            if (datos[i3]<=datos[i3-1]){
            bits.add(0);
        }
        else{
            bits.add(1);
        }                
    }
    
        //Imprimimos la cadena de ceros y unos
        String binario="";
        
        for (i=0; i<bits.size(); i++)
        {
            //System.out.print(bits.get(i));
            binario = binario + bits.get(i);
        }
        
        cadena.setText(binario);
        
        //Contamos las corridas. 
        corridas = 1;
        //Comenzamos observando el primer dígito
        dato= bits.get(0);
        //Comparamos cada dígito con el observado, cuando cambia es 
        //una nueva corrida
        for (i=1; i<bits.size(); i++)
        {
        if (bits.get(i) != dato)
        {
            corridas++;
            dato = bits.get(i);
        }
    
        }
        //System.out.println("Corridas " + corridas);
        jnc.setText(String.valueOf(corridas));
        
    
        //Aplicamos las fórmulas para media, varianza y Z.
        media = (2*datos.length-1)/ (double)3;
        //System.out.println("Media: " +media);
        jm.setText(String.valueOf(media));
        varianza = (16*datos.length-29)/(double) 90;
        //System.out.println("Varianza: " + varianza);
        jv.setText(String.valueOf(varianza));
        z= Math.abs((corridas-media)/Math.sqrt(varianza));
        //System.out.println("Z=" + z);
        jz.setText(String.valueOf(z));
 
        //Obtenemos el valor Z de la tabla de distribución normal
        NormalDistribution normal = new NormalDistribution();
        double  zn =  normal.inverseCumulativeProbability(1-alfa/2);
        //Comparamos: si es mayor mi valor Z al de la tabla, no pasa
        if (z < zn)
        {    
            //System.out.println("No se rechaza que son independientes. " );
            res.setText("No se rechaza que son independientes");
        }
        else
        {
            //System.out.println("No Pasa la prueba de corridas");
            res.setText("No Pasa la prueba de corridas");
        }
        
    }
   
}

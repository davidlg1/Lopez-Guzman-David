/**
 * Inteligencia Artificial
 * 8:00 - 9:00 am
 * David Lopez Guzman
 * Problema ocho reinas
 */

import java.util.ArrayList;
public class ProblemaReinas {
    private boolean[] horizontal;
    private boolean[] vertical;
    private boolean[] diagonalSuperior;
    private boolean[] diagonalInferior;
    private int n;
    private int[] solucion;
    private boolean haySolucion;
    private ArrayList soluciones = new ArrayList();
 
    public ProblemaReinas(){
        n=8;  //n ES EL NUMERO DE REINAS
        inicializar();
        /*
        EL METODO inicializar(), CREA UNA SERIE DE ARREGLOS PARA EMULAR LAS 
        POSICIONES QUE TIENE EL TABLERO DE AJEDREZ, LOS CUALES SON:
        horizontal = FILAS
        vertical = COLUMNAS
        diagonalInferior y diagonalSuperior = DIAGONALES
        TODOS LOS ARREGLOS SON INICIADOS CON EL VALOR TRUE
        */
    }
 
    private void inicializar(){
        //EL TAMAÑO DE CADA ARREGLO ES DE n, ES DECIR 8
        this.horizontal = new boolean[n];
        this.vertical = new boolean[n];
        this.solucion = new int[n];
        /*
        SE LLENAN LOS ARREGLOS horizontal y ventical CON VALORES 'true'
        El ARREGLO solucion SE LLENA CON VALOR -1
        */
        for (int i = 0; i<n;i++){
            this.horizontal [i] = true;
            this.vertical [i] = true;
            this.solucion [i] = -1;
        }
        //SE ASIGNA EL TAMAÑO A LOS ARREGLOS diagonalInferior y superior
        //CON EL TAMAÑO DE 15 = (2*8-1)
        this.diagonalInferior = new boolean[2*n-1];
        this.diagonalSuperior = new boolean[2*n-1];
        //SE LLENAN AMBOS ARREGLOS CON VALORES 'true'
        for (int i = 0; i<2*n-1;i++){
            this.diagonalInferior[i] = true;
            this.diagonalSuperior[i] = true;
        }
        haySolucion = false;
    }
 
    private void buscarSolucion(int fila){
        /*
        ESTE METODO REALIZA UNA BUSQUEDA DE LA POSICION EN LA QUE SE PUEDE COLOCAR 
        UNA REINA SIN SER AMENAZADA POR OTRA, EL CUAL REALIZA UN RECORRIDO FILA 
        Y COLUMNA DE LA SIGUIENTE MANERA:
        (0,0), (0,1), (0,2)... (0,8)
        (1,0), (1,1), (1,2)... (1,8)
        ...
        (8,0)...
        */
        int col = 0;
        //MIENTRAS COLUMNA SEA MENOR A 8 Y AUN NO SE ENCUENTRE UNA SOLUCION...
        while (col < n && !haySolucion){
            /*
            ESTE IF VERIFICA SI LA CELDA EN LA QUE SE COLOCARA LA PIEZA REINA
            NO SE ENCUENTRA AMENAZADA, ES DECIR SI UN VALOR DEL ARREGLO
            horizontal[fila] Y vertical[col] Y diagonalInferior[col-fila+n-1] Y
            diagonalSuperior[col+fila] SON VERDADEROS, QUIERE DECIR QUE ESA CELDA 
            DONDE SE COLOCARA LA PIEZA REINA NO SE ENCUENTRA AMENAZADA POR OTRA,
            DE MODO QUE PUEDE SER COLOCADA EN ESE LUGAR Y UNA VEZ COLOCADA SE ASIGNAN
            LAS FILAS (horizontal[fila]), COLUMNAS(vertical[col]) Y DIAGONALES (diagonalInferior y superior)
            QUE SERAN AMENAZADOS POR ESTA NUEVA PIEZA REINA EN EL TABLERO.
            */
            if (horizontal[fila] && vertical[col] && diagonalInferior[col-fila+n-1] && diagonalSuperior[col+fila]){
 
                solucion[fila] = col;
                horizontal[fila] = false;
                vertical[col] = false;
                diagonalInferior[col-fila+n-1] = false;
                diagonalSuperior[col+fila] = false;
 
                /*
                ESTE IF VERIFICA CUANDO SE LLEGA A LA FILA 7 Y SI LA SOLUCION
                QUE SE ENCONTRO DURANTE EL RECORRIDO NO SE ENCUENTRA REPETIDA
                ENTONCES CAMBIA EL VALOR DE LA VARIABLE haySolucion A 'true'
                PARA SALIR DEL CICLO while (col < n && !haySolucion)
                */
                if (fila == n-1 && solucionNueva(this.solucion)){
                    haySolucion = true;
                    
                }
                
                else{
                    /*
                    SI AUN NO SE LLEGA A LA FILA 7 ENTONCES ENTONCES SE INCREMENTA 
                    EL VALOR DE LA FILA EN 1 Y SE MANDA A LLAMAR A ESTE MISMO METODO 
                    DE FORMA RECURSIVA
                    */
                    if (fila+1 < n ){
                        buscarSolucion(fila+1); 
                    }
                    
                    /* !Backtracking!
                    SI YA SE LLEGO A LA FILA 7 PERO NO EXISTE SOLUCION
                    ENTONCES LA CELDA DONDE SE COLOCO LA PIEZA REINA
                    ES REMOVIDA DE MODO QUE CAMBIA DE NUEVO SUS VALORES A 'true'
                    QUITANDO LOS LUGARES DONDE LA PIEZA REINA GENERABA AMENAZA
                    
                    NOTA: MAS ADELANTE SE INCREMENTA LA VARIABLE col, QUE ES LA
                    COLUMNA, DE MODO QUE TRAS REMOVER LA PIEZA DE LA CELDA
                    POR EJEMPLO (1,1) ES COLOCADA EN LA SIGUIENTE CELDA "SOLO SI
                    SE CUMPLE LA CONDICION DE QUE LA CELDA ES SEGURA", SERIA COLOCADA
                    EN LA CELDA (1,2) O (1,3)
                    
                    */
                    if (!haySolucion){                 
                        solucion[fila] = -1;
                        horizontal[fila] = true;
                        vertical[col] = true;
                        diagonalInferior[col-fila+n-1] = true;
                        diagonalSuperior[col+fila] = true;
 
                    }
                }
            }
            col++;
        }
    }
 
    /*
    BUSCA LAS SOLUCIONES ENCONTRADAS Y LS AGREGA
    */
    public void buscarSoluciones(){
        boolean flag = true;
        while(flag){
            buscarSolucion(0);
            if (solucionNueva(solucion)){
                flag = true;
                agregarSolucion();
                 
            } else{
                flag = false;
            }
            inicializar();
        }
    }
    public void buscarUnaSolucion(){
        buscarSolucion(0);
        agregarSolucion();
    }
    
    //AGREGA LA SOLUCION ENCONTRADA AL ARREGLO soluciones[]
    private void agregarSolucion(){
        soluciones.add(this.solucion);  
    }
    
    //COMPARA SI LA SOLUCION QUE SE LE ENVIA NO ESTA REPETIDA PARA AGREGARLA
    //AL ARREGLO DE SOLUCIONES
    private boolean solucionNueva(int[] nuevaSolucion){
        if (nuevaSolucion[0] == -1) return false;
        boolean esNueva = true;
        int i = 0;
        while (i<soluciones.size() && esNueva){ 
            int[] unaSol = (int[]) soluciones.get(i);
            //METODO PARA COMPARAR SI SON IGUALES
            esNueva = !sonIguales(unaSol,nuevaSolucion);
            i++;
        }
 
        return esNueva;
    }
    
    
    private  boolean sonIguales (int[] a, int[] b){
        int i = 0;
        int j = 0;
        boolean flag = true;        
        while ((i<a.length) && (j<b.length)){
            if(a[i] != b[j]){
                return false;
            }
            i++;
            j++;            
        }
        return flag;
    }
    
    //DEVUELVE EL ARREGLO CON TODAS LAS SOLUCIONES ENCONTRADAS
    public ArrayList getSoluciones(){
        return this.soluciones;
    }
    
    public static void main(String[] args) {        
        ProblemaReinas reinas= new ProblemaReinas();
        /*
        PARA IMPRIMIR SOLO UNA SOLUCION SE UTILIZA
        buscarSolucion()
        */
        
        reinas.buscarUnaSolucion(); //<<--- IMPRIME UNA SOLA SOLUCION
        
        /*
        PARA IMPRIMIR TODAS LAS SOLUCIONES POSIBLES:
        buscarSoluciones()
        NOTA: DESCOMENTAR EL METODO:
        reinas.buscarSoluciones();
        Y COMENTAR EL METODO
        //reinas.buscarUnaSolucion();
        */
        
        //reinas.buscarSoluciones();    //<<<----- IMPRIMIR TODAS LAS SOLUCIONES
        
        ArrayList soluciones = reinas.getSoluciones();
        for (int i = 0; i<soluciones.size();i++){
            int[] aux  = (int[]) soluciones.get(i);
            System.out.println("Solucion " + (i+1) + ":");
            for (int j = 0; j<aux.length;j++){
                System.out.print("(" + (j+1) + "," + (aux[j]+1) + ")");
            }
            System.out.println("");
        }
 
    }
}
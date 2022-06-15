package TransporteApp;


/**
* TransporteApp/TransporteOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from transporte.idl
* martes 14 de junio de 2022 09:07:03 PM COT
*/

public interface TransporteOperations 
{
  void setDestino (String nombre, double precio, int disponible);
  int buscarDestinoInArray (String destino);
  String verDisponibilidad (String destino);
  double getPrecio (String destino);
  String hacerReservacion (String destino);
} // interface TransporteOperations

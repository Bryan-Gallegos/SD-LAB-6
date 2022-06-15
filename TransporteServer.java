import TransporteApp.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;
import java.util.*;

class TransporteTransport{
	public String nombreDestino;
	public double precioDestino;
	public int diponibilidadAsientos;
	
	public TransporteTransport(String __nombreDestino, double __precioDestino, int __diponibilidadAsientos) {
		this.nombreDestino=__nombreDestino;
		this.precioDestino=__precioDestino;
		this.diponibilidadAsientos=__diponibilidadAsientos;
	}
}

class TransporteImpl extends TransportePOA{
	public List<TransporteTransport> listaDestinos = new ArrayList<>();
	private ORB orb;
	public void setORB(ORB orb_val) {
		this.orb=orb_val;
	}
	
	public void setDestino(String __nombreDestino, double __precioDestino, int __diponibilidadAsientos) {
		listaDestinos.add(new TransporteTransport(__nombreDestino, __precioDestino, __diponibilidadAsientos));
	}
	
	public int buscarDestinoInArray(String destino) {
		int pos=0;
		for(TransporteTransport tt : listaDestinos) {
			if(tt.nombreDestino == destino) {
				return pos;
			}else {
				pos++;
			}
		}
		return 0;
	}
	
	public String verDisponibilidad(String destino) {
		TransporteTransport ref = listaDestinos.get(buscarDestinoInArray(destino));
		if(ref== null) {
			return "Destino no encontrado";
		}else {
			if(ref.diponibilidadAsientos>0)
				return "Quedan: "+String.valueOf(ref.diponibilidadAsientos)+" asientos disponibles";
			else
				return "No hay asientos disponibles";
		}
	}
	
	public double getPrecio(String destino) {
		TransporteTransport ref = listaDestinos.get(buscarDestinoInArray(destino));
		if(ref==null)
			return 0;
		else
			return ref.precioDestino;
	}
	
	public String hacerReservacion(String destino) {
		TransporteTransport ref = listaDestinos.get(buscarDestinoInArray(destino));
		if(ref==null)
			return "Destino no encontrado";
		else {
			if(ref.diponibilidadAsientos>0) {
				ref.diponibilidadAsientos--;
				return "Reservacion realizada con exito";
			}else {
				return "No se pudo realizar la Reservacion\tNo quedan asientos disponibles";
			}
		}
	}
}

public class TransporteServer {
	public static void main(String[]args) {
		try {
			ORB orb = ORB.init(args,null);
			
			POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			rootpoa.the_POAManager().activate();
			
			TransporteImpl transporteImpl = new TransporteImpl();
			transporteImpl.setORB(orb);
			
			org.omg.CORBA.Object ref = rootpoa.servant_to_reference(transporteImpl);
			Transporte href = TransporteHelper.narrow(ref);
			
			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
			
			String name="Transporte";
			NameComponent path[] = ncRef.to_name(name);
			ncRef.rebind(path,href);
			
			System.out.println("Servidor de Transporte listo y en espera");
			orb.run();
			
		}catch(Exception e) {
			System.out.println("Error: "+e);
			e.printStackTrace(System.out);
		}
	}
}

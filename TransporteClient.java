import TransporteApp.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;


public class TransporteClient {
	static Transporte transporteImpl;
	
	public static void main(String[]args) {
		try {
			ORB orb = ORB.init(args,null);
			
			org.omg.CORBA.Object object = orb.resolve_initial_references("NameService");
			NamingContextExt ncRef = NamingContextExtHelper.narrow(object);
			
			String name="Transporte";
			transporteImpl = TransporteHelper.narrow(ncRef.resolve_str(name));
			
			transporteImpl.setDestino("Arequipa",85.0,15);
			transporteImpl.setDestino("Lima",65.5,20);
			transporteImpl.setDestino("Ayacucho",55.0,25);
			transporteImpl.setDestino("Tacna",70.5,20);
			
			System.out.println("Obtencion de un handle sobre el objeto servidor: "+transporteImpl);
			System.out.println("Respuestas:\n");
			System.out.println(transporteImpl.verDisponibilidad("Arequipa"));
			System.out.println(transporteImpl.getPrecio("Arequipa"));
			System.out.println(transporteImpl.hacerReservacion("Arequipa"));
			
			System.out.println(transporteImpl.verDisponibilidad("Arequipa"));
			
			
		}catch(Exception e) {
			System.out.println("Error: "+e);
			e.printStackTrace(System.out);
		}
	}
}

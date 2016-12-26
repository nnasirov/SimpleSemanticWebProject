// Package 
package explorerowlexample;

//Imports
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nijat Nasirov 
 */
//Class OpenOWL
class OpenOWL {
   static  String  s;
   
   //Connection
     static  OntModel OpenConnectOWL(){
        
        OntModel mode = null;
    mode = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM_RULE_INF );
    java.io.InputStream in = FileManager.get().open( "C:\\Users\\nicatn\\Downloads\\ExplorerOWLExample_By_Ila\\ExplorerOWLExample\\MyProductOWL.owl" ); // be sure file into c:\
    if (in == null) {
        throw new IllegalArgumentException("Fichier ontology introuvable");
    }
        return  (OntModel) mode.read(in, "");
    }
    
     static  com.hp.hpl.jena.query.ResultSet ExecSparQl(String Query){
         
          com.hp.hpl.jena.query.Query query = QueryFactory.create(Query);
                QueryExecution qe = QueryExecutionFactory.create(query, OpenConnectOWL());
                com.hp.hpl.jena.query.ResultSet results = qe.execSelect();
                return results;
         
     }
     
     // Connection To Sparql EndPoint
      static  String GetResultAsString(String Query){
        try {
            com.hp.hpl.jena.query.Query query = QueryFactory.create(Query);
                  QueryExecution qe = QueryExecutionFactory.sparqlService("http://localhost:8890/sparql", query);
                  com.hp.hpl.jena.query.ResultSet results = qe.execSelect();
                  if(results.hasNext()){
                     ByteArrayOutputStream go = new ByteArrayOutputStream ();
                   ResultSetFormatter.out((OutputStream)go ,results, query);
                  
                       s = new String(go.toByteArray(), "UTF-8");
                   }
                  else{
                      
                      s = "There are no corrosponding data";
                  }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(OpenOWL.class.getName()).log(Level.SEVERE, null, ex);
        }
         return s;
      }
    
}
//End
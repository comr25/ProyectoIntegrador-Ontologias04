

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.mindswap.pellet.jena.PelletReasonerFactory;

public class OpenOntology 
{
    java.io.InputStream in = null;
    private OntModel model = null;
    static int ini = 0,res = 0;
    String Cad = new String();
    String namefile = new String();
       
    public String cargaOntologia(OntModel modelo)
    {
		if (modelo !=  null) 
                    modelo.close();
                
                model = ModelFactory.createOntologyModel( PelletReasonerFactory.THE_SPEC ); 
               
                LeerArchivo();                    
                
	        return Cad;
    }   
  
    public OntModel GetOntModel()
    {
        return model;
    }  
  
    public String GetNameFile()
    {
        return namefile;
    }  
    
    
    private void LeerArchivo()
    {
        String aux = null;               
        JFileChooser a = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("n3, Turtle, RDF, and OWL Languages","n3","turtle","rdf","OWL");
        a.setFileSelectionMode(JFileChooser.FILES_ONLY);
        a.setFileFilter(filter);                                     
        a.setDialogTitle("Choose an Ontology"); // a.addChoosableFileFilter(filter);                
        res = a.showOpenDialog(a);         
        if ( res == JFileChooser.APPROVE_OPTION){
             namefile = a.getSelectedFile().getAbsolutePath();
             in = FileManager.get().open( a.getSelectedFile().getAbsolutePath() );		
             if (in == null) {
                throw new IllegalArgumentException("Ontology not found!");
             }
             Cad = getExtension( a.getSelectedFile() );
             JOptionPane.showMessageDialog(null," It was a success! \n "+a.getSelectedFile().getName(),"Ontology loaded!",JOptionPane.WARNING_MESSAGE,new ImageIcon(getClass().getResource("Tasks.png")));              
             if ( Cad.compareTo("n3") == 0 )
               model.read(in, "", "TTL"); // Se leen tripletas en n3
             else    
               model.read(in, "", "");   // Se leen ontologias en OWL      
             Cad = a.getSelectedFile().getName();     
        }       
    }

    private static String getExtension( File f)
    {
        String ext = null;
        String cad = f.getName();
        int i = cad.lastIndexOf('.');
        if (i > 0 && i < cad.length() - 1 ){ 
            ext = cad.substring(i+1).toLowerCase();
        }
        return ext;
    }   
}
  
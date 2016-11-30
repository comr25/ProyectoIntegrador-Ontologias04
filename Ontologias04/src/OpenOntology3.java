/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;


import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;
import org.apache.jena.graph.Graph;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.mindswap.pellet.jena.PelletReasonerFactory;
import org.openjena.riot.system.IRIResolver;
import org.apache.jena.rdf.model.RDFWriter;



import javax.swing.JFileChooser;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;


/**
 *
 * @author administrador
 */
public class OpenOntology3 {
    java.io.InputStream in = null;
    private OntModel model = null;
    static int ini = 0,res = 0;
    String Cad = new String();
    String namefile = new String();
       
    public String cargaOntologia(OntModel modelo)
    {
		if (modelo !=  null) 
                    modelo.close();
                
                model = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM ); 
               
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
    
    private void LeerArchivo()
    {
        String aux = null;               
        JFileChooser a = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("N3, JSON","N3","JSON");
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
             
             if ( Cad.compareTo("n3") == 0 ){
                 System.out.println("n3");
                 RDFDataMgr.read(model, in, Lang.N3);}
                // model.read(in, "", "TTL"); // Se leen tripletas en n3
             else if(Cad.compareTo("json") == 0 ){
                 System.out.println("json");
                 RDFDataMgr.read(model, in, Lang.RDFJSON);}
                 
               //model.read(in, "", "");   // Se leen ontologias en OWL      
             Cad = a.getSelectedFile().getName();     
        }       
    }  
}

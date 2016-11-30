import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.util.iterator.ExtendedIterator;

import java.awt.Font;
import java.util.Iterator;
import java.awt.Frame;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.WindowConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
//import javax.swing.tree.TreeCellRenderer;

public class ShowClasses {
    private File in = null;
    private OntModel model = null;
    static int ini = 0,res = 0;
    static String Cad = null;
    JTree tree;
    DefaultTreeModel modelo;
    DefaultMutableTreeNode abuelo;
    static Frame f;
     
    public void cargaOntologia(OntModel modelo, String Name){
                model = modelo;  
	        CreaRaizArbol();	
                MuestraClases(Name);                      
     }       
    
    private void CreaRaizArbol(){ // Construccion del arbol
        abuelo        = new DefaultMutableTreeNode("thing",true);
        modelo        = new DefaultTreeModel(abuelo);
        tree          = new JTree(modelo);
             
          // Cambiamos los iconos
        DefaultTreeCellRenderer render= (DefaultTreeCellRenderer)tree.getCellRenderer();
        render.setLeafIcon(new ImageIcon(getClass().getResource("class.gif")));
        render.setOpenIcon(new ImageIcon(getClass().getResource("class.gif")));
        render.setClosedIcon(new ImageIcon(getClass().getResource("class.gif")));
       
        tree.setRootVisible(true);
        tree.setShowsRootHandles(true); 
        tree.setVisibleRowCount(15);   
    }
    
    private void MuestraClases(String name){
       int indx = 0, indh;
       
        if( model == null)
            JOptionPane.showMessageDialog(null,"You have to Open an Ontology!","México",JOptionPane.WARNING_MESSAGE,new ImageIcon(this.getClass().getResource("upload.png")));
        else
        {                       
            ExtendedIterator iteratorClasses = model.listHierarchyRootClasses();        
	    while ( iteratorClasses.hasNext() ){
                    System.out.println("Aqui estoy");
		    OntClass ontClass = (OntClass) iteratorClasses.next();     
                    if( ontClass.getLocalName() != null && !"Thing".equals(ontClass.getLocalName())   ){       
                            Cad = ontClass.getLocalName(); 
                            DefaultMutableTreeNode padre = new DefaultMutableTreeNode(Cad);                   
                            modelo.insertNodeInto(padre, abuelo, indx++);     
                           
                        String URI = ontClass.getURI();
                        indh = 0;    
                        if (ontClass.hasSubClass()) {                         
                             OntClass subclass = model.getOntClass(URI);
                             for (Iterator i = subclass.listSubClasses(); i.hasNext();) {
                                OntClass c = (OntClass) i.next();
                                Cad = c.getLocalName(); 
                                if( Cad != null ){
                                    DefaultMutableTreeNode hijo = new DefaultMutableTreeNode(Cad);
                                    modelo.insertNodeInto(hijo, padre, indh++);  
                                }    
                             }  
                        }                                      
                  }   
          }
          tree.expandPath(new TreePath(abuelo)); // Se muestra la raiz con sus hijos, se expande el arbol         
          
          if(tree.getRowCount()>1)
              tree.expandRow(1);
          // Construccion y visualizacion de la ventana     
          
                JLabel jUserName = new JLabel("Demo How to Set JLabel font size");
                jUserName.setFont(new Font("Serif", Font.BOLD, 11));
                
                JFrame v = new JFrame("Classes of:"+" "+name);
              
                v.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("ontology.gif")));   
                JScrollPane scroll = new JScrollPane(tree);
                v.getContentPane().add(scroll);
                v.setBounds(20, 170, 60, 160); //Ubicar el frame en la ventana
                v.pack();          
                v.setLocationRelativeTo(null);
                v.setSize(300, 400);
                v.setVisible(true);
                v.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
                
       }
    }
}
  
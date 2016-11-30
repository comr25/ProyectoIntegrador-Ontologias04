import org.apache.jena.ontology.OntModel;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.jena.ontology.DatatypeProperty;

import java.awt.Toolkit;

import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.WindowConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
//import javax.swing.tree.TreeCellRenderer;

public class ShowDataProperties {
    private File in = null;
    private OntModel model = null;
    static int ini = 0,res = 0;
    static String Cad = null;
    JTree tree;
    DefaultTreeModel modelo;
    DefaultMutableTreeNode abuelo;
    
    public void LoadDataProperties(OntModel modelo, String name){                
		model = modelo;                                              
	        CreaRaizArbol();	
                MuestraDataProperties(name);                                   
     }   
    
    private void CreaRaizArbol(){ // Construccion del arbol
        abuelo        = new DefaultMutableTreeNode("thing",true);
        modelo        = new DefaultTreeModel(abuelo);
        tree          = new JTree(modelo);
             
          // Cambiamos los iconos
        DefaultTreeCellRenderer render= (DefaultTreeCellRenderer)tree.getCellRenderer();
        render.setLeafIcon(new ImageIcon(this.getClass().getResource("dataproperties.gif")));
        render.setOpenIcon(new ImageIcon(this.getClass().getResource("dataproperties.gif")));
        render.setClosedIcon(new ImageIcon(this.getClass().getResource("dataproperties.gif")));
       
        tree.setRootVisible(true);
        tree.setShowsRootHandles(true);
        tree.setVisibleRowCount(15);   
    }
    
    private void MuestraDataProperties(String name){
        int indx = 0, indh;
                                
       // Se muestra todas las Data Properties
        if( model == null)
             JOptionPane.showMessageDialog(null,"You have to Open an Ontology!","México",JOptionPane.WARNING_MESSAGE,new ImageIcon(this.getClass().getResource("upload.png")));
        else
        {
          ExtendedIterator iteratorData = model.listDatatypeProperties();                 
          while ( iteratorData.hasNext() ){		                                               
                    DatatypeProperty ontClass = (DatatypeProperty) iteratorData.next();                     
                    if( ontClass.getLocalName() != null && ontClass.isDatatypeProperty()  ){
                        Cad = ontClass.getLocalName();
                        DefaultMutableTreeNode padre = new DefaultMutableTreeNode(Cad);
                        modelo.insertNodeInto(padre, abuelo, indx++);     
                    }
                }                
                     
               tree.expandPath(new TreePath(abuelo)); // Se muestra la raiz con sus hijos, se expande el arbol
            // Construccion y visualizacion de la ventana
                JFrame v = new JFrame("Datatype properties of:"+" "+name);
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
  